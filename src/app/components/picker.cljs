(ns app.components.picker
  (:require [app.state :refer [app-state]]
            [reagent.core :as r]))

(defn view []
  (let [drag-start (r/atom 0)
        left (r/atom 0)
        distance (r/atom 0)
        dragged (r/atom false)]
    (fn []
      (let [transform (str "translate3d(" @left "px , 0px, 0px)")
            transition-property (if @dragged "all" "none")
            transition-duration (if @dragged "200ms" "0ms")
            touch-start (fn [e]
                          (let [moved-x (.-clientX (aget (.-touches e) 0))]
                            (reset! dragged true)
                            (reset! drag-start moved-x)
                            (reset! distance @left)))
            touch-move (fn [e]
                         (let [moved-x (.-clientX (aget (.-touches e) 0))
                               distancex (if-not (zero? @distance)
                                           (+ @distance (- moved-x @drag-start))
                                           (- moved-x @drag-start))]
                           (reset! left distancex)))
            touch-end (fn [e]
                        (reset! dragged false)
                        (let [transx (- @left @distance)
                              trans (if (neg? transx) (- transx) transx)
                              trans-items (quot (- @left @distance) 44)
                              trans-items (+ trans-items (quot (rem transx 44) 22))]
                          (if (zero? trans-items)
                            (reset! left @distance)
                            (reset! left (+ @distance (* 44 trans-items))))))]
         [:div.van-picker
          [:div.van-picker__columns {:style {:width "220px"}}
           [:div.van-picker-column
            [:ul.van-picker-column__wrapper {:on-touch-start touch-start
                                             :on-touch-move touch-move
                                             :on-touch-end touch-end
                                             :style {:transform transform :transition-duration transition-duration :transition-property transition-property :line-height "44px"}}
             [:li.van-ellipsis.van-picker-column__item {:style {:width "44px"}} "Delaware"]
             [:li.van-ellipsis.van-picker-column__item {:style {:width "44px"}} "Florida"]
             [:li.van-ellipsis.van-picker-column__item.van-picker-column__item--selected {:style {:width "44px"}} "Georqia"]
             [:li.van-ellipsis.van-picker-column__item {:style {:width "44px"}} "Indiana"]
             [:li.van-ellipsis.van-picker-column__item {:style {:width "44px"}} "Maine"]]]
           [:div.van-picker__mask {:style {:background-size "88px 100%"}}]
           [:div.van-hairline--top-bottom.van-picker__frame {:style {:width "44px"}}]]]))))
