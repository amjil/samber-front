(ns app.components.keyboard.candidate
  (:require [reagent.core :as r]
            [app.components.atom :refer [quill-editor cand-list key-list]]))

; (def cand-list (r/atom []))

(defn on-click [item]
  ())

(defn view []
  (fn []
    [:div#candidate {:style {;:display "block"
                             :display "flex"
                             :flex-direction "row"
                             :position "absolute" :left "0" :bottom "182px"
                             :width "100%"
                             :padding-top ".2rem"
                             :overflow "hidde"
                             ; :background-color "lightblue"
                             :box-shadow "2px -2px 3px #aaaaaa"
                             :opacity ".8"}}
     (if-not (empty? @key-list)
       [:ul {:style {:overflow "auto" :width "100%"
                     :writing-mode "horizontal-tb"
                     :display "flex"; :flex-direction "column"}}
                     :padding-bottom "1rem"}}
        [:li {:style {:writing-mode "horizontal-tb"
                        :padding-left ".2rem"}}
          "["]
        (for [kk @key-list]
          ^{:key kk}
          [:li {:style {:writing-mode "horizontal-tb"
                        :padding-left ".2rem"}}
            kk])
        [:li {:style {:writing-mode "horizontal-tb"
                        :padding-left ".2rem"}}
          "]"]])
     (if-not (empty? @cand-list)
       [:ul {:style {:overflow "auto" :width "100%"
                     :padding-bottom "1rem"}}
        (for [cand @cand-list]
          ^{:key (str (:tb cand) (:id cand))}
          [:li (:char_word cand)])])]))
