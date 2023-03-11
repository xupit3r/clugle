(ns clugle.learn.matrix.vectors)

;; calcuate the vector length
(defn vec-length [vector]
  (Math/sqrt
   (apply + (mapv
             (fn [v] (Math/pow v 2))
             vector))))