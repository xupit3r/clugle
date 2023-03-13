(ns clugle.test.preprocess
  (:require [clojure.test :refer [deftest is]]
            [clugle.learn.text.utils :refer [tokenize]]
            [clugle.learn.text.preprocess :refer [remove-stops]]))

(deftest test-remove-stops []
  (is (= (-> "the sentence is this, i am removing stopwords, bruh"
             tokenize
             remove-stops)
         ["sentence" "this," "removing" "stopwords," "bruh"])))