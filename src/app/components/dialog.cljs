(ns app.components.dialog
  (:require [app.state :refer [app-state]]
            [reagent.core :as r]))

(defn view []
  (let [display (r/atom "none")
        is-visiable (r/atom false)
        animation1 (r/atom "")
        animation2 (r/atom "")]
    (fn []
      (let [on-click (fn [e]
                       (if @is-visiable
                         (do
                           (reset! is-visiable false)
                           (reset! animation1 "animated fadeOut")
                           (reset! animation2 "animated fadeOut"))
                         (do
                           (reset! is-visiable true)
                           (reset! display "block")
                           (reset! animation1 "animated fadeIn")
                           (reset! animation2 "animated fadeIn"))))
            on-click-overlay (fn [e]
                               (reset! is-visiable false)
                               (reset! animation1 "animated fadeOut")
                               (reset! animation2 "animated fadeOut"))
            on-animation-end (fn [e]
                               (reset! animation1 "")
                               (reset! animation2 "")
                               (if-not @is-visiable
                                 (do
                                   (reset! display "none"))))]
        [:div {:style {:padding "1rem"}}
         [:span.van-button.van-button--primary.van-button--normal
          {:on-click on-click}
          "Confirm Dialog"]
         [:div
          [:div.van-overlay {:style {:z-index "2214" :display @display}
                             :class @animation1
                             :on-click on-click-overlay
                             :on-animation-end on-animation-end}]
          [:div.van-dialog
           {:style {:z-index "2215" :display @display}
            :class @animation2}
           [:div.van-dialog__header "Title"]
           [:div.van-dialog__content
            [:div.van-dialog__message.van-dialog__message--has-title "Content"]]
           [:div.van-hairline--top.van-dialog__footer.van-dialog__footer--buttons
            [:span.van-button.van-button--default.van-button--large.van-dialog__cancel
             {:on-click on-click-overlay}
             [:span.van-button__text "Cancel"]]
            [:span.van-button.van-button--default.van-button--large.van-dialog__confirm.van-hairline--left
             [:span.van-button__text "Confirm"]]]]]]))))
