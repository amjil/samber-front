(ns app.components.popup
  (:require [app.state :refer [app-state]]
            [reagent.core :as r]))

(defn view [content]
  (let [
        visiable (r/atom false)
        animating (r/atom false)
        display (r/track #(if @visiable "block" (if @animating "block" "none")))
        animation1 (r/track #(if @animating (if @visiable "animated fadeIn" "animated fadeOut") ""))
        animation2 (r/track #(if @animating (if @visiable "animated fadeInLeft" "animated fadeOutLeft") ""))]
    (fn []
      (let [on-button-click (fn [e]
                              (reset! visiable true)
                              (reset! animating true))
            on-overlay-click (fn [e] (reset! visiable false)
                               (reset! animating true))
            on-animation-end (fn [e] (reset! animating false))]
        [:div {:style {:padding "1rem"}}
         [:span.van-button.van-button--primary.van-button--normal
          {:on-click on-button-click}
          "From Left"]
         [:div
          [:div.van-overlay
           {:on-click on-overlay-click
            :on-animation-end on-animation-end
            :class @animation1
            :style {:z-index "2214" :display @display}}]
          [:div.van-popup {:class @animation2
                           ; :on-animation-end #(reset! animation2 "")
                           :style {:top "0" :left "0" :width "70%" :height "100%" :z-index "2215"
                                   :display @display}}
            content]]]))))
