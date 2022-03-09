  (ns app.components.editor
    (:require
     [reagent.core :as r]
     [reagent.dom :as rd]
     ["quill" :as quill]
     [app.components.atom :refer [editor-cursor editor-content quill-editor keyboard-layout input-type is-editor]]
     [re-frame.core :refer [subscribe dispatch]]
     [app.components.tabbar :as tabbar]
     [app.components.navbar :as navbar]
     [app.components.caret :as caret]
     [app.components.long-tap :as long-tap]
     [app.components.range-selection :as range-selection]
     [app.components.context-menu :as context-menu]
     [app.components.keyboard.keyboard :as keyboard]
     [app.components.keyboard.candidate :as candidate]
     ["dayjs" :as dayjs])
    (:import
     [goog.async Debouncer]))

  (defn editor [{:keys [id content selection on-change-fn]}]
    (let [;this (r/atom nil)
          value #(aget @quill-editor "container" "firstChild" "innerHTML")
          ; value #(aget @this "container" "firstChild" "innerHTML")
          last-tap-time (r/atom nil)
          hide-selection (Debouncer. caret/selection-caret-hide 2000)]
      (r/create-class
       {:component-did-mount
        (fn [component]
          (reset! quill-editor
                  (quill.
                   (aget (.-children (rd/dom-node component)) 0)
                   #js {
                        :modules #js {:toolbar false}
                        :theme "snow"
                        ; :readOnly true
                        ; :debug "info"
                        :placeholder "ᠠᠭᠤᠯᠭ᠎ᠠ ᠪᠠᠨ ᠨᠠᠢᠷᠠᠭᠤᠯᠤᠶ᠎ᠠ ..."}))

          (.on @quill-editor "text-change"
               (fn [delta old-delta source]
                 (js/console.log "...........<><><><>>")
                 (on-change-fn source (aget @quill-editor "container" "firstChild" "innerHTML"))))

          (if (= selection nil)
            (.setSelection @quill-editor nil)
            (.setSelection @quill-editor (first selection) (second selection) "api"))

          (let [ql-clipboard (js/document.querySelector ".ql-clipboard")]
            ; (aset (.-style ql-clipboard) "visibility" "hidden")
            (aset (.-style ql-clipboard) "display" "none"))

          (let [ql-editor (js/document.querySelector ".ql-editor")]
            (long-tap/index ql-editor quill-editor)
            (aset (.-style ql-editor) "white-space" "pre-wrap"))

          (let [my-editor (js/document.getElementById (str "quill-editor-" id))]
            (range-selection/create-range quill-editor my-editor 1)
            (range-selection/create-range quill-editor my-editor 2)
            (context-menu/create-element my-editor))

          ; (new BScroll ".ql-editor" #js {})
          (dispatch [:set-quill @quill-editor]))

        :component-will-receive-props
        (fn [component next-props]
          (if
              (or
               (not= (:content (second next-props)) (value))
               (not= (:id (r/props component)) (:id (second next-props))))
            (do
              (if (= selection nil)
                (.setSelection @quill-editor nil)
                (.setSelection @quill-editor (first selection) (second selection) "api"))
              (.pasteHTML @quill-editor (:content (second next-props))))))

        :display-name  (str "quill-editor-" id)

        :reagent-render
        (fn []
          [:div {:id (str "quill-wrapper-" id)
                 :style {:width "100%"
                         :display "flex"
                         :flex-direction "row"}}
                         ; :overflow "hidden"}}
           [:div {:id (str "quill-editor-" id)
                  :class "quill-editor"
                  :style {:width "100%"

                          :height "100%"
                          :position "relative"
                          :padding-top "1px"
                          ; :overflow "auto"
                          :-webkit-touch-callout "none"}
                  :dangerouslySetInnerHTML {:__html content}}]])})))

  (defn hide-editor []
    (swap! is-editor not)
    (let [editor-text (clojure.string/trim (.getText @quill-editor))]
      (when (and (some? @editor-cursor) (= reagent.ratom/RAtom (type @editor-cursor)) (not-empty editor-text))
        (if (= "input" @input-type)
          (reset! @editor-cursor {:content editor-text :delta (.getContents @quill-editor)})
          (let [content (aget @quill-editor "container" "firstChild" "innerHTML")]
            (reset! @editor-cursor {:content content :delta (.getContents @quill-editor)}))))

      (.setContents @quill-editor [] {})))

  (defn on-change-fn [content-atom x y]
    (if (= x "api")
      (do
        (if (= "input" @input-type)
          (reset! content-atom {:content (subs y 3 (- (count y) 4))})
          (reset! content-atom {:content y}))
        (println (str "text changed: " y)))))

  (defn index [id]
    (fn []
      [:div {:style {:display "flex" :flex-direction "row"}}
       [:div.van-hairline--top-bottom.van-nav-bar--fixed.van-nav-bar
        {:style {:width "100vw"
                 :overflow "hidden"}}
        [:div.van-nav-bar__left
         {:style {:height "100%"}
          :on-click #(hide-editor)}
         [:i.van-icon.van-icon-arrow-left.van-nav-bar__arrow]]
        [:div.van-nav-bar__title.van-ellipsis {:style {:height "100%"}} ""]]
       [:div {:style {:height "100%"
                      :margin "46px 0 0 0"
                      :padding ".7rem"
                      :overflow-y "hidden"
                      :width "100vw"}}
        [editor
         {:id id
          :content ""
          :selection nil
          :on-change-fn #(if (= % "api")
                           (do
                             (if (= "input" @input-type)
                               (reset! editor-content (subs %2 3 (- (count %2) 4)))
                               (reset! editor-content %2))
                             (println (str "text changed: " %2))))}]]
       [keyboard/index "input"]
       [candidate/view]]))
