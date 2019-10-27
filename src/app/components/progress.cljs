(ns app.components.progress
  (:require [app.state :refer [app-state]]
            [reagent.core :as r]))

(defn view []
  [:div {:style {:padding "1rem"}}; :background "#fff"}}
   [:div.van-progress
    [:div.van-progress__portion.van-progress__portion--with-pivot {:style {:background "rgb(25, 137, 250)" :height "48%"}}
     [:div.van-progress__pivot {:style {:color "#fff" :background "rgb(25, 137, 250)"}} "50%"]]]])
