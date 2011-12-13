(ns clugle.crawl
  (:require [clj-http.client :as client] 
            [clugle.console :as console]
            [clugle.util :as util]
            [clugle.soupu :as soupu]
            [pl.danieljanus.tagsoup :as tagsoup]))

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
      (let [nth-tag (soupu/tagname the-child)
            htn-attributes (soupu/attributes the-child)
            nth-children (soupu/children the-child)]
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

;;;; Crawler Tasks ;;;;


;; returns some delicious HTML soup
(defn eat-url [url]
  (let [{status :status, header :header, body :body} (client/get url)]
    ; perform crawler responsiblity
    (tagsoup/parse-string body)))