(ns clugle.test.response
  (:use [clojure.test])
  (:use [clugle.web.response]))

(deftest test-handle-json []
  (let [simple-json "{\"joe\" : \"d\", \"jim\" : \"e\"}"] 
    (is (= {:joe "d" :jim "e"} 
           (handle-json simple-json)))))

