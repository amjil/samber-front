(ns app.components.tabbar
  (:require [app.state :refer [app-state]]
            [reagent.core :as r]))

(defn view []
  (let [data [{:id 1 :name "" :icon "home-o" :on-click #(js/console.log "item 1")}
              {:id 2 :name "" :icon "search" :on-click #(js/console.log "item 2")}
              {:id 3 :name "" :icon "friends-o" :on-click #(js/console.log "item 3")}
              {:id 4 :name "" :icon "setting-o" :on-click #(js/console.log "item 4")}]
        active-index (r/atom 1)]
    (fn []
      [:div.van-hairline--top-bottom.van-tabbar.van-tabbar--fixed
       {:style {:width "100vw"
                :overflow "hidden"}}
       (doall
         (for [item data]
           ^{:key (str "item-" (:id item))}
           [:div.van-tabbar-item {:class (if (= @active-index (:id item)) "van-tabbar-item--active" "")
                                  :on-click (fn [e] (reset! active-index (:id item)))}
            [:div.van-tabbar-item__icon
             [:i.van-icon {:class (str "van-icon-" (:icon item))}]]
            [:div.van-tabbar-item__text (:name item)]]))])))
