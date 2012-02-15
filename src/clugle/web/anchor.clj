(ns clugle.web.anchor
  (:require [clugle.web.soupu :as soupu])
  (:import (java.net URL)))

;;;; functions for working with anchor tags ;;;;

;; is a URL absolute?
(defn abs-url? [url]
  (try
    (do (URL. url) 
      true)
    (catch Exception e
      false)))

;; retrieve an href from an anchor
(defn get-href [anchor]
  (get (soupu/attributes anchor) :href))

;; returns the supplied set of anchors
;; but with all anchors defined using 
;; absolute paths
(defn bld-abs-anchor [base anchors]
  (let [baseUrl (URL. base)]
    (map 
      (fn [an]
        (let [theUrl (get-href an)]
          (if (not (abs-url? theUrl))
            (let [newURL (URL. baseUrl (get-href an))]
              (vector
                (nth an 0)
                (assoc (soupu/attributes an) 
                       :href
                       (.toString newURL))
                (nth an 2)))
            an)))
      anchors)))
    

