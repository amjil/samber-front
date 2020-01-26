(ns app.components.caret)

(defn index [range]
  (let [caret-div (js/document.getElementById "caret-position-div")
        div (if caret-div caret-div (js/document.createElement "div"))
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
            y (if (> (- (.-endOffset range) 1) 0)
                (.-bottom bound)
                (.-top bound))]
        (doseq [[k v] {"top" (str y "px")
                       "left" (str (.-left bound) "px")}]
          (aset (.-style div) k v))))

    (.remove shadow-caret)

    (when-not caret-div
      (set! (.-id div) "caret-position-div")
      (let [quill-wrapper (js/document.getElementById "app")]
        (.appendChild quill-wrapper div)))
    (.detach cloned-range)))
