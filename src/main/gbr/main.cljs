
(ns gbr.main
  (:require
   [reagent.dom :as rdom]
   [gbr.state :refer [state]]
   [gbr.home :refer [home]]
   [gbr.checkout :refer [checkout]]))

;; a very simple router
(defn page-choice []
  (if (:on-home-page @state)
    (home)
    (checkout)))

(defn ^:dev/after-load start []
  (rdom/render [page-choice] (js/document.getElementById "root")))

(defn init []
  (start))
