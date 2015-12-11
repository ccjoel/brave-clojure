(ns clojure-test.vamp
  (:gen-class))

(def vampire-database
  {
   0 {:makes-blood-puns? false, :has-pulse? true, :name "Fish"}
   1 {:makes-blood-puns? false, :has-pulse? true, :name "Mackson"}
   2 {:makes-blood-puns? true, :has-pulse? false, :name "Salvatore"}
   3 {:makes-blood-puns? true, :has-pulse? true, :name "Mickey"}
   })

(defn vampire-related-details
  ""
  [ssn]
  (Thread/sleep 1000)
  (get vampire-database ssn))

(defn vampire?
  "returns a record if its a vampire. It is a vampire if it makes blood puns and does not have a pulse"
  [record]
  (and (:makes-blood-puns? record)
       (not (:has-pulse? record))
       record))

(defn identify-vampire
  [all-ssn]
  (first (filter vampire?
                 (map vampire-related-details all-ssn))))

(time (def mapped-details (map vampire-related-details (range 0 10000000))))

(def batman-song (concat (take 8 (repeat "na")) ["Batman!"]))

; or can use repeatedly, which receives a function to generate items in a seq:

(defn repeating [] (take 3 (repeatedly #(rand-int 10))))

(defn even-numbers
    ([] (even-numbers 0))
    ([n] (cons n (lazy-seq (even-numbers (+ n 2))))))

#(take 10 (even-numbers))
; => (0 2 4 6 8 10 12 14 16 18)


; difference between conj and into...
; into receives two collections
; whereas conj and disj receive a collection and a scalar

; apply calls a function and applies these elements of a vector as args
; just like in javascript
#(apply max [0 1 2])
; => 2


;partial takes a function and any number of arguments. It then returns a new function. When you call the returned function, it calls the original function with the original arguments you supplied it along with the new arguments.

(defn my-partial
  "receives a function and rest arguments, creates a new function that uses the a partial function and applies additional args"
  [partialized-fn & args]
  (fn [& more-args]
   (apply partialized-fn (into args more-args))))

; partials are great for creating secondary functions in which it passes default arguments to another previously defined function


