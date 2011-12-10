(ns clugle.crawl
  (:require [clj-http.client :as client] 
            [pl.danieljanus.tagsoup :as tagsoup]))

;;;; Parsed HTML Data Helper Functions ;;;;

;; get the children of a provided node
(defn children [node]
  (rest (rest node)))

;; get the attributes of a provide node
(defn attributes [node]
  (first (rest node)))

;; get the tag for a provided node
(defn tagname [node]
  (first node))

;;;; Tag Helper Functions ;;;;

;; is the current node the provided tag?
(defn tagp [node tag]
  (= (first node) tag))

;; determine if the provided structure
;; is mapable
(defn mapable? [something]
  (or (list? something)
      (vector? something)
      (set? something)
      (seq? something)))

;; gimme dem tags
(defn tag-me [soup atag]
  (mapcat (fn this [node]
            (cond
              ; if this is a node of the specified tag
              ; and it has children, create a list of 
              ; keep this node and process its children
              (and (tagp node atag) 
                   (mapable? node)
                   (children node))
              (conj (list node) 
                    (mapcat this (children node)))
              ; if this is a node of the specified tag
              ; keep this node (at this point, we know it 
              ; does not have any children
              (tagp node atag) (list node)
              ; if it has children, go ahead and search them
              ; this may contain a node of the tag we are looking 
              ; for
              (and (mapable? node)
                   (children node))
              (mapcat this (children node)))) 
            soup))


;;;; Crawler Tasks ;;;;

;; returns some delicious HTML soup
(defn eat-url [url]
  (let [{status :status, header :header, body :body} (client/get url)]
    ; perform crawler responsiblity
    ))


  




