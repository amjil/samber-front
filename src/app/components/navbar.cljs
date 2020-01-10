(ns app.components.navbar
  (:require [reagent.core :as r]))

(defn index []
  (fn []
    [:div.van-hairline--top-bottom.van-nav-bar--fixed.van-nav-bar
     {:style {:width "100vw"
              :overflow "hidden"}}
     [:div.van-nav-bar__left
      {:style {:height "100%"}}
      [:i.van-icon.van-icon-arrow-left.van-nav-bar__arrow]]
     [:div.van-nav-bar__title.van-ellipsis {:style {:height "100%"}} "amjil"]]))
