(ns app.components.article.events
  (:require-macros [cljs.core.async.macros :refer [go]])
  (:require
            [re-frame.core :as rf :refer [dispatch subscribe]]
            [reframe-utils.core :as rf-utils]
            [cljs.core.async :refer [<! >! chan put!]]
            [cljs-http.client :as http]
            [clojure.string :as str]))

(rf-utils/reg-set-event :articles)
(rf-utils/reg-set-event :article)
(rf-utils/reg-set-event :search)

(def base-url "http://localhost:3002/api/v1")

(defn url [s] (str base-url s))

(rf/reg-fx
  :article-http-search
  (fn [[params res-func]]
    (go (let [uri (url "/posts")
              response (<! (http/get uri {:query-params params :with-credentials? false}))]
          (js/console.log (clj->js (:body response)))
          (res-func (:body response))))))

(rf/reg-event-fx
  :article-search
  (fn [{:keys [db]} [_ data]]
    {:db db
     :article-http-search [{:q data} #(dispatch [:set-search %])]}))

(rf/reg-fx
  :article-http-get
  (fn [[params res-func]]
    (go (let [uri (url (str "/posts/" params))
              response (<! (http/get uri {:query-params {} :with-credentials? false}))]
          (js/console.log (clj->js (:body response)))
          (res-func (:body response))))))

(rf/reg-event-fx
  :article-get
  (fn [{:keys [db]} [_ data]]
    {:db db
     :article-http-get [data #(dispatch [:set-article (:post %)])]}))

(rf/reg-fx
  :article-http-create
  (fn [[params res-func]]
    (go (let [uri (url "/posts")
              response (<! (http/post uri {:json-params params :with-credentials? false}))]
          (js/console.log (clj->js (:body response)))
          (res-func (:body response))))))

(rf/reg-fx
  :article-http-list
  (fn [[params res-func]]
    (go (let [uri (url "/posts")
              response (<! (http/get uri {:query-params params :with-credentials? false}))]
          (js/console.log (clj->js (:body response)))
          (res-func (:body response))))))

(rf/reg-event-fx
  :article-list
  (fn [{:keys [db]} [_ data]]
    {:db db
     :article-http-list [data #(dispatch [:set-articles %])]}))

(rf/reg-event-fx
  :article-update
  (fn [{:keys [db]} [_ data]]
    {:db db
     :article-http-update [data #(dispatch [:set-active-page :article-list])]}))

(rf/reg-fx
  :article-http-update
  (fn [[params res-func]]
    (go (let [uri (url (str "/posts/" (:id params)))
              response (<! (http/patch uri {:json-params params :with-credentials? false}))]
          (js/console.log (clj->js (:body response)))
          (res-func (:body response))))))
