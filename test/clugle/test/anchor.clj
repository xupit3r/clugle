(ns clugle.test.anchor
  (:use clojure.test)
  (:require [clugle.anchor :as anchor]
            [clugle.soupu :as soupu]))

;; test the abs-url? function
;; run through a set of URLs both 
;; absolute and relative
(deftest test-abs-url? []
  (is (false? (anchor/abs-url? "../joe")))
  (is (false? (anchor/abs-url? "www.google.com")))
  (is (true? (anchor/abs-url? "http://thejoeshow.net")))
  (is (false? (anchor/abs-url? "joe.jpg")))
  (is (true? (anchor/abs-url? "http://www.google.com")))
  (is (true? (anchor/abs-url? "     http://www.google.com    ")))
  (is (true? (anchor/abs-url? "   http://thejoeshow.net"))))


;; test the bld-abs-anchor function
;; verify that with the available information 
;; the expected absolute URL is being built
(deftest test-bld-abs-anchor []
  (let [anchors 
        (vector (vector :a (hash-map :href "http://www.google.com") "google.com")
         (vector :a (hash-map :href "thejoeshow.net") "thejoeshow.net")
         (vector :a (hash-map :href "joe.jpg") "View joe.jpg")
         (vector :a (hash-map :href "../../fun.html") "Super Fun!"))]
    (let [refined-anchors (anchor/bld-abs-anchor "http://thejoeshow.net" anchors)]
      (is (= (anchor/get-href (nth refined-anchors 0)) 
             "http://www.google.com"))
      (is (= (anchor/get-href (nth refined-anchors 1)) 
             "http://thejoeshow.net/thejoeshow.net"))
      (is (= (anchor/get-href (nth refined-anchors 2)) 
             "http://thejoeshow.net/joe.jpg"))
      (is (= (anchor/get-href (nth refined-anchors 3)) 
             "http://thejoeshow.net/../../fun.html")))))