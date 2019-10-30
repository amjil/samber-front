(ns app.components.home
  (:require
            [app.components.quill :as quill-view]
            [re-frame.core :refer [subscribe dispatch]]))


(defn index []
  (fn []
    [quill-view/editor
                       {:id "my-quill-editor-component-id"
                        :content "welcome to reagent-quill!"
                        :selection nil
                        :on-change-fn #(if (= % "user")
                                         (do
                                           (println (str "text changed: " %2))
                                           (let [quill @(subscribe [:quill])]
                                             (js/console.log "xxxxxx")
                                             (js/console.log (.getSelection quill)))))}]))
