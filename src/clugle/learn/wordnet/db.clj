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

;; updates docs with the sentiment enhancements
(defn update-sentiment [sentiments]
  (doseq [sentiment sentiments]
    (println (format "updating %s" (:word sentiment)))
    (mc/update (connect-db DB_NAME)
               (:words COLLECTIONS)
               {:$and [{:pos (:pos sentiment)}
                       {:offset (:offset sentiment)}]}
               {:$set {:sentiment sentiment}})))

;; finds a reference in the database.
;; unique identification is done by
;; using the combo of part of speech and 
;; lemma offset
(defn get-ref [ref]
  (mc/find-one-as-map (connect-db DB_NAME)
                      (:words COLLECTIONS)
                      {:$and [{:pos (:pos ref)
                               :offset (:offset ref)}]}
                      {:sentiment.positive 1
                       :sentiment.negative 1
                       :lemma 1}))

;; finds ALL entries for a given word
(defn get-entries [word]
  (mc/find-maps (connect-db DB_NAME)
                (:words COLLECTIONS)
                {:lemma word}
                {:refs 1
                 :pos 1
                 :offset 1
                 :lemma 1
                 :sentiment.positive 1
                 :sentiment.negative 1}))