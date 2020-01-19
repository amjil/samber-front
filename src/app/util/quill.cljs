(ns app.util.quill
  (:require ["quill-delta-to-html" :refer [QuillDeltaToHtmlConverter]]))

(defn delta [quill start-idx end-idx]
  (.getContents quill start-idx end-idx))

(defn delta-to-html [dlt]
  (.convert (QuillDeltaToHtmlConverter. (.-ops dlt) {})))
