(ns app.components.range-selection
  (:require
    ["quill" :as Quill]))

(defn position [range]
  (let [
        cloned-range (.cloneRange range)
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
            left-index (.-left bound)
            y (if (> (- (.-endOffset range) 1) 0)
                (.-bottom bound)
                (.-top bound))]
        (.remove shadow-caret)
        [left-index y]))))

(defn move-border [quill e type]
  (let [moved (aget (.-touches e) 0)
        moved-x (+ (.-clientX moved) (if (= 1 type) 14 (- 14)))
        moved-y (.-clientY moved)
        range (js/document.caretRangeFromPoint moved-x moved-y)]
    (if-not (or
              (= "selection-start-div" (.-id (.-endContainer range)))
              (= "selection-end-div" (.-id (.-endContainer range)))
              (= "dot" (.-className (.-endContainer range))))
      (let [range-el (.-parentNode (.-target e))
            cloned-range (.cloneRange range)
            [left top] (position range)
            parent-el (.-parentNode range-el)
            left-index (-> left
                         (- (.-scrollLeft parent-el))
                         (- (.-offsetLeft parent-el)))
            top-index (- top (.-offsetTop parent-el))
            ;;
            quill-range (.getSelection @quill)
            _ (js/console.log cloned-range)
            selection-index
              (+ (.-startOffset cloned-range) (.getIndex @quill (.find Quill (.-startContainer cloned-range))))]
        (aset (.-style range-el) "left" (str left-index "px"))
        (aset (.-style range-el) "top" (str top-index "px"))
        ;;
        (if (= type 1)
          (.setSelection @quill selection-index (- (+ (.-index quill-range) (.-length quill-range)) selection-index))
          (.setSelection @quill (.-index quill-range) (- selection-index (.-index quill-range)))))
      (js/console.log "move border nilll......"))
    ;;
    (js/console.log "move caret .....")))

(defn create-range [quill el type]
  (let [start-id (if (= 1 type) "selection-start-div" "selection-end-div")
        div (js/document.createElement "div")
        ;;
        span-class "dot"
        span (js/document.createElement "span")
        touch-start (fn [e]
                      (.preventDefault e)
                      (js/console.log "touch-start ...."))
        touch-move (fn [e] (move-border quill e type))
        touch-end (fn [e] (js/console.log "touch-end ...."))
        ;;
        shadow-caret (js/document.createTextNode "|")]
    ;;
    (.addEventListener span "touchstart" touch-start)
    (.addEventListener span "touchmove" touch-move)
    (.addEventListener span "touchend" touch-end)
    (.add (.-classList span) span-class)
    (.appendChild div span)
    ;;
    (set! (.-id div) start-id)
    (aset (.-style div) "display" "none")
    (.appendChild el div)))

(defn hide [el]
  (let [my-editor (.-parentNode el)
        el-start (.querySelector my-editor "#selection-start-div")
        el-end (.querySelector my-editor "#selection-end-div")]
    (if-not (= "none" (aget (.-style el-start) "display"))
      (aset (.-style el-start) "display" "none"))
    (if-not (= "none" (aget (.-style el-end) "display"))
      (aset (.-style el-end) "display" "none"))))

(defn index [el quill index type]
  (let [id (if (= 1 type) "#selection-start-div" "#selection-end-div")
        range-el (.querySelector (.-parentNode el) id)]
    (if (= "none" (aget (.-style range-el) "display"))
      (aset (.-style range-el) "display" "block"))
    (let [[blot offset] (.getLeaf @quill index)
          flag (and (= 3 (.-nodeType (.-domNode blot))) (= offset (.-length (.-domNode blot))))
          _ (js/console.log " == " flag " == " (.-length (.-domNode blot)) " -- " offset)
          index (if flag (- index 1) index)
          offset (if flag 1 0)
          bound (.getBounds @quill index offset)
          left-index (+ (.-scrollLeft (.-parentNode el)) (.-left bound))
          top-index (if flag (.-bottom bound) (.-top bound))]
      (js/console.log left-index " --- " top-index)
      (aset (.-style range-el) "left" (str left-index "px"))
      (aset (.-style range-el) "top" (str top-index "px")))))
