(ns clugle.web.page
  (:require [clugle.web.http :refer [request-get]]
             [hickory.core :refer [parse, as-hickory]]))

(defn has-kiddos [vec]
  (seq
   (filter
    (fn [{content :content}]
      (not (or (string? content) (empty? content))))
    vec)))

(defn tag-em [tags content] 
  (filterv (fn [{t :tag}] (contains? tags t)) content))

(defn bag-em [& stuffs]
  (flatten (apply conj stuffs)))

(defn collect [collected {content :content} tags]
  (if (not (has-kiddos content)) 
    collected
    (bag-em
      (tag-em tags content)
      (for [tree content] (collect collected tree tags)))))

(defn parse-body [{body :body}]
 (->> (parse body)
      (as-hickory)))

(defn process [url]
  (let [parsed (->> (request-get url) (parse-body))]
    (group-by :tag (collect [] parsed #{:a :p :img}))))

