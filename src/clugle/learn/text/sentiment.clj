(ns clugle.learn.text.sentiment
  (:require [clojure.string :as str]
            [clugle.util.hlpr :refer [sum]]
            [clugle.learn.text.utils :refer [get-data-lines]]))

;; sentiment mapping file
(def SENTIMENT_MAPPINGS 
  {:english "src/clugle/learn/text/sentiment/afinn/english.txt"})

;; processes an individual sentiment line
;; from the file. it returns a mapping of
;; word to score.
(defn process-sentiment-line [line]
  (let [[word score] (str/split line #"\t")]
    {word (Integer/parseInt score)}))

;; retrieves sentiment data from a file.
;; this function is memoized so it will
;; only process the file once. returns
;; a map of words to sentiment scores
(def get-sentiment
  (memoize
   (fn [source]
     (->> source
          (SENTIMENT_MAPPINGS)
          (get-data-lines)
          (mapv process-sentiment-line)
          (apply merge)))))

;; given a set of tokens and valence mappings,
;; this will calculate the overall sum of 
;; sentiment scores within the tokens
(defn sentiment-sum [tokens valences]
  (sum 
   (mapv 
    (fn [t] (get valences t 0)) 
    tokens)))

;; returns a simple lexicon based score for the 
;; sentiment. this based purely off of a dictionary
;; of words mapped to positivity/negativity
;; valences
(defn lexicon-score
  ([tokens] (lexicon-score tokens :english))
  ([tokens source]
   (let [valences (get-sentiment source)]
     (/ (sentiment-sum tokens valences) 
        (count tokens)))))