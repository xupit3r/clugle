(ns clugle.test.wstack
  (:require [clugle.learn.which.wrule :as rule]
            [clugle.learn.which.wstack :as stack])
  (:use [clojure.test]))

;; list of rules that we will
;; use to test stack operations
(def TEST_RULES
  [(struct-map 
     rule/wrule
     :rule {:a #{2}}
     :score 0.96)
   (struct-map 
     rule/wrule
     :rule {:a #{2} :b #{2}}
     :score 0.78)
   (struct-map
     rule/wrule
     :rule {:a #{2 3} :b #{1 2 3}}
     :score 0.50)
   (struct-map
     rule/wrule
     :rule {:b #{3}}
     :score 0.90)])

(def TEST_STACK_MAX_SIZE 100)

;; test the stack addition function
(deftest test-stack-add []
  (loop [idx 0 stk []]
    (if (< idx (count TEST_RULES))
      (recur (inc idx) 
             (stack/wadd
               (nth TEST_RULES idx)
               stk
               TEST_STACK_MAX_SIZE))
      (do
        (is (= (:score (nth stk 0)) 0.96))
        (is (= (:score (nth stk 1)) 0.90))
        (is (= (:score (nth stk 2)) 0.78))
        (is (= (:score (nth stk 3)) 0.50))))))

;; test the find-total function.  this
;; function is expected to return the 
;; sum total of scores for the stack
(deftest test-find-total []
  (loop [idx 0 stk []]
    (if (< idx (count TEST_RULES))
      (recur (inc idx) 
             (stack/wadd
               (nth TEST_RULES idx)
               stk
               TEST_STACK_MAX_SIZE))
      (do
        (is (= (:score (stack/find-total stk) 3.14)))))))

;; tests the pick1 function of the stack.
;; this function should select a "random"
;; rule from the stack. (NOTE: this test
;; relies on a random generator seed 
;; (clugle.util.prob/RANNUM_SEED) being set
;; to 1024).
(deftest test-pick1 []
  (loop [idx 0 stk []]
    (if (< idx (count TEST_RULES))
      (recur (inc idx) 
             (stack/wadd
               (nth TEST_RULES idx)
               stk
               TEST_STACK_MAX_SIZE))
      (do
        (is (= (:score (stack/pick1 stk) 0.96)))
        (is (= (:score (stack/pick1 stk) 0.50)))
        (is (= (:score (stack/pick1 stk) 0.50)))
        (is (= (:score (stack/pick1 stk) 0.90)))))))
