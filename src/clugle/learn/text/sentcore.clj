(ns clugle.learn.text.sentcore
  (:require [clojure.string :as str])
  (:import [java.util
            Properties]
           [edu.stanford.nlp.pipeline
            StanfordCoreNLP 
            CoreDocument]))

;; sentiment directed config
(def annotators "tokenize,ssplit,pos,parse,sentiment")
(def algorithm "neural")

;; maps the sentiment classes to a numeric
;; score
(def SCORES {"very_positive" 2
             "positive" 1
             "neutral" 0
             "negative" -1
             "very_negative" -2})

;; build a pipeline to annotate the text
(defn get-pipeline []
  (let [props (Properties.)]
    (.setProperty props "annotators" annotators)
    (.setProperty props "coref.algorithm" algorithm)
    (StanfordCoreNLP. props)))

;; parse + annotate the document
(defn annotate [text]
  (let [document (CoreDocument. text)
        pipeline (get-pipeline)]
    (.annotate pipeline document)
    document))

;; generates a score between -1 and 1
;; -1 being very negative
;;  1 being very positive
;;  0 being generally neutral...
(defn genscore [groups]
  (let [max (* (apply + (map #(count %) (vals groups))) 2)]
    (/ (reduce-kv
        #(+ %1 (* (SCORES (str/lower-case %2)) (count %3)))
        0
        groups)
       max)))

;; get the per-sentence sentiment breakdown
;; of the text
(defn senttext [text]
  (->> text
       annotate
       .sentences
       (map #(.sentiment %))
       (group-by identity)
       (genscore)))