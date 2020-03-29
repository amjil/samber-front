(ns app.components.article.new
  (:require
    [app.components.navbar :as navbar]
    [app.components.tabbar :as tabbar]
    [app.components.editor :as editor]
    [app.components.caret :as caret]
    [app.components.atom :refer [quill-editor editor-content key-list cand-list filter-prefix editor-cursor input-type is-editor]]
    [app.components.keyboard.keyboard :as keyboard]
    [app.components.keyboard.candidate :as candidate]
    [reagent.core :as r]
    [re-frame.core :refer [dispatch subscribe]])
  (:import
   [goog.async Debouncer]))

(defn change-editor-field [content type]
  (when @quill-editor
    ; (.setContents @quill-editor [] {})

    (reset! input-type type)
    (reset! key-list [])
    (reset! cand-list [])
    (reset! filter-prefix "")
    (reset! editor-cursor content)

    (js/console.log "..............")
    (js/console.log (clj->js @content))
    (when (and (empty? (clojure.string/trim (.getText @quill-editor))) (some? (:content @content)))
      (.dangerouslyPasteHTML (.-clipboard @quill-editor) 0 (:content @content)))

    (.setSelection @quill-editor (.getLength @quill-editor))))
;
(defn set-caret []
  (let [editor-range (.getSelection @quill-editor)]
    (if editor-range
      (caret/set-range quill-editor (.-index editor-range))
      (caret/set-range quill-editor 0))))

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
        set-caret-fn (Debouncer. set-caret 400)
        article @(subscribe [:article])
        current (r/atom "")
        title-field-fn (Debouncer. #(change-editor-field title "input") 100)
        content-field-fn (Debouncer. #(change-editor-field content "textarea") 100)]
    (when (and article (nil? (:content @title)) (nil? (:content @content)))
      (reset! title {:content (:title article)})
      (reset! content {:content (:content article)})
      (js/console.log "set initial .........8888888"))
    (fn []
      ; [:div]
       ; [:div {:style {:display (if @is-editor "block" "none")}}
       ;  [editor/index "my-editor-id"]]
       ; [:div {:style {:display (if @is-editor "none" "flex") :flex-direction "column"}}]

      [:div {:style {:display "flex" :flex-direction "row"}}
       ; [:div {:style {:display "flex" :flex-direction "column"
       ;                :width "100%"}}
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
        [:div {:style {:margin "46px 0 0 0"
                       :height "100%"}}
         [:div.van-cell-group.van-hairline--top-bottom
          [:div.van-cell
           [:div.van-cell__title
            [:span "ᠰᠢᠨ᠎ᠡ ᠨᠡᠢᠲᠡᠯᠡᠯ"]]]
          [:div.van-cell.van-field
           ; [:div.van-cell__title.van-field__label
           ;  [:span "ᠭᠠᠷᠴᠠᠭ"]]
           [:div.van-cell__value.van-field__value
            {:on-click #(do
                          (when-not (and @is-editor (= "title" @current))
                            (reset! current "title")
                            (reset! is-editor true)
                            (.fire title-field-fn)
                            (.fire set-caret-fn))
                          (when (and @is-editor (= "title" @current))
                            (set-caret)))}
            [:div.van-field__body
             [:div
              (if (and @is-editor (= "title" @current))
                [editor/editor
                 {:id "my-editor-title"
                  :content ""
                  :selection nil
                  :on-change-fn #(if (= % "api")
                                   (do
                                     (if (= "input" @input-type)
                                       (reset! title {:content (subs %2 3 (- (count %2) 4))})
                                       (reset! title {:content %2}))
                                     (println (str "text changed: " %2))))}]
                (if (empty? (:content @title))
                  "ᠭᠠᠷᠴᠠᠭ"
                  (:content @title)))]]]]
          [:div.van-cell.van-field
           ; [:div.van-cell__title.van-field__label
           ;  [:span "ᠠᠭᠤᠯᠭ᠎ᠠ"]]
           [:div.van-cell__value.van-field__value
            {:on-click #(do
                          (when-not (and @is-editor (= "content" @current))
                            (reset! current "content")
                            (reset! is-editor true)
                            (.fire content-field-fn)
                            (.fire set-caret-fn))
                          (when (and @is-editor (= "title" @current))
                            (set-caret)))}
            [:div.van-field__body {:style {;:width "calc(100vw - 7rem)"
                                           :overflow "auto"}}
             (if (and @is-editor (= "content" @current))
               [:div
                {:style {:width "9rem" :height "100%" :text-align "left"
                         :white-space "pre-wrap" :line-height "1rem"}}
                [editor/editor
                 {:id "my-editor-content"
                  :content ""
                  :selection nil
                  :on-change-fn #(if (= % "api")
                                   (do
                                     (if (= "input" @input-type)
                                       (reset! content {:content (subs %2 3 (- (count %2) 4))})
                                       (reset! content {:content %2}))
                                     (println (str "text changed: " %2))))}]]
               [:div
                {:style {:width "9rem" :height "100%" :text-align "left"
                         :white-space "pre-wrap" :line-height "1rem"}
                 :dangerouslySetInnerHTML {:__html (if (empty? (:content @content))
                                                     "ᠠᠭᠤᠯᠭ᠎ᠠ"
                                                     (:content @content))}}])]]]]]
        (if @is-editor
          [:div
           [candidate/view]
           [keyboard/index "input"]]
          [tabbar/view])])))
