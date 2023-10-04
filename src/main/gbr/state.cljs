
(ns gbr.state
  (:require [reagent.core :as r]))

;; the application state 
(def state (r/atom { :cart [] :on-home-page true }))

