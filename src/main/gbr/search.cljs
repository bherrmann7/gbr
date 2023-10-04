
(ns gbr.search
    (:require
            [gbr.state :refer [state]]
            [ajax.core :refer [GET]]))

(defn handler [resp]
  (let [results (get resp "results")]
    (swap! state assoc :games results :total-found (get resp "number_of_total_results"))))

(defn error-handler [resp]
  (js/alert (str "Search error " resp)))

(defn search-for [ search-text ]
  (GET (str "/api/search/?format=json&query=" search-text "&resources=game")
       {:handler handler
        :format :json
        :error-handler error-handler
        :response-format :json}))

