(ns app.components.hammer
  (:require
            [reagent.core :as r]
            ["@egjs/hammerjs" :default Hammer]))

(defn index [id]
  (r/create-class
    {:component-did-mount
     (fn [component]
       (js/console.log "-------------")
       (js/console.log component)
       (js/console.log "-------------")
       (let [hammertime (new Hammer (js/document.getElementById id))]))
       ;   (.on hammertime "pan"
       ;     (fn [ev]
       ;       (js/console.log "Hammerjs test .....")
       ;       (js/console.log ev)))))
     :reagent-render
     (fn []
       [:div {:id id}])}))
