(ns clugle.api.base
    (:require [compojure.core :refer [defroutes, GET]]
              [compojure.route :as route]
              [cheshire.core :refer [generate-string]]
              [clugle.web.http :refer [get-request]]))

(defn handler-wrapper [fun param]
  (fn [req]
    (let [resp (fun (get (:params req) param))]
      {:status  200
       :headers {"Content-Type" "text/json"}
       :body  (generate-string resp)})))

(defroutes api-routes
  (GET "/api/url" [] (handler-wrapper get-request :url))
  (route/not-found "Error, page not found!"))
