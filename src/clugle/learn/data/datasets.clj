(ns clugle.learn.data.datasets
  (:require [clojure.string :as str]))

;; reads in a csv file, creating a vector
;; of vectors (one for each line)
(defn read-csv [file]
  (let [csv-line (fn [lines]
                   (mapv #(str/split %1 #",") lines))]
    (-> file
        slurp
        str/split-lines
        csv-line)))

(def DATASETS 
  {:twitter-airline #(read-csv "datasets/sentiment/twitter-airline.csv")})


(defn load-dataset [dataset]
  ((DATASETS dataset)))