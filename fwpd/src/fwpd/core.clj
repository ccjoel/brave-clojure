(ns fwpd.core
  (:require [clojure.java.io :as io]))

(def filename "resources/suspects.csv")

;(slurp filename)

(def vamp-keys [:name :glitter-index])

(defn str->int
  [str]
  (Integer. str))

(def conversions {:name identity
                  :glitter-index str->int})

(defn convert
  [vamp-key value]
  ((get conversions vamp-key) value))

(defn parse
  "Convert a CSV file into rows of columns.
  Sample output:
      ['Ed' '10']"
  [string]
  (map #(clojure.string/split % #",")
       (clojure.string/split string #"\n")))

(defn mapify
  "Return a seq of maps like {:name \"Char Name\" :glitter-index 10}
    from a list of vector pairs with no keys (only values)."
  [rows]
  (map (fn [unmapped-row]
         (reduce (fn [row-map [vamp-key value]]
                   (assoc row-map vamp-key (convert vamp-key value)))
                 {}
                 (map vector vamp-keys unmapped-row)))
       rows))

(defn glitter-filter
  [minimum-glitter records]
  (filter #(>= (:glitter-index %) minimum-glitter) records))

(defn append
  "Adds additional suspects to the csv file list"
  [string]
  (spit filename string :append true))


;

(defn recurless-sum
  ([vals]
    (recurless-sum vals 0))
  ([vals accumulating-total]
    (if (empty? vals)
      accumulating-total
      (recurless-sum (rest vals) (+ (first vals) accumulating-total)))))

(defn sum
  ([vals]
   (sum vals 0))
  ([vals accumulator]
   (if (empty? vals)
     accumulator
     (recur (rest vals) (+ (first vals) accumulator)))))

(require '[clojure.string :as s])

(defn clean
  [text]
  (s/replace (s/trim text) #"lol" "LOL"))

; we can do function composition as we would do in mathematical equations
#((comp inc *) 2 3)
; => 7 , since * 2 3 = 6 and inc 6 = 7

; takes 10, divides by 2, then increments, then converts to int (floor)
(def spell-slots-comp (comp int inc #(/ % 2) 10))

(defn sleepy-identity
    "Returns the given value after 1 second"
    [x]
    (Thread/sleep 1000)
    x)
;(sleepy-identity "Mr. Fantastico")
; => "Mr. Fantastico" after 1 second

;(sleepy-identity "Mr. Fantastico")
; => "Mr. Fantastico" after 1 second

(def memo-sleepy-identity (memoize sleepy-identity))
;(memo-sleepy-identity "Mr. Fantastico")
; => "Mr. Fantastico" after 1 second

;(memo-sleepy-identity "Mr. Fantastico")
; => "Mr. Fantastico" immediately

; memoize caches the result of heavy computation functions or heavy network call results


; private functions in clojure:

(defn- private-fun
  "example"
  []
  )
