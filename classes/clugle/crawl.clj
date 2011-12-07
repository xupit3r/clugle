(ns clugle.crawl
  (:require [clj-http.client :as client] 
            [pl.danieljanus.tagsoup :as tagsoup]))

;; returns some delicious HTML soup
(defn soup-it [url]
  (let [{body :body} (client/get url)]
    (tagsoup/parse-string body)))