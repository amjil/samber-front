(ns app.components.context-menu
  (:require
    [reagent.core :as r]))


(defn create-element [el]
  (let [
        div (js/document.createElement "div")
        touch-start (fn [e]
                      (.preventDefault e)
                      (js/console.log "touch-start ...."))
        touch-move (fn [e] (js/console.log "touch-move ..."))
        touch-end (fn [e] (js/console.log "touch-end ...."))
        data ["ᠪᠦᠭᠦᠳᠡ" "ᠬᠠᠭᠤᠯᠬᠤ" "ᠨᠠᠭᠠᠬᠤ" "ᠬᠠᠰᠤᠬᠤ"]]
    ;;
    ; (.addEventListener div "touchstart" touch-start)
    ; (.addEventListener div "touchmove" touch-move)
    ; (.addEventListener div "touchend" touch-end)
    ;;
    (.add (.-classList div) "context-menu")
    (doseq [x data]
      (let [ele (js/document.createElement "div")]
        (set! (.-innerHTML ele) x)
        (.appendChild div ele)))
    (aset (.-style div) "display" "none")
    (aset (.-style div) "with" ".7rem")
    (aset (.-style div) "z-index" "1000")
    (aset (.-style div) "top" "7rem")
    (aset (.-style div) "opacity" "0.7")
    (set! (.-id div) "context-menu")

    (.appendChild el div)))

(defn index [el quill]
  (let [menu-el (.querySelector (.-parentNode el) "#context-menu")
        quill-range (.getSelection @quill)
        index (.-index quill-range)
        [blot offset] (.getLeaf @quill index)
        flag (and (= 3 (.-nodeType (.-domNode blot))) (= offset (.-length (.-domNode blot))))
        _ (js/console.log " == " flag " == " (.-length (.-domNode blot)) " -- " offset)
        index (if flag (- index 1) index)
        offset (if flag 1 0)
        bound (.getBounds @quill index offset)
        parent-rect (.getBoundingClientRect el)
        left-index (-> (.-left bound)
                       (+ (.-left parent-rect)))]
    (js/console.log bound)
    (js/console.log (.-offsetLeft (.-parentNode (.-parentNode el))))
    (js/console.log (.getBoundingClientRect el))
    (aset (.-style menu-el) "display" "flex")
    (aset (.-style menu-el) "left" (str left-index "px"))))

(defn hide-menu [el]
  (js/console.log "hide menu ....")
  (let [menu-el (.querySelector (.-parentNode el) "#context-menu")]
    (if-not (= "none" (aget (.-style menu-el) "display"))
      (aset (.-style menu-el) "display" "none"))))
