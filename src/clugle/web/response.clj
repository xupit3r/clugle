(ns clugle.web.response
  (:require [cheshire.core :as json]))

;;;; logic for handling responses
;;;; from the repo/code management
  

;; takes in some JSON and returns 
;; a map
(defn handle-json [str] 
  (json/parse-string str true))


;; takes in some XML and returns 
;; a map
(defn handle-xml [str] )

