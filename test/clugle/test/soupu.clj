(ns clugle.test.soupu
  (:use clojure.test)
  (:require [clugle.soupu :as soupu]
            [clj-http.client :as client] 
            [clugle.util :as util]
            [pl.danieljanus.tagsoup :as tagsoup]))


;; test tag-me
(deftest test-tag-me []
  (let [{status :status, header :header, body :body} (client/get "http://thejoeshow.net")]
    (let [html-children (soupu/children (tagsoup/parse-string body))]
      (is (= 11 (util/size (soupu/tag-me html-children :a))))
      (is (= 1 (util/size (soupu/tag-me html-children :h1))))
      (is (= 0 (util/size (soupu/tag-me html-children :h2))))
      (is (= 5 (util/size (soupu/tag-me html-children :h3))))
      (is (= 15 (util/size (soupu/tag-me html-children :div))))
      (is (= 1 (util/size (soupu/tag-me html-children :p))))
      (is (= 2 (util/size (soupu/tag-me html-children :ol))))
      (is (= 6 (util/size (soupu/tag-me html-children :li))))
      (is (= 1 (util/size (soupu/tag-me html-children :object)))))))

