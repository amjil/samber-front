(ns app.components.collapse
  (:require [app.state :refer [app-state]]
            [reagent.core :as r]))


; [collapse "Title1" "Content"]
(defn view [title content]
  (let [is-visiable (r/atom false)]
    (fn []
      [:div.van-collapse.van-hairline--top-bottom
       [:div.van-collapse-item
        [:div.van-cell.van-cell--clickable.van-collapse-item__title
         {:class (if @is-visiable "van-collapse-item__title--expanded")
          :on-click (fn [e] (reset! is-visiable (if @is-visiable false true)))}
         [:div.van-cell__title
          [:span title]]
         [:i.van-icon.van-icon-arrow.van-cell__right-icon]]
        [:div.van-collapse-item__wrapper
         {:style (if-not @is-visiable {:height "0px" :display "none"})}
         [:div.van-collapse-item__content content]]]])))
