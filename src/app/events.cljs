(ns app.events
  (:require [app.db :refer [default-db]]
            [app.router :as router]
            [re-frame.core :as rf]
            [reframe-utils.core :as rf-utils]
            [app.util.api :as api]
            [goog.object :refer [getValueByKeys]]))


(rf/reg-event-fx
 :initialize-db

 [(rf/inject-cofx :current-user)]

 (fn [{:keys [current-user]} _]
   {:db (assoc default-db :current-user current-user)})) ;; what it returns becomes the new application state


; (rf-utils/reg-set-event :active-page)
(rf-utils/reg-set-event :loading)
(rf-utils/reg-set-event :paginate)

(rf/reg-event-fx
  :set-active-page
  (fn [{:keys [db]} [_ page]]
    (let [set-page (assoc db :active-page page)]
      (case page
        (:login :settings :home :result
         :description :form)

        {:db set-page}

        :todo {:db  set-page
               :dispatch [:todos]}))))

(rf/reg-fx
  :set-url
  (fn [{:keys [url]}]
    (router/set-token! url)))
