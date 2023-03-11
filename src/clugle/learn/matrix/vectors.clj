(ns clugle.learn.matrix.vectors)

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