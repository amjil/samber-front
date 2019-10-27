(ns app.components.checkbox
  (:require [app.state :refer [app-state]]
            [reagent.core :as r]))

(defn view []
  (let [items [{:key 1 :label "Checkbox a"} {:key 2 :label "Checkbox b"} {:key 3 :label "Checkbox c"}]
        checked (r/atom #{1 2})
        checked-class "van-checkbox__icon--checked"
        on-click (fn [item e]
                   (if (contains? @checked (:key item))
                     (reset! checked (disj @checked (:key item)))
                     (reset! checked (conj @checked (:key item)))))]
    (fn []
      [:div {:style {:padding "1rem"}}; :background "#fff"}}
       [:h2.checkbox-title "Checkbox Group"]
       [:div.van-checkbox-group {:style {:margin-top "35px"}}
        (doall
          (for [item items]
            ^{:key (str "item-" (:key item))}
             [:div.van-checkbox {:on-click #(on-click item %)}
              [:div.van-checkbox__icon.van-checkbox__icon--round {:class (if (contains? @checked (:key item)) checked-class "")}
               [:i.van-icon.van-icon-success]]
              [:span.van-checkbox__label (:label item)]]))]
       ; [:h2 (clojure.string/join "," (map (fn [x] (:label x)) (filter #(contains? @checked (:key %)) items)))]
       [:h2 (clojure.string/join "," @checked)]])))
