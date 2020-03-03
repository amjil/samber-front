(ns app.components.keyboard.symbol)

(defn index []
  (fn []
    [:div.simple-keyboard.hg-theme-default.mobile-theme.keyboardDefContainer.hg-layout-default
     [:div.hg-row
      {:style {:flex-direction "row"}}
      [:div.hg-row
       {:style {:flex-direction "column"}}
       [:div.hg-row
        {:style {:flex-direction "row"}}
        [:div.hg-row
         [:div.hg-button.hg-standardBtn [:span "1"]]
         [:div.hg-button.hg-standardBtn [:span "2"]]
         [:div.hg-button.hg-standardBtn [:span "3"]]]
        [:div.hg-row
         [:div.hg-button.hg-standardBtn [:span "4"]]
         [:div.hg-button.hg-standardBtn [:span "5"]]
         [:div.hg-button.hg-standardBtn [:span "6"]]]
        [:div.hg-row
         [:div.hg-button.hg-standardBtn [:span "7"]]
         [:div.hg-button.hg-standardBtn [:span "8"]]
         [:div.hg-button.hg-standardBtn [:span "9"]]]]
       [:div.hg-row
        [:div.hg-row
         [:div.hg-button.hg-standardBtn [:span "7"]]]]]

      [:div.hg-row
       [:div.hg-button.hg-standardBtn
        [:span "󠁿󠁿@?"]]
       [:div.hg-button.hg-standardBtn
        [:span "←"]]
       [:div.hg-button.hg-standardBtn
        {:flex- "2"}
        [:span "0"]]
       [:div.hg-button.hg-standardBtn
        [:span "☐"]]
       [:div.hg-button.hg-standardBtn
        [:span "↵"]]]]]))
