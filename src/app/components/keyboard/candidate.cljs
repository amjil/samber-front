(ns app.components.keyboard.candidate
  (:require [reagent.core :as r]
            [app.components.atom :refer [quill-editor cand-list key-list]]
            [app.components.keyboard.key-action :as key-action]
            [app.components.keyboard.http :as http]))

; (def cand-list (r/atom []))

(defn view []
  (fn []
    [:div.simple-keyboard-candidate
     {:style {:opacity "1"}}
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
                     :padding-bottom "1rem"
                     :font-size "20px"}}
        (for [cand @cand-list]
          ^{:key (str (:tb cand) (:id cand))}
          [:li
           {:on-click (fn [e] (key-action/on-candidate-select (:char_word cand))
                              (http/update-order (:id cand) (:giglgc cand))
                              (http/next-words (:giglgc cand) (:id cand) #(reset! cand-list %)))}
           (:char_word cand)])])]))
