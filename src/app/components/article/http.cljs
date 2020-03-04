(ns app.components.article.http
  (:require-macros [cljs.core.async.macros :refer [go]])
  (:require [cljs.core.async :refer [<! >! chan put!]]
            [cljs-http.client :as http]
            [clojure.string :as str]))


(def base-url "http://localhost:3002/api/v1")

(defn url [s] (str base-url s))

(defn http-create [model res-func]
  (go (let [uri (url "/posts")
            response (<! (http/post uri {:json-params model :with-credentials? false}))]
        (js/console.log (clj->js (:body response)))
        (res-func (:body response)))))

(defn http-list [q]
  (go (let [uri (url "/posts")
            response (<! (http/get uri {:query-params {} :with-credentials? false}))]
        (js/console.log (clj->js (:body response))))))
