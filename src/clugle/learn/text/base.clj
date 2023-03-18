(ns clugle.learn.text.base
  (:require [clojure.math :as math]
            [clugle.learn.text.preprocess :refer [normalize remove-punc
                                                  tokenize]]
            [clugle.learn.text.sentiment :refer [lexicon-score]]
            [clugle.util.hlpr :refer [apply-mf maxv vec-range]]))


;; builds a frequency count
;; of words appearing in a string
(defn termfreq [str]
  (-> str tokenize frequencies))

;; creates a weighted frequency map
;; this uses an augmented frequency,
;; using the max frequency to weigh
;; the frequencies relative to the total
;; string
(defn weighted [str]
  (let [freqs (termfreq str)
        maxf (-> freqs vals maxv)]
    (apply-mf
     freqs
     (fn [v]
       (+ 0.5 (* 0.5 (/ v maxf)))))))

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

;; cleans the docs and calculates weighted
;; term frequencies for each doc
(defn doc-weights [docs]
  (map weighted 
       (map remove-punc
            docs)))

;; docs that contain a specified term
(defn docs-with [docs term]
  (filter (fn [d] (get d term)) docs))

;; returns a function that will calculate
;; the tfidf for a token on a given a doc
(defn calc-tfidf [token weights]
   (fn [doc]
     (let [token-docs (docs-with weights token)]
       (if (empty? token-docs)
         0
         (* (get doc token 0)
            (math/log10
             (/
              (count weights)
              (count token-docs))))))))

;; run tfidf on a term and set of docs
(defn tfidf [term docs]
  (let [weights (doc-weights docs)
        tokens (tokenize term)]
    (apply merge (for [token tokens]
           {(keyword token)
            (vec (map 
                  (calc-tfidf token weights) 
                  weights))}))))

;; performs a sentiment calculation on the 
;; provided string
(defn score-sentiment [str]
  (-> str
      normalize
      lexicon-score))
