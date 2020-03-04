(ns app.components.keyboard.http
  (:require-macros [cljs.core.async.macros :refer [go]])
  (:require [cljs.core.async :refer [<! >! chan put!]]
            [cljs-http.client :as http]
            [clojure.string :as str]))


(def xvlvn-url "http://localhost:3000/api/candidate")

(def xvlvn-update "http://localhost:3000/api/update-order")

(def url-next-words "http://localhost:3000/api/next-words")

(defn candidate [cands res-func]
  (js/console.log "candidate string " (clj->js cands))
  (go (let [input (str/join "," cands)
            response (<! (http/get xvlvn-url {:query-params {:input input} :with-credentials? false}))]
        (js/console.log "candidate status "(:status response))
        (js/console.log (clj->js (map #(:char_word %) (:body response))))
        (res-func (sort-by :active_order > (into #{} (:body response)))))))

(defn update-order [id candstr]
  (go (let [response (<! (http/get xvlvn-update {:query-params {:input candstr :id id} :with-credentials? false}))]
        (js/console.log "Update order !!!!")
        (js/console.log (clj->js (:body response))))))

(defn next-words [candstr id res-func]
  (go (let [response (<! (http/get url-next-words {:query-params {:input candstr :id id} :with-credentials? false}))]
        (js/console.log "next words status "(:status response))
        (js/console.log (clj->js (map #(:char_word %) (:body response))))
        (res-func (sort-by :id > (into #{} (:body response)))))))
