(ns clugle.learn.text.sentcore
  (:import [java.util
            Properties]
           [edu.stanford.nlp.pipeline
            StanfordCoreNLP 
            CoreDocument]))

;; sentiment directed config
(def annotators "tokenize,ssplit,pos,parse,sentiment")
(def algorithm "neural")

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

;; get the per-sentence sentiment breakdown
;; of the text
(defn senttext [text]
  (->> text
       annotate
       .sentences
       (map #(.sentiment %))))