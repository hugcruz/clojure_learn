(ns webserver.db)

(require '[clojure.java.jdbc :as j])

(def db-settings
   {
    :classname   "org.h2.Driver"
    :subprotocol "h2:file"
    :subname     (str (System/getProperty "user.dir") "/" "demo")
    :user        "sa"
    :password    ""
   }
  )

(defn create
  []
  (if (.exists (clojure.java.io/as-file (str (System/getProperty "user.dir") "/" "demo.mv.db")))
    (println "Database already exists.")
    (j/db-do-commands db-settings
      (j/create-table-ddl :datatable
        [:key "varchar(32)"]
        [:value "varchar(32)"])
    )
  )
)

(defn save
  [key value]
  (j/insert! db-settings :datatable {
    :key key
    :value value
   }) 
  (println "Saving data here" key value)
)

(defn load
  [key]
  (
    let [results (j/query db-settings ["select value from datatable where key=?" key])]
    (first results)
  )
)
