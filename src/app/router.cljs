(ns app.router
  (:require [bidi.bidi :as bidi]
            [pushy.core :as pushy]
            [reagent.core :as r]
            [app.state :refer [app-state]]))

(def app-routes
  ["/" {"home"  :home
        "edit"  :edit
        "toast" :toast
        "login" :login}])

(defn set-page! [match]
  (swap! app-state assoc :page match))

(def history
  (pushy/pushy set-page! (partial bidi/match-route app-routes)))

(defn start! []
  (pushy/start! history))

(defn set-token!
  [token]
  (pushy/set-token! history token))
