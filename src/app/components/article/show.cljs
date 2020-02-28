(ns app.components.article.show
  (:require
    [app.components.tabbar :as tabbar]
    [re-frame.core :refer [dispatch subscribe]]))

(defn index []
  (fn []
    (let [article @(subscribe [:article])]
      [:div {:style {:display "flex" :flex-direction "column"
                     :width "100%"}}
       [:div.van-hairline--bottom.van-nav-bar--fixed.van-nav-bar
        {:style {:width "100vw" :overflow "hidden"}}
        [:div.van-nav-bar__title.van-ellipsis {:style {:height "100%"}} ""]
        [:div.van-nav-bar__left
         {:style {:height "100%" :right "0"}
          :on-click #(dispatch [:set-active-page :article-list])}
         [:i.van-icon.van-icon-arrow-left]]
        [:div.van-nav-bar__right
         {:style {:height "100%" :right "0"}
          :on-click #(dispatch [:set-active-page :article-new])}
         [:i.van-icon.van-icon-edit]]]
       [:div {:style {:margin "46px 0 40px 0"}}
        [:div {:style {:padding "1rem" :background-color "white"}}
         [:h3 {:style {:font-size "16px"}} (:title article)]
         [:div {:style {:overflow "auto"
                        :margin-left "1rem"}}
          [:div
           {:style {:width "100%" :height "100%" :text-align "left"
                    :white-space "pre-wrap" :line-height "1rem"
                    :font-size "14px"}
            :dangerouslySetInnerHTML {:__html (if (empty? (:content article))
                                                  ""
                                                  (:content article))}}]]]]
       [tabbar/view]])))
