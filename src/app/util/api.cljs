(ns app.util.api
  (:require-macros [cljs.core.async.macros :refer [go]])
  (:require [cljs.core.async :refer [<! >! chan put!]]
            [cljs-http.client :as http]
            [re-frame.core :as rf :refer [dispatch subscribe]]
            [reframe-utils.core :as rf-utils]
            [clojure.string :as str]
            [app.router :refer [set-token!]]))

; (defonce url "http://localhost:8080/rest")
(defonce url "http://localhost:3000/")

(defn api-url [u] (str url u))

(defn auth-header [token]
  {"Authorization" (str "Bearer " token)})

(defn paginate [paginate]
  (let [{:keys [offset limit]} paginate]
    {"Prefer" "count=exact"
     "Range-Unit" "items"
     "Range" (str offset "-" (+ offset limit -1))}))

(defn handler [response res-func]
  (condp = (:status response)
    0 (dispatch [:notify {:type "error" :message "出错了!" :description "网络连接出现问题了"}])
    401 (do
          (dispatch [:notify {:type "error" :message "出错了!" :description "请先登录"}])
          (set-token! "/login"))
    400 (dispatch [:api-request-error (:body response)])
    404 (dispatch [:notify {:type "error" :message "出错了" :description "地址不存在"}])
    409 (dispatch [:notify {:type "error" :message "出错了" :description (-> response :body :message)}])
    (res-func response)))

(defn GET [uri params res-func]
  (go (let [response (<! (http/get uri params))]
        (dispatch [:set-loading false])
        (handler response res-func))))

(defn PUT [uri params res-func]
  (go (let [response (<! (http/put uri params))]
        (dispatch [:set-loading false])
        (handler response res-func))))

(defn POST [uri params res-func]
  (go (let [response (<! (http/post uri params))]
        (dispatch [:set-loading false])
        (handler response res-func))))

(defn PATCH [uri params res-func]
  (go (let [response (<! (http/patch uri params))]
        (dispatch [:set-loading false])
        (handler response res-func))))

(defn DELETE [uri params res-func]
  (go (let [response (<! (http/delete uri params))]
        (dispatch [:set-loading false])
        (handler response res-func))))

(rf/reg-fx
  :http-request
  (fn [[type url headers json-params res-func]]
    (let [url (api-url url)]
      (condp = type
        :get    (GET    url {:json-params json-params :headers headers :with-credentials? false} res-func)
        :post   (POST   url {:json-params json-params :headers headers :with-credentials? false} res-func)
        :patch  (PATCH  url {:json-params json-params :headers headers :with-credentials? false} res-func)
        :delete (DELETE url {:json-params json-params :headers headers :with-credentials? false} res-func)))))

(defn http-request
  [http-method url res-func {:keys [db]} [_ data]]
  (let [token (get-in db [:current-user :token])
        headers (auth-header token)
        params data]
    {:db db
     :http-request [http-method url headers params res-func]}))

(defn http-request-anonymous
  [http-method url res-func {:keys [db]} [_ data]]
  (let [params data]
    {:db db
     :http-request [http-method url {} params res-func]}))


(defn error-message [response]
  (let [code (:code response)
        message (:message response)]
    (if code
      (condp = code
        "42501" "您没有权限"
        "P0001" "用户名或者密码错误"
        message)
      message)))
