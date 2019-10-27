(ns app.components.skeleton
  (:require [app.state :refer [app-state]]
            [reagent.core :as r]))

(defn view []
  [:div ;{:style {:padding "1rem"}}; :background "#fff"}}
   [:div.van-skeleton.van-skeleton--animate
    [:div.van-skeleton__avatar.van-skeleton__avatar--round {:style {:width "32px" :height "32px"}}]
    [:div.van-skeleton__content
     [:h3.van-skeleton__title {:style {:height "40%"}}]
     [:div.van-skeleton__row {:style {:height "100%"}}]
     [:div.van-skeleton__row {:style {:height "100%"}}]
     [:div.van-skeleton__row {:style {:height "60%"}}]]]
   [:div.van-skeleton.van-skeleton--animate
    [:div.van-skeleton__avatar.van-skeleton__avatar--round {:style {:width "32px" :height "32px"}}]
    [:div.van-skeleton__content
     [:h3.van-skeleton__title {:style {:height "40%"}}]]]])
