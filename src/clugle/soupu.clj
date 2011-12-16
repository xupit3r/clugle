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
  
;; gimme dem tags
;; retrieves tags of a specific type
;; (defined by the atag parameter)
;; NOTE: using tagsoup, this method must operate
;; from the context of the HTML tag's children
;; (i.e. a list containing HEAD and BODY)
(defn tag-me
  ([the-children atag] (tag-me the-children atag '()))
  ([the-children atag acc]
    (loop [idx 1 the-child (nth the-children 0 nil) acc1 acc]
      (let [nth-tag (tagname the-child)
            htn-attributes (attributes the-child)
            nth-children (children the-child)]
        (if (= nth-tag atag)
          (if (not (nil? nth-children))
            (recur (inc idx)
                   (nth the-children idx nil)
                   (tag-me nth-children atag (cons the-child acc1)))
            (recur (inc idx) 
                   (nth the-children idx nil) 
                   (cons the-child acc1)))
          (if (not (nil? nth-children))
            (recur (inc idx)
                   (nth the-children idx nil)
                   (tag-me nth-children atag acc1))
            acc1))))))

