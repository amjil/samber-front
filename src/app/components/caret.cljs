(ns app.components.caret
  (:require [reagent.core :as r]
            [re-frame.core :refer [subscribe]])
  (:import [goog.async Debouncer]))

(declare index selection-caret
         selection-caret-hide selection-caret-show
         set-position)

(defn create-caret [el hide-selection]
  (let [div (js/document.createElement "div")
        dot-span (js/document.createElement "span")
        current-range (r/atom nil)
        touch-start (fn [e]
                      (.preventDefault e)
                      (js/console.log "touch-start ....")
                      (.fire hide-selection))
        touch-move (fn [e]
                     (let [moved (aget (.-touches e) 0)
                           moved-x (- (.-clientX moved) 14)
                           moved-y (.-clientY moved)
                           range (js/document.caretRangeFromPoint moved-x moved-y)]
                       (.fire hide-selection)
                       (when-not (or
                                   (= "caret-position-div" (.-id (.-endContainer range)))
                                   (= "caret-position-div" (.-id (.-parentNode (.-endContainer range)))))
                         (reset! current-range range)
                         (set-position (.-parentNode (.-target e)) range))))
                         ; (index range))))
        touch-end (fn [e]
                    (js/console.log "touch-end ....")
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
    (.appendChild div dot-span)
    (set! (.-id div) "caret-position-div")
    (.appendChild el div)))

(defn get-position [el range]
  (let [cloned-range (.cloneRange range)
        shadow-caret (js/document.createTextNode "|")]
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
            parent-el (.-parentNode el)
            left-index (-> (.-left bound)
                           (- (.-offsetLeft parent-el))
                           (+ (.-scrollLeft (.-parentNode (.-parentNode parent-el)))))
            y (if (> (- (.-endOffset range) 1) 0)
                (.-bottom bound)
                (.-top bound))]
        (.remove shadow-caret)
        (.detach cloned-range)
        [left-index (- y (.-offsetTop parent-el))]))))

(defn set-position [el range]
  (let [[left top] (get-position el range)]
    (doseq [[k v] {"top" (str top "px")
                   "left" (str left "px")}]
      (aset (.-style el) k v))))

(defn selection-caret-hide []
  (let [caret-div (js/document.getElementById "caret-position-div")
        dot-span (.querySelector caret-div ".dot")]
    (aset (.-style dot-span) "display" "none")))

(defn selection-caret-show []
  (let [caret-div (js/document.getElementById "caret-position-div")
        dot-span (.querySelector caret-div ".dot")]
    (aset (.-style dot-span) "display" "inline-block")))
