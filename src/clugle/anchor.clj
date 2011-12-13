(ns clugle.anchor
  (:require clugle.soupu :as soupu)
  (:import java.net URL))

;;;; functions for working with anchor tags ;;;;

;; determines if a supplied 
;; URL is absolute or relative
;; ... I am using Clojure for a couple
;; reasons, one of them is that I like 
;; having available that big ole Java 
;; library.  I think this is a case where 
;; it would be helpful.

;; returns the supplied set of anchors
;; but with all anchors defined using 
;; absolute paths
(defn bld-abs-anchor [base anchors]
  (let [baseUrl (URL. base)]
    (mapcat (fn [an]
              (let [newURL (URL. baseURL 
                                 (get (soupu/attributes an) :href))]
              (assoc (soupu/attributes an) 
                     :href
                     (.toString newURL)))) 
            anchors)))

