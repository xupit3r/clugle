(ns clugle.test.textu
  (:use [clojure.test])
  (:use [clugle.textu]))


;; test tokenize
(deftest test-tokenize []
  (let [text_space "joe is joe"
        text_pipe "joe|is|joe"
        text_pound "joe#is#joe"
        text_percent "joe%is%joe"
        text_tab "joe\tis\tjoe"
        text_caret "joe^is^joe"
        text_period "joe.is.joe"
        text_comma "joe,is,joe"]
    (is (= '(["joe" 1] ["is" 1] ["joe" 1]) (tokenize text_space)))
    (is (= '(["joe" 1] ["is" 1] ["joe" 1]) (tokenize text_pipe :pipe)))
    (is (= '(["joe" 1] ["is" 1] ["joe" 1]) (tokenize text_pound :pound)))
    (is (= '(["joe" 1] ["is" 1] ["joe" 1]) (tokenize text_percent :percent)))
    (is (= '(["joe" 1] ["is" 1] ["joe" 1]) (tokenize text_tab :tab)))
    (is (= '(["joe" 1] ["is" 1] ["joe" 1]) (tokenize text_caret :caret)))
    (is (= '(["joe" 1] ["is" 1] ["joe" 1]) (tokenize text_period :period)))
    (is (= '(["joe" 1] ["is" 1] ["joe" 1]) (tokenize text_comma :comma)))))

