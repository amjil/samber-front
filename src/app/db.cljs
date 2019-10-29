(ns app.db
  (:require [reagent.core :refer [atom]]
            [re-frame.core :refer [reg-fx reg-cofx]]
            [goog.crypt.base64 :as b64]))

(def default-db {:active-page :home :loading false})

(defn set-item!
  [key val]
  (.setItem (.-localStorage js/window) key (b64/encodeString (.stringify js/JSON (clj->js val)))))

(defn delete-item!
  [key]
  (.removeItem (.-localStorage js/window) key))

(defn- get-current-user-map
  []
  (let [data (.getItem js/localStorage "currentUser")]
    (if (some? data)
      (into (sorted-map)
            (as-> data d
                  (b64/decodeString d)
                  (.parse js/JSON d)
                  (js->clj d :keywordize-keys true))))))

(reg-fx
  :set-current-user!
  (fn [current-user]
    (set-item! "currentUser" current-user)))

(reg-fx
  :remove-current-user!
  (fn [user]
    (delete-item! "currentUser")))

(reg-cofx
  :current-user
  (fn [cofx _]
    (assoc cofx :current-user (get-current-user-map))))
