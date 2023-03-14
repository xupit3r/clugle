(ns clugle.test.preprocess
  (:require [clojure.test :refer [deftest is]]
            [clugle.learn.text.preprocess :refer [remove-punc remove-stops]]
            [clugle.learn.text.utils :refer [tokenize]]))

(deftest test-remove-stops []
  (is (= (-> "the sentence is this, i am removing stopwords, bruh"
             tokenize
             remove-stops)
         ["sentence" "this," "removing" "stopwords," "bruh"])))

(deftest test-remove-punc []
  (is (= (remove-punc
          "joe is typing this, now, and it has puncuation.")
         "joe is typing this now and it has puncuation")))