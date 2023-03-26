(ns clugle.learn.wordnet.db
  (:require [monger.core :as mg]
            [monger.collection :as mc])
  (:import [org.bson.types ObjectId]))

(def DB_NAME "wordnet")

(def COLLECTIONS {:words "words"})

(def CONNECTION (mg/connect))

;; grabs a db connection
(defn connect-db [name]
  (mg/get-db CONNECTION name))

;; inserts a collection of words into
;; the words collection
(defn insert-words [docs]
  (mc/insert-batch
   (connect-db DB_NAME)
   (:words COLLECTIONS)
   (mapv #(assoc % :_id (ObjectId.)) 
         docs)))