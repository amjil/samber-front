(ns app.views
  (:require [app.state :refer [app-state]]
            [reagent.core :as r]
            [app.router :as router]
            [re-frame.core :refer [subscribe dispatch]]
            [app.components.quill :as quill]
            [app.components.tabbar :as tabbar]
            [app.components.home :as home]))

(defn pages [page-name]
  (case page-name
    :home             [home/index]
    :quill            [quill/editor
                       {:id "my-quill-editor-component-id"
                        :content "welcome to reagent-quill!"
                        :selection nil
                        :on-change-fn #(if (= % "user")
                                         (println (str "text changed: " %2)))}]
    [home/index]))

(defn app []
  (fn []
    (let [active-page @(subscribe [:active-page])]
      [:div {:style {:display "flex" :flex-direction "row"}}
       [:div {:style {:height "calc(100vh - 50px)"
                      :overflow "hidden"
                      :width "100vw"}}
        [pages active-page]]

       [tabbar/view]])))
