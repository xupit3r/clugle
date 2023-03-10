(ns clugle.test.maxtrix
  (:require [clojure.test :refer [deftest is]]
            [clugle.learn.matrix.base :refer [do-matrix-ops m-add]]))

(deftest test-do-matrix-ops []
  (is (=
       (do-matrix-ops
        [[1 0 0] [0 1 0] [0 0 1]]
        [[1 2 3] [4 5 6]])
       [[1 2 3] [4 5 6]])))

(deftest test-m-add []
  (is (=
       (m-add
        [[1 2] [3 4]]
        [[2 -1] [-1 2]]
        [[1 1] [1 1]])
       [[4 2] [3 7]])))