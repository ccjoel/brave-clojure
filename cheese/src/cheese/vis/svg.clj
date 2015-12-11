(ns cheese.vis.svg)

(defn latlng->point
  "convert lan/lng map to comma separated string point"
  [latlng]
  (str (:lng latlng) "," (:lat latlng)))

(defn points
  [locations]
  (clojure.string/join " " (map latlng->point locations)))


