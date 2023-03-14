(ns clugle.learn.text.utils
   (:require [clojure.java.io :refer [file]]
             [babashka.fs :refer [directory?]]))

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
