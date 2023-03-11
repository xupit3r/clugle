(ns clugle.api.types
  (:require [cheshire.core :as json]))

;; returns a string
(defn string [v] (str v))

;; returns an integer
(defn integer [v] (Integer/parseInt v))

;; returns a non-lazy datastructure
(defn json [v] (json/parse-string-strict v))