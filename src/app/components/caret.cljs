(ns app.components.caret
  (:require [reagent.core :as r]
            [re-frame.core :refer [subscribe]])
  (:import [goog.async Debouncer]))

(declare selection-caret)

(defn index [range]
  (let [caret-div (js/document.getElementById "caret-position-div")
        div (if caret-div caret-div (js/document.createElement "div"))
        cloned-range (.cloneRange range)
        shadow-caret (js/document.createTextNode "|")
        my-editor (js/document.getElementById "quill-editor-my-editor-id")]
    (if (> (- (.-endOffset range) 1) 0)
      (do
        (.setStart cloned-range (.-endContainer range) (- (.-endOffset range) 1))
        (.setEnd cloned-range (.-endContainer range) (.-endOffset range)))
      (do
        (.insertNode cloned-range shadow-caret)
        (.selectNode cloned-range shadow-caret)))
    (let [bound (.getBoundingClientRect cloned-range)]
      ;; Sometimes when wrapping get zero bound
      (when (= (.-left bound) 0)
        (.insertNode cloned-range shadow-caret)
        (.selectNode cloned-range shadow-caret))
      (let [bound (.getBoundingClientRect cloned-range)
            left-index (-> (.-left bound)
                           (- (.-offsetLeft my-editor))
                           (+ (.-scrollLeft my-editor)))
            y (if (> (- (.-endOffset range) 1) 0)
                (.-bottom bound)
                (.-top bound))]
        (js/console.log "<<<<<<<<<" left-index "-" y)
        (doseq [[k v] {"top" (str (- y (.-offsetTop my-editor)) "px")
                       "left" (str left-index "px")}]
          (aset (.-style div) k v))

        (.remove shadow-caret)

        (when-not caret-div
          (set! (.-id div) "caret-position-div")
          (let [quill-wrapper (js/document.getElementById "quill-editor-my-editor-id")]
            (.appendChild quill-wrapper div)))
        (.detach cloned-range)))))

(defn selection-caret-hide []
  (let [caret-div (js/document.getElementById "caret-position-div")
        dot-span (.querySelector caret-div ".dot")]
    (aset (.-style dot-span) "display" "none")))

(defn selection-caret-show []
  (let [caret-div (js/document.getElementById "caret-position-div")
        dot-span (.querySelector caret-div ".dot")]
    (aset (.-style dot-span) "display" "inline-block")))

(defn selection-caret [hide-selection]
  (let [caret-div (js/document.getElementById "caret-position-div")
        dot-span (.querySelector caret-div ".dot")
        total (r/atom 0)
        current-range (r/atom nil)]
    (when (and caret-div (not dot-span))
      (let [dot-span (js/document.createElement "span")
            touch-start (fn [e]
                          (.preventDefault e)
                          (js/console.log "touch-start ....")
                          (.fire hide-selection))
            touch-move (fn [e]
                         (let [moved (aget (.-touches e) 0)
                               moved-x (- (.-clientX moved) 14)
                               moved-y (.-clientY moved)
                               range (js/document.caretRangeFromPoint moved-x moved-y)
                               dot-span (.querySelector caret-div ".dot")]
                           (swap! total + 1)
                           (if (= "none" (aget (.-style dot-span) "display"))
                             (selection-caret-show))
                           (.fire hide-selection)
                           (when-not (or
                                       (= "caret-position-div" (.-id (.-endContainer range)))
                                       (= "caret-position-div" (.-id (.-parentNode (.-endContainer range)))))
                             (index range)
                             (reset! current-range range))))
            touch-end (fn [e]
                        (js/console.log "touch-end ...." @total)
                        (reset! total 0)
                        (when @current-range
                          (.collapse (js/window.getSelection) (.-endContainer @current-range) (.-endOffset @current-range))
                          (reset! current-range nil))
                        (.fire hide-selection))]

        (.addEventListener dot-span "touchstart" touch-start)
        (.addEventListener dot-span "touchmove" touch-move)
        (.addEventListener dot-span "touchend" touch-end)
        (.addEventListener dot-span "scroll"
          (fn [e]
            (js/console.log "scroll ....")))

        (.add (.-classList dot-span) "dot")
        (.appendChild caret-div dot-span)))))
