(ns clugle.test.sentcore
  (:require [clojure.test :refer [deftest is]]
            [clugle.learn.text.sentcore :refer [senttext]]))

;; test text
(def faketext
  "Joe Smith was born in California. 
   In 2017, he went to Paris, France in the summer. 
   His flight left at 3:00pm on July 10th, 2017. 
   After eating some escargot for the first time, Joe said, \"That was delicious!\" 
   He sent a postcard to his sister Jane Smith. 
   After hearing about Joe's trip, Jane decided she might go to France one day.
   However, for now her next trip is to a creek down the street.
   She thought about this and was very disappointed")

;; expected per sentence sentiment
(def sentiment '("Neutral"
                 "Neutral" 
                 "Neutral" 
                 "Positive" 
                 "Neutral" 
                 "Neutral" 
                 "Neutral" 
                 "Negative"))

(deftest test-senttext []
  (is (= (senttext faketext) sentiment)))