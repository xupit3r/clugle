(ns clugle.learn.text.utils
   (:require [clojure.java.io :refer [file]]
             [clojure.string :as str]
             [babashka.fs :refer [directory?]]))

;; reads files into a list of strings
(defn doc-arr [docs]
  (map slurp
       (filter #(not (directory? %1)) docs)))

;; read in all the files within a directory
;; this reads them into an array of strings,
;; so for directories with lotsa files...
;; umm don't use this probably :)
(defn load-docs [dir]
  (-> dir file file-seq doc-arr vec))

;; reads all non-comment lines from a file
;; i.e. core data of the file :)
(defn get-data-lines [file]
  (filter #(not (str/starts-with? %1 ";"))
          (str/split-lines (slurp file))))
