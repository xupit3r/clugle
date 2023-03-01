(ns clugle.api.base
    (:require [compojure.core :refer [defroutes, GET]]
              [compojure.route :refer [not-found]]
              [cheshire.core :refer [generate-string]]
              [clugle.web.page :refer [process]]
              [clugle.learn.text.words :refer [weighted]]))

(defn extract-params [params, req]
  (map (fn [param] (get (:params req) param)) params))

(defn handler [fun params]
  (fn [req]
    (let [resp (apply fun (extract-params params req))]
      {:status  200
       :headers {"Content-Type" "text/json"}
       :body  (generate-string resp)})))

(defroutes api-routes
  (GET "/api/web/page" [] (handler process [:url :tags]))
  (GET "/api/learn/text/weighted" [] (handler weighted [:text]))
  (not-found "Error, page not found!"))
