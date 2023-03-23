(ns clugle.learn.wordnet.base
  (:require [clojure.string :as str]
            [clugle.learn.wordnet.parsers :refer [index-parser 
                                                  data-parser]]))

;; locations of the index and data files
(def DICT_LOC "/meatwad/dictionaries/wordnet/wn3.1/dict")

;; reads in data lines (i.e. non-comment lines)
(defn line-reader [file]
  (filter #(not (str/starts-with? %1 "  "))
          (str/split-lines (slurp file))))

;; provides a function that will read a line
;; at the specified offset.
(defn line-seeker [filename]
  (let [file (java.io.RandomAccessFile. filename "r")]
    (fn [offset]
      (.seek file offset)
      (.readLine file))))

;; reads in the index file for a part 
;; of speech (e.g. "noun")
(defn read-index [pos]
  (map
   index-parser
   (line-reader
    (format "%s/index.%s" DICT_LOC pos))))

;; reads in the data file for a part of
;; speach (e.g. "noun")
(defn read-data [pos offset]
  (let [reader (line-seeker (format "%s/data.%s" DICT_LOC pos))]
    (data-parser (reader offset))))