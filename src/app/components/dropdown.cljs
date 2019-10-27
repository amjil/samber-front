(ns app.components.dropdown
  (:require [app.state :refer [app-state]]
            [reagent.core :as r]))

(defn transition-left [el]
  (+ (.-offsetLeft el) (.-offsetWidth (.-firstChild el))))

(defn view []
  (let [active-color "rgb(25, 137, 250)"
        color (r/atom active-color)
        display (r/atom "none")
        is-visiable (r/atom false)
        animation1 (r/atom "")
        animation2 (r/atom "")
        left (r/atom 0)
        component-fn
        (fn [e]
          (let [el (r/dom-node e)
                el (.-firstChild el)] ;el (.-firstChild el)]
            (js/console.log el)
            (js/console.log (.-offsetLeft el))
            (js/console.log (.-offsetWidth (.-firstChild el)))
            (reset! left (transition-left el))))]
    (r/create-class
      {:component-did-mount component-fn
       :reagent-render
       (fn []
         (let [on-click (fn [e]
                          (if @is-visiable
                            (do
                              (reset! is-visiable false)
                              (reset! animation1 "animated fadeOut")
                              (reset! animation2 "animated fadeOutLeft"))
                            (do
                              (reset! is-visiable true)
                              (reset! display "block")
                              (reset! animation1 "animated fadeIn")
                              (reset! animation2 "animated fadeInLeft"))))
               on-click-overlay (fn [e]
                                  (reset! is-visiable false)
                                  (reset! animation1 "animated fadeOut")
                                  (reset! animation2 "animated fadeOutLeft"))
               on-animation-end (fn [e]
                                  (reset! animation1 "")
                                  (reset! animation2 "")
                                  (if-not @is-visiable
                                    (do
                                      (reset! display "none"))))]

           [:div ;{:style {:padding "1rem"}}
            [:div.van-dropdown-menu.van-hairline--top-bottom
             [:div.van-dropdown-menu__item {:role "button" :tabIndex "0"
                                            :on-click on-click}
              [:span.van-dropdown-menu__title {:class (if @is-visiable "van-dropdown-menu__title--down") :style {:color (if @is-visiable @color)}}
               [:div.van-ellipsis "OptionA"]]]
             ; [:div.van-dropdown-menu__item {:role "button" :tabIndex "0"}
             ;  [:span.van-dropdown-menu__title
             ;   [:div.van-ellipsis "OptionB"]]]
             [:div.van-dropdown-item.van-dropdown-item--down
              {:style {:z-index "10" :top "1rem" :bottom "1rem" :left (str @left "px")}}
              [:div.van-overlay {:style {:z-index "2212" :position "absolute" :display @display}
                                 :class @animation1
                                 :on-animation-end on-animation-end
                                 :on-click on-click-overlay}]
              [:div.van-popup.van-popup--top.van-dropdown-item__content
               {:style {:z-index "2217" :position "absolute"
                        :display @display}
                :class @animation2}
               [:div.van-cell.van-cell--clickable.van-dropdown-item__option {:style {:color @color}}
                [:div.van-cell__title "Option1"]
                [:div.van-cell__value
                 [:i.van-icon.van-icon-success.van-dropdown-item__icon {:style {:color @color}}]]]
               [:div.van-cell.van-cell--clickable.van-dropdown-item__option
                [:div.van-cell__title "Option2"]]
               [:div.van-cell.van-cell--clickable.van-dropdown-item__option
                [:div.van-cell__title "Option3"]]]]]]))})))
