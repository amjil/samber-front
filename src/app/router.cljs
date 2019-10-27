(ns app.router
  (:require [bidi.bidi :as bidi]
            [pushy.core :as pushy]
            [reagent.core :as r]
            [app.state :refer [app-state]]))

(def app-routes
  ["/" {"home" :home
        "action-sheet" :action-sheet
        "button" :button
        "card" :card
        "cell" :cell
        "checkbox" :checkbox
        "collapse" :collapse
        "dialog" :dialog
        "divider" :divider
        "dropdown" :dropdown
        ; "overlay" :overlay
        "panel" :panel
        "picker" :picker
        "popup" :popup
        "progress" :progress
        "radio" :radio
        "skeleton" :skeleton
        "swipe-cell" :swipe-cell
        "tab" :tab
        "tabbar" :tabbar
        "tag" :tag
        "toast" :toast}])

(defn set-page! [match]
  (swap! app-state assoc :page match))

(def history
  (pushy/pushy set-page! (partial bidi/match-route app-routes)))

(defn start! []
  (pushy/start! history))

(defn set-token!
  [token]
  (pushy/set-token! history token))
