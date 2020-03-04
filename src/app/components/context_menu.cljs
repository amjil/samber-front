(ns app.components.context-menu)

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

    (.appendChild el div)))
