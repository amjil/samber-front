  (ns app.components.editor
    (:require
     [reagent.core :as r]
     ["quill" :as quill]
     [re-frame.core :refer [subscribe dispatch]]
     [app.components.tabbar :as tabbar]
     [app.components.navbar :as navbar]
     [app.components.quill :as qeditor]
     [app.components.position :as position]))

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
                              (js/console.log "xxxxxxx")
                              (js/console.log (.getSelection @this))
                              (position/index (.-index (.getSelection @this))))
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
                "Returns the lines contained within the specified location.<br>"
                "direction: ltr;box-sizing: border-box;width: 90px;height: 408.906px;overflow: hidden;border-width: 0px;border-style: none;padding: 0px;text-align: start;text-transform: none;text-indent: 0px;text-decoration: none solid rgb(85, 85, 85);letter-spacing: normal;word-spacing: 0px;white-space: pre-wrap;tab-size: 8;writing-mode: vertical-lr;position: absolute;visibility: hidden;top: 58px;left: 13px;")
          :selection nil
          :on-change-fn #(if (= % "user")
                           (do
                             (println (str "text changed: " %2))
                             (let [quill @(subscribe [:quill])]
                               (js/console.log "xxxxxx")
                               (js/console.log (.getSelection quill)))))}]]]))
