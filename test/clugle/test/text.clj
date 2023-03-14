(ns clugle.test.text
  (:require [clojure.test :refer [deftest is]]
            [clugle.learn.text.base :refer [termfreq weighted ngram]]))

;; test the extraction of word frequencies
(deftest test-word-freq []
  (is (= (termfreq "joe is joe")
         {"joe" 2, "is" 1})))

;; test the weighting of word frequencies
(deftest test-weighted []
  (is (= (weighted "joe is joe")
         {"joe" 1.0 "is" 0.75})))

;; super simple test for ngram
(def expected-ngram
  [["joe" "is" "typing" "this"]
   ["is" "typing" "this" "sentence"]
   ["typing" "this" "sentence" ""]
   ["this" "sentence" "" ""]])

(deftest test-ngram []
  (is (= expected-ngram 
         (ngram "joe is typing this sentence" 4))))
    

