(ns app.components.caret)

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
        (doseq [[k v] {"top" (str (- y (.-offsetTop my-editor)) "px")
                       "left" (str left-index "px")}]
          (aset (.-style div) k v))))

    (.remove shadow-caret)

    (when-not caret-div
      (set! (.-id div) "caret-position-div")
      (let [quill-wrapper (js/document.getElementById "quill-editor-my-editor-id")]
        (.appendChild quill-wrapper div)))
    (.detach cloned-range)))
