(ns clugle.learn.wordnet.base
  (:require [clojure.string :as str]))

;; locations of the index and data files
(def DICT_INDEX "/meatwad/dictionaries/wordnet/wn3.1/dict")

;; reads in data lines (i.e. non-comment lines)
(defn read-data-lines [file]
  (filter #(not (str/starts-with? %1 " "))
          (str/split-lines (slurp file))))

;; parses a line from the index count
(defn parse-index-line [line]
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

;; reads in the index file for a part 
;; of speech (e.g. "noun")
(defn read-index [pos]
  (map
   parse-index-line
   (read-data-lines
    (format "%s/index.%s" DICT_INDEX pos))))