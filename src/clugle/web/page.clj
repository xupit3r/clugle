(ns clugle.web.page
  (:require [clugle.web.http :refer [request-get]]
            [hickory.core :refer [parse, as-hickory]]
            [clojure.string :refer [split]]))

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

(defn tagset [tags]
  (set (map keyword (split tags #","))))

(defn process [url tags]
  (group-by 
   :tag 
   (collect
    []
    (-> (request-get url) :body parse as-hickory) 
    (tagset tags))))
