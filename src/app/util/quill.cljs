(ns app.util.quill
  (:require ["quill-delta-to-html" :refer [QuillDeltaToHtmlConverter]]))

(defn delta [quill idx]
  (.getContents quill 0 idx))

(defn delta-to-html [dlt]
  (.convert (QuillDeltaToHtmlConverter. (.-ops dlt) {})))
