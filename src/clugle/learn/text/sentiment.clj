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
   #(->> %1
         (SENTIMENT_MAPPINGS)
         (get-data-lines)
         (mapv process-sentiment-line)
         (apply merge))))

;; assigns a score to each token in the
;; tokens vector. if a token is neutral,
;; 0 is assigned.
(defn assign-scores [tokens valences]
  (mapv #(get valences %1 0) tokens))

;; provides a scale factor of tokens
;; contributing to overall sentiment
;; relative to neutral tokens
(defn sentiment-scale [scores]
  (let [contributing (count (filterv #(not (zero? %1)) scores))
        non-contributing (count (filterv zero? scores))]
    (if (zero? non-contributing) 1 (/ contributing non-contributing))))

;; performs the actual calculation of 
;; the lexicon score, scaling sentiment
;; contributions to the overall sentiment
(defn calc-lexicon-score [scores]
  (* (sentiment-scale scores)
     (sum scores)))

;; returns a simple lexicon based score for the 
;; sentiment. this based purely off of a dictionary
;; of words mapped to positivity/negativity
;; valences
(defn lexicon-score
  ([tokens] (lexicon-score tokens :english))
  ([tokens source]
   (->> source
        (get-sentiment)
        (assign-scores tokens)
        (calc-lexicon-score))))