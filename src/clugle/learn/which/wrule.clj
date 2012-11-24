(ns clugle.learn.which.wrule
  (:use [clojure.set])
  (:require [clugle.util.hlpr :as hlpr]))

;; the WHICH rule definition
;; a rule is defined as a vector
;; of lists.  the car of each list 
;; represents the the class, the cdr 
;; of each list represents possible 
;; values the class can have
(def wrule (create-struct
             :rule
             :score
             :gkl
             :atts
             :stats
             :comp))

;; converts a rule into something 
;; more useful for comparison
(defn rule-cnv [r]
  (into {} (map (fn [sr]
                  [(first sr) 
                   (into #{} (rest sr))]) r)))

;; to be used to reduce a 
;; set of boolean values to 
;; a scalar (boolean)
(defn and-reduce!
  ([a] (and-reduce! a true))
  ([a b] (and a b)))

;; rule equality comparison
;; two rules are equal if 
;; the difference of the 
;; set representation of each 
;; list is nil
(defn eql [r1 r2]
  (let [r1c (rule-cnv r1)
        r2c (rule-cnv r2)]
    (reduce and-reduce!  
            (map (fn [[k v]]
                   (empty? 
                     (difference v (k r2c))))
                 r1c))))