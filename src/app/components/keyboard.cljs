(ns app.components.keyboard)

(defn eng-board []
  (fn []
    [:div.simple-keyboard.hg-theme-default.mobile-theme.keyboardDefContainer.hg-layout-default
     [:div.hg-row
      (doall
        (for [i ["q" "w" "e" "r" "t" "y" "u" "i" "o" "p"]]
          [:div.hg-button.hg-standardBtn [:span i]]))]
     [:div.hg-row {:style {:padding "0 .875rem"}}
      (doall
        (for [i ["a" "s" "d" "f" "g" "h" "j" "k" "l"]]
          [:div.hg-button.hg-standardBtn [:span i]]))]
     [:div.hg-row
      [:div.hg-button.hg-standardBtn
       {:style {:width "1.4rem"}}
       [:span "⇧"]]
       ; "⇪"
      (doall
        (for [i [ "z" "x" "c" "v" "b" "n" "m"]]
          [:div.hg-button.hg-standardBtn [:span i]]))
      [:div.hg-button.hg-standardBtn
       {:style {:width "1.4rem"}}
       [:span "⌫"]]]
     [:div.hg-row
      [:div.hg-button.hg-standardBtn
       [:span "123"]]
      [:div.hg-button.hg-standardBtn
       {:style {:flex-grow "5"}}
       [:span ""]]
      [:div.hg-button.hg-standardBtn
       ; "↩"
       [:span "↩️"]]]]))

(defn num-board []
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


(defn index []
  (fn []
    [:div.simple-keyboard-wrapper
     [:div.simple-keyboard-preview]
     [num-board]]))
