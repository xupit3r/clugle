(ns clugle.util.hlpr)

;;;; general utility methods for the project

;; sum up values in a list
(defn sum [lst]
  (apply + lst))

;; multiplies a supplied set of vectors
(defn mul [& vecs]
  (apply mapv * vecs))

;; combines two or many lists
(defn join [lst1 & args]
  (apply conj lst1 (flatten args)))

;; finds the max value
(defn maxv [lst]
  (apply max lst))

;; returns a random permutation of
;; the provided list
(defn shuffem [lst]
  (shuffle lst))

;; provides a simple abstraction to update
;; a hash map by appying a function (f) to
;; each value
(defn apply-mf [m f]
  (reduce-kv (fn [m k v] (assoc m k (f v))) {} m))

;; provides an index range for a vector
(defn vec-range [vec]
  (range (- (count vec) 1)))

;; pulls a column from a matrix (vector x vector)
(defn column [matrix cnum]
  (mapv (fn [m] (m cnum)) matrix))