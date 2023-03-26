(ns clugle.learn.wordnet.base
  (:require [clojure.string :as str]
            [environ.core :refer [env]]
            [clugle.learn.wordnet.db :refer [insert-words]]
            [clugle.learn.wordnet.parsers :refer [index-parser 
                                                  data-parser]]))

;; some stuff to help load files more easily
(def wnfile
  (memoize
   #(format "%s/%s.%s" (env :wordnet-dict) %1 %2)))

(def index-file #(wnfile "index" %1))
(def data-file #(wnfile "data" %1))

;; retrieves a db file (e.g. noun.location)
(def db-file
  (memoize
   #(format "%s/dbfiles/%s" (env :wordnet-dict) %1)))

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

;; merges the index with the data info
;; returning a single combined hashmap
(defn merge-data [pos indices]
  (for [index indices] 
    (merge index (read-data pos (:offset index)))))

;; provides some start status
(defn start [pos]
  (print (format "processing %ss " pos))
  pos)

;; summarizes the processing of words
;; returns the words as a vector
(defn summarize [pos words]
  (println (format "processed %d %ss." (count words) pos))
  (vec words))

;; given a part of speech, this will carry out
;; the process of reading the index and data
;; files for the part of speech, combine them,
;; and then insert them into the "words" database
;; collection
(defn process-words [pos]
  (->> (start pos)
       (read-index)
       (merge-data pos)
       (summarize pos)
       (insert-words)))
