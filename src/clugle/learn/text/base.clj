(ns clugle.learn.text.base
  (:require [clojure.string :as str]
            [clojure.math :as math]
            [clojure.java.io :refer [file]]
            [babashka.fs :refer [directory?]]
            [clugle.util.hlpr :refer [maxv apply-mf vec-range]]))

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

;; super simple for right now...
(defn clean-text [str]
  (str/replace str #"\." ""))

;; reads files into a list of strings
(defn doc-arr [docs] 
  (map slurp 
       (filter (fn [d] (not (directory? d))) docs)))

;; read in all the files within a directory
;; this reads them into an array of strings,
;; so for directories with lotsa files...
;; umm don't use this probably :)
(defn load-docs [dir]
  (-> dir file file-seq doc-arr vec))

;; creates a vector of tuples 
;; the first of each tuple is the 
;; token, the second is a talley of 
;; 1 (you will see what it is being used for)
(defn tokenize
  ([txt] (tokenize txt :space))
  ([txt delimiter] (str/split txt (delim delimiter))))

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
       (map clean-text 
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
