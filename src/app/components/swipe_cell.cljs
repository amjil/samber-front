(ns app.components.swipe-cell
  (:require [app.state :refer [app-state]]
            [reagent.core :as r]))

(defn view []
  (let [drag-start (r/atom 0)
        left (r/atom 0)
        dragged (r/atom false)
        location (r/atom 0)
        drag-end (r/atom 0)]
    (fn []
      (let [transform (str "translate3d(0px, " @left "px, 0px)")
            transition (if @dragged "none 0s ease 0s" "all 0.6s cubic-bezier(0.18, 0.89, 0.32, 1) 0s")]
        [:div {:style {:padding "1rem"}}
         [:div.van-swipe-cell {:on-touch-start (fn [e]
                                                (let [moved-x (.-clientY (aget (.-touches e) 0))]
                                                  (reset! dragged true)
                                                  (reset! drag-start moved-x)))
                               :on-touch-move (fn [e]
                                                (let [moved-x (.-clientY (aget (.-touches e) 0))
                                                      distance (if (zero? @location) (- moved-x @drag-start) (+ @left (- moved-x @drag-start)))
                                                      this (.-currentTarget e)
                                                      bottom-node (.querySelector this ".van-swipe-cell__right")
                                                      bottom-height (.-offsetHeight bottom-node)
                                                      top-node (.querySelector this ".van-swipe-cell__left")
                                                      top-height (.-offsetHeight top-node)
                                                      distancey (if (neg? distance)
                                                                  (max distance (- bottom-height))
                                                                  (min distance top-height))]
                                                  (reset! left distancey)
                                                  (reset! drag-end moved-x)))
                               :on-touch-end (fn [e]
                                               (let [this (.-currentTarget e)
                                                     bottom-node (.querySelector this ".van-swipe-cell__right")
                                                     bottom-height (.-offsetHeight bottom-node)
                                                     top-node (.querySelector this ".van-swipe-cell__left")
                                                     top-height (.-offsetHeight top-node)
                                                     distance (- @drag-end @drag-start)]
                                                 (reset! dragged false)
                                                 (if (neg? distance)
                                                   (do
                                                     (reset! left (- bottom-height))
                                                     (reset! location -1))
                                                   (do
                                                     (reset! left top-height)
                                                     (reset! location 1)))))

                               :on-click (fn [e]
                                           (when (not (zero? @location))
                                             (reset! left 0)
                                             (reset! location 0)))}

          [:div.van-swipe-cell__wrapper {:style {:transform transform :transition transition}}
           [:div.van-swipe-cell__left
            [:span.van-button.van-button--primary.van-button--normal.van-button--square
             [:span.van-button__text "Select"]]]
           [:div.van-cell.van-cell--borderless
            [:div.van-cell__title
             [:span "Cell"]]
            [:div.van-cell__value
             [:span "Content"]]]
           [:div.van-swipe-cell__right
            [:span.van-button.van-button--danger.van-button--normal.van-button--square
             [:span.van-button__text "Delete"]]
            [:span.van-button.van-button--primary.van-button--normal.van-button--square
             [:span.van-button__text "Collect"]]]]]]))))
