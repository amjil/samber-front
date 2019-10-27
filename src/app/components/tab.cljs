(ns app.components.tab
  (:require [app.state :refer [app-state]]
            [reagent.core :as r]))

; [["Tab 1" 1 "content of tab 1"] ["Tab 2" 2 "content of tab 2"] ["Tab 3" 3 "content of tab 3"] ["Tab 4" 4 "content of tab 4"]]

(defn transition-left [el]
  (+ (.-offsetTop el) (/ (.-offsetHeight el) 2)))

(defn tabs-line [options]
  (let [data (:tabs options)
        curindex (r/atom 1)
        left (r/atom 0)
        item-height (r/atom 0)
        component-fn
        (fn [e]
          (let [el (r/dom-node e) el (.-firstChild el) el (.-firstChild el) el (.-firstChild el)]
            (reset! left (transition-left el))
            (reset! item-height (/ (.-offsetHeight el) 2))))]
    (r/create-class
      {:reagent-render
       (fn []
         [:div.van-tabs.van-tabs--line
          [:div.van-tabs__wrap.van-hairline--top-bottom
           [:div.van-tabs__nav.van-tabs__nav--line
            (doall
              (for [item data]
                ^{:key (str "item-" (second item))}
                [:div.van-tab {:class (if (= @curindex (second item)) "van-tab--active" "")
                               :on-click (fn [e]
                                           (let [el (.-currentTarget e)]
                                             (reset! curindex (second item))
                                             (reset! left (transition-left el))
                                             (reset! item-height (/ (.-offsetHeight el) 2))))}
                 [:span.van-ellipsis (first item)]]))
            [:div.van-tabs__line {:style {"height" @item-height :transform (str "translateY(" @left "px) translateY(-50%)") :transition-duration "0.3s"}}]]]
          (doall
            (for [item data]
              ^{:key (str "item-" (second item))}
              [:div.van-tabs__content {:style {:display (if-not (= @curindex (second item)) "none")}}
               [:div.van-tab__pane (last item)]]))])
       :component-did-mount component-fn})))
