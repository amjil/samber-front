(ns app.components.keyboard.key-action
  (:require
    [app.components.caret :as caret]
    [app.components.atom :refer [quill-editor key-list filter-prefix cand-list]]
    [app.components.keyboard.http :as http]))

(defn delete-text [range length]
  (js/console.log "delete-text " range)
  (if (pos? (.-index range))
    (let [ops (.-ops (.deleteText @quill-editor (- (.-index range) 1) 1))]
      (js/console.log "ops" ops)
      (if (and (empty? ops) (pos? length))
        (.deleteText @quill-editor (- (.-index range) 2) 1)))))

(defn on-delete []
  (let [range (.getSelection @quill-editor)
        length (.getLength @quill-editor)]
    (js/console.log "<<<<<<<<<<<<" range)
    (if (zero? (.-length range))
      (let [new-index (- (.-index range) 1)]
        (js/console.log (.getContents @quill-editor))
        (delete-text range length)
        (js/console.log (.getContents @quill-editor))
        ; (.setSelection @quill-editor new-index 0)
        (caret/set-range quill-editor (.-index (.getSelection @quill-editor)))))
    (js/console.log "<<<<<<<<<<<< end" (.getSelection @quill-editor) (.getLength @quill-editor))))

(defn reduce-fn [a b]
  (for [x a y b]
    (str x y)))

(defn reduce-query [keys]
  (let [kk (map #(map char %) keys)]
    (prn kk)
    (reduce
      #(reduce-fn %1 %2)
      [""]
      kk)))

(defn on-key [value]
  (if (empty? @key-list)
    (swap! key-list conj value)
    (swap! key-list conj (clojure.string/replace value #"a|e|i|o|u|v|q" "")))

  ;;;;
  (let [query-list
        (if (empty? @filter-prefix)
          (reduce-query @key-list)
          (filter #(clojure.string/starts-with? (:bqr_biclg %) @filter-prefix) (reduce-query @key-list)))]
    (http/candidate query-list #(reset! cand-list %))))

(defn on-clear []
  (reset! key-list [])
  (reset! filter-prefix "")
  (reset! cand-list []))
