(ns clugle.learn.text.base
  (:require [clojure.string :refer [split]]
            [clugle.util.hlpr :refer [sum apply-mf vec-range]]))

;;;; logic for doing stuff with text

;; return a string representing 
;; the desired delimiter
(defn delim [dkey]
  (cond (= dkey :space) #"\s"
        (= dkey :pipe) #"\|"
        (= dkey :pound) #"#"
        (= dkey :percent) #"%"
        (= dkey :tab) #"\t"
        (= dkey :caret) #"\^"
        (= dkey :period) #"\."
        (= dkey :comma) #","))

;; creates a vector of tuples 
;; the first of each tuple is the 
;; token, the second is a talley of 
;; 1 (you will see what it is being used for)
(defn tokenize
  ([txt] (tokenize txt :space))
  ([txt delimiter] (split txt (delim delimiter))))

;; builds a frequency count
;; of words appearing in a string
(defn word-freq [str]
  (-> str tokenize frequencies))

;; creates a weighted frequency map
(defn weighted [str]
  (let [freqs (word-freq str)
        sum (-> freqs vals sum)]
    (apply-mf freqs (fn [v] (/ v sum)))))

;; build ngrams for a given string
;; return is a vector of vectors, where
;; each subvector is of length n and
;; represents an ngram from the source
;; string
(defn ngram [str n]
  (let [tokens (tokenize str)]
    (vec (for [i (vec-range tokens)] 
      (vec (for [j (range n)]
        (nth tokens (+ i j) "")))))))