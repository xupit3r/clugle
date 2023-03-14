(ns clugle.learn.text.preprocess
  (:require [clojure.string :as str]))

;; stop words sources (just english right now)
(def SOURCES {:english "src/clugle/learn/text/stopwords/english.txt"})

;; puncuation regex, will be used to strip these symbols
;; from text
(def PUNCUATION #"[.,?!;:]")

;; return a string representing 
;; the desired delimiter
(defn delim [dkey]
  (cond (= dkey :space) #"\s"
        (= dkey :pipe) #"\|"
        (= dkey :pound) #"#"
        (= dkey :percent) #"%"
        (= dkey :tab) #"\t"
        (= dkey :caret) #"\^"
        (= dkey :period) #"\."
        (= dkey :comma) #","))
;; creates a vector of the words that
;; comprise the supplied string
(defn tokenize
  ([txt] (tokenize txt :space))
  ([txt delimiter] (str/split txt (delim delimiter))))

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

;; removes puncuation present in the text
(defn remove-punc [txt]
  (str/replace txt PUNCUATION ""))

;; performs some text denoising
(defn denoise [txt]
  (-> txt
      remove-punc
      tokenize
      remove-stops))