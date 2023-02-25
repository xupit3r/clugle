(ns clugle.learn.which.wrule
  (:require [clojure.set :refer [difference union]]))

;; the WHICH rule definition
;; a rule is defined as a map 
;; whose set of keys represent 
;; the attributes observed by 
;; the rule.  each attribute is
;; mapped to a set of possible
;; values:
;; {:a #{1 2}
;;  :b #{1}}
(def wrule (create-struct
             :rule
             :score
             :gkl
             :atts
             :stats
             :comp))

;; declare the functions in this file
(declare and-reduce! eql combine)

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
(defn eql [{r1 :rule}
           {r2 :rule}]
  (if (not= (count (keys r1))
            (count (keys r2)))
    false 
    (reduce 
      and-reduce!
      (map (fn [[k v]]
             (empty?
               (difference 
                 (union v (k r2)) v)))
           r1))))

;; combines to supplied rules
;; to form a new rule.
(defn combine [{r1 :rule} 
               {r2 :rule}]
  (struct-map
    wrule
    :rule (merge-with union r1 r2)))