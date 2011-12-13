(ns clugle.soupu
  (:require [clugle.util :as util]))

;;;; Parsed HTML Data Helper Functions ;;;;

;; get the children of a provided node
(defn children [node]
  (if (util/listy? node)
    (rest (rest node))
    nil))

;; get the attributes of a provide node
(defn attributes [node]
  (if (util/listy? node)
    (first (rest node))
    nil))

;; get the tag for a provided node
(defn tagname [node]
  (if (util/listy? node)
    (first node)
    nil))

