(ns app.components.overlay
  (:require [app.state :refer [app-state]]
            [reagent.core :as r]))

(defn view []
  (let [is-visible (r/atom true)]
    (fn []
      [:div {:style {:padding "1rem"}}
       [:div.van-overlay {:style {:z-index "1" :display (if @is-visible "block" "none")}
                          :on-click #(reset! is-visible (if @is-visible false true))}]])))
