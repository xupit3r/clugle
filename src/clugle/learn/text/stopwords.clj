(ns clugle.learn.text.stopwords
  (:require [clojure.string :as str]))

;; stop words sources (just english right now)
(def SOURCES {:english "src/clugle/learn/text/stopwords/english.txt"})

;; loads the stop words for a specified source
;; note: this function is memoized so subsequent
;; calls with the same source will not result in
;; re-excution
(def load-stops 
  (memoize
   (fn [source]
     (mapv str/trim
           (str/split-lines
            (slurp (SOURCES source)))))))