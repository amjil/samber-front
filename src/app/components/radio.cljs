(ns app.components.radio
  (:require [app.state :refer [app-state]]
            [reagent.core :as r]))

(defn view []
  (let [checked (r/atom 2)
        checked-class "van-radio__icon--checked"]
    (fn []
      [:div {:style {:padding "1rem"}}; :background "#fff"}}
       [:div.van-radio-group {:role "radiogroup"}
        [:div.van-radio {:on-click (fn [e] (reset! checked 1))}
         [:div.van-radio__icon.van-radio__icon--round {:class (if (= 1 @checked) checked-class "")}
          [:i.van-icon.van-icon-success]]
         [:span.van-radio__label "Radio 1"]]
        [:div.van-radio {:on-click (fn [e] (reset! checked 2))}
         [:div.van-radio__icon.van-radio__icon--round {:class (if (= 2 @checked) checked-class "")}
          [:i.van-icon.van-icon-success]]
         [:span.van-radio__label "Radio 2"]]]])))
