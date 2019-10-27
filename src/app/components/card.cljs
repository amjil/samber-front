(ns app.components.card
  (:require [app.state :refer [app-state]]
            [reagent.core :as r]))

(defn view []
  [:div {:style {:padding "1rem"}}
   [:div.van-card
    [:div.van-card__header
     [:a.van-card__thumb
      [:div.van-image {:style {:width "100%" :height "100%"}}
       [:img.van-image__img {:src "https://img.yzcdn.cn/vant/t-thirt.jpg"}]]]
     [:div.van-card__content
      [:div.van-card__title "Title"]
      [:div.van-card__desc "Description"]
      [:div.van-card__bottom
       [:div.van-card__price "¥ 2.00"]
       [:div.van-card__num "x 2"]]]]
    [:div.van-card__footer
     [:div
      [:span.van-button.van-button--default.van-button--mini.van-button--round "Button"]
      [:span.van-button.van-button--default.van-button--mini.van-button--round "Button"]]]]])
