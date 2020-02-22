(ns app.components.caret
  (:require [reagent.core :as r]
            [re-frame.core :refer [subscribe]]
            ["quill" :as Quill])
  (:import [goog.async Debouncer]))

(declare index selection-caret
         selection-caret-hide selection-caret-show
         set-position)

(defn create-caret [quill el hide-selection]
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
                         (let [selection-index (+ (.-endOffset range) (.getIndex @quill (.find Quill (.-endContainer range))))]
                           (js/console.log "<<<<<<<<<<" selection-index)
                           (set-position quill (.-parentNode (.-target e)) selection-index range)))))
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
    (aset (.-style dot-span) "display" "none")
    (.appendChild div dot-span)
    (set! (.-id div) "caret-position-div")
    (aset (.-style div) "display" "none")
    (.appendChild el div)))

(defn get-native-position [el range]
  (let [cloned-range (.cloneRange range)
        shadow-caret (js/document.createTextNode "|")]
    ; (.insertNode cloned-range shadow-caret)
    ; (.selectNode cloned-range shadow-caret)
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

(defn get-position [quill el index range]
  (js/console.log "get-position" (.getSelection @quill))
  (js/console.log (.-startOffset range))
  ; (let [[left top] (get-native-position el range)]
  ;   [left top]))
  (let [
        [blot offset] (.getLeaf @quill index)
        flag (and (= 3 (.-nodeType (.-domNode blot))) (= offset (.-length (.-domNode blot))))
        index (if flag (- index 1) index)
        bound (.getBounds @quill index 1)
        _ (js/console.log "bound" bound)
        left-index (.-left bound)
        top-index (if flag (.-bottom bound) (.-top bound))
        [left top]
        (if (<= top-index 0)
          (let [[left top] (get-native-position el range)]
            [left top])
          [left-index top-index])]
    [left top]))

(defn set-position [quill el index range]
  (let [[left top] (get-position quill el index range)]
    (doseq [[k v] {"top" (str top "px")
                   "left" (str left "px")}]
      (aset (.-style el) k v))
    (let [caret-display (aget (.-style el) "display")]
      (if (and caret-display (= "none" caret-display))
        (aset (.-style el) "display" "block")))))

(defn set-range [quill selection-index]
  (let [el (js/document.getElementById "caret-position-div")
        [blot offset] (.getLeaf @quill selection-index)
        range (js/document.createRange)]
    (js/console.log "<<<< set-range " selection-index)
    (.setEnd range (.-domNode blot) offset)
    (.setStart range (.-domNode blot) offset)
    (set-position quill el selection-index range)))

(defn selection-caret-hide []
  (js/console.log "selection caret hide .....")
  (let [caret-div (js/document.getElementById "caret-position-div")
        dot-span (.querySelector caret-div ".dot")]
    (aset (.-style dot-span) "display" "none")))

(defn selection-caret-show []
  (let [caret-div (js/document.getElementById "caret-position-div")
        dot-span (.querySelector caret-div ".dot")]
    (aset (.-style dot-span) "display" "inline-block")))
