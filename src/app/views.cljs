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
            [app.components.keyboard.keyboard :as keyboard]
            [app.components.article.list :as article-list]
            [app.components.article.new :as article-new]
            [app.components.article.show :as article-show]
            [app.components.article.search :as article-search]))

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
    :article-new      [article-new/index]
    :article-show     [article-show/index]
    :article-list     [article-list/index]
    :article-search   [article-search/index]
    ; [editor/index]))
    [article-list/index]))

(defn app []
  (fn []
    (let [active-page @(subscribe [:active-page])]
      (js/console.log "active-page = " active-page)
      [pages active-page])))
