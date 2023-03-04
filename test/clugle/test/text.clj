(ns clugle.test.text
  (:require [clojure.test :refer [deftest is]]
            [clugle.learn.text.base :refer [tokenize termfreq weighted ngram]]))

(def expected-tokens ["joe" "is" "joe"])


;; test the tokenizing of a string
(deftest test-tokenize []
  (let [text_space "joe is joe"
        text_pipe "joe|is|joe"
        text_pound "joe#is#joe"
        text_percent "joe%is%joe"
        text_tab "joe\tis\tjoe"
        text_caret "joe^is^joe"
        text_period "joe.is.joe"
        text_comma "joe,is,joe"]
    (is (= expected-tokens 
           (tokenize text_space)))
    (is (= expected-tokens 
           (tokenize text_pipe :pipe)))
    (is (= expected-tokens 
           (tokenize text_pound :pound)))
    (is (= expected-tokens 
           (tokenize text_percent :percent)))
    (is (= expected-tokens 
           (tokenize text_tab :tab)))
    (is (= expected-tokens 
           (tokenize text_caret :caret)))
    (is (= expected-tokens 
           (tokenize text_period :period)))
    (is (= expected-tokens 
           (tokenize text_comma :comma)))))


;; test the extraction of word frequencies
(deftest test-word-freq []
  (is (= (termfreq "joe is joe")
         {"joe" 2, "is" 1})))

;; test the weighting of word frequencies
(deftest test-weighted []
  (is (= (weighted "joe is joe")
         {"joe" (/ 2 3), "is" (/ 1 3)})))

;; super simple test for ngram
(def expected-ngram
  [["joe" "is" "typing" "this"]
   ["is" "typing" "this" "sentence"]
   ["typing" "this" "sentence" ""]
   ["this" "sentence" "" ""]])

(deftest test-ngram []
  (is (= expected-ngram 
         (ngram "joe is typing this sentence" 4))))
    
