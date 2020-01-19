(ns app.components.position
  (:require [app.util.quill :as quill-util]
            [re-frame.core :refer [dispatch subscribe]]))

(def properties
  [ "direction"  ;; RTL support
    "box-sizing"
    "width"  ;; on Chrome and IE exclude the scrollbar so the mirror div wraps exactly as the textarea does
    "height"
    "overflow-x"
    "overflow-y"  ;; copy the scrollbar for IE

    "border-top-width"
    "border-right-width"
    "border-bottom-width"
    "border-left-width"
    "border-style"

    "padding-top"
    "padding-right"
    "padding-bottom"
    "padding-left"

    ;; https://developer.mozilla.org/en-US/docs/Web/CSS/font
    "font-style"
    "font-variant"
    "font-weight"
    "font-stretch"
    "font-size"
    "font-size-adjust"
    "line-height"
    "font-family"

    "text-align"
    "text-transform"
    "text-indent"
    "text-decoration"  ;; might not make a difference but better be safe

    "letter-spacing"
    "word-spacing"
    "white-space"

    "tab-size"
    "moz-tab-size"])

(def is-browser (exists? js/window))
(def is-firefox (and is-browser (exists? js/window.mozInnerScreenX)))

(defn coordinates [e innter-html]
  (let [div (js/document.createElement "div")
        computed (js/window.getComputedStyle e)
        span (js/document.createElement "span")]
    (set! (.-id div) "input-textarea-caret-position-mirror-div")
    (js/document.body.appendChild div)
    (doall (map #(aset (.-style div) % (.getPropertyValue computed %)) properties))
    (doseq [[k v] {"writing-mode" "vertical-lr"
                   "position" "absolute"
                   "visibility" "hidden"
                   "font-family" "Mongolian White"
                   "white-space" "pre-wrap"
                   "top" "3.2rem"
                   "left" ".7rem"}]
      (aset (.-style div) k v))

    (if is-firefox
      (if (> (.-scrollHeight e) (js/parseInt (.getPropertyValue computed "height")))
        (aset (.-style div) "overflow-y" "scroll"))
      (aset (.-style div) "overflow" "hidden"))

    (set! (.-innerHTML div) innter-html)
    (set! (.-textContent span) ".")

    (if (= 1 (.-length (.-children div)))
      (.appendChild (aget (.-children div) 0) span)
      (.appendChild div span))

    ;; [top left height]
    [(+ (.-offsetTop span) (js/parseInt (.getPropertyValue computed "border-top-width")))
     (+ (.-offsetLeft span) (js/parseInt (.getPropertyValue computed "border-left-width")))
     (js/parseInt (.getPropertyValue computed "line-height"))]))

(defn editor-div []
  (js/document.querySelector ".ql-editor"))

(defn index [idx]
  (let [div (editor-div)
        quill @(subscribe [:quill])
        quill-delta (quill-util/delta quill idx)
        html-content (quill-util/delta-to-html quill-delta)]
    (coordinates div html-content)))
