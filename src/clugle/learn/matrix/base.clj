(ns clugle.learn.matrix.base
  (:require [clugle.util.hlpr :refer [sum mul join column]]))

;; returns a function that will apply a
;; matrix operation to the supplied input
;; if the operation array is larger than
;; the input, extra operational values will
;; be added to the total result
(defn apply-op [input]
  (fn [op]
    (sum
     (join
      (map * (subvec op 0 (count input)) input)
      (subvec op (count input))))))

;; applies a supplied set of vectors
;; to a supplied set of inputs...neat!
(defn do-matrix-ops [ops inputs]
  (mapv
   (fn [input]
     (mapv (apply-op input) ops))
   inputs))

;; adds a bunch of matrices (assumes same length)
(defn m-add [& matrices]
  (let [x (count (first matrices))
        y (count ((first matrices) 0))]
    (vec
     (for [i (range x)]
       (vec
        (for [j (range y)]
          (apply + (mapv (fn [m] ((m i) j))
                         matrices))))))))

;; multiplies 2 matrices
(defn m-mul [m1 m2]
  (vec
   (for [i (range (count m1))]
     (vec
      (for [j (range (count (m1 i)))]
        (sum (mul (m1 i) (column m2 j))))))))