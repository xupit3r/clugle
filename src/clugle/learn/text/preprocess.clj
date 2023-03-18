(ns clugle.learn.text.preprocess
  (:require [clojure.string :as str]
            [clugle.learn.text.utils :refer [get-data-lines]]))

;; stop words sources (just english right now)
(def STOP_SOURCES {:english "src/clugle/learn/text/stopwords/english.txt"})

;; lemma sources (unprocessed)
(def LEMMA_SOURCES {:english "src/clugle/learn/text/lemma/english/lemma.en.txt"})

;; contractions source
(def CONTRACTION_SOURCES {:english "src/clugle/learn/text/contractions/english.txt"})

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

;; prepares a line from the contractions
;; file to be part of the mapping
(defn process-contraction-line [line]
  (let [[word subs] (str/split line #",")]
    {(str/trim word) 
     (mapv str/trim (str/split subs #"\|"))}))

;; retrieves contraction mappings from a specified
;; source file. this function is memoized, so the
;; source file data will only be processed once
(def get-contractions
  (memoize
   (fn [source]
     (->> (CONTRACTION_SOURCES source)
          (get-data-lines)
          (mapv process-contraction-line)
          (apply merge)))))

;; provides a function that will return the relevant
;; contraction (if it exists) otherwise the token
;; provided is returned
(defn contraction-expander [source]
  (let [contractions (get-contractions source)]
    (fn [token] 
      (first (get contractions token [token])))))

;; loads the stop words for a specified source
;; note: this function is memoized so subsequent
;; calls with the same source will not result in
;; re-excution
(def get-stops
  (memoize
   (fn [source]
     (set
      (mapv str/trim
            (str/split-lines
             (slurp (STOP_SOURCES source))))))))

;; processes a line from the lemma file
(defn process-lemma-line [line]
  (let [[base forms] (str/split line #"->")
        norm-form (str/trim (first (str/split base #"/")))]
    (reduce
     (fn [m v] (assoc m (str/trim v) norm-form))
     {}
     (str/split forms #","))))

;; loads in the lemma data for use in normalization
;; processes n'at. this function is memoized, so
;; the actual processing will only be carried out
;; once per source
(def get-lemma
  (memoize
   (fn [source]
     (->> (LEMMA_SOURCES source)
          (get-data-lines)
          (mapv process-lemma-line)
          (apply merge)))))


;; provides a function that will map 
;; a provided token to its normal form
;; based on lemma mappings
(defn lemma-mapper [source]
  (let [lemmas (get-lemma source)]
    (fn [token] (get lemmas token token))))

;; provides a function that will return
;; true if the supplied word is a stop word
(defn stop-filter [source]
  (let [stopwords (get-stops source)]
    (fn [word] (not (contains? stopwords word)))))

;; for a given token vector this removes any
;; stopwords that are present
(defn remove-stops
  ([tokens] (remove-stops tokens :english))
  ([tokens source] (filterv (stop-filter source) tokens)))

;; removes any preceeding/trailing whitespace
;; from each of the tokens
(defn remove-whitespace [tokens]
  (filter (fn [s] (not (str/blank? s)))
          (mapv str/trim tokens)))

;; removes puncuation present in the text
(defn remove-punc [txt]
  (str/replace txt PUNCUATION ""))

;; will normalize all tokens in the given text
;; (at this point just maps em to the normal form)
;; if no normal form is found, the token will be
;; returned
(defn normalize
  ([str]
   (normalize str :english))
  ([str source]
   (->>
    str
    str/lower-case
    remove-punc
    tokenize
    remove-whitespace
    (mapv (contraction-expander source))
    (mapv (lemma-mapper source)))))