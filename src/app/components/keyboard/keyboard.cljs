(ns app.components.keyboard.keyboard
  (:require
    [app.components.keyboard.key-action :as key-action]
    [app.components.atom :as aa]))

(defn eng-board []
  (fn []
    [:div.simple-keyboard.hg-theme-default.mobile-theme.keyboardDefContainer.hg-layout-default
     [:div.hg-row
      (doall
        (for [i ["q" "w" "e" "r" "t" "y" "u" "i" "o" "p"]]
          ^{:key i}
          [:div.hg-button.hg-standardBtn
           { :on-click #(key-action/on-key i)}
           [:span i]]))]
     [:div.hg-row {:style {:padding "0 .875rem"}}
      (doall
        (for [i ["a" "s" "d" "f" "g" "h" "j" "k" "l"]]
          ^{:key i}
          [:div.hg-button.hg-standardBtn
           { :on-click #(key-action/on-key i)}
           [:span i]]))]
     [:div.hg-row
      [:div.hg-button.hg-standardBtn
       {:style {:width "1.4rem"}}
       [:span "⇧"]]
       ; "⇪"
      (doall
        (for [i [ "z" "x" "c" "v" "b" "n" "m"]]
          ^{:key i}
          [:div.hg-button.hg-standardBtn
           { :on-click #(key-action/on-key i)}
           [:span i]]))
      [:div.hg-button.hg-standardBtn
       {:style {:width "1.4rem"}
        :on-click #(key-action/on-delete)}
       [:span "⌫"]]]
     [:div.hg-row
      [:div.hg-button.hg-standardBtn
       [:span "123"]]
      [:div.hg-button.hg-standardBtn
       {:style {:flex-grow "4"}
        :on-click #(key-action/on-space)}
       [:span ""]]
      [:div.hg-button.hg-standardBtn
       { :on-click #(reset! aa/keyboard-layout 2)}
       [:span "abc"]]
      [:div.hg-button.hg-standardBtn
       {:on-click #(key-action/on-clear)}
       [:span "☐"]]
      [:div.hg-button.hg-standardBtn
       ; "↩"
       { :on-click #(key-action/on-return)}
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
         [:span {:style {:flex-grow "1" :padding ".3rem" :width "100%" :border-bottom "0.5px" :border-style "ridge" :text-align "center"}
                 :on-click #(key-action/on-normal-key "᠂")}
          [:span {:style {:writing-mode "vertical-lr"}} "᠂"]]
         [:span {:style {:flex-grow "1" :padding ".3rem" :width "100%" :border-bottom "0.5px" :border-style "ridge" :text-align "center"}
                 :on-click #(key-action/on-normal-key "᠃")}
          [:span {:style {:writing-mode "vertical-lr"}} "᠃"]]
         [:span {:style {:flex-grow "1" :padding ".3rem" :width "100%" :border-bottom "0.5px" :border-style "ridge" :text-align "center"}
                 :on-click #(key-action/on-normal-key "?")}
          [:span {:style {:writing-mode "vertical-lr"}} "?"]]
         [:span {:style {:flex-grow "1" :padding ".3rem" :width "100%" :border-bottom "0.5px" :border-style "ridge" :text-align "center"}
                 :on-click #(key-action/on-normal-key "!")}
          [:span {:style {:writing-mode "vertical-lr"}} "!"]]]]
       [:div.hg-row {:style {:flex-grow "1"}}
        [:div.hg-button {:style {:height "100%"}}
         [:span "123"]]]]

      [:div {:style {:display "flex" :flex-direction "row" :flex-grow "4.5"
                     :margin "0 2px"}}
       [:div {:style {:display "flex" :flex-direction "column" :margin "0 1px 4px 1px"}}
        [:div.hg-button.hg-standardBtn [:span "1"]]
        [:div.hg-button.hg-standardBtn
         [:div {:style {:display "flex" :flex-direction "row" :align-items "center" :font-size "12px"}
                :on-click #(key-action/on-key "abc")}
          [:span "2"]
          [:span "ABC"]]]
        [:div.hg-button.hg-standardBtn
         [:div {:style {:display "flex" :flex-direction "row" :align-items "center" :font-size "12px"}
                :on-click #(key-action/on-key "def")}
          [:span "3"]
          [:span "DEF"]]]]
       [:div {:style {:display "flex" :flex-direction "column" :margin "0 1px 4px 1px"}}
        [:div.hg-button.hg-standardBtn
         [:div {:style {:display "flex" :flex-direction "row" :align-items "center" :font-size "12px"}
                :on-click #(key-action/on-key "ghi")}
          [:span "4"]
          [:span "GHI"]]]
        [:div.hg-button.hg-standardBtn
         [:div {:style {:display "flex" :flex-direction "row" :align-items "center" :font-size "12px"}
                :on-click #(key-action/on-key "jkl")}
          [:span "5"]
          [:span "JKL"]]]
        [:div.hg-button.hg-standardBtn
         [:div {:style {:display "flex" :flex-direction "row" :align-items "center" :font-size "12px"}
                :on-click #(key-action/on-key "mno")}
          [:span "6"]
          [:span "MNO"]]]]
       [:div {:style {:display "flex" :flex-direction "column" :margin "0 1px 4px 1px"}}
        [:div.hg-button.hg-standardBtn
         [:div {:style {:display "flex" :flex-direction "row" :align-items "center" :font-size "12px"}
                :on-click #(key-action/on-key "pqrs")}
          [:span "7"]
          [:span "PQRS"]]]
        [:div.hg-button.hg-standardBtn
         [:div {:style {:display "flex" :flex-direction "row" :align-items "center" :font-size "12px"}
                :on-click #(key-action/on-key "tuv")}
          [:span "8"]
          [:span "TUV"]]]
        [:div.hg-button.hg-standardBtn
         [:div {:style {:display "flex" :flex-direction "row" :align-items "center" :font-size "12px"}
                :on-click #(key-action/on-key "wxyz")}
          [:span "9"]
          [:span "WXYZ"]]]]
       [:div {:style {:display "flex" :flex-direction "column" :margin "0 1px 0 1px"}}
        [:div.hg-button.hg-standardBtn
         [:span {:style {:writing-mode "vertical-lr"}} "?"]
         [:span {:style {:writing-mode "vertical-lr"}} "!"]]
        [:div.hg-button.hg-standardBtn
         {:on-click #(key-action/on-space)}
         [:span "⌞⌟"]]
        [:div.hg-button.hg-standardBtn
         { :on-click #(reset! aa/keyboard-layout 1)}
         [:span "abc"]]]]
      ; [:div.hg-button {:style {:flex-grow "1" :height "9.56rem" :margin-left "2px"}} [:span "3"]]
      [:div {:style {:flex-grow "1" :height "9.56rem" :margin-left "2px"
                               :display "flex" :flex-direction "row"}}
       [:div.hg-row {:style {:flex-grow "1"}}
        [:div.hg-button {:style {:height "100%"}
                         :on-click #(key-action/on-delete)}
         [:span  "⌫"]]]
       [:div.hg-row {:style {:flex-grow "1"}}
        [:div.hg-button {:style {:height "100%"}
                         :on-click #(key-action/on-clear)}
         [:span "☐"]]]
       [:div.hg-row {:style {:flex-grow "2.55"}}
        [:div.hg-button {:style {:height "100%"}
                         :on-click #(key-action/on-return)}
         [:span "←"]]]]]]))

(defn index []
  (fn []
    [:div.simple-keyboard-wrapper
     (condp = @aa/keyboard-layout
       1 [eng-board]
       2 [nine-layout-board]
       [eng-board])]))

(defn index2 []
  (fn []
    [:div.simple-keyboard-wrapper
     [:div.simple-keyboard-preview]
     [nine-layout-board]]))
     ; [eng-board]]))
