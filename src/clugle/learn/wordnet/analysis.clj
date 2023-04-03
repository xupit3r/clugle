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
(defn prepare-word-entries [token-obj]
  (assoc token-obj :entries
         (pmap
          #(assoc % :refs (resolve-refs (:refs %)))
          (:entries token-obj))))

;; grabs the most likely entries set amongs
;; a set of possible candidates
(defn get-likely-entries [candidates]
  (->> candidates
       (pmap #(hash-map :token % :entries (get-entries %)))
       (some #(if (seq (:entries %)) % false))))

;; simple function to pull a token from the
;; token candidate list
(defn pull-token [idx tokens]
  (get (nth tokens idx {}) :token ""))

;; simplifies our list by removing any entries that 
;; are subsumed by a previous word/phrase
(defn simplify [tokens]
  (for [i (range 0 (count tokens))
        :let [tkn (pull-token i tokens)
              prv (pull-token (- i 1) tokens)]
              :when (nil? (str/index-of prv tkn))]
    (get tokens i)))

;; given a setence, this will assign parts
;; of speech to all known words
(defn lookup-words [sentence]
  (->> sentence
       (prepare-sentence)
       (mapv get-likely-entries)
       (simplify)
       (mapv prepare-word-entries)))