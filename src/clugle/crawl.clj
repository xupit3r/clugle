(ns clugle.crawl
  (:require [clj-http.client :as client] 
            [clugle.console :as console]
            [clugle.util :as util]
            [clugle.soupu :as soupu]
            [pl.danieljanus.tagsoup :as tagsoup]))

;;;; Crawler Tasks ;;;;


;; returns some delicious HTML soup
(defn eat-url [url]
  (let [{status :status, header :header, body :body} (client/get url)]
    ; perform crawler responsiblity
    (tagsoup/parse-string body)))