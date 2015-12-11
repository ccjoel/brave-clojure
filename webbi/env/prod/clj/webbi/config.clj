(ns webbi.config
  (:require [taoensso.timbre :as timbre]))

(def defaults
  {:init
   (fn []
     (timbre/info "\n-=[webbi started successfully]=-"))
   :middleware identity})
