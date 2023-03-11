(ns clugle.test.vectors
  (:require [clojure.test :refer [deftest is]]
            [clugle.learn.matrix.vectors :refer [vec-length vec-add vec-scale]]))

(deftest test-vec-length []
  (is (= (vec-length [2 -5 4 7])
         6.708203932499369)))

(deftest test-vec-length []
  (is (= (vec-length [2 -5 4 7])
         9.695359714832659)))

(deftest test-vec-add []
  (is (= (vec-add [1 3 -1] [-2 1 6])
         [-1 4 5])))

(deftest test-vec-scale []
  (is (= (vec-scale 7 [1 0 -2 1])
         [7 0 -14 7])))

