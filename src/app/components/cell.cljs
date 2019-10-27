(ns app.components.cell
  (:require [app.state :refer [app-state]]
            [reagent.core :as r]))


(defn view []
  (fn []
    [:div {:style {:padding "1rem"}}
     [:div.van-cell-group
      [:div.van-cell
       [:div.van-cell__title
        [:span "Cell Title"]]
       [:div.van-cell__value
        [:span "Content"]]]
      [:div.van-cell
       [:div.van-cell__title
        [:span "Cell Title"]
        [:div.van-cell__label
         [:span "Description"]]]
       [:div.van-cell__value
        [:span "Content"]]]]
     [:div.van-cell-group {:style {:margin-left "1rem"}}
      [:div.van-cell
       [:div.van-cell__value.van-cell__value--alone
        [:span "Content"]]]]
     [:div.van-cell-group {:style {:margin-left "1rem"}}
      [:div.van-cell.van-cell--clickable
       [:div.van-cell__title
        [:span "Cell Title"]]]]]))
