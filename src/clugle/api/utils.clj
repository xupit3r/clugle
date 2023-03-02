(ns clugle.api.utils
  (:require [cheshire.core :refer [generate-string]]))

(defn extract-params [params req]
  (map (fn [key] 
         ((key params) (key (:params req)))) 
       (keys params)))

(defn handler [fun params]
  (fn [req]
    (let [resp (apply fun (extract-params params req))]
      {:status  200
       :headers {"Content-Type" "text/json"}
       :body  (generate-string resp)})))