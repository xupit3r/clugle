(ns clugle.test.stopwords
  (:require [clojure.test :refer [deftest is]]
            [clugle.learn.text.utils :refer [tokenize]]
            [clugle.learn.text.stopwords :refer [remove-stops]]))

(deftest test-remove-stops []
  (is (= (-> "the sentence is this, i am removing stopwords, bruh"
             tokenize
             remove-stops)
         ["sentence" "this," "removing" "stopwords," "bruh"])))