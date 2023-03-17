(ns clugle.test.sentiment
  (:require [clojure.test :refer [deftest is]]
            [clugle.learn.text.sentiment :refer [get-sentiment]]))

(def sentiment-mapping (get-sentiment :english))

(deftest test-sentiment-mapping []
  (is (= (sentiment-mapping "euphoria") 3))
  (is (= (sentiment-mapping "resign") -1)))