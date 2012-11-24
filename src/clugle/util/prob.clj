(ns clugle.util.prob
  (:import (java.util Random)))

;;;; probabilityutility functions

;; seed for the random number generator
(def RANNUM_SEED 1024)

;; seeded random number generator
(def RANNUM_GEN (Random. RANNUM_SEED))

;; return a random number between
;; 0 and max. (NOTE: this uses a
;; seeded random number generator)
(defn mrand [max]
  (.nextInt RANNUM_GEN max))

