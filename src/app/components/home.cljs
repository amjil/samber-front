(ns app.components.home
  (:require
            [app.components.quill :as quill-view]
            [re-frame.core :refer [subscribe dispatch]]
            [app.components.tabbar :as tabbar]
            [app.components.navbar :as navbar]
            [app.components.hammer :as hammer]))


(defn index []
  (fn []
    [:div {:style {:display "flex" :flex-direction "column"}}
      [navbar/index]
      [hammer/index
       [:div {:style {;:height "calc(100vh - 86px)"
                               :height "100%"
                               :margin "46px 0 40px 0"
                               :overflow "hidden"
                               :width "100vw"}}
        [:div.van-cell-group
         {:style {:border "thin dotted #FF0000"}}
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
           [:span "Cell Title"]]]]]]
      [tabbar/view]]))
