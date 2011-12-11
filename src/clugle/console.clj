(ns clugle.console)

;;;; console printing


;; print tag search result
(defn prin-tag-res [result for-tag]
  (print "---begin:" for-tag "---\n")
  (print result "\n")
  (print "---end:" for-tag "---\n")
  (print "\n"))

;; prints the tag label followed by 
;; whatever
(defn print-generic-tag [for-tag result]
  (print for-tag result "\n"))




