(ns app.components.navbar
  (:require [reagent.core :as r]
            [re-frame.core :refer [subscribe dispatch]]))

(defn index []
  (fn []
    (let [active-page @(subscribe [:active-page])
          back-button-visible? (some #{active-page} [:editor])]
      [:div.van-hairline--top-bottom.van-nav-bar--fixed.van-nav-bar
       {:style {:width "100vw"
                :overflow "hidden"}}
       (if back-button-visible?
         [:div.van-nav-bar__left
          {:style {:height "100%"}}
          [:i.van-icon.van-icon-arrow-left.van-nav-bar__arrow]])
       [:div.van-nav-bar__title.van-ellipsis {:style {:height "100%"}} "sambar"]])))
