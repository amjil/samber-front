  (ns app.components.editor
    (:require
     [reagent.core :as r]
     ["quill" :as quill]
     [re-frame.core :refer [subscribe dispatch]]
     [app.components.tabbar :as tabbar]
     [app.components.navbar :as navbar]
     [app.components.quill :as qeditor]
     [app.components.caret :as caret]
     [app.components.long-tap :as long-tap]
     ["react-hammerjs" :default Hammer]
     ["dayjs" :as dayjs])
    (:import
     [goog.async Debouncer]))

  (defn editor [{:keys [id content selection on-change-fn]}]
    (let [this (r/atom nil)
          value #(aget @this "container" "firstChild" "innerHTML")
          last-tap-time (r/atom nil)
          hide-selection (Debouncer. caret/selection-caret-hide 2000)]
      (r/create-class
       {:component-did-mount
        (fn [component]
          (reset! this
                  (quill.
                   (aget (.-children (r/dom-node component)) 0)
                   #js {
                        :modules #js {:toolbar false}
                        :theme "snow"
                        :readOnly true
                        :placeholder "11Compose an epic..."}))

          (.on @this "text-change"
               (fn [delta old-delta source]
                 (on-change-fn source (value))))

          (if (= selection nil)
            (.setSelection @this nil)
            (.setSelection @this (first selection) (second selection) "api"))

          (let [ql-clipboard (js/document.querySelector ".ql-clipboard")]
            (aset (.-style ql-clipboard) "visibility" "hidden"))

          (let [ql-editor (js/document.querySelector ".ql-editor")]
            (long-tap/index ql-editor this))

          ; (new BScroll ".ql-editor" #js {})

          (dispatch [:set-quill @this]))

        :component-will-receive-props
        (fn [component next-props]
          (if
              (or
               (not= (:content (second next-props)) (value))
               (not= (:id (r/props component)) (:id (second next-props))))
            (do
              (if (= selection nil)
                (.setSelection @this nil)
                (.setSelection @this (first selection) (second selection) "api"))
              (.pasteHTML @this (:content (second next-props))))))

        :display-name  (str "quill-editor-" id)

        :reagent-render
        (fn []
          [:div {:id (str "quill-wrapper-" id)
                 :style {:width "100%"
                         :display "flex"
                         :flex-direction "row"}}
           [:div {:id (str "quill-editor-" id)
                  :class "quill-editor"
                  :style {:overflow-y "auto"
                          :width "100%"
                          :position "relative"
                          :padding-top "1px"
                          :user-select "none"}
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
        ; [qeditor/editor
        [editor
         {:id "my-editor-id"
          :content
          (str  "welcome to reagent-quill!<br>"
                "aaaaa<br>"
                "bbbbb<br>"
                "ᠠᠳᠤᠭᠤ᠂ ᠬᠡᠰᠡᠭ ᠪᠣᠰᠤᠭ <br>"
                "ᠴᠢᠨ᠋ᠸ᠎ᠠ ᠬᠤᠷᠠᠭ᠎ᠠ ᠬᠣᠶᠠᠷ ᠬᠦᠴᠦᠨ ᠪᠠᠭᠤᠷᠠᠢ ᠨᠢ ᠬᠦᠴᠦᠲᠡᠨ ᠤ ᠳᠡᠷᠭᠡᠲᠡ ᠬᠡᠵᠢᠶᠡᠲᠡ ᠵᠥᠪ ᠢᠶᠡᠨ ᠣᠯᠳᠠᠭ ᠦᠭᠡᠢ ᠦᠯᠢᠭᠡᠷ ᠵᠢᠱᠢᠶ᠎ᠡ ᠲᠡᠦᠬᠡ ᠱᠠᠰᠲᠢᠷ ᠲᠤ ᠣᠯᠠᠨ ᠪᠠᠢ᠌ᠳᠠᠭ᠂ ᠭᠡᠪᠡᠴᠦ ᠪᠢ ᠡᠨᠳᠡ ᠲᠡᠦᠬᠡ ᠶᠠᠷᠢᠬᠤ ᠦᠭᠡᠢ᠂ ᠬᠠᠷᠢᠨ ᠡᠨᠡ ᠲᠤᠬᠠᠢ ᠨᠢᠭᠡ ᠶᠣᠭᠲᠠ ᠦᠯᠢᠭᠡᠷ ᠶᠠᠷᠢᠶ᠎ᠠ᠃
ᠨᠢᠭᠡᠨ ᠬᠠᠯᠠᠭᠤᠨ ᠡᠳᠦᠷ ᠬᠤᠷᠠᠭ᠎ᠠ ᠭᠣᠷᠣᠬ᠎ᠠ ᠳᠡᠭᠡᠷ᠎ᠠ ᠤᠰᠤ ᠤᠤᠭᠤᠬᠤ ᠪᠡᠷ ᠣᠴᠢᠭᠠᠳ᠂ ᠭᠠᠢ ᠭᠠᠮᠰᠢᠭ ᠲᠤ ᠤᠴᠠᠷᠠᠬᠤ ᠨᠢ ᠭᠠᠷᠴᠠᠭ᠎ᠠ ᠦᠭᠡᠢ ᠪᠣᠯᠪᠠ᠂ ᠤᠴᠢᠷ ᠨᠢ ᠲᠡᠭᠦᠪᠡᠷ ᠨᠢᠭᠡ ᠦᠯᠦᠨ ᠴᠢᠨ᠋ᠸ᠎ᠠ ᠢᠳᠡᠰᠢ ᠬᠠᠢ᠌ᠨ ᠦᠯᠪᠡᠯᠵᠡᠨ ᠶᠠᠪᠤᠵᠤ ᠪᠠᠢ᠌ᠵᠠᠢ᠃
ᠴᠢᠨ᠋ᠸ᠎ᠠ ᠬᠤᠷᠠᠭ᠎ᠠ ᠶᠢ ᠦᠵᠡᠭᠡᠳ᠂ ᠲᠡᠷᠡ ᠬᠦ ᠪᠡᠯᠡᠨ ᠬᠣᠭᠣᠯᠠ ᠤᠷᠤᠭᠤ ᠤᠬᠤᠰᠭᠢᠨ ᠬᠦᠷᠥᠯ᠎ᠡ᠃ ᠭᠡᠪᠡᠴᠦ ᠲᠡᠭᠦᠨ ᠢ ᠢᠳᠡᠬᠦ ᠳ᠋ᠤ ᠪᠠᠨ ᠤᠴᠢᠷ ᠰᠢᠯᠲᠠᠭᠠᠨ ᠭᠠᠷᠭᠠᠬᠤ ᠶᠢᠨ ᠲᠦᠯᠦᠭᠡ:
《 ᠬᠦᠶᠢ᠋᠂ ᠴᠢ ᠮᠠᠭᠤ ᠨᠢᠭᠤᠷ ᠦᠭᠡᠢ ᠠᠮᠢᠲᠠᠨ᠂ ᠶᠠᠭᠤ ᠭᠡᠵᠦ ᠪᠣᠵᠠᠷ ᠬᠣᠩᠰᠢᠶᠠᠷ ᠢᠶᠡᠷ ᠢᠶᠡᠨ ᠮᠢᠨᠦ ᠡᠨᠳᠡᠬᠢ ᠠᠷᠢᠭᠤᠨ ᠤᠮᠳᠠᠭᠠᠨ ᠢ ᠰᠢᠪᠠᠷ ᠱᠠᠪᠠᠬᠠᠢ ᠪᠡᠷ ᠪᠣᠯᠠᠩᠬᠢᠷᠲᠤᠭᠤᠯᠪᠠ᠂ ᠴᠢᠨᠦ ᠡᠨᠡ ᠬᠦ ᠬᠡᠷᠴᠡᠭᠡᠢ ᠪᠠᠯᠠᠮᠠᠳ ᠠᠵᠢᠯᠯᠠᠭᠠᠨ ᠤ ᠬᠠᠷᠢᠭᠤ ᠳ᠋ᠤ ᠪᠢ ᠪᠠᠭᠠᠯᠵᠠᠭᠤᠷ ᠢ ᠴᠢᠨᠢ ᠲᠠᠰᠤ ᠬᠠᠵᠠᠨ᠎ᠠ》 ᠭᠡᠵᠦ ᠵᠠᠩᠳᠤᠷᠤᠨ ᠬᠡᠯᠡᠪᠡ᠃"
                "<br>"
                "ᠶᠠᠮᠠᠷ ᠨᠢᠭᠡᠨ ᠶᠠᠪᠤᠳᠠᠯ ᠢ ᠳᠠᠭᠤᠷᠢᠶᠠᠨ ᠰᠤᠷᠬᠤ ᠳ᠋ᠤ ᠪᠠᠨ ᠤᠶᠤᠨ ᠤᠬᠠᠭᠠᠨ  ᠢᠶᠡᠨ ᠬᠡᠷᠭ᠍ᠯᠡᠪᠡᠯ ᠠᠰᠢᠭ ᠲᠤᠰᠠ ᠣᠯᠵᠤ ᠴᠢᠳᠠᠨ᠎ᠠ᠂ ᠬᠡᠷᠪᠡ ᠰᠣᠬᠣᠷ ᠪᠠᠯᠠᠢ ᠪᠡᠷ ᠳᠠᠭᠤᠷᠢᠶᠠᠨ ᠰᠤᠷᠪᠠᠯ᠂ ᠪᠣᠷᠬᠠᠨ ᠡ᠂ ᠶᠠᠮᠠᠷ ᠴᠦ ᠤᠴᠠᠷᠠᠵᠤ ᠮᠡᠳᠡᠨ᠎ᠡ ᠰᠢᠦ! ᠪᠢ ᠡᠨᠡ ᠲᠠᠯ᠎ᠠ ᠪᠡᠷ ᠠᠯᠤᠰ ᠣᠷᠣᠨ ᠳ᠋ᠤ ᠪᠣᠯᠣᠭᠰᠠᠨ ᠨᠢᠭᠡᠨ ᠶᠠᠪᠤᠳᠠᠯ ᠢᠶᠡᠨ ᠵᠢᠱᠢᠶ᠎ᠡ ᠲᠠᠲᠠᠵᠤ ᠶᠠᠷᠢᠶ᠎ᠠ᠃ ᠰᠠᠷᠮᠠᠭᠴᠢᠨ ᠢ ᠣᠯᠵᠤ ᠦᠵᠡᠭ᠍ᠰᠡᠨ ᠬᠦᠮᠦᠨ ᠪᠦᠬᠦᠨ ᠲᠡᠳᠡᠨ ᠤ ᠬᠦᠮᠦᠨ ᠳᠠᠭᠤᠷᠢᠶᠠᠬᠤ ᠵᠠᠩᠲᠠᠢ ᠶᠢ ᠮᠡᠳᠡᠨ᠎ᠡ᠃ ᠠᠹᠠᠷᠢᠺᠠ ᠲᠢᠪ ᠤᠨ ᠥᠳᠬᠡᠨ ᠤᠢ ᠰᠢᠭᠤᠢ ᠳᠣᠲᠣᠷ᠎ᠠ᠂ ᠮᠣᠳᠣᠨ ᠤ ᠰᠠᠯᠠᠭ᠎ᠠ ᠮᠦᠴᠢᠷ ᠳᠡᠭᠡᠭᠦᠷ ᠰᠦᠷᠥᠭ᠌ ᠰᠠᠷᠮᠠᠭᠴᠢᠨ ᠰᠠᠭᠤᠵᠠᠭᠠᠵᠤ ᠪᠠᠢᠵᠠᠢ᠃ ᠲᠡᠳᠡ ᠳᠣᠷᠣᠭᠰᠢ ᠬᠠᠷᠠᠵᠤ ᠦᠵᠡᠲᠡᠯ᠎ᠡ᠂ ᠰᠠᠷᠮᠠᠭᠴᠢᠨ ᠪᠠᠷᠢᠬᠤ ᠬᠦᠮᠦᠨ᠂ ᠡᠪᠡᠰᠦ ᠨᠣᠭᠣᠭᠠᠨ ᠳᠡᠭᠡᠷ᠎ᠠ ᠲᠣᠣᠷ ᠳᠡᠯᠭᠡᠵᠦ᠂ ᠳᠡᠭᠡᠭᠦᠷ ᠨᠢ ᠥᠩᠭᠥᠷᠢᠨ ᠬᠦᠷᠪᠡᠵᠦ ᠪᠠᠢᠯ᠎ᠠ᠃ ᠲᠡᠭᠡᠭᠡᠳ ᠲᠡᠳᠡ ᠪᠡᠶ᠎ᠡ ᠪᠡᠶ᠎...")
          :selection nil
          :on-change-fn #(if (= % "user")
                           (do
                             (println (str "text changed: " %2))
                             (let [quill @(subscribe [:quill])]
                               (js/console.log "xxxxxx")
                               (js/console.log (.getSelection quill)))))}]]]))
