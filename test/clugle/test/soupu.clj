(ns clugle.test.soupu
  (:use [clojure.test])
  (:use [clugle.soupu])
  (:use [clugle.util])
  (:require [clj-http.client :as client] 
            [pl.danieljanus.tagsoup :as tagsoup]))


;; test tag-me
(deftest test-tag-me []
  (let [{status :status, header :header, body :body} (client/get "http://thejoeshow.net")]
    (let [html-children (children (tagsoup/parse-string body))]
      (is (= 11 (size (tag-me html-children :a))))
      (is (= 1 (size (tag-me html-children :h1))))
      (is (= 0 (size (tag-me html-children :h2))))
      (is (= 5 (size (tag-me html-children :h3))))
      (is (= 15 (size (tag-me html-children :div))))
      (is (= 1 (size (tag-me html-children :p))))
      (is (= 2 (size (tag-me html-children :ol))))
      (is (= 6 (size (tag-me html-children :li))))
      (is (= 1 (size (tag-me html-children :object)))))))

