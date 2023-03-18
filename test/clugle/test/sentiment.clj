(ns clugle.test.sentiment
  (:require [clojure.test :refer [deftest is]]
            [clugle.learn.text.sentiment :refer [get-sentiment lexicon-score]]))

(def valences (get-sentiment :english))
(def positive ["look" "at" "these" "sweet" "cookies" "i" "will" "ignore"])
(def negative ["these" "cookies" "are" "dumb"])


(deftest test-sentiment-mapping []
  (is (= (valences "euphoria") 3))
  (is (= (valences "resign") -1)))

(deftest test-lexicon-score []
  (is (= (lexicon-score positive) (* (/ 1 3) (/ 1 8))))
  (is (= (lexicon-score negative) (* (/ 1 3) (/ -3 4)))))