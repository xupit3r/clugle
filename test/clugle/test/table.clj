(ns clugle.test.table
  (:require [clojure.test :refer [deftest is]]
            [clugle.learn.data.table :as table]))

;; test data for the below test cases
(def TEST_DATA {:name "weather"
                :columns ["forecast" "temp" "humidity" "$windy" "play"]
                :examples [["sunny" "hot" "high" 0 "no"]
                           ["sunny" "hot" "high" 1 "yes"]
                           ["sunny" "hot" "high" -1 "yes"]]
                :klass -1})

;; test the creation of header metadata
(deftest test-columns-new []
  (let [headers (table/columns-new 
                  (:columns TEST_DATA)
                  (- (count (:columns TEST_DATA)) 1))]
    (is (= (count headers) 5))))

;; test the nump function which
;; should determine if a provided
;; column is numeric
(deftest test-nump []
  (let [nump-yes "$number"
        nump-no1 "number"
        nump-no2 "number$"]
    (is (table/nump nump-yes))
    (is (not (table/nump nump-no1)))
    (is (not (table/nump nump-no2)))))

;; test the ignorep function which
;; should determine if a provided
;; column is to be ignored
(deftest test-ignorep []
  (let [igp-yes "?ignore"
        igp-no1 "ignore"
        igp-no2 "ignore?"
        igp-no3 "$ignore"]
    (is (table/ignorep igp-yes))
    (is (not (table/ignorep igp-no1)))
    (is (not (table/ignorep igp-no2)))
    (is (not (table/ignorep igp-no3)))))

;; test the klass-idx function
;; which should provide the expected
;; index of the class of a table
(deftest test-klass-idx []
  (let [defined-idx 3
        undefined-idx -1
        width 5]
    (is (= (table/klass-idx defined-idx width) 3))
    (is (= (table/klass-idx undefined-idx width) 4))))

;; test the mdata function, which should
;; construct an in-memory representation
;; of a provided set of data
(deftest test-mdata []
  (let [tbl-ns (table/mdata false TEST_DATA)
        tbl-s (table/mdata true TEST_DATA)]
    (is (and (= (:name tbl-ns) "weather")
             (= (:name tbl-s) "weather")))
    (is (and (= (count (:columns tbl-ns)) 5)
             (= (count (:columns tbl-s)) 5)))
    (is (and (= (count (:examples tbl-ns)) 3)
             (= (count (:examples tbl-s)) 3)))
    (is (and (= (:klass tbl-ns) 4)
             (= (:klass tbl-s) 4)))))
