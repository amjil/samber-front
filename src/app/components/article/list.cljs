(ns app.components.article.list
  (:require
    [app.components.navbar :as navbar]
    [app.components.tabbar :as tabbar]
    [re-frame.core :refer [dispatch subscribe]]
    [app.components.article.http :as http]
    [app.components.article.events]
    [app.components.article.subs]))

(defn index []
  (fn []
    (let [articles @(subscribe [:articles])]
      [:div {:style {:display "flex" :flex-direction "column"
                     :width "100%"}}
       [:div.van-hairline--bottom.van-nav-bar--fixed.van-nav-bar
        {:style {:width "100vw" :overflow "hidden"}}
        [:div.van-nav-bar__title.van-ellipsis {:style {:height "100%"}} ""]
        [:div.van-nav-bar__left
         {:style {:height "100%" :left "0"}
          :on-click #(do (dispatch [:set-active-page :article-new])
                         (dispatch [:set-article nil]))}
         [:i.van-icon.van-icon-add]]
        [:div.van-nav-bar__right
         {:style {:height "100%" :right "0"}
          :on-click #(do (dispatch [:set-active-page :article-search])
                         (dispatch [:set-search nil]))}
         [:i.van-icon.van-icon-search]]]
       [:div {:style {:margin "46px 0 40px 0"}}
        [:div.van-cell-group
         (for [item (:posts articles)]
           ^{:key (:id item)}
           [:div.van-cell.van-cell--clickable
            {:on-click #(do (dispatch [:article-get (:id item)])
                            (dispatch [:set-active-page :article-show]))}
            [:div.van-cell__title
             [:span (:title item)]]])]]
       [tabbar/view]])))
