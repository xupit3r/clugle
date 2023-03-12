(ns clugle.test.vectors
  (:require [clojure.test
             :refer [deftest is]]
            [clugle.learn.matrix.vectors
             :refer [vec-length vec-add vec-scale vec-dot vec-cross]]))

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

(deftest test-vec-dot []
  (is (= (vec-dot [12 20] [16 -5]) 92)))

(deftest test-vec-cross []
  (is (= (vec-cross
          [(/ 1 4) (- (/ 1 2)) 1]
          [(/ 1 3) 1 (- (/ 2 3))])
         [-2/3 1/2 5/12])))