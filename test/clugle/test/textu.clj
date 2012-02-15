(ns clugle.test.textu
  (:use [clojure.test])
  (:use [clugle.util.textu]))


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
    (is (= '(["joe" 1] ["is" 1] ["joe" 1]) 
           (tokenize text_space)))
    (is (= '(["joe" 1] ["is" 1] ["joe" 1]) 
           (tokenize text_pipe :pipe)))
    (is (= '(["joe" 1] ["is" 1] ["joe" 1]) 
           (tokenize text_pound :pound)))
    (is (= '(["joe" 1] ["is" 1] ["joe" 1]) 
           (tokenize text_percent :percent)))
    (is (= '(["joe" 1] ["is" 1] ["joe" 1]) 
           (tokenize text_tab :tab)))
    (is (= '(["joe" 1] ["is" 1] ["joe" 1]) 
           (tokenize text_caret :caret)))
    (is (= '(["joe" 1] ["is" 1] ["joe" 1]) 
           (tokenize text_period :period)))
    (is (= '(["joe" 1] ["is" 1] ["joe" 1]) 
           (tokenize text_comma :comma)))))

;; test the grouping of word instances
(deftest test-group-instances []
  (let [tokens_dbl_joe (tokenize "joe is joe")
        tokens_caps_joe (tokenize "joe is JOE is JoE is joe")]
    
    (is (=  (group-instances tokens_dbl_joe)
            {"joe" '(1 1), "is" '(1)}))
    (is (= (group-instances tokens_caps_joe)
           {"joe" '(1 1), "is" '(1 1 1), "JOE" '(1), "JoE" '(1)}))))

;; test the extraction of word frequencies
(deftest test-word-freq []
  (is (= (word-freq "joe is joe")
         {"joe" 2, "is" 1})))
    

