(ns app.components.context-menu
  (:require
    [reagent.core :as r]
    [app.components.atom :as editor-atom]
    [app.components.position :as position]
    [app.components.caret :as caret]))

(declare hide-menu hide-selection-range)

(defmulti button-action (fn [x y] (identity x)))

(defmethod button-action :select-all [action-type el]
  (js/console.log "button-action -- select-all")
  (let [length (.getLength @editor-atom/quill-editor)]
    (.setSelection @editor-atom/quill-editor 0 length)
    (.focus @editor-atom/quill-editor)
    (position/range-position el editor-atom/quill-editor 1)
    (position/range-position el editor-atom/quill-editor 2)
    (position/context-menu-position el editor-atom/quill-editor)))

(defmethod button-action :copy [_ el e]
  (js/console.log "button-action -- copy")
  (let [flag (js/document.execCommand "Copy")]
    (js/console.log "copy = " flag))
  (.focus @editor-atom/quill-editor)
  (let [range (.getSelection @editor-atom/quill-editor)]
    (.setSelection @editor-atom/quill-editor (+ (.-index range) (.-length range)) 0))
  (let [new-range (.getSelection @editor-atom/quill-editor)]
    (caret/set-range editor-atom/quill-editor (.-index new-range)))
  (hide-selection-range el)
  (hide-menu el))

(defmethod button-action :paste [_ el e]
  (js/console.log "button-action -- paste")
  ; (.focus @editor-atom/quill-editor)
  (let [ql-range (.getSelection @editor-atom/quill-editor)]
    (-> (.readText js/navigator.clipboard)
      (.then #(.insertText @editor-atom/quill-editor (.-index ql-range) %))
      (.catch #(js/console.log %))
      (.finally #(js/console.log "cleanup"))))
  (let [new-range (.getSelection @editor-atom/quill-editor)]
    (caret/set-range editor-atom/quill-editor (.-index new-range)))
  (hide-selection-range el)
  (hide-menu el))

(defmethod button-action :delete [_ el e]
  (js/console.log "button-action -- delete")
  (let [ql-range (.getSelection @editor-atom/quill-editor)]
    (.deleteText @editor-atom/quill-editor (.-index ql-range) (.-length ql-range)))
  (let [new-range (.getSelection @editor-atom/quill-editor)]
    (caret/set-range editor-atom/quill-editor (.-index new-range)))
  (hide-selection-range el)
  (hide-menu el))


(defn create-element [el]
  (let [
        div (js/document.createElement "div")
        touch-start (fn [e]
                      (.preventDefault e)
                      (js/console.log "touch-start ...."))
        touch-move (fn [e] (js/console.log "touch-move ..."))
        touch-end (fn [e] (js/console.log "touch-end ...."))
        data [{:label "ᠪᠦᠭᠦᠳᠡ" :action :select-all} {:label "ᠬᠠᠭᠤᠯᠬᠤ" :action :copy} {:label "ᠨᠠᠭᠠᠬᠤ" :action :paste} {:label "ᠬᠠᠰᠤᠬᠤ" :action :delete}]]
    ;;
    ; (.addEventListener div "touchstart" touch-start)
    ; (.addEventListener div "touchmove" touch-move)
    ; (.addEventListener div "touchend" touch-end)
    ;;
    (.add (.-classList div) "context-menu")
    (doseq [x data]
      (let [ele (js/document.createElement "div")]
        (set! (.-innerHTML ele) (:label x))
        (.addEventListener ele "touchstart"
          (fn [e]
            (.preventDefault e)
            (button-action (:action x) el e)))
        (.appendChild div ele)))
    (aset (.-style div) "display" "none")
    (aset (.-style div) "with" ".7rem")
    (aset (.-style div) "z-index" "1000")
    (aset (.-style div) "top" "7rem")
    (aset (.-style div) "opacity" "0.7")
    (set! (.-id div) "context-menu")

    (.appendChild el div)))

(defn index2 [el quill]
  (let [menu-el (.querySelector (.-parentNode el) "#context-menu")
        [left-index top] (position/index el quill 1)
        parent-rect (.getBoundingClientRect el)
        left-index (-> left-index
                       (+ (.-left parent-rect))
                       (- 20))
        left-index (if (< left-index 10)
                     10
                     left-index)]
    (aset (.-style menu-el) "display" "flex")
    (aset (.-style menu-el) "left" (str left-index "px"))))

(defn hide-menu [el]
  (js/console.log "hide menu ....")
  (let [menu-el (.querySelector (.-parentNode el) "#context-menu")]
    (if-not (= "none" (aget (.-style menu-el) "display"))
      (aset (.-style menu-el) "display" "none"))))

(defn hide-selection-range [el]
  (let [el-start (.querySelector el "#selection-start-div")
        el-end (.querySelector el "#selection-end-div")]
    (if-not (= "none" (aget (.-style el-start) "display"))
      (aset (.-style el-start) "display" "none"))
    (if-not (= "none" (aget (.-style el-end) "display"))
      (aset (.-style el-end) "display" "none"))))
