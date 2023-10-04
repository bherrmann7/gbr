
(ns gbr.home
  (:require [gbr.state :refer [state]]
            [gbr.search :refer [search-for]]))

(defn add-to-cart [id name]
  (swap! state #(assoc % :cart (conj (:cart %) [id name]))))

(defn remove-from-cart [id name]
  (swap! state #(assoc % :cart (remove (fn [x] (= x [id name])) (:cart %)))))

(defn proceed-to-checkout []
  (swap! state assoc :on-home-page false))

(defn search-keydown [evt]
  (if (= "Enter" (.-key evt))
    (let [search-text (.-value (.-target evt))]
      (search-for search-text))
    nil))

(defn in-cart? [id name]
  (let [items (:cart @state)]
    (some #(and (= id (first %)) (= name (second %))) items)))

(defn home []
  [:div.content
   [:img {:src "gbr.png"}]
   [:br]
   [:br]
   [:div.row
    [:div.col-md-2
     [:input.form-control {:on-key-press search-keydown :auto-focus true :size 20 :placeholder "Search for game by title"}]]
    [:div.col-md-6]
    [:div.col-md-2
     (if (empty? (:cart @state))
       "Cart is Empty."
       [:div "Cart has " (count (:cart @state)) " Items. "
        [:a.btn.btn-primary {:on-click proceed-to-checkout} "Proceed to checkout"]])]]
   [:br]
   (when (:total-found @state)
     [:h2 "Found " (:total-found @state) " games"])
   [:br]
   [:div.row.row-cols-5
    (let [games (:games @state)]
      (doall (for [g games]
        (let [{:strs [id name]} g]
          [:div.card.col {:key id :style {"width" "30rem"}}
           [:img.card-img-top {:height "330rem" :src (get-in g ["image" "small_url"])}]
           [:div.card-body
            (if (in-cart? id name)
              [:a.btn.btn-secondary.btn-sm {:on-click #(remove-from-cart id name) :style {:float "right"}} "Remove from Cart"]
              [:a.btn.btn-primary.btn-sm {:on-click #(add-to-cart id name) :style {:float "right"}} "Add to Cart"])
            [:h5.card-title name]]]))))]])
