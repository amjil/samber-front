(ns app.components.position
  (:require
    ["quill" :as Quill]))

; (defn index [])
;
(defn index [quill index]
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
