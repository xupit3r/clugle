(ns clugle.learn.wordnet.parsers
  (:require [clojure.string :as str]
            [clugle.learn.wordnet.mappings :refer [lexnames]]))

;; parses a line from an index file
(defn index-parser [line]
  (let [tokens (str/split line  #" ")
        p_cnt (Integer/parseInt (tokens 3))]
    {:lemma (tokens 0)
     :pos (tokens 1)
     :synset_cnt (Integer/parseInt (tokens 2))
     :p_cnt p_cnt
     :ptr_symbol (subvec tokens (if (> p_cnt 0) 4 3) (+ 4 p_cnt))
     :sense_cnt (Integer/parseInt (tokens (+ 4 p_cnt)))
     :tagsense_cnt (Integer/parseInt (tokens (+ 5 p_cnt)))
     :synset_offset (Integer/parseInt (tokens (+ 6 p_cnt)))}))

;; creates groups of size n for a supplied
;; collection
(defn groups [n col]
  (mapv
   #(subvec col % (+ % n))
   (take (Math/floor (/ (count col) n))
         (iterate #(+ % n) 0))))

;; parses the words, creating a hashmap
;; containing the word and the lookup
;; in the lex file
(defn parse-words [col]
  (->> col
      (groups 2)
      (mapv #(hash-map 
              :word (get % 0) 
              :lookup (str/join %)))))

(defn parse-refs [col]
  (->> col
       (groups 4)
       (mapv #(hash-map
               :symbol (get % 0)
               :offset (get % 1)
               :pos (get % 2)
               :target (get % 3)))))

;; parses a line from a data file 
(defn data-parser [line]
  (let [[lex gloss] (str/split line #"\|")
        tokens (str/split (str/trim lex) #" ")
        w_cnt (Integer/parseInt (tokens 3))
        w_pairs (* w_cnt 2)
        p_cnt (Integer/parseInt (tokens (+ 4 w_pairs)))
        p_sets (* p_cnt 4)]
    {:synset_offset (Integer/parseInt (tokens 0))
     :lex_filename (lexnames (tokens 1))
     :pos (tokens 2)
     :words (parse-words
               (subvec tokens
                       (if (> w_cnt 0) 4 3)
                       (+ 4 w_pairs)))
     :refs (parse-refs
            (subvec tokens
                    (if (> p_cnt 0) (+ 5 w_pairs) (+ 4 w_pairs))
                    (+ 5 w_pairs p_sets)))
     :gloss (str/trim gloss)}))