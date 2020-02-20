(ns app.components.keyboard.keyboard)

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

(defn nine-layout-board []
  (fn []
    [:div.simple-keyboard.hg-theme-default.mobile-theme.keyboardDefContainer.hg-layout-default
     [:div.hg-row {:style {:flex-direction "column"
                           :overfow "hidden"}}
      [:div {:style {:flex-grow "1" :height "9.56rem" :margin-right "2px"
                               :display "flex" :flex-direction "row"
                               :overflow "hidden"}}
       [:div.hg-row {:style {:flex-grow "4.5"
                             :overflow "auto"}}
        [:div.hg-button {:style {:height "100%"
                                 :display "flex" :flex-direction "column"
                                 :justify-content "flex-start"
                                 :align-items "center"
                                 :min-height "10px"
                                 :overflow "scroll"
                                 :padding "0"}}
         [:span {:style {:flex-grow "1" :padding ".3rem" :width "100%" :border-bottom "0.5px" :border-style "ridge" :text-align "center"}}
          [:span {:style {:writing-mode "vertical-lr"}} "᠂"]]
         [:span {:style {:flex-grow "1" :padding ".3rem" :width "100%" :border-bottom "0.5px" :border-style "ridge" :text-align "center"}}
          [:span {:style {:writing-mode "vertical-lr"}} "᠃"]]
         [:span {:style {:flex-grow "1" :padding ".3rem" :width "100%" :border-bottom "0.5px" :border-style "ridge" :text-align "center"}}
          [:span {:style {:writing-mode "vertical-lr"}} "᠄"]]
         [:span {:style {:flex-grow "1" :padding ".3rem" :width "100%" :border-bottom "0.5px" :border-style "ridge" :text-align "center"}}
          [:span {:style {:writing-mode "vertical-lr"}} "᠁"]]]]
       [:div.hg-row {:style {:flex-grow "1"}}
        [:div.hg-button {:style {:height "100%"}}
         [:span "123"]]]]

      [:div {:style {:display "flex" :flex-direction "row" :flex-grow "4.5"
                     :margin "0 2px"}}
       [:div {:style {:display "flex" :flex-direction "column" :margin "0 1px 4px 1px"}}
        [:div.hg-button.hg-standardBtn [:span "1"]]
        [:div.hg-button.hg-standardBtn
         [:div {:style {:display "flex" :flex-direction "row" :align-items "center" :font-size "12px"}}
          [:span "2"]
          [:span "ABC"]]]
        [:div.hg-button.hg-standardBtn
         [:div {:style {:display "flex" :flex-direction "row" :align-items "center" :font-size "12px"}}
          [:span "3"]
          [:span "DEF"]]]]
       [:div {:style {:display "flex" :flex-direction "column" :margin "0 1px 4px 1px"}}
        [:div.hg-button.hg-standardBtn
         [:div {:style {:display "flex" :flex-direction "row" :align-items "center" :font-size "12px"}}
          [:span "4"]
          [:span "GHI"]]]
        [:div.hg-button.hg-standardBtn
         [:div {:style {:display "flex" :flex-direction "row" :align-items "center" :font-size "12px"}}
          [:span "5"]
          [:span "JKL"]]]
        [:div.hg-button.hg-standardBtn
         [:div {:style {:display "flex" :flex-direction "row" :align-items "center" :font-size "12px"}}
          [:span "6"]
          [:span "MNO"]]]]
       [:div {:style {:display "flex" :flex-direction "column" :margin "0 1px 4px 1px"}}
        [:div.hg-button.hg-standardBtn
         [:div {:style {:display "flex" :flex-direction "row" :align-items "center" :font-size "12px"}}
          [:span "7"]
          [:span "PQRS"]]]
        [:div.hg-button.hg-standardBtn
         [:div {:style {:display "flex" :flex-direction "row" :align-items "center" :font-size "12px"}}
          [:span "8"]
          [:span "TUV"]]]
        [:div.hg-button.hg-standardBtn
         [:div {:style {:display "flex" :flex-direction "row" :align-items "center" :font-size "12px"}}
          [:span "9"]
          [:span "WXYZ"]]]]
       [:div {:style {:display "flex" :flex-direction "column" :margin "0 1px 0 1px"}}
        [:div.hg-button.hg-standardBtn [:span "?!"]]
        [:div.hg-button.hg-standardBtn [:span "⌞⌟"]]
        [:div.hg-button.hg-standardBtn [:span "abc"]]]]
      ; [:div.hg-button {:style {:flex-grow "1" :height "9.56rem" :margin-left "2px"}} [:span "3"]]
      [:div {:style {:flex-grow "1" :height "9.56rem" :margin-left "2px"
                               :display "flex" :flex-direction "row"}}
       [:div.hg-row {:style {:flex-grow "1"}}
        [:div.hg-button {:style {:height "100%"}}
         [:span "⌫"]]]
       [:div.hg-row {:style {:flex-grow "1"}}
        [:div.hg-button {:style {:height "100%"}}
         [:span "☐"]]]
       [:div.hg-row {:style {:flex-grow "2.55"}}
        [:div.hg-button {:style {:height "100%"}}
         [:span "←"]]]]]]))


(defn index []
  (fn []
    [:div.simple-keyboard-wrapper
     [:div.simple-keyboard-preview]
     [nine-layout-board]]))
     ; [eng-board]]))
