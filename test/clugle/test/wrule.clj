(ns clugle.test.wrule
  (:use [clojure.test])
  (:use [clugle.learn.which.wrule]))

(deftest test-rule-eql []
  (let [r1 [(list :a 3 4 5) (list :c 3) (list :b 4 5)]
        r2 [(list :b 4 5)]
        r3 [(list :b 4 5)]]
    (is (not (eql r1 r2)))
    (is (not (eql r1 r3)))
    (is (eql r2 r3))))

