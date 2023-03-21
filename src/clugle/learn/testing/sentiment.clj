(ns clugle.learn.testing.sentiment
  (:require [clugle.learn.data.datasets :refer [load-dataset]]
            [clugle.learn.testing.base :refer [runner reporter]]
            [clugle.learn.text.preprocess :refer [normalize]]
            [clugle.learn.text.sentiment :refer [lexicon-score]]))

(defn lexicon-predictor [{text :text}]
  (if (nil? text) nil
      (let [score (-> text normalize lexicon-score)]
        (cond
          (< score 0) "negative"
          (> score 0) "positive"
          :else "neutral"))))

(defn lexicon []
  (-> {:dataset (load-dataset :twitter-airline)
       :predictor lexicon-predictor
       :actual :airline_sentiment}
      runner
      reporter))