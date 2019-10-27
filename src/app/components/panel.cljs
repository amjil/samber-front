(ns app.components.panel
  (:require [app.state :refer [app-state]]
            [reagent.core :as r]))

(defn view []
  [:div {:style {:padding "1rem"}}; :background "#fff"}}
   [:div.van-cell-group.van-hairline--top-bottom.van-panel
    [:div.van-cell.van-panel__header
     [:div.van-cell__title
      [:span "Title"]
      [:div.van-cell__label "Description"]]
     [:div.van-cell__value.van-panel__header-value
      [:span "Status"]]]
    [:div.van-panel__content {:style {:padding "20px"}}
     [:div "Content"]]
    [:div.van-panel__footer.van-hairline--top
     [:span.van-button.van-button--default.van-button--small
      [:span.van-button__text "Button"]]
     [:span.van-button.van-button--danger.van-button--small {:style {:margin-top "5px"}}
      [:span.van-button__text "Button"]]]]])
