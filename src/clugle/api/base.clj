(ns clugle.api.base
    (:require [clugle.api.types :as types]
              [clugle.api.utils :refer [handler]]
              [clugle.learn.matrix.base :refer [m-add]]
              [clugle.learn.text.base :refer [ngram score-sentiment weighted]]
              [clugle.web.page :refer [process]]
              [clugle.learn.wordnet.analysis :refer [lookup-words]]
              [compojure.core :refer [defroutes GET]]
              [compojure.route :refer [not-found]]))

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
  (GET "/api/learn/text/sentiment/score" []
    (handler score-sentiment
             {:text types/string}))
  (GET "/api/learn/matrix/add" []
    (handler m-add
             {:m1 types/json
              :m2 types/json}))
  (GET "/api/learn/wordnet/words/sentence" []
    (handler lookup-words
             {:sentence types/string}))
  (not-found "no endpoint, bruh..."))

;; updating swim
;; updating swim
;; updating base