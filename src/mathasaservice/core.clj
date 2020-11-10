(ns mathasaservice.core
  (:require [mathasaservice.webapp :as webapp]
            [taoensso.timbre :as log]
            [mount.core :as mount]))

(defn -main [& _]
  (java.util.Locale/setDefault java.util.Locale/ENGLISH)
  (mount/in-clj-mode)
  (mount/start
   #'webapp/webapp)
  (log/info "🖖"))
