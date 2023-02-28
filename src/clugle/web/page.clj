(ns clugle.web.page
  (:require [clugle.web.http :refer [request-get]]
             [hickory.core :refer [parse, as-hickory]]))

(defn has-kiddos [vec]
  (seq
   (filter
    (fn [{content :content}]
      (not (or (string? content) (empty? content))))
    vec)))

(defn tag-em [tag content] 
  (filterv (fn [{t :tag}]  (= t tag)) content))

(defn bag-em [& stuffs]
  (flatten (apply conj stuffs)))

(defn collect [collected {content :content} tag]
  (if (not (has-kiddos content)) 
    collected
    (bag-em
      (tag-em tag content)
      (for [tree content] (collect collected tree tag)))))

(defn parse-body [{body :body}]
 (->> (parse body)
      (as-hickory)))

(defn process [url]
  (let [parsed (->> (request-get url) (parse-body))]
    {:anchors (collect [] parsed :a)
     :paragraphs (collect [] parsed :p)}))

