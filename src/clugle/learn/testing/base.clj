(ns clugle.learn.testing.base)

;; generic function to run a simple
;; predictor against a dataset and
;; return some simple results
(defn runner [{dataset :dataset 
               predictor :predictor
               actual :actual}]
  (reduce
   #(assoc
     %1
     :total (inc (:total %1))
     :correct (+ (:correct %1)
                 (if (= (%2 0) (%2 1)) 1 0)))
   {:total 0 :correct 0}
   (mapv
    #(vec [(actual %1) (predictor %1)])
    dataset)))

;; generates a report of the run
(defn reporter [{total :total correct :correct}]
  (let [accuracy (/ correct total)]
    {:total total
     :correct correct
     :accuracy accuracy
     :message (format
               "got %.3f%% correct of %d samples"
               (float (* 100 accuracy))
               total)}))