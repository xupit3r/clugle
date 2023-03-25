(ns clugle.learn.wordnet.db
  (:require [monger.core :as mg]
            [mongo.collection :as mc])
  (:import [org.bson.types ObjectId]))

(def DB_NAME "wordnet")

(def COLLECTIONS {:words "words"})

(def CONNECTION (mg/connect))

;; grabs a db connection
(defn connect-db [name]
  (mg/get-db CONNECTION name))

;; inserts a word document into the "words"
;; collection
(defn insert-word [doc]
  (mc/insert
   (connect-db DB_NAME)
   (:words COLLECTIONS)
   (assoc doc :_id (ObjectId.))))