(ns app.components.home
  (:require
            [app.components.quill :as quill-view]
            [re-frame.core :refer [subscribe dispatch]]))


(defn index []
  (fn []
    [:div.van-cell-group
     [:div.van-cell.van-cell--clickable
      [:div.van-cell__title
       [:div.van-cell__label
        [:span "amjil"]]
       [:span "Cell Title ......"]]
      [:div.van-cell__value
       [:div.van-cell__label
        [:span "2020.01.02"]]]]
     [:div.van-cell.van-cell--clickable
      [:div.van-cell__title
       [:span "Cell Title"]]]]))
