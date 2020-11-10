(ns mathasaservice.webapp
  (:require
   [mathasaservice.parser :as parser]
   [taoensso.timbre :as log]
   [ring.adapter.jetty :refer [run-jetty]]
   [compojure.core :refer [GET] :as compojure]
   [mount.core :refer [defstate]]
   [ring.util.response :refer [response]]
   [compojure.route :as route]
   [ring.middleware params keyword-params json]))

(defn try-start-app [app port]
  (try
    (run-jetty app {:port port :join? false})
    (catch java.net.BindException e
      (log/error e "Failed to start jetty" app port))))

(defn try-stop-app [jetty]
  (when-not (nil? jetty)
    (.stop jetty)))

(defn decode-base64
  "Compojure Helper: Decode base64 string"
  [s]
  (when (nil? s)
    (throw (IllegalArgumentException. "Invalid base64")))
  (String. (.decode (java.util.Base64/getDecoder) s)))

(defn wrap-exception [handler]
  (fn [request]
    (try
      (handler request)
      (catch Exception ex
        (log/debug ex "Exception during request" request)
        (response {:error true
                   :message (str ex)})))))

(def app-routes
  (compojure/routes
   (GET "/calculus" [query :<< decode-base64]
     (let [result (parser/parse-eval-infix-arith query)]
       (response {:error false
                  :result result})))
   (route/not-found (response {:error true :message "Not Found"}))))

(def app
  (->
   app-routes
   wrap-exception
   ring.middleware.keyword-params/wrap-keyword-params
   ring.middleware.params/wrap-params
   ring.middleware.json/wrap-json-response))

(defstate webapp
  :start (try-start-app app 2342)
  :stop (try-stop-app webapp))


