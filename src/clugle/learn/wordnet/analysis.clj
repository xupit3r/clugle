(ns clugle.learn.wordnet.analysis
  (:require [clojure.string :as str]
            [clugle.learn.text.preprocess
             :refer [remove-punc
                     get-stops
                     tokenize]]
            [clugle.learn.wordnet.db
             :refer [get-entries
                     get-ref]]))

;; replace stop words with empty strings...
(defn replace-stops [tokens]
  (let [stops (get-stops :english)]
    (mapv #(if (contains? stops %) "" %) tokens)))

;; recursively collects adjacent tokens into a group
;; from a given starting point in a sequence
(defn groups [collected tokens]
  (if (str/blank? (first tokens))
    collected
    (groups (conj collected (first tokens))
            (rest tokens))))

;; walks over a sequence and creates groups
;; of continuous tokens
(defn create-groups [tokens]
  (for [i (range 0 (count tokens))
        :let [tkn (nth tokens i "")]]
    (if (str/blank? tkn) []
      (groups [tkn]
              (nthrest tokens (+ i 1))))))

;; creates the string combos for a given group
(defn combos [groups]
  (mapv #(vec
          (reverse
           (for [i (range 0 (count %))]
             (str/join "_" (subvec % 0 (+ i 1))))))
        groups))

;; will find adjacent tokens and create possible
;; wordnet lookups amongst the tokens
(defn build-lookups [tokens]
  (->> tokens
       (create-groups)
       (filterv seq)
       (combos)))

;; performs some basic cleanup on the provided
;; sentence
(defn prepare-sentence [sentence]
  (->>
   sentence
   str/lower-case
   remove-punc
   tokenize
   replace-stops
   build-lookups))

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