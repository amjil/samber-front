(ns app.views
  (:require [app.state :refer [app-state]]
            [reagent.core :as r]
            [app.router :as router]
            [app.components.tab :as tab]
            [app.components.action-sheet :as action-sheet]
            [app.components.collapse :as collapse]
            [app.components.tabbar :as tabbar]
            [app.components.cell :as cell]
            [app.components.button :as button]
            [app.components.card :as card]
            [app.components.overlay :as overlay]
            [app.components.dropdown :as dropdown]
            [app.components.popup :as popup]
            [app.components.dialog :as dialog]
            [app.components.swipe-cell :as swipe-cell]
            [app.components.divider :as divider]
            [app.components.panel :as panel]
            [app.components.progress :as progress]
            [app.components.skeleton :as skeleton]
            [app.components.picker :as picker]
            [app.components.radio :as radio]
            [app.components.checkbox :as checkbox]
            [app.components.tag :as tag]
            [app.components.toast :as toast]
            [app.components.quill :as quill]))

(defn app []
  (fn []
    (let [page (:handler (:page @app-state))
          page (if (nil? page) :home page)]
      [:div {:style {:display "flex" :flex-direction "row"}}
                     ; :overflow "hidden important!"
                     ; :width "100vw"}}
       [:div {:style {:height "calc(100vh - 50px)"
                      :overflow "hidden"
                      :width "100vw"}}
        ; ;; font: inherit;  !!!!
        ; [:div
        ;  {:style {:writing-mode "horizontal-tb"}}
        ;  [:em
        ;   {:style {:writing-mode "horizontal-tb"}}
        ;   "aa"]
        ;  [:strong
        ;   {:style {:writing-mode "horizontal-tb"}}
        ;   "aa"]]]

        [quill/editor
         {:id "my-quill-editor-component-id"
          :content "welcome to reagent-quill!"
          :selection nil
          :on-change-fn #(if (= % "user")
                           (println (str "text changed: " %2)))}]]
        ; (let [data [["ᠬᠠᠯᠠᠮᠰᠢᠯ" 1 "content of tab 1"] ["ᠰᠢᠨ᠎ᠡ" 2 "content of tab 2"]]]
        ;   [tab/tabs-line {:tabs data}])]])))
       [tabbar/view]])))
