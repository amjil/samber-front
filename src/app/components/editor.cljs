(ns app.components.editor
  (:require
   [reagent.core :as r]
   ["quill" :as quill]
   [re-frame.core :refer [subscribe dispatch]]
   [app.components.tabbar :as tabbar]
   [app.components.navbar :as navbar]
   [app.components.quill :as qeditor]))

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
                      ; :modules #js {:toolbar (aget (.-children (r/dom-node component)) 0)}
                      :modules #js {:toolbar false}
                      :theme "snow"
                      :readOnly true
                      :placeholder "11Compose an epic..."}))

        (.on @this "text-change"
             (fn [delta old-delta source]
               (on-change-fn source (value))))

        (if (= selection nil)
          (.setSelection @this nil)
          (.setSelection @this (first selection) (second selection) "api")))

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
                       ; :overflow "auto"}}
         ; [quill-toolbar id]
         [:div {:id (str "quill-editor-" id)
                :class "quill-editor"
                ; :on-focus (fn [x]
                ;             (let [quill @(subscribe [:quill])]
                ;               (js/console.log "xxxxxxx")
                ;               (js/console.log (.getSelection quill))))
                            ; (.blur js/document.activeElement))
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
        :content "welcome to reagent-quill!"
        :selection nil
        :on-change-fn #(if (= % "user")
                         (do
                           (println (str "text changed: " %2))
                           (let [quill @(subscribe [:quill])]
                             (js/console.log "xxxxxx")
                             (js/console.log (.getSelection quill)))))}]]]))
