(defproject clojure-fun "1.0"
  :description "quick, learn!"
  :url "https://github.com/xupit3r/clugle"
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url "https://www.eclipse.org/legal/epl-2.0/"}
  :dependencies [[org.clojure/clojure "1.11.1"]
                 [clj-http "3.12.3"]
                 [clj-tagsoup "0.3.0" :exclusions [org.clojure/clojure]] 
                 [cheshire "5.11.0"]
                 [compojure "1.7.0"] 
                 [http-kit "2.6.0"]
                 [ring/ring-defaults "0.3.4"]]
  :repl-options {:init-ns clugle.core}
  :main clugle.core)