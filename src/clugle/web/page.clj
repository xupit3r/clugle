(ns clugle.web.page
  (:require [clugle.web.http :refer [request-get]]
             [hickory.core :refer [parse, as-hickory]]))

(defn has-kiddos [vec]
  (not 
   (empty?
    (filter
     (fn [{content :content}]
       (not (or (string? content) (empty? content))))
     vec))))

(defn collect-anchors [collected {content :content}]
    (if (not (has-kiddos content)) 
      collected
      (flatten 
       (apply
        conj
        (filterv (fn [{tag :tag}] (= tag :a)) content)
        (for [item content] (collect-anchors collected item))))))

(defn parse-body [{body :body}]
 (->> (parse body)
      (as-hickory)))

(defn process [url]
  (let [parsed (->> (request-get url) (parse-body))]
    {:anchors (collect-anchors [] parsed)}))

