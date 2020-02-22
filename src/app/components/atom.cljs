(ns app.components.atom
  (:require
    [reagent.core :as r]
    [clojure.string :as str]
    [app.components.keyboard.http :as http]))


(def quill-editor (r/atom nil))
;;;

(def key-list (r/atom []))

(def filter-prefix (r/atom ""))


; (def query-list
;   (r/track
;     (fn []
;       (if (empty? @filter-prefix)
;         (reduce-query @key-list)
;         (filter #(str/starts-with? (:bqr_biclg %) @filter-prefix) (reduce-query @key-list))))))

(def cand-list (r/atom []))
  ; (r/track
  ;   (fn []
  ;     (http/candidate @query-list identity))))

(def keyboard-layout (r/atom 1))
