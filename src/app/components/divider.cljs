(ns app.components.divider
  (:require [app.state :refer [app-state]]
            [reagent.core :as r]))

(defn view []
  [:div {:style {:padding "1rem" :background "#fff"}}
   [:div.van-divider.van-divider--hairline.van-divider--content-center {:role "separator"}
    "Text"]])
