(ns app.components.article.subs
  (:require [reframe-utils.core :as rf-utils]))

(rf-utils/reg-basic-sub :articles)
(rf-utils/reg-basic-sub :article)
(rf-utils/reg-basic-sub :search)
