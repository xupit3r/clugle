(ns clugle.util)

;;;; general utility methods for the project

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

