  (ns app.components.editor
    (:require
     [reagent.core :as r]
     ["quill" :as quill]
     [app.components.atom :refer [quill-editor]]
     [re-frame.core :refer [subscribe dispatch]]
     [app.components.tabbar :as tabbar]
     [app.components.navbar :as navbar]
     [app.components.quill :as qeditor]
     [app.components.caret :as caret]
     [app.components.long-tap :as long-tap]
     [app.components.range-selection :as range-selection]
     [app.components.context-menu :as context-menu]
     [app.components.keyboard.keyboard :as keyboard]
     [app.components.keyboard.candidate :as candidate]
     ["react-hammerjs" :default Hammer]
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
                   (aget (.-children (r/dom-node component)) 0)
                   #js {
                        :modules #js {:toolbar false}
                        :theme "snow"
                        :readOnly true
                        ; :debug "info"
                        :placeholder "11Compose an epic..."}))

          (.on @quill-editor "text-change"
               (fn [delta old-delta source]
                 (on-change-fn source (aget @quill-editor "container" "firstChild" "innerHTML"))))

          (if (= selection nil)
            (.setSelection @quill-editor nil)
            (.setSelection @quill-editor (first selection) (second selection) "api"))

          (let [ql-clipboard (js/document.querySelector ".ql-clipboard")]
            (aset (.-style ql-clipboard) "visibility" "hidden"))

          (let [ql-editor (js/document.querySelector ".ql-editor")]
            (long-tap/index ql-editor quill-editor))

          (let [my-editor (js/document.getElementById "quill-editor-my-editor-id")]
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

  (defn index []
    (fn []
      [:div {:style {:display "flex" :flex-direction "row"}}
       [navbar/index]
       [:div {:style {:height "100%"
                      :margin "46px 0 0 0"
                      :padding ".7rem"
                      :overflow-y "hidden"
                      :width "100vw"}}
        [editor
         {:id "my-editor-id"
          :content
          (str  "welcome to reagent-quill!<br>"
                "aaaaa<br>"
                "bbbbb<br>"
                "ᠠᠳᠤᠭᠤ᠂ ᠬᠡᠰᠡᠭ ᠪᠣᠰᠤᠭ")
          :selection nil
          :on-change-fn #(if (= % "user")
                           (do
                             (println (str "text changed: " %2))
                             (let [quill @(subscribe [:quill])]
                               (js/console.log "xxxxxx")
                               (js/console.log (.getSelection quill)))))}]]
       [:div.simple-keyboard-wrapper
        (let [quill (subscribe [:quill])]
          [keyboard/nine-layout-board quill])]
       [candidate/view]]))
