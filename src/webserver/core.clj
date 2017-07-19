(ns webserver.core)

(require 'webserver.db)

(defn getValue
  [value]
  (:value value "Key is missing")
)

(defn -main
  "I don't do a whole lot."
  []
  (webserver.db/create)
  (webserver.db/save "myKey" "myValue")
  (println (getValue (webserver.db/load "myKey")))
  (println (getValue (webserver.db/load "missingKey")))
)
