(ns clugle.test.textu
  (:require [clojure.test :refer [deftest is]]
            [clugle.util.textu :refer [tokenize word-freq]]))

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
  (is (= (word-freq "joe is joe")
         {"joe" 2, "is" 1})))
    

