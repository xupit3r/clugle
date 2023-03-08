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


;; given a vector, an iteration, and a desired length
;; this will create an appropriate sliding window padding
;; to allow for matrix operation
(defn conv-op [v i len]
  (let [offset (abs (- (count v) i))]
    (vec
     (flatten
      (if (<= i (count v))
        (conj
         (vec
          (repeat (- len i) 0)) (subvec v 0 i))
        (conj
         (vec
          (repeat (- len (count v) offset) 0))
         v
         (vec
          (repeat offset 0))))))))

;; prepares input data for a convolution operation
(defn prep-conv-input [window input]
  (vec
   (flatten
    (conj (vec (repeat (count window) 0))
          (reverse input)))))

;; performs a convolution between a supplied
;; window and input to apply the window to
(defn convolution [window input]
  (let [iterations (+ (count window) (count input))
        ivec (prep-conv-input window input)]
        (vec
         (for [i (range 1 iterations)
               :let [op (conv-op window i iterations)]]
           (apply + (mapv * op ivec))))))

;; flips a 2d kernel
(defn flipk [kernel]
  (vec 
   (reverse 
    (map (fn [k] (vec (reverse k))) 
         kernel))))

;; creates a version of the kernel slid to 
;; the supplied origin (i, j). values will
;; be padded with zeros and the resulting
;; kernel will be the same dimensions as
;; the original kernel
(defn slidek [kernel i j]
  (let [cm (int (/ (count (kernel 0)) 2))
        cn (int (/ (count kernel) 2))
        dm (- cm i)
        dn (- cn j)]
    (vec (for [m (range (count kernel))]
      (vec (for [n (range (count (kernel m)))]
        (get (get kernel (+ m dm) []) (+ n dn) 0)))))))