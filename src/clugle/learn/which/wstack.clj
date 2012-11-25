(ns clugle.learn.which.wstack
  (:require [clugle.util.hlpr :as hlpr]
            [clugle.util.prob :as prob]
            [clugle.learn.which.wrule :as wrule]))

;;;; code is a translation of the logic in the 
;;;; ICCLE (http://code.google.com/p/iccle/)

;;;; this is the stack portion of the WHICH 
;;;; learner (it is the heart and soul of the 
;;;; learner)

;; declare the functions in this file
(declare wadd wadd! rmlast pick1 pick1-worker find-total pick2)

;; add a rule to the stack
(defn wadd [rule stk n]
  (if (or (nil? stk) 
          (= (count stk) 0)) 
    [rule]
    (if (< (count stk) n)
      (wadd! rule (first stk) (rest stk) n)
      (when (> (:score rule) 
               (:score (last stk)))
        (wadd! rule (first stk) (drop-last (rest stk)) n)))))

;; determine the position of the rule within the
;; stack
(defn wadd! [rule1 rule2 stk n]
  (if (not (nil? (:score rule2)))
    (if (<= (:score rule1) 
            (:score rule2))
      (cons rule2 (wadd rule1 stk n))
      (cons rule1 (cons rule2 stk)))
    (cons rule1 stk)))

;; randomly select a rule from the WHICH
;; stack
(defn pick1 [stk]
  (pick1-worker
    (find-total stk)
    (/ (+ 1 (prob/mrand 100)) 
       100) stk))

;; helper function for carrying out the
;; selection of a rule from the WHICH stack
(defn pick1-worker [total enough stk]
  (if (nil? stk)
    nil
    (let [maybe (first stk)
          vrest (rest stk)
          score (/ (:score (first stk)) total)]
      (cond (> score enough) maybe
            (nil? vrest) maybe
            true (pick1-worker total
                               (- enough score)
                               vrest)))))

;; determine the sum total of
;; scores for rules on the 
;; stack
(defn find-total [stk]
  (->>
    (map #(:score %) stk)
    (apply +)))

;; selects two random rules
;; from the stack and combines
;; the rules, returning the result
(defn pick2 [stk]
  (wrule/combine
    (pick1 stk)
    (pick1 stk)))
