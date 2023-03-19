(ns clugle.learn.data.datasets
  (:require [clojure.string :as str]))

;; some file cleaners
(def CLEANERS
  {:default #(str/trim %1)
   :csv #(str/replace
          %1
          #"(?<=^|,)\"([^\"]*)(?:([\r\n])+([^\"]*))\"(?=,|$)"
          "$1 $3")})

;; reads lines of a file into a vector
(defn read-lines
  ([file] (read-lines file :default))
  ([file cleaner]
   (-> file
       slurp
       ((CLEANERS cleaner))
       str/split-lines)))

;; splits the lines of a csv file into
;; a vector, one element for each column
(defn get-csv-lines [file]
  (mapv #(str/split %1 #",")
        (read-lines file :csv)))

;; uses the header column of a csv file
;; to create objects (keyed by header) 
;; for each line in the csv file
(defn make-csv-objects [[header & lines]]
  (let [line-keys (mapv #(keyword %1) header)]
    (mapv #(zipmap line-keys %1) lines)))

;; reads in a csv file, creating a vector
;; of vectors (one for each line)
(defn read-csv [file]
  (-> file
      get-csv-lines
      make-csv-objects))

;; current datasets that can be loaded
(def DATASETS 
  {:twitter-airline #(read-csv "datasets/sentiment/twitter-airline.csv")})

;; loads a given dataset
(def load-dataset
  (memoize
   (fn [dataset]
     ((DATASETS dataset)))))