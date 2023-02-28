(ns clugle.core
  (:require [org.httpkit.server :refer [run-server]]
            [ring.middleware.defaults :refer [wrap-defaults, site-defaults]]
            [clugle.api.base :refer [api-routes]])
  (:gen-class))

(defn -main
  "main entry point - starts the API server"
  [& args]
  (let [port (Integer/parseInt (or (System/getenv "PORT") "3000"))]
    (run-server (wrap-defaults #'api-routes site-defaults) {:port port})
    (println (str "Running webserver at http://127.0.0.1:" port "/"))))