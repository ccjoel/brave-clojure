(ns webbi.config
  (:require [selmer.parser :as parser]
            [taoensso.timbre :as timbre]
            [webbi.dev-middleware :refer [wrap-dev]]))

(def defaults
  {:init
   (fn []
     (parser/cache-off!)
     (timbre/info "\n-=[webbi started successfully using the development profile]=-"))
   :middleware wrap-dev})
