(ns app.components.hammer
  (:require
            [reagent.core :as r]
            ["react-hammerjs" :default Hammer]))

(defn index []
  (r/create-class
    {:component-did-mount
     (fn [component]
       (js/console.log "-------------")
       (js/console.log component)
       (js/console.log "-------------"))
     :reagent-render
     (fn []
       [:> Hammer
        {:onTap #(js/console.log "OnTap .....")
         :onDoubleTap #(js/console.log "OnDoubleTap .....")}
        [:div "touch me"]])}))
