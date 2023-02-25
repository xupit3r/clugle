(ns clugle.api.base
    (:require [compojure.core :refer [defroutes, GET]]
              [compojure.route :as route]
              [cheshire.core :refer [generate-string]]
              [clugle.web.http :refer [request-get]]))

(defn extract-params [params, req]
  (map (fn [param] (get (:params req) param)) params))

(defn handler-wrapper [fun params]
  (fn [req]
    (let [resp (apply fun (extract-params params req))]
      {:status  200
       :headers {"Content-Type" "text/json"}
       :body  (generate-string resp)})))

(defroutes api-routes
  (GET "/api/http/url" [] (handler-wrapper request-get [:url]))
  (route/not-found "Error, page not found!"))
