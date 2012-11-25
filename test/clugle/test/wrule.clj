(ns clugle.test.wrule
  (:require [clugle.learn.which.wrule :as rule])
  (:use [clojure.test]))

;; list of rules that we will
;; use to test stack operations
(def TEST_RULES
  {:rule1 (struct-map 
            rule/wrule
            :rule {:a #{2}})
   :rule2 (struct-map 
            rule/wrule
            :rule {:a #{3} :b #{2}})
   :rule3 (struct-map
            rule/wrule
            :rule {:a #{2 3} :b #{1 2 3}})
   :rule4 (struct-map
            rule/wrule
            :rule {:a #{2}})
   :rule5 (struct-map
            rule/wrule
            :rule {:a #{3 2} :b #{3 1 2}})
   :rule6 (struct-map
            rule/wrule
            :rule {:b #{3}})})

;; test the rule equality logic
(deftest test-rule-eql []
    (is (not (rule/eql (:rule1 TEST_RULES) 
                  (:rule2 TEST_RULES))))
    (is (not (rule/eql (:rule1 TEST_RULES)
                  (:rule3 TEST_RULES))))
    (is (not (rule/eql (:rule2 TEST_RULES)
                       (:rule3 TEST_RULES))))
    (is (rule/eql (:rule1 TEST_RULES) 
             (:rule4 TEST_RULES)))
    (is (rule/eql (:rule3 TEST_RULES)
             (:rule5 TEST_RULES))))

;; test the combining of rules to form
;; a new rule
(deftest test-combine []
  (is (rule/eql (rule/combine (:rule1 TEST_RULES)
                              (:rule2 TEST_RULES))
                (struct-map
                  rule/wrule
                  :rule {:a #{2 3} :b #{2}})))
  (is (rule/eql (rule/combine (:rule2 TEST_RULES)
                              (:rule6 TEST_RULES))
                (struct-map
                  rule/wrule
                  :rule {:a #{3} :b #{2 3}})))
  (is (rule/eql (rule/combine (:rule3 TEST_RULES)
                              (:rule4 TEST_RULES))
                (struct-map
                  rule/wrule
                  :rule {:a #{2 3} :b #{1 2 3}}))))

