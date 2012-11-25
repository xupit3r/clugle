(ns clugle.learn.data.table
  (:require [clugle.util.hlpr :as hlpr]))

;;;; functions for creating in memory 
;;;; data tables and loading data from 
;;;; standard data formats (e.g. ARFF).

;; defines header information
(def header (create-struct
              :name
              :nump
              :classp
              :ignorep))

;; defines an in memory table
(def dtable (create-struct
              :name
              :columns
              :examples
              :klass))

;; functions declared in this file
(declare print-table columns-new nump ignorep klass-idx mdata)

;; prints the table contents
;; NOTE: this attempts to print 
;; the whole table (regardless 
;; of size)
(defn print-table [tbl]
  (clojure.pprint/pprint tbl))


;; TABLE COMPONENT FUNCTIONS ;;

;; builds the header information
;; accepts the set of columns for 
;; the table and the class index
;; (klass) and returns a list
;; of header objs representing
;; the supplied columns
(defn columns-new [cols klass]
  (assoc-in 
    (vec 
      (map 
        #(struct-map
           header
           :name %
           :nump (nump %)
           :klassp false ; set later 
           :ignorep (ignorep %)) cols))
    [klass :klassp] true))
  
;; is the supplied column numeric?
(defn nump [col]
  (= (.indexOf (subs col 0 1) "$") 0))

;; should we ignore the supplied column?
(defn ignorep [col]
  (= (.indexOf (subs col 0 1) "?") 0))

;; returns an appropriate class (klass)
;; index for the table. if the supplied
;; index is non-negative, then it is assumed
;; that this is the index, otherwise, the
;; index will be the last column of the table
(defn klass-idx [kidx width]
  (if (< kidx 0)
    (- width 1)
    kidx))

;; build an in memory representation of a
;; data table. if parameter shuffle? is 
;; set to true, the provided examples
;; (egs) will be stored in a random order.
(defn mdata [shuffle? {nme :name
                       cols :columns
                       egs :examples
                       kls :klass}]
  (struct-map 
    dtable
    :name nme
    :columns (columns-new 
               cols (klass-idx kls (count cols))) 
    :examples (if shuffle?
                (hlpr/shuffem egs) 
                egs)
    :klass (klass-idx kls (count cols))))
