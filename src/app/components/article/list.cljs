(ns app.components.article.list
  (:require
    [app.components.navbar :as navbar]
    [app.components.tabbar :as tabbar]))

(defn index []
  (fn []
    [:div {:style {:display "flex" :flex-direction "column"
                   :width "100%"}}
     [navbar/index]
     [:div {:style {:margin "46px 0 40px 0"}}
      [:div.van-cell-group
       [:div.van-cell.van-cell--clickable
        [:div.van-cell__title
         [:span "Cell Title"]]]
       [:div.van-cell.van-cell--clickable
        [:div.van-cell__title
         [:span "Cell Title"]]]
       [:div.van-cell.van-cell--clickable
        [:div.van-cell__title
         [:span "Cell Title"]]]]]
     [tabbar/view]]))
