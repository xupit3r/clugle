(ns clugle.test.service
    (:require [clojure.test :refer [deftest is]]
              [clugle.web.service :refer [call-api]]))

;; some mock functions to test 
;; the API request process
(defn mock-req-func [url]
  (list url "mrqf"))

(defn mock-resp-func [data]
  (conj data "mrpf"))

(defn mock-canon-func [data]
  (conj data "mcf"))

;; for now, I just want to make sure that 
;; the function will run without error
(deftest test-call-api []
  (let [mock-api-result 
        (call-api mock-req-func
                  mock-resp-func
                  mock-canon-func
                  "A_URL")]
    (is (= '("mcf" "mrpf" "A_URL" "mrqf")
           mock-api-result))))
            


