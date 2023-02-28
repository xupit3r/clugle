(ns clugle.util.textu
  (:require [clojure.string :refer [split]]))

;;;; logic for doing stuff with text

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

;; creates a vector of tuples 
;; the first of each tuple is the 
;; token, the second is a talley of 
;; 1 (you will see what it is being used for)
(defn tokenize 
  ([txt] (tokenize txt :space))
  ([txt delimiter] (split txt (delim delimiter))))

;; builds a frequency count
;; of words appearing in a string
(defn word-freq [str]
  (->> 
    (split str #" ")
    (frequencies)))
