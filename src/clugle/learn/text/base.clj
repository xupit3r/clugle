(ns clugle.learn.text.base
  (:require [clojure.string :as str]
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
(defn calc-tf [docs]
  (map weighted 
       (map clean-text 
            docs)))

;; calculates the inverse term frequencies
;; for all terms in all docs
(defn calc-idf [weights]
  (map
   (fn [doc] 
     (reduce-kv
      (fn [m k v]
        (assoc m k (/
                    (count (filter (fn [d] (get d k)) weights))
                    (count weights))))
      {} doc)) 
   weights))

