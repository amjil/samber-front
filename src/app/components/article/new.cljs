(ns app.components.article.new
  (:require
    [app.components.navbar :as navbar]
    [app.components.tabbar :as tabbar]
    [app.components.editor :as editor]
    [app.components.caret :as caret]
    [app.components.atom :refer [quill-editor editor-content key-list cand-list filter-prefix editor-cursor input-type is-editor]]
    [reagent.core :as r]
    [re-frame.core :refer [dispatch subscribe]])
  (:import
   [goog.async Debouncer]))

(defn change-editor-field [content type]
  (swap! is-editor not)
  (reset! input-type type)
  (reset! key-list [])
  (reset! cand-list [])
  (reset! filter-prefix "")
  (reset! editor-cursor content)
  (js/console.log "..............")
  (js/console.log (clj->js @content))

  (if (:delta @content)
    (.setContents @quill-editor (.-ops (:delta @content)) {})
    (if (some? (:content @content))
      ;; set the initial content when update
      (let [clipboard (.-clipboard @quill-editor)]
        (.setContents @quill-editor [] {})
        (.dangerouslyPasteHTML clipboard 0 (:content @content)))
      (.setContents @quill-editor [] {})))
    ;;
    ; (.setContents @quill-editor [] {}))

  (.setSelection @quill-editor (.getLength @quill-editor)))
;
(defn set-caret []
  (let [editor-range (.getSelection @quill-editor)]
    (caret/set-range quill-editor (.-index editor-range))))

(defn new-article [title content]
  (let [article @(subscribe [:article])
        data {:post {:title (:content @title) :content (:content @content)}}]
    (if article
      (dispatch [:article-update (assoc data :id (:id article))])
      (dispatch [:article-create data]))))
      ; (js/console.log "article update ....")
      ; (js/console.log "article create ...."))))

(defn index []
  (let [title (r/atom {})
        content (r/atom {})
        set-caret-fn (Debouncer. set-caret 100)
        article @(subscribe [:article])]
    (when (and article (nil? (:content title)) (nil? (:content content)))
      (reset! title {:content (:title article)})
      (reset! content {:content (:content article)})
      (js/console.log "set initial .........8888888"))
    (fn []
      [:div
       [:div {:style {:display (if @is-editor "block" "none")}}
        [editor/index "my-editor-id"]]
       [:div {:style {:display (if @is-editor "none" "flex") :flex-direction "column"
                      :width "100%"}}
        [:div.van-hairline--bottom.van-nav-bar--fixed.van-nav-bar
         {:style {:width "100vw"
                  :overflow "hidden"}}
         [:div.van-nav-bar__left
          {:style {:height "100%"}
           :on-click #(dispatch [:set-active-page :article-list])}
          [:i.van-icon.van-icon-arrow-left.van-nav-bar__arrow]]
         [:div.van-nav-bar__title.van-ellipsis {:style {:height "100%"}} ""]
         [:div.van-nav-bar__right
          {:style {:height "100%" :right "0"}
           :on-click #(new-article title content)}
          [:i.van-icon.van-icon-success]]]
        [:div {:style {:margin "46px 0 40px 0"}}
         [:div.van-cell-group.van-hairline--top-bottom
          [:div.van-cell
           [:div.van-cell__title
            [:span "ᠰᠢᠨ᠎ᠡ ᠨᠡᠢᠲᠡᠯᠡᠯ"]]]
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
            [:div.van-field__body {:style {;:width "calc(100vw - 7rem)"
                                           :overflow "auto"}}
             [:div
              {:style {:width "100%" :height "100%" :text-align "left"
                       :white-space "pre-wrap" :line-height "1rem"}
               :dangerouslySetInnerHTML {:__html (if (empty? (:content @content))
                                                   "Please Enter Text"
                                                   (:content @content))}}]]]]]]
        [tabbar/view]]])))
