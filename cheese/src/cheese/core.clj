(ns cheese.core  ; ns refers clojure.core by default
  (:require [cheese.vis.svg :as svg]
            [clojure.string :as string]
            )
  (:gen-class))

;(require '[cheese.vis.svg :as svg])
; same as:
;(alias 'svg 'cheese.vis.svg)
; after require
; if i dont even want to use an alias:
;(refer 'cheese.vis.svg)

; using use instead of require, adds the refer automatically


(def heists [{:location "Cologne, Germany"
                            :cheese-name "Archbishop Hildebold's Cheese Pretzel"
                            :lat 50.95
                            :lng 6.97}
                          {:location "Zurich, Switzerland"
                                         :cheese-name "The Standard Emmental"
                                         :lat 47.37
                                         :lng 8.55}
                          {:location "Marseille, France"
                                         :cheese-name "Le Fromage de Cosquer"
                                         :lat 43.30
                                         :lng 5.37}
                          {:location "Zurich, Switzerland"
                                         :cheese-name "The Lesser Emmental"
                                         :lat 47.37
                                         :lng 8.55}
                          {:location "Vatican City"
                                         :cheese-name "The Cheese of Turin"
                                         :lat 41.90
                                         :lng 12.45}])

(defn -main
    [& args]
    (println (svg/points heists)))

; zipmap:

#(zipmap [:a :b :c] [1 2 3])
;=> {:a 1, :b 2, :c 3}

; merge-with:

#(merge-with + {:lat 50, :lng 10} {:lat 10, :lng 40})
;=> {:lat 60, :lng 50}  -> they were both merged with + function per each map entry

#(System/getProperty "user.dir")
