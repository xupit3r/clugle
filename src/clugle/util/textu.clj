(ns clugle.util.textu
  (:require [clugle.util.hlpr :refer [sum]]))



;;;; logic for doing stuff with text


;; return a string representing 
;; the desired delimiter
(defn delim [dkey]
  (cond (= dkey :space) "\\s"
        (= dkey :pipe) "\\|"
        (= dkey :pound) "#"
        (= dkey :percent) "%"
        (= dkey :tab) "\\t"
        (= dkey :caret) "\\^"
        (= dkey :period) "\\."
        (= dkey :comma) ","))


;; creates a vector of tuples 
;; the first of each tuple is the 
;; token, the second is a talley of 
;; 1 (you will see what it is being used for)
(defn tokenize 
  ([txt] (tokenize txt :space))
  ([txt delimiter]
    (let [tokens (.split txt (delim delimiter))]
      (map #(vector % 1) tokens))))

;; creates a grouping of all unique instances 
;; of words (tokens)
;; note: this assumes that we have a list 
;; of tuples that represent a tokenized version 
;; of a body of text
(defn group-instances [tokenized]
  (->> 
    (group-by first tokenized)
    (map (fn [[k v]]
           {k (map second v)}))
    (apply merge-with conj)))

;; sum up the value portion 
;; of a key value pair
(defn sum-v [[k v]]
  {k (sum v)})

;; build a final mapping of frequency 
;; counts of tokens (token -> frequency)
(defn build-freq-map [instances]
    (apply merge 
           (map sum-v instances)))

;; builds a frequency count
;; of words appearing in a string
(defn word-freq [str]
  (->> 
    (tokenize str)
    (group-instances)
    (build-freq-map)))

      
    

