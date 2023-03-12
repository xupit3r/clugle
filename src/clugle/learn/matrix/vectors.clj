(ns clugle.learn.matrix.vectors
  (:require [clugle.util.hlpr :refer [flatme]]))

;; calcuate the vector length
(defn vec-length [vector]
  (Math/sqrt
   (apply + (mapv
             (fn [v] (Math/pow v 2))
             vector))))

;; add two vectors together
(defn vec-add [v1 v2]
  (mapv + v1 v2))

;; scales a vector by some supplied
;; scalar value
(defn vec-scale [scale vec]
  (mapv (fn [v] (* scale v)) vec))

;; dot product of two vectors
(defn vec-dot [v1 v2]
  (apply + (mapv * v1 v2)))

;; create determinant matrices for a 
;; set of vectors
(defn determs [v1 v2]
  (flatme
   (vec
    (for [i (range 1 (count v1))]
      (vec
       (for [j (range 1 (+ (- (count v1) i) 1))]
         (let [c1 (- (count v1) i j)
               c2 (- (count v1) i)]
           [[(get v1 c1) (get v1 c2)]
            [(get v2 c1) (get v2 c2)]])))))))

;; solve a determinant matrix
(defn solve-deter [m]
  (- (* (get (get m 0) 0) (get (get m 1) 1))
     (* (get (get m 0) 1) (get (get m 1) 0))))

;; generate vector coefficients for the determinant
;; matrices (not sure if this is right....)
(defn coeffs [v1]
  (vec (for [i (range (count v1))]
         (if (even? i) 1 -1))))

;; calc cross product
(defn vec-cross [v1 v2]
  (mapv * (coeffs v1)
        (mapv solve-deter (determs v1 v2))))