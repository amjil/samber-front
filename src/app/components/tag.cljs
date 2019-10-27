(ns app.components.tag
  (:require [app.state :refer [app-state]]
            [reagent.core :as r]))

(defn view []
  [:div {:style {:padding "1rem"}}; :background "#fff"}}
   [:div.demo-tag
    [:h2 {:style {:margin "16px 8px 0 0"}} "Basic Usage"]
    [:div.van-tag.van-tag--default "Tag"]
    [:div.van-tag.van-tag--danger "Tag"]
    [:div.van-tag.van-tag--primary "Tag"]
    [:div.van-tag.van-tag--success "Tag"]]
   [:div.demo-tag
    [:h2 {:style {:margin "16px 8px 0 0"}} "Plain style"]
    [:div.van-tag.van-tag--plain.van-tag--default.van-hairline--surround "Tag"]
    [:div.van-tag.van-tag--plain.van-tag--round.van-tag--danger.van-hairline--surround "Tag"]
    [:div.van-tag.van-tag--plain.van-tag--round.van-tag--primary.van-hairline--surround "Tag"]
    [:div.van-tag.van-tag--plain.van-tag--success.van-hairline--surround "Tag"]]
   [:div.demo-tag
    [:h2 {:style {:margin "16px 8px 0 0"}} "Mark style"]
    [:div.van-tag.van-tag--mark.van-tag--default "Tag"]
    [:div.van-tag.van-tag--mark.van-tag--danger "Tag"]
    [:div.van-tag.van-tag--mark.van-tag--primary "Tag"]
    [:div.van-tag.van-tag--mark.van-tag--success "Tag"]]
   [:div.demo-tag
    [:h2 {:style {:margin "16px 8px 0 0"}} "Custom Color"]
    [:div.van-tag.van-tag--plain.van-tag--default.van-hairline--surround {:style {:color "#fff" :background-color "rgb(242, 130, 106)"}} "Tag"]
    [:div.van-tag.van-tag--plain.van-tag--default.van-hairline--surround {:style {:color "rgb(242, 130, 106)"}} "Tag"]
    [:div.van-tag.van-tag--default {:style {:background-color "rgb(114, 50, 221)"}} "Tag"]
    [:div.van-tag.van-tag--plain.van-tag--default.van-hairline--surround {:style {:color "rgb(114, 50, 221)"}} "Tag"]
    [:div.van-tag.van-tag--default {:style {:background-color "rgb(255, 225, 225)" :color "rgb(173, 0, 0)"}} "Tag"]]])
