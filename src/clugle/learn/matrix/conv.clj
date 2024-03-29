(ns clugle.learn.matrix.conv)

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
(defn conv [window input]
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
;; the input
(defn slidek [kernel input i j]
  (let [cm (int (/ (count (kernel 0)) 2))
        cn (int (/ (count kernel) 2))
        dm (- cm i)
        dn (- cn j)]
    (vec (for [m (range (count input))]
           (vec (for [n (range (count (input m)))]
                  (get (get kernel (+ m dm) []) (+ n dn) 0)))))))

;; apply a 2d convolution (very dumb implementation)
(defn conv2d [kernel input]
  (let [fk (flipk kernel)
        fi (flatten input)]
    (vec (for [i (range (count input))]
           (vec (for [j (range (count (input i)))]
                  (apply + (mapv * fi (flatten (slidek fk input i j))))))))))