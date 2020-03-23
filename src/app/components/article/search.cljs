(ns app.components.article.search
  (:require
    [app.components.tabbar :as tabbar]
    [app.components.article.new :as arnew]
    [app.components.editor :as editor]
    [re-frame.core :refer [dispatch subscribe]]
    [reagent.core :as r]
    [app.components.atom :refer [is-editor]])
  (:import
   [goog.async Debouncer]))

(defn index []
  (let [content (r/atom {:search-fn (fn [x] (dispatch [:article-search x])
                                            (js/console.log "search-fn ...." x))})
        set-caret-fn (Debouncer. arnew/set-caret 100)]
    (fn []
      (let [searchs @(subscribe [:search])]
        [:div
         [:div {:style {:display (if @is-editor "block" "none")}}
          [editor/index "my-editor-id"]]
         [:div {:style {:display (if @is-editor "none" "flex") :flex-direction "column"
                        :width "100%"}}
          [:div.van-hairline--bottom.van-nav-bar--fixed.van-nav-bar
           {:style {:width "100vw" :overflow "hidden"}}
           [:div.van-nav-bar__title.van-ellipsis {:style {:height "100%"}} ""]
           [:div.van-nav-bar__left
            {:style {:height "100%"}
             :on-click #(dispatch [:set-active-page :article-list])}
            [:i.van-icon.van-icon-arrow-left.van-nav-bar__arrow]]]
          [:div {:style {:margin "46px 0 40px 0"}}
           [:div.van-search {:style {:background "rgb(79, 192, 141)"}}
            [:div.van-search__content.van-search__content--round
             [:div.van-cell.van-cell--borderless.van-field
              [:div.van-field__left-icon
               [:i.van-icon.van-icon-search]]
              [:div.van-cell__value.van-cell__value--alone.van-field__value
               [:div.van-field__body
                {:on-click #(do
                              (arnew/change-editor-field content "search")
                              (js/console.log "into search .....")
                              (.fire set-caret-fn))}
                [:span
                 (if (empty? (:content @content))
                   "Please Enter Text"
                   (:content @content))]]]]]]
           [:div.van-cell-group
            (for [item (:posts searchs)]
              ^{:key (:id item)}
              [:div.van-cell.van-cell--clickable
               {:on-click #(do (dispatch [:article-get (:id item)])
                               (dispatch [:set-active-page :article-show]))}
               [:div.van-cell__title
                [:span
                 {:dangerouslySetInnerHTML {:__html (if (empty? (:title item))
                                                        ""
                                                        (:title item))}}]
                [:div.van-cell__label
                 {:dangerouslySetInnerHTML {:__html (if (empty? (:content item))
                                                        ""
                                                        (:content item))}}]]])]]
          [tabbar/view]]]))))
