(ns clugle.test.conv
  (:require [clojure.test :refer [deftest is]]
            [clugle.learn.matrix.conv :refer [conv conv2d]]))

(deftest test-convolution []
  (let [window [3 2 1]
        input  [1 2 3 4 5]]
    (is (=
         (conv window input)
         [3 8 14 20 26 14 5]))
    (is (=
         (apply
          max
          (conv
           [0.05 0.03 0.01]
           [10 20 30 20 10 10 10]))
         2.2))))

(deftest test-conv2d []
  (let [kernel [[-1 -2 -1] [0 0 0] [1 2 1]]
        input [[1 2 3] [4 5 6] [7 8 9]]]
    (is (= (conv2d kernel input)
           [[-13 -20 -17] [-18 -24 -18] [13 20 17]]))))