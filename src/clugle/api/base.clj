(ns clugle.api.base
    (:require [compojure.core :refer [defroutes GET]]
              [compojure.route :refer [not-found]]
              [clugle.api.types :as types]
              [clugle.api.utils :refer [handler]]
              [clugle.web.page :refer [process]] 
              [clugle.learn.text.base :refer [weighted ngram]]
              [clugle.learn.matrix.base :refer [m-add]]))

(defroutes api-routes
  (GET "/api/web/page" [] 
    (handler process 
             {:url types/string 
              :tags types/string}))
  (GET "/api/learn/text/weighted" [] 
    (handler weighted 
             {:text types/string}))
  (GET "/api/learn/text/ngram" [] 
    (handler ngram 
             {:text types/string 
              :n types/integer}))
  (GET "/api/learn/matrix/add" [] 
    (handler m-add 
             {:m1 types/json 
              :m2 types/json }))
  (not-found "no endpoint, bruh..."))
