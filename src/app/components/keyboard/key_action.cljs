(ns app.components.keyboard.key-action
  (:require
    [app.components.caret :as caret]))

(defn on-delete [quill]
  (let [range (.getSelection @quill)]
    (.deleteText @quill (.-index range) (.-length range))
    (.setSelection @quill (.-index range) 0)))
