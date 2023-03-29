(ns clugle.learn.wordnet.analysis
  (:require [clugle.learn.text.preprocess :refer [tokenize]]
            [clugle.learn.wordnet.db :refer [get-entries]]))

;; given a setence, this will assign parts
;; of speech to all known words
(defn lookup-words [sentence]
  (mapv #(get-entries %)
        (tokenize sentence)))