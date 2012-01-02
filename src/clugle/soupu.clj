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

;;;; Tag Helper Functions ;;;;

;; is the current node the provided tag?
(defn tagp [node tag]
  (and (util/listy? node)
       (= (first node) tag)))

