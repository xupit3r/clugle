(ns clugle.test.maxtrix
  (:require [clojure.test :refer [deftest is]]
            [clugle.learn.matrix.base :refer [do-matrix-ops convolution]]))

(deftest test-do-matrix-ops []
  (is (= 
       (do-matrix-ops 
         [[1 0 0] [0 1 0] [0 0 1]] 
         [[1 2 3] [4 5 6]])
       [[1 2 3] [4 5 6]])))


(deftest test-convolution []
  (let [window [3 2 1]
        input  [1 2 3 4 5]]
    (is (=
         (convolution window input)
         [3 8 14 20 26 14 5]))
    (is (=
         (apply
          max
          (convolution
           [0.05 0.03 0.01]
           [10 20 30 20 10 10 10]))
         2.2))))