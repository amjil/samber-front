(ns app.components.toast
  (:require [app.state :refer [app-state]]
            [reagent.core :as r]))

(defn view []
  (let [is-visiable (r/atom false)
        animating (r/atom false)
        display (r/track #(if @is-visiable "block" "none"))]
    (fn []
      (let [on-button-click (fn [e]
                              (reset! is-visiable (if @is-visiable false true))
                              (js/setTimeout #(reset! is-visiable false) 2000))]
        [:div {:style {:padding "1rem"}}
         [:span.van-button.van-button--primary.van-button--normal
          {:on-click on-button-click}
          "Toast"]
         [:div.van-toast.van-toast--middle {:style {:display @display}}
          [:i.van-icon.van-icon-success.van-toast__icon]
          [:div.van-toast__text "Success"]]]))))
