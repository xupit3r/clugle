(ns clugle.util.hlpr)

;;;; general utility methods for the project

;; sum up values in a list
(defn sum [lst]
  (apply + lst))

;; remove empty lists from a provided list
(defn drop-empties
  ([lst] (drop-empties lst '()))
  ([lst acc]
    (if (first lst)
      (drop-empties (rest lst) (conj (first lst) acc))
      (if (not (empty? lst))
        (drop-empties (rest lst) acc)
        acc))))

;; get the size of a collection
(defn size
  ([col] (size col 0))
  ([col cnt] 
    (if (not (empty? col))
      (size (rest col) (inc cnt))
      cnt)))

;; a listy item is one that 
;; can be treated somewhat 
;; as a list
(defn listy? [something]
  (or (list? something)
      (vector? something)
      (set? something)
      (seq? something)))

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

