(ns clugle.learn.testing.sentiment
  (:require [clugle.learn.data.datasets :refer [load-dataset]]
            [clugle.learn.text.preprocess :refer [normalize]]
            [clugle.learn.text.sentiment :refer [lexicon-score]]))

(defn get-lexicon-sentiment [{text :text}]
  (if (nil? text) nil
      (let [score (-> text normalize lexicon-score)]
        (cond
          (< score 0) "negative"
          (> score 0) "positive"
          :else "neutral"))))

(defn lexicon []
  (let [dataset (load-dataset :twitter-airline)]
    (reduce
     #(assoc
       %1
       :total (inc (:total %1))
       :correct (+ (:correct %1) (if (= (%2 0) (%2 1)) 1 0)))
     {:total 0 :correct 0}
     (mapv
      #(vec [(:airline_sentiment %1) (get-lexicon-sentiment %1)])
      dataset))))