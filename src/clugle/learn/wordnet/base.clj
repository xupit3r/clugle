(ns clugle.learn.wordnet.base
  (:require [clojure.string :as str]
            [environ.core :refer [env]]
            [clugle.learn.wordnet.parsers :refer [index-parser 
                                                  data-parser]]))

;; some stuff to help load files more easily
(def wnfile
  (memoize
   #(format "%s/%s.%s" (env :wordnet-dict) %1 %2)))

(def index-file #(wnfile "index" %1))
(def data-file #(wnfile "data" %1))

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
   (line-reader (index-file pos))))

;; reads in the data file for a part of
;; speach (e.g. "noun")
(defn read-data [pos offset]
  (let [reader (line-seeker (data-file pos))]
    (data-parser (reader offset))))