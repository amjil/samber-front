(ns app.subs
  (:require [re-frame.core :as re-frame]
            [reframe-utils.core :as rf-utils]))

(re-frame/reg-sub
  ::name
  (fn [db]
    (:name db)))

(rf-utils/reg-basic-sub :active-page)
(rf-utils/reg-basic-sub :loading)
(rf-utils/reg-basic-sub :paginate)
(rf-utils/reg-basic-sub :drawer)

(re-frame/reg-sub
  :token
  (fn [db]
    (-> db :current-user :token)))

(re-frame/reg-sub
  :user-role
  (fn [db]
    (-> db :current-user :me :role)))

(rf-utils/reg-basic-sub :sidebar)
