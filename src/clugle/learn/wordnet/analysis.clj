(ns clugle.learn.wordnet.analysis
  (:require [clojure.string :as str]
            [clugle.learn.text.preprocess
             :refer [remove-punc
                     remove-stops
                     remove-whitespace
                     tokenize]]
            [clugle.learn.wordnet.db
             :refer [get-entries
                     get-ref]]
            [clugle.util.hlpr
             :refer [vec-range]]))


;; creates some groups out of the supplied tokens
(defn group-em [n tokens]
  (vec
   (for [i (vec-range tokens)]
     (vec
      (for [j (range n)]
        (nth tokens (+ i j) ""))))))

;; performs some basic cleanup on the provided
;; sentence
(defn prepare-sentence [sentence]
  (->>
   sentence
   str/lower-case
   remove-punc
   tokenize
   remove-stops
   remove-whitespace))

;; resolves a sequence of refs,
;; returning a vector of the resolved
;; references
(defn resolve-refs [refs]
  (filterv #(not (nil? %))
           (mapv #(get-ref %) refs)))

;; given a sequence of word entries, this will 
;; resolve references within those entries
(defn prepare-word-entries [word-entries]
  (mapv
   #(assoc % :refs (resolve-refs (:refs %)))
   word-entries))

;; given a setence, this will assign parts
;; of speech to all known words
(defn lookup-words [sentence]
  (->> sentence
       (prepare-sentence)
       (mapv get-entries)
       (mapv prepare-word-entries)))