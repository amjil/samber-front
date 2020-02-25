(ns app.components.article.new
  (:require
    [app.components.navbar :as navbar]
    [app.components.tabbar :as tabbar]
    [app.components.editor :as editor]
    [app.components.caret :as caret]
    [app.components.atom :refer [quill-editor editor-content key-list cand-list filter-prefix editor-cursor input-type is-editor]]
    [reagent.core :as r])
  (:import
   [goog.async Debouncer]))

(defn change-editor-field [content type]
  (swap! is-editor not)
  (reset! input-type type)
  (reset! key-list [])
  (reset! cand-list [])
  (reset! filter-prefix "")
  (reset! editor-cursor content)
  (js/console.log "-------------- change editor field starting ...")
  (js/console.log (:delta @content))
  (if (:delta @content)
    (.setContents @quill-editor (.-ops (:delta @content)) {})
    (.setContents @quill-editor [] {}))
  (.setSelection @quill-editor (.getLength @quill-editor))
  (js/console.log "-------------- change editor field ending ...."))
;
(defn set-caret []
  (let [editor-range (.getSelection @quill-editor)]
    (caret/set-range quill-editor (.-index editor-range))))

(defn index []
  (let [title (r/atom {})
        content (r/atom {})
        set-caret-fn (Debouncer. set-caret 100)]
    ; (r/create-class
    ;   {:reagent-render
    (js/console.log "<><><><><><><><><")
    (js/console.log (clj->js @title))
    (fn []
      [:div
       [:div {:style {:display (if @is-editor "block" "none")}}
        [editor/index]]
       [:div {:style {:display (if @is-editor "none" "flex") :flex-direction "column"
                      :width "100%"}}
        [navbar/index]
        [:div {:style {:margin "46px 0 40px 0"}}
         [:div.van-cell-group.van-hairline--top-bottom
          {:style {:margin ".5rem"}}
          [:div.van-cell.van-field
           [:div.van-cell__title.van-field__label
            [:span "ᠭᠠᠷᠴᠠᠭ"]]
           [:div.van-cell__value.van-field__value
            {:on-click #(do
                          (change-editor-field title "input")
                          (.fire set-caret-fn))}
            [:div.van-field__body
             [:span
              (if (empty? (:content @title))
                "Please Enter Text"
                (:content @title))]]]]
          [:div.van-cell.van-field
           [:div.van-cell__title.van-field__label
            [:span "ᠠᠭᠤᠯᠭ᠎ᠠ"]]
           [:div.van-cell__value.van-field__value
            {:on-click #(do
                          (change-editor-field content "textarea")
                          (.fire set-caret-fn))}
            [:div.van-field__body {:style {:width "calc(100vw - 7rem)"
                                           :overflow "auto"}}
             [:div
              {:style {:width "100%" :height "100%" :text-align "left"
                       :white-space "pre-wrap"}
               :dangerouslySetInnerHTML {:__html (if (empty? (:content @content))
                                                   "Please Enter Text"
                                                   (:content @content))}}]]]]
          [:div.van-panel__footer
           [:span.van-button.van-button--default.van-button--small
            [:span.van-button__text "ᠪᠤᠴᠠᠬᠤ"]]
           [:span.van-button.van-button--danger.van-button--small {:style {:margin-top "5px"}}
            [:span.van-button__text  "ᠬᠠᠳᠠᠭᠠᠯᠠᠬᠤ"]]]]]
        [tabbar/view]]])))
