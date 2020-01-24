(ns app.components.keyboard)

(defn index []
  (fn []
    [:div.simple-keyboard-wrapper
     [:div.simple-keyboard-preview]
     [:div.simple-keyboard.hg-theme-default.mobile-theme.keyboardDefContainer.hg-layout-default
      [:div.hg-row
       (doall
         (for [i ["q" "w" "e" "r" "t" "y" "u" "i" "o" "p"]]
           [:div.hg-button.hg-standardBtn [:span i]]))]
      [:div.hg-row {:style {:padding "0 14px"}}
       (doall
         (for [i ["a" "s" "d" "f" "g" "h" "j" "k" "l"]]
           [:div.hg-button.hg-standardBtn [:span i]]))]
      [:div.hg-row
       [:div.hg-button.hg-standardBtn
        {:style {:flex-grow "1.8"}}
        [:span "⇧"]]
        ; "⇪"
       (doall
         (for [i [ "z" "x" "c" "v" "b" "n" "m"]]
           [:div.hg-button.hg-standardBtn [:span i]]))
       [:div.hg-button.hg-standardBtn
        {:style {:flex-grow "1.8"}}
        [:span "⌫"]]]
      [:div.hg-row
       [:div.hg-button.hg-standardBtn
        [:span "123"]]
       [:div.hg-button.hg-standardBtn
        {:style {:flex-grow "5"}}
        [:span ""]]
       [:div.hg-button.hg-standardBtn
        ; "↩"
        [:span "↩️"]]]]]))
