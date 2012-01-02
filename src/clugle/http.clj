(ns clugle.http
  (:require [clj-http.client :as client]))

;;;; HTTP utility methods

;; was it a good response?
(defn res-ok? [status]
  ;FIXME: implement this method
  true)

;; carries out an HTTP GET request
;; returns a map containing the head 
;; and the body of the response
(defn get-request [url]
  (let [{status :status, header :header, body :body} (client/get "http://thejoeshow.net")]
    (if (res-ok? status)
      {"header" header, "body" body})))

