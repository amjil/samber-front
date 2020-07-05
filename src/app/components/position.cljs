(ns app.components.position
  (:require
    ["quill" :as Quill]))


(defn index [el quill type]
  (let [selection-range (.getSelection @quill)
        index (if (= 1 type)
                (.-index selection-range)
                (+ (.-index selection-range) (.-length selection-range)))
        [blot offset] (.getLeaf @quill index)
        flag (and (= 3 (.-nodeType (.-domNode blot))) (= offset (.-length (.-domNode blot))))
        index (if flag (- index 1) index)
        offset (if flag 1 0)
        ;; editor bound
        bound (.getBounds @quill index offset)

        left-index (+ (.-scrollLeft (.-parentNode el)) (.-left bound))
        top-index (if flag (.-bottom bound) (.-top bound))]
    (js/console.log ",<<,,,," left-index top-index)
    [left-index top-index]))

(defn index2 [quill index]
  (let [[blot offset] (.getLeaf @quill index)
        flag (and (= 3 (.-nodeType (.-domNode blot))) (= offset (.-length (.-domNode blot))))
        index (if flag (- index 1) index)
        offset (if flag 1 0)
        bound (.getBounds @quill index offset)
        ;; editor wrapper
        left-index (.-left bound)
        top-index (if flag (.-bottom bound) (.-top bound))]
    (js/console.log ",<<,,,," left-index top-index)
    [left-index top-index]))

;;
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
          my-editor (js/document.querySelector "quill-wrapper-my-editor-id")
          range-index
          (if (= (.-left bound) 0)
            [nil nil]
            (let [bound (.getBoundingClientRect cloned-range)
                  left-index (-> (.-left bound)
                                 (- (.-offsetLeft my-editor)))
                                 ; (+ (.-scrollLeft my-editor)))
                  y (if (> (- (.-endOffset range) 1) 0)
                      (.-bottom bound)
                      (.-top bound))]
              [left-index (- y (.-offsetTop my-editor))]))]
      (.remove shadow-caret)
      (js/console.log "range position" range-index)
      range-index)))

;;
;; range position
(defn range-position [el quill type]
  (let [id (if (= 1 type) "#selection-start-div" "#selection-end-div")
        range-el (.querySelector el id)]
    (if (= "none" (aget (.-style range-el) "display"))
      (aset (.-style range-el) "display" "block"))
    (let [[left-index top-index] (index el quill type)]
      (aset (.-style range-el) "left" (str left-index "px"))
      (aset (.-style range-el) "top" (str top-index "px"))
      (js/console.log "in index ...... "))))

;; context menu position
(defn context-menu-position [el quill]
  (let [menu-el (.querySelector (.-parentNode el) "#context-menu")
        [left-index top] (index el quill 1)
        parent-rect (.getBoundingClientRect el)
        left-index (-> left-index
                       (+ (.-left parent-rect))
                       (- 20))
        left-index (if (< left-index 10)
                     10
                     left-index)]
    (aset (.-style menu-el) "display" "flex")
    (aset (.-style menu-el) "left" (str left-index "px"))))
