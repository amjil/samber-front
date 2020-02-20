(ns app.components.range-selection
  (:require
    ["quill" :as Quill]
    [clojure.string :as str]
    [app.components.caret :as caret]
    [reagent.core :as r]))

(declare index)

(defn position [range]
  (let [cloned-range (.cloneRange range)
        shadow-caret (js/document.createTextNode "|")]
    (if (> (- (.-endOffset range) 1) 0)
      (do
        (.setStart cloned-range (.-endContainer range) (- (.-endOffset range) 1))
        (.setEnd cloned-range (.-endContainer range) (.-endOffset range)))
      (do
        (.insertNode cloned-range shadow-caret)
        (.selectNode cloned-range shadow-caret)))
    (let [bound (.getBoundingClientRect cloned-range)
          range-index
          (if (= (.-left bound) 0)
            [nil nil]
            (let [bound (.getBoundingClientRect cloned-range)
                  left-index (.-left bound)
                  y (if (> (- (.-endOffset range) 1) 0)
                      (.-bottom bound)
                      (.-top bound))]
              [left-index y]))]
      (.remove shadow-caret)
      range-index)))

(defn set-range [quill el selection-index]
  (let [[blot offset] (.getLeaf @quill selection-index)
        range (js/document.createRange)]
    (.setEnd range (.-domNode blot) offset)
    (.setStart range (.-domNode blot) offset)
    (caret/set-position el range)))

(defn set-quill-selection [quill el type selection-index q-range range]
  (let [quill-range (.getSelection @quill)]
    (if (= type 1)
      (if (>= selection-index (+ (.-index @q-range) (.-length @q-range)))
        (let [select-text (.getText @quill selection-index 30)
              text-list (str/split select-text #"\s")
              sel-end (if (or (str/starts-with? select-text " ") (empty? (first text-list)))
                          1
                          (count (first (str/split select-text #"\s"))))]
          (.setSelection @quill selection-index sel-end)
          (set-range quill (.querySelector (.-parentNode el) "#selection-end-div") (+ selection-index sel-end)))
        (do
          (.setSelection @quill selection-index (- (+ (.-index quill-range) (.-length quill-range)) selection-index))
          ; bug fix ( @q-range has failed use quill-range to set error range and reset use @q-range)
          (if (not= (.-length quill-range) (.-length @q-range))
            (.setSelection @quill selection-index (- (+ (.-index @q-range) (.-length @q-range)) selection-index)))
          (caret/set-position el range)))
      (if (<= selection-index (.-index quill-range))
        (let [start-index (max (- selection-index 30) 0)
              select-text (.getText @quill start-index (- selection-index start-index))
              sel-start (if (some #{(last select-text)} [" " "\n" "\r"])
                          1
                          (count (last (str/split select-text #"\s"))))]
          (.setSelection @quill (- selection-index sel-start) sel-start)
          (set-range quill (.querySelector (.-parentNode el) "#selection-start-div") (- selection-index sel-start)))
        (do
          (.setSelection @quill (.-index quill-range) (- selection-index (.-index quill-range)))
          (caret/set-position el range))))))


(defn move-border [quill el q-range e type]
  (let [moved (aget (.-touches e) 0)
        moved-x (+ (.-clientX moved) (- 0 14))
        moved-y (.-clientY moved)
        range (js/document.caretRangeFromPoint moved-x moved-y)
        start-el (.-startContainer range)
        start-offset (.-startOffset range)]
    (if (nil? (.find Quill start-el))
      (js/console.log "sorry 11!!!" (.getSelection @quill))
      (let [
            selection-index (+ start-offset (.getIndex @quill (.find Quill start-el)))
            [left top] (position range)
            ;;;

            range-el (.-parentNode (.-target e))
            parent-el (.-parentNode range-el)
            left-index (-> left
                         (+ (.-scrollLeft (.-parentNode parent-el)))
                         (- (.-offsetLeft parent-el)))
            top-index (- top (.-offsetTop parent-el))
            range (js/document.caretRangeFromPoint moved-x moved-y)]
            ;;;
            ; quill-range (.getSelection @quill)]
            ; quill-range @q-range]
            ;;
        ; (caret/set-position (.-target e) range)
        ; (aset (.-style range-el) "left" (str left-index "px"))
        ; (aset (.-style range-el) "top" (str top-index "px"))
        (js/console.log "<<<<<<<<<<" selection-index "-" start-offset)
        ; (js/console.log quill-range)
        (js/console.log @q-range)
        ;;
        ; (if (= type 1)
        ;   (do
        ;     (.setSelection @quill selection-index (- (+ (.-index quill-range) (.-length quill-range)) selection-index))
        ;     (if (not= (.-length quill-range) (.-length @q-range))
        ;       (.setSelection @quill selection-index (- (+ (.-index @q-range) (.-length @q-range)) selection-index))))
        ;   (.setSelection @quill (.-index quill-range) (- selection-index (.-index quill-range))))
        (let [range (js/document.caretRangeFromPoint moved-x moved-y)]
          (set-quill-selection quill range-el type selection-index q-range range))
        (reset! q-range (.getSelection @quill))))
    ;;
    (js/console.log "move caret .....")))

(defn create-range [quill el type]
  (let [start-id (if (= 1 type) "selection-start-div" "selection-end-div")
        quill-range (r/atom nil)
        div (js/document.createElement "div")
        ;;
        span-class "dot"
        span (js/document.createElement "span")
        touch-start (fn [e]
                      (.preventDefault e)
                      (reset! quill-range (.getSelection @quill))
                      (js/console.log "touch-start ...."))
        touch-move (fn [e] (move-border quill el quill-range e type))
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
