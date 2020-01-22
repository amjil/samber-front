  (ns app.components.editor
    (:require
     [reagent.core :as r]
     ["quill" :as quill]
     [re-frame.core :refer [subscribe dispatch]]
     [app.components.tabbar :as tabbar]
     [app.components.navbar :as navbar]
     [app.components.quill :as qeditor]
     [app.components.caret :as caret]
     ["react-hammerjs" :default Hammer]))

  (defn editor [{:keys [id content selection on-change-fn]}]
    (let [this (r/atom nil)
          value #(aget @this "container" "firstChild" "innerHTML")]
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
            (.addEventListener ql-editor
              "click"
              (fn [e]
                (caret/index [(.-clientX e) (.-clientY e)]))))

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
                  :on-click (fn [x]
                              (js/console.log "xxxxxxx"))
                              ; (let [idx (.-index (.getSelection @this))
                              ;       idx (if (some #{(.getText @this idx 1)} [" " " "]) (+ 1 idx) idx)]
                              ;   (position/index idx)))
                  :on-blur (fn [] (js/console.log "blur ......"))
                  :style {:overflow-y "auto"
                          :width "100%"}
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
《 ᠬᠦᠶᠢ᠋᠂ ᠴᠢ ᠮᠠᠭᠤ ᠨᠢᠭᠤᠷ ᠦᠭᠡᠢ ᠠᠮᠢᠲᠠᠨ᠂ ᠶᠠᠭᠤ ᠭᠡᠵᠦ ᠪᠣᠵᠠᠷ ᠬᠣᠩᠰᠢᠶᠠᠷ ᠢᠶᠡᠷ ᠢᠶᠡᠨ ᠮᠢᠨᠦ ᠡᠨᠳᠡᠬᠢ ᠠᠷᠢᠭᠤᠨ ᠤᠮᠳᠠᠭᠠᠨ ᠢ ᠰᠢᠪᠠᠷ ᠱᠠᠪᠠᠬᠠᠢ ᠪᠡᠷ ᠪᠣᠯᠠᠩᠬᠢᠷᠲᠤᠭᠤᠯᠪᠠ᠂ ᠴᠢᠨᠦ ᠡᠨᠡ ᠬᠦ ᠬᠡᠷᠴᠡᠭᠡᠢ ᠪᠠᠯᠠᠮᠠᠳ ᠠᠵᠢᠯᠯᠠᠭᠠᠨ ᠤ ᠬᠠᠷᠢᠭᠤ ᠳ᠋ᠤ ᠪᠢ ᠪᠠᠭᠠᠯᠵᠠᠭᠤᠷ ᠢ ᠴᠢᠨᠢ ᠲᠠᠰᠤ ᠬᠠᠵᠠᠨ᠎ᠠ》 ᠭᠡᠵᠦ ᠵᠠᠩᠳᠤᠷᠤᠨ ᠬᠡᠯᠡᠪᠡ᠃")
          :selection nil
          :on-change-fn #(if (= % "user")
                           (do
                             (println (str "text changed: " %2))
                             (let [quill @(subscribe [:quill])]
                               (js/console.log "xxxxxx")
                               (js/console.log (.getSelection quill)))))}]]]))
