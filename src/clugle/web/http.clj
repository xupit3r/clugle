(ns clugle.web.http
  (:require [clj-http.client :as client]))

;;;; HTTP utility methods

;; was it a good response?
(defn ok? [status] (< status 300))

;; carries out an HTTP GET request
;; returns a map containing the head 
;; and the body of the response
(defn request-get [url]
  (let [{status :status, 
         header :header, 
         body :body} (client/get url)]
    (if (ok? status)
      {:header header, :body body} 
      {:error "request failed"})))

