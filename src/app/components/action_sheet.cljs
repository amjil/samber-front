(ns app.components.action-sheet
  (:require [app.state :refer [app-state]]
            [reagent.core :as r]))

(defn action-sheet []
  (let [is-visiable (r/atom false)
        is-animation-end (r/atom true)
        display (r/track #(if @is-visiable "block" (if @is-animation-end "none" "block")))
        animation1 (r/track #(if @is-animation-end "" (if @is-visiable "animated fadeIn" "animated fadeOut")))
        animation2 (r/track #(if @is-animation-end "" (if @is-visiable "animated fadeInLeft" "animated fadeOutLeft")))]
    (fn []
      (let [on-click (fn [e]
                       (reset! is-animation-end false)
                       (reset! is-visiable (if @is-visiable false true)))
            on-click-overlay (fn [e]
                               (reset! is-visiable false)
                               (reset! is-animation-end false))
            on-animation-end (fn [e] (reset! is-animation-end true))]
        [:div {:style {:padding "1rem"}}
         [:span.van-button.van-button--primary.van-button--normal {:on-click on-click} "Action Sheet"]
         [:div
          [:div.van-overlay {:style {:z-index "2214" :display @display}
                             :class @animation1
                             :on-click on-click-overlay
                             :on-animation-end on-animation-end}]
          [:div.van-popup.van-popup--left.van-action-sheet.van-action-sheet--safe-area-inset-bottom
           {:style {:height "100%" :z-index "2215" :display @display}
            :class @animation2}
           [:div.van-action-sheet__item.van-hairline--right
            [:div.van-action-sheet__name "Option"]]
           [:div.van-action-sheet__item.van-hairline--right
            [:div.van-action-sheet__name "Option"]]
           [:div.van-action-sheet__item.van-hairline--right
            [:div.van-action-sheet__name "Option"]
            [:div.van-action-sheet__subname "Description"]]
           [:div.van-action-sheet__cancel
            {:on-click on-click-overlay}
            "Cancel"]]]]))))
