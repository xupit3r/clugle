(ns clugle.api.base
    (:require [compojure.core :refer [defroutes GET]]
              [compojure.route :refer [not-found]]
              [clugle.api.utils :refer [handler]]
              [clugle.web.page :refer [process]]
              [clugle.learn.text.base :refer [weighted ngram]]))

(defroutes api-routes
  (GET "/api/web/page" [] 
    (handler process {:url str :tags str}))
  (GET "/api/learn/text/weighted" [] 
    (handler weighted {:text str}))
  (GET "/api/learn/text/ngram" [] 
    (handler ngram {:text str :n (fn [v] (Integer/parseInt v))}))
  (not-found "no endpoint, bruh..."))
