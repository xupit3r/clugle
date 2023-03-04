(ns clugle.learn.text.utils
   (:require [clojure.string :as str]
            [clojure.java.io :refer [file]]
            [babashka.fs :refer [directory?]]))

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

;; super simple for right now...
(defn clean-text [str]
  (str/replace str #"\." ""))

;; reads files into a list of strings
(defn doc-arr [docs]
  (map slurp
       (filter (fn [d] (not (directory? d))) docs)))

;; read in all the files within a directory
;; this reads them into an array of strings,
;; so for directories with lotsa files...
;; umm don't use this probably :)
(defn load-docs [dir]
  (-> dir file file-seq doc-arr vec))

;; creates a vector of tuples 
;; the first of each tuple is the 
;; token, the second is a talley of 
;; 1 (you will see what it is being used for)
(defn tokenize
  ([txt] (tokenize txt :space))
  ([txt delimiter] (str/split txt (delim delimiter))))
