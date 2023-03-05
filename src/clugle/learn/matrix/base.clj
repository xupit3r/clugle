(ns clugle.learn.matrix.base
  (:require [clugle.util.hlpr :refer [sum join]]))

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