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
     (set
      (mapv str/trim
            (str/split-lines
             (slurp (SOURCES source))))))))

;; provides a function that will return
;; true if the supplied word is a stop word
(defn stop-filter [source]
  (let [stopwords (load-stops source)]
    (fn [word] (not (contains? stopwords word)))))

;; for a given token vector this removes any
;; stopwords that are present
(defn remove-stops 
  ([tokens] (remove-stops tokens :english))
  ([tokens source] (filterv (stop-filter source) tokens)))