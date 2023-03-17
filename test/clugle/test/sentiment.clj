(ns clugle.test.sentiment
  (:require [clojure.test :refer [deftest is]]
            [clugle.learn.text.sentiment :refer [get-sentiment sentiment-sum]]))

(def sentiment-mapping (get-sentiment :english))

(deftest test-sentiment-mapping []
  (is (= (sentiment-mapping "euphoria") 3))
  (is (= (sentiment-mapping "resign") -1)))

(deftest test-sentiment-sum []
  (let [positive ["look" "at" "these" "sweet" "cookies" "i" "will" "ignore"]
        negative ["these" "cookies" "are" "dumb"]]
    (is (< (sentiment-sum negative) 0))
    (is (= (sentiment-sum negative) -3))
    (is (> (sentiment-sum positive) 0))
    (is (= (sentiment-sum positive) 1))))