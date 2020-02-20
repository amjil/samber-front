(ns app.views
  (:require [app.state :refer [app-state]]
            [reagent.core :as r]
            [app.router :as router]
            [re-frame.core :refer [subscribe dispatch]]
            [app.components.quill :as quill]
            [app.components.home :as home]
            [app.components.login :as login]
            [app.components.editor :as editor]
            [app.components.hammer :as hammer]
            [app.components.keyboard.keyboard :as keyboard]))

(defn pages [page-name]
  (case page-name
    :home             [home/index]
    :login            [login/index]
    :quill            [quill/editor
                       {:id "my-quill-editor-component-id"
                        :content "welcome to reagent-quill!"
                        :selection nil
                        :on-change-fn #(if (= % "user")
                                         (println (str "text changed: " %2)))}]
    :editor           [editor/index]
    [editor/index]))
    ; [keyboard/index]))

(defn app []
  (fn []
    (let [active-page @(subscribe [:active-page])]
      (js/console.log "active-page = " active-page)
      [pages active-page])))
