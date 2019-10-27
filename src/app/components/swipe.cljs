(ns app.components.swipe
  (:require [app.state :refer [app-state]]
            [reagent.core :as r]))

(defn view []
  (let [index (r/atom 1)
        translatex (r/atom 0)
        width (.-width js/window.screen)
        startx (r/atom 0)
        currentx (r/atom 0)
        is-swipping? (r/atom false)]
    (fn []
      (let [on-touch-start (fn [x]
                             (let [clientx (.-clientX (aget (.-touches x) 0))]
                               (reset! startx clientx)
                               (js/console.log "start " clientx)
                               (reset! is-swipping? true)))
            on-touch-move (fn [x]
                            (let [this (.-currentTarget x)
                                  items-num (.-length (.-children this))
                                  moved-x (.-clientX (aget (.-touches x) 0))
                                  translate (- moved-x @startx)
                                  first-child (aget (.-children this) 0)
                                  last-child (aget (.-children this) (- items-num 1))]
                              (reset! currentx moved-x)
                              (js/console.log "move " moved-x)))
                              ; (aset (.-style first-child) "transform" (str "translateX(" 0 "px)"))
                              ; (aset (.-style last-child) "transform" (str "translateX(" 0 "px)"))
                              ; (aset (.-style this) "transform" (str "translateX(" translate "px)"))))
            on-touch-end (fn [x]
                            (let [this (.-currentTarget x)
                                  items-num (.-length (.-children this))
                                  translate (- @currentx @startx)
                                  first-child (aget (.-children this) 0)
                                  last-child (aget (.-children this) (- items-num 1))]
                              (reset! is-swipping? false)
                              (if-not (zero? translate)
                                (if (neg? translate)
                                  (reset! translatex (+ @translatex (- width)))))
                              (js/console.log "end " @currentx)))]

         [:div {:style {:height "150px"}}
          [:div.van-swipe
           [:div.van-swipe__track {:style {:width (str (* width 4) "px") :transition-duration (if (true? @is-swipping?) "0ms" "500ms") :transform (str "translateX(" @translatex "px)")}
                                   :on-touch-start on-touch-start
                                   :on-touch-move on-touch-move
                                   :on-touch-end on-touch-end}
            [:div.van-swipe-item {:style {:width (str width "px") :height "100%" :transform "translateX(0px)"}} "1"]
            [:div.van-swipe-item {:style {:width (str width "px") :height "100%" :transform "translateX(0px)"}} "2"]
            [:div.van-swipe-item {:style {:width (str width "px") :height "100%" :transform "translateX(0px)"}} "3"]
            [:div.van-swipe-item {:style {:width (str width "px") :height "100%" :transform "translateX(0px)"}} "4"]]]]))))
