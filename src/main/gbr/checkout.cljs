
(ns gbr.checkout
  (:require [gbr.state :refer [state]])
  (:import  [goog.i18n NumberFormat]
            [goog.i18n currency]))

(defn compute-and-format-total []
  (let [fmt (NumberFormat. (.getLocalCurrencyPattern currency "USD"))]
    (.format fmt (* 1.99 (count (:cart @state))))))

(defn go-back-to-home []
  (swap! state assoc :on-home-page true))


;;
;; NOTE This checkout block draws upon the boostrap example, https://getbootstrap.com/docs/5.0/examples/checkout/
;; 

(defn checkout []
  [:div
   {:class "container"}
   [:div
    {:class "py-5 text-center"}
    [:img
     {:class "d-block mx-auto mb-4",
      :src
      "gbr.png"}]
    [:h2 "Checkout Form"]
    [:p
     {:class "lead"}]]
   [:div
    {:class "row"}
    [:div
     {:class "col-md-4 order-md-2 mb-4"}]
    [:div
     {:class "col-md-4 order-md-2 mb-4"}
     [:h4
      {:class "d-flex justify-content-between align-items-center mb-3"}
      [:span {:class "text-primary"} "Your cart" ]
      [:span {:class "badge bg-primary rounded-pill"} (count (:cart @state))]]
     [:ul
      {:class "list-group mb-3"}
      (for [cart-item (:cart @state)]
        [:li
         {:key (first cart-item)
          :class
          "list-group-item d-flex justify-content-between lh-condensed"}
         [:div
          [:h6 {:class "my-0"} (second cart-item)]
          [:small {:class "text-muted"} (first cart-item)]]
         [:span {:class "text-muted"} "$1.99"]])
      [:li
       {:class "list-group-item d-flex justify-content-between"}
       [:span "Total (USD)"]
       [:strong (compute-and-format-total)]]]

     [:div
      [:button.btn.btn-primary "Rent"] " "
      [:button.btn.btn-secondary {:on-click go-back-to-home} "Back"]]]]])
