(defproject mathasaservice "0.1.0-SNAPSHOT"
  :dependencies [[org.clojure/clojure "1.10.1"]
                 [ring/ring-core "1.8.1"]
                 [ring/ring-jetty-adapter "1.8.1"]
                 [mount "0.1.16"]
                 [com.taoensso/timbre "4.10.0"]
                 [compojure "1.6.2"]
                 [ring/ring-json "0.5.0"]
                 [instaparse "1.4.10"]]
  :main mathasaservice.core
  :aot [mathasaservice.core]
  :repl-options {:init-ns mathasaservice.core})
