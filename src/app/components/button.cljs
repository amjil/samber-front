(ns app.components.button
  (:require [app.state :refer [app-state]]
            [reagent.core :as r]))

(defn view []
  [:div {:style {:display "table-cell"}}
   [:div {:style {:display "flex"}}
    [:span.van-button.van-button--normal.van-button--primary  "primary"]
    [:span.van-button.van-button--normal.van-button--default "normal"]]
   [:div {:style {:display "flex" :margin-left "1rem"}}
    [:span.van-button.van-button--normal.van-button--info "info"]
    [:span.van-button.van-button--normal.van-button--danger "danger"]
    [:span.van-button.van-button--normal.van-button--warning "warning"]]
   [:div {:style {:display "flex" :margin-left "1rem"}}
    [:span.van-button.van-button--normal.van-button--primary.van-button--plain "info"]
    [:span.van-button.van-button--normal.van-button--danger.van-button--plain "danger"]]])
