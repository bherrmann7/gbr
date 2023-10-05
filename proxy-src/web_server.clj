

(ns web-server
  (:require [ring.adapter.jetty]
            [babashka.curl :as curl]))

;; This acts as a proxy to allow the hosted web page to access the
;; Giantbomb API.  This is needed because of CORS.

(def GIANT_BOMB_KEY (slurp (.trim (str (System/getProperty "user.home") "/.giant-bomb-key"))))
(def auth-param (str "&api_key=" GIANT_BOMB_KEY))

(defn handle-api-request [request]
  (let [{:keys [uri query-string]} request
        resource-path (str uri "?" query-string)]
    {:status 200
     :body (:body (curl/get (str "https://www.giantbomb.com" resource-path auth-param)))}))

;; lock down resource snooping
(def allowed-resources #{"/gbr.png",
                         "/"
                         "/js/main.js"})

(defn handle-local-request [{:keys [uri]}]
  (if (contains? allowed-resources uri)
    (let [uri-cooked (if (= uri "/") "/index.html" uri)]
      {:status 200
       :headers {"Content-Type" (if (.endsWith uri-cooked ".png") "image/png" "text/html")}
       :body (java.io.File. (str "public" uri-cooked))})
    {:status 404
     :headers {"Content-Type" "text/html"}
     :body "Invalid resource."}))

(defn handler [request]
  (if (.startsWith (:uri request) "/api")
    (handle-api-request request)
    (handle-local-request request)))

(defn start []
  (println "Listenting for web connections at :7878")
  (ring.adapter.jetty/run-jetty #(handler %1) {:port 7878 :join? false}))

(start)

