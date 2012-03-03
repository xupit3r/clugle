(ns clugle.learn.which.wstack
  (:require [clugle.util.hlpr :as hlpr]
            [clugle.learn.which.wrule :as wrule]))

;;;; code is a translation of the logic in the 
;;;; ICCLE (http://code.google.com/p/iccle/)

;;;; this is the stack portion of the WHICH 
;;;; learner (it is the heart and soul of the 
;;;; learner)

;; no, really, we are going to add it, i swear!
(defn wadd! [rule1 rule2 stk n] 
  (if (<= (:score rule1) (:score rule2))
    (conj (list rule2) (wadd rule1 stk n))
    (conj (list rule1) (wadd rule2 stk n))))

;; add a rule to the stack
(defn wadd [rule stk n]
  (if (nil stk)
    (list rule)
    (if (< (hlpr/size stk) n)
      (wadd! rule (first stk) (rest stk) n)
      (when (> (:score rule) (:score (first (last stk))))
        (wadd! rule (first stk) (rest stk) n))
      (drop-last stk))))
        
      



