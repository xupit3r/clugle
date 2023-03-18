(ns clugle.test.sentiment
  (:require [clojure.test :refer [deftest is]]
            [clugle.learn.text.sentiment :refer [get-sentiment sentiment-sum lexicon-score]]))

(def valences (get-sentiment :english))
(def positive ["look" "at" "these" "sweet" "cookies" "i" "will" "ignore"])
(def negative ["these" "cookies" "are" "dumb"])


(deftest test-sentiment-mapping []
  (is (= (valences "euphoria") 3))
  (is (= (valences "resign") -1)))

(deftest test-sentiment-sum []
  (is (< (sentiment-sum negative valences) 0))
  (is (= (sentiment-sum negative valences) -3))
  (is (> (sentiment-sum positive valences) 0))
  (is (= (sentiment-sum positive valences) 1)))

(deftest test-lexicon-score []
  (is (= (lexicon-score positive) (/ 1 8)))
  (is (= (lexicon-score negative) (/ -3 4))))