(ns clugle.api
    (:require [clugle.http :as http] 
              [clugle.response :as response]))

;;;; this file contains logic for generally 
;;;; dealing with service APIs


;; basic process:
;; 1. make a request to the service API
;; 2. handle the response (this will typically
;;    mean pulling out the useful data in the 
;;    response payload)
;; 3. put the data into some kind of canonical form

;; wrapper for request method
(defn make-request
  ([the-func url] (the-func url)))

;; wrapper for response handling
(defn handle-response
  ([the-func resp] (the-func resp)))

;; wrapper for data canonicalization
(defn canon-data
  ([data] data)
  ([the-func data] (the-func data)))

;; carry out a call to some RESTful API
(defn call-api
  ([url] (call-api 
           (fn [url] (:body (http/get-request url))) 
           response/handle-json  
           canon-data
           url))
  ([req-func resp-func canon-func url]
    (->> 
      (make-request req-func url)
      (handle-response resp-func)
      (canon-data canon-func))))