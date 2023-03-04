(ns clugle.learn.matrix.base)

;; applies a supplied set of vectors
;; to a supplied set of inputs...neat!
(defn do-matrix-ops [ops inputs]
  (mapv
   (fn [input]
     (mapv (fn [r] (apply + r))
           (mapv (fn [op] (map * op input)) ops)))
   inputs))