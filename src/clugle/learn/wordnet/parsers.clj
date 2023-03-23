(ns clugle.learn.wordnet.parsers
  (:require [clojure.string :as str]))

;; parses a line from the index count
(defn index-parser [line]
  (let [tokens (str/split line  #" ")
        p_cnt (Integer/parseInt (tokens 3))]
    {:line line
     :lemma (tokens 0)
     :pos (tokens 1)
     :synset_cnt (Integer/parseInt (tokens 2))
     :p_cnt p_cnt
     :ptr_symbol (subvec tokens (if (> p_cnt 0) 4 3) (+ 4 p_cnt))
     :sense_cnt (Integer/parseInt (tokens (+ 4 p_cnt)))
     :tagsense_cnt (Integer/parseInt (tokens (+ 5 p_cnt)))
     :synset_offset (Integer/parseInt (tokens (+ 6 p_cnt)))}))