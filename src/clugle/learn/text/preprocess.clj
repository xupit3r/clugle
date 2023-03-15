(ns clugle.learn.text.preprocess
  (:require [clojure.string :as str]))

;; stop words sources (just english right now)
(def STOP_SOURCES {:english "src/clugle/learn/text/stopwords/english.txt"})

;; lemma sources (unprocessed)
(def LEMMA_SOURCES {:english "src/clugle/learn/text/lemma/english/lemma.en.txt"})

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
(def get-stops 
  (memoize
   (fn [source]
     (set
      (mapv str/trim
            (str/split-lines
             (slurp (STOP_SOURCES source))))))))

;; reads the core lemma definitions in
;; (removing any comment lines or other markup)
(defn get-core-lemma [source]
  (filter (fn [line] (not (str/starts-with? line ";")))
          (str/split-lines (slurp (LEMMA_SOURCES source)))))

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
     (->> (get-core-lemma source)
          (mapv process-lemma-line)
          (apply merge)))))


;; provides a function that will map 
;; a provided token to its normal form
;; based on lemma mappings
(defn lemma-mapper [source]
  (let [lemmas (get-lemma source)]
    (fn [token] (get lemmas token token))))

;; will normalize all tokens in the given text
;; (at this point just maps em to the normal form)
;; if no normal form is found, the token will be
;; returned
(defn normalize
  ([tokens] (normalize tokens :english))
  ([tokens source] (mapv (lemma-mapper source) tokens)))

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

;; performs some text denoising
(defn denoise [txt]
  (-> txt
      remove-punc
      str/lower-case
      tokenize
      remove-whitespace
      remove-stops
      normalize))