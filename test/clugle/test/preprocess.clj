(ns clugle.test.preprocess
  (:require [clojure.test :refer [deftest is]]
            [clugle.learn.text.preprocess 
             :refer [normalize
                     remove-punc
                     remove-stops
                     remove-whitespace
                     tokenize]]))

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


(deftest test-remove-stops []
  (is (= (-> "the sentence is this, i am removing stopwords, bruh"
             tokenize
             remove-stops)
         ["sentence" "this," "removing" "stopwords," "bruh"])))

(deftest test-remove-punc []
  (is (= (remove-punc
          "joe is typing this, now, and it has puncuation.")
         "joe is typing this now and it has puncuation")))

(deftest test-remove-whitespace []
  (is (= (-> "joe   is     typing    this"
             tokenize
             remove-whitespace)
         ["joe" "is" "typing" "this"])))

(deftest test-normalize []
  (is (= (normalize "Joe, Are YOU tyPing this n'at?")
         ["joe" "be" "you" "type" "this" "and that"])))