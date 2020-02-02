(ns app.components.long-tap
  (:require
    [reagent.core :as r]
    [re-frame.core :refer [subscribe]]
    ["quill" :as Quill]
    ["dayjs" :as dayjs]
    [app.components.caret :as caret]
    [clojure.string :as str])
  (:import
   [goog.async Debouncer]))

(defn- touch-start [e ql-editor is-long? timer hide-fn]
  ; (.preventDefault e)
  (let [caret-div (js/document.getElementById "caret-position-div")
        caret-display (if caret-div (aget (.-style caret-div) "display"))]
    (if (and caret-display (= "none" caret-display))
      (aset (.-style caret-div) "display" "block")))
  (let [location (aget (.-touches e) 0)
        range (js/document.caretRangeFromPoint (.-clientX location) (.-clientY location))
        quill @(subscribe [:quill])
        cloned-range (.cloneRange range)
        selection-index (+ (.-startOffset cloned-range) (.getIndex quill (.find Quill (.-startContainer cloned-range))))]
    (.setSelection quill selection-index 0)
    (js/console.log (.getSelection quill))
    (caret/index range)
    (when (and @timer (> 1000 (- (.valueOf (dayjs)) @timer)))
      (.fire hide-fn)
      (caret/selection-caret hide-fn)
      (caret/selection-caret-show)))
  (reset! timer (.valueOf (dayjs)))
  (reset! is-long? true)
  (js/console.log "touch start ...."))

(defn- touch-move [e is-long?]
  (reset! is-long? false)
  (js/console.log "touch move ...."))

(defn- touch-end [e is-long? timer]
  (js/console.log "touch end ...." @is-long?)
  (reset! is-long? false))

(defn- onselect [this is-long? timer]
  (let [duration (- (.valueOf (dayjs)) @timer)]
    (when (and (true? @is-long?) (<= 800 duration))
      (js/console.log "on selection ...... > " duration)
      (let [range (.getSelection @this)
            total-length (.getLength @this)
            default-len 30
            ;;
            start-index (max (- (.-index range) default-len) 0)
            start-offset (min (- (.-index range) start-index) default-len)
            start-text (.getText @this start-index start-offset)
            _ (js/console.log start-text)
            ;;
            end-index (.-index range)
            end-offset (min (- total-length end-index) default-len)
            end-text (.getText @this end-index end-offset)
            _ (js/console.log end-text)
            ;;
            sel-start (if (some #{(last start-text)} [" " "\n" "\r"])
                        0
                        (count (last (str/split start-text #"\s"))))
            sel-end (if (str/starts-with? end-text " ")
                      0
                      (count (first (str/split end-text #"\s"))))
            selected-index (-> (.-index range) (- sel-start))
            selected-offset (+ sel-start sel-end)]
        (js/console.log range)
        (js/console.log " 1 ... " start-index " -- " start-offset)
        (js/console.log " 2 ... " end-index " -- " end-offset)
        (js/console.log " 3 ... " selected-index " -- " selected-offset)
        (js/console.log " 4 ... " sel-start " -- " sel-end)
        (.setSelection @this selected-index selected-offset)
        (let [caret-div (js/document.getElementById "caret-position-div")]
          (aset (.-style caret-div) "display" "none"))))))
            ; end-text (.getText @this)]))))


(defn index [el this]
  (let [long-press-timer (r/atom nil)
        is-long? (r/atom false)
        tap-location (r/atom nil)
        hide-fn (Debouncer. caret/selection-caret-hide 2000)
        onselect-fn (Debouncer. #(onselect this is-long? long-press-timer) 1000)]
    (.addEventListener el "touchstart"
      (fn [e]
        (touch-start e el is-long? long-press-timer hide-fn)
        (.fire onselect-fn)))
    (.addEventListener el "touchmove"
      (fn [e] (touch-move e is-long?)))
    (.addEventListener el "touchend"
      (fn [e] (touch-end e is-long? long-press-timer)))
    (.addEventListener el "selectionchange" (fn [e] (js/console.log "selectionchange ....")))
    (.addEventListener el "scroll" (fn [e] (js/console.log "scroll ....")))
    (.addEventListener el "contextmenu"
      (fn [e]
        (.preventDefault e)
        (js/console.log "contextmenu")))
    (.addEventListener el "selectstart"
      (fn [e]
        (.preventDefault e)
        (js/console.log "selectstart ....")))
    (.addEventListener el "selectchange"
      (fn [e]
        (js/console.log "selectchange ....")))))
