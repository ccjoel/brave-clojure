(ns clojure-test.core
;  (:require  [clojure.contrib.math :as m])
  (:gen-class))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println "417: I'm a teapot!"))

(defn plastic
  "I'm a sample accessible from repl"
  [& args]
  (print "prints with no old line!")
  (println "hello worlds end!"))

(defn tt
  "Docs for tt test"
  [& args]
  (if true
    (println "the value is true!")
    (println "the value is false!")))

(defn third-test
  "docs for third test"
  [& args]
  (if true
    (do (-main) "Hello worls in 2nd do!")))

(def listings
  ["fail" "mayne fial" "im learning something here"])

(defn error-message
  "defines an error message with a given severity as parameter. why i can do this a loooooooong string for documentationnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnn fdfdfdf
  no waaaaaay"
  [severity]
  (str "OMG the weather is "
  (if (= severity :mild)
    "okay"
    "super bad!"
    )))

(defn shout
  "shout someone!"
    [name]
    (str "consider yourself shouted!" name "!"))

(defn shout-to-ppl
  [& list-of-ppl]
  (map shout list-of-ppl))

;(defn +
;  [& args]
;  (+ args)
;  (println "i added something"))

(def mult (fn [x] (* 3 x) ))

(def asym-hobbit-body-parts [{:name "head" :size 3}
                             {:name "left-eye" :size 1}
                             {:name "left-ear" :size 1}
                             {:name "mouth" :size 1}
                             {:name "nose" :size 1}
                             {:name "neck" :size 2}
                             {:name "left-shoulder" :size 3}
                             {:name "left-upper-arm" :size 3}
                             {:name "chest" :size 10}
                             {:name "back" :size 10}
                             {:name "left-forearm" :size 3}
                             {:name "abdomen" :size 6}
                             {:name "left-kidney" :size 1}
                             {:name "left-hand" :size 2}
                             {:name "left-knee" :size 2}
                             {:name "left-thigh" :size 4}
                             {:name "left-lower-leg" :size 3}
                             {:name "left-achilles" :size 1}
                             {:name "left-foot" :size 2}
                             ])

(defn matching-part
  "creates other side of body"
  [part]
  {:name (clojure.string/replace (:name part) #"^left-" "right-")
   :size (:size part)})

(defn symmetrize-body-parts
  "Expects a collection of maps (prob vector) that have a :name and :size.
  Expects some names to have left- properties to clone to right"
  [asym-body-parts]
  (loop [remaining-asym-parts asym-body-parts
         final-body-parts []]
    (if (empty? remaining-asym-parts)
      final-body-parts
      (let [[part & remaining] remaining-asym-parts]
        (recur remaining
               (into final-body-parts
                     (set [part (matching-part part)])))))))

(defn iLoop
  "sample loop.. prints from 0 to x, and returns x at the end,
  since it prints after entering if, not before."
  [x]
  (loop [i 0]
    (if (not (= i x))
      (do
        (println i)
        (recur (inc i)))
      i
      )))

(defn symmetrize
  [asym]
  (reduce (fn [final-body-parts part]
            (into final-body-parts (set [part (matching-part part)])))
          []
          asym))

(defn hit
  [asym-body-parts]
  (let [sym-parts (symmetrize asym-body-parts)
        body-part-size-sum (reduce + (map :size sym-parts))
        target (rand body-part-size-sum)]
    (loop [[part & remaining] sym-parts
           accumulated-size (:size part)]
      (if (> accumulated-size target)
        part
        (recur remaining (+ accumulated-size (:size (first remaining))))))))

; ds( destroys parenthesis around
; cs(] changes parenthesis to ]
; ysiw)  adds parenthesis around, if using ( instead, adds with space

(if true
    (do  (println "Success!")
              "By Zeus's hammer!")
    (do  (println "Failure!")
              "By Aquaman's trident!"))

(when true
    (println "Success!")
    "abra cadabra")
(nil? 1)

{:first-name "Charlie"
  :last-name "McFishwich"}

(hash-map :a 1 :b 2)

(get  {:a 0 :b 1} :c "unicorns?")

(get-in  {:a 0 :b  {:c "ho hum"}}  [:b :c])

;[3 2 1]

(vector "creepy" "full" "moon")
; => ["creepy" "full" "moon"]

(conj  [1 2 3] 4)
; => [1 2 3 4]

'(1 2 3 4)
; => (1 2 3 4)


(nth '(:a :b :c) 0)
; => :a

;sets
#{"kurt vonnegut" 20 :icicle}

(contains? #{:a :b} :a)
; => true

; function arity overloading.. one will run depending on amount of params
;(defn multi-arity
 ;   ;; 3-arity arguments and body
  ;  ([first-arg second-arg third-arg]
   ;       (do-things first-arg second-arg third-arg))
    ;; 2-arity arguments and body
  ;  ([first-arg second-arg]
  ;        (do-things first-arg second-arg))
    ;; 1-arity arguments and body
  ;  ([first-arg]
   ;       (do-things first-arg)))

; separate head and rest in params
(defn favorite-things
    [name & things]
    (str "Hi, " name ", here are my favorite things: "
                (clojure.string/join ", " things)))

(favorite-things "Doreen" "gum" "shoes" "kara-te")
; => "Hi, Doreen, here are my favorite things: gum, shoes, kara-te"


;; Return the first element of a collection -> param vector destructuring
(defn my-first
    [[first-thing]] ; Notice that first-thing is within a vector
    first-thing)

(my-first  ["oven" "bike" "war-axe"])
; => "oven"

(def my-special-multiplier  (fn  [x]  (* x 3)))
(my-special-multiplier 12)
; => 36

; or,

(#(* % 3) 8)
; => 24

; By now you’ve seen that functions can return other functions. The returned functions are closures, which means that they can access all the variables that were in scope when the function was created

;(defn inc-maker
;    "Create a custom incrementor"
;    [inc-by]
;    #(+ % inc-by))

;;(def inc3  (inc-maker 3))

;;(inc3 7)
; => 10

;; sum with reduce
;(reduce + [1 2 3 4])
; => 10

;(#(+ % 100) 5)

;(defn pow
;  "to the power of"
;  [x y]
;  (m/expt x y))

;Clojure uses the Boolean operators or and and.
;or returns either the first truthy value or the last value.
;and returns the first falsey value or, if no values are falsey, the last truthy value.
;not -> negates boolean

;user=> (type 3)
;    => java.lang.Long

;user=> Long/MAX_VALUE
;9223372036854775807

(defn nt
  "Returns nth number. std lib reimplementation."
  [li n]
  ; use take + 1 of n, then get the last? nth is zero indexed. [0 1 2 3]
  (last (take (inc n) li)))

; conj on a list adds to the start:
;(conj '(0 1 2) 4)
;=> (4 0 1 2 )
; conj on a vector adds to the end, not the start:
;(conj '[0 1 2] 4)
;=> [0 1 2 4]

; transform list to vector:
;(vec '(0 1 2))

; lists behave like linked lists, where the left element is the last one in
; last in first out. you cant get elements with get (like array index..not part of lists)

; vectors work like an array... internally represented by a bin tree.
;finding a specific element with nth is faster
; when you add, you add to last, deeper into the tree

;(type str)
;(class 'str)
;(doc assert)
;(println Long/MAX_VALUE)
;(name "clojure.core/str")
;(boolean 4)
;(nil? nil)

; symbols is everything we use for variables. like a keyword in another language
; keywords use :colon before the keyword. it is like symbols in other languages -_-

;(rest '(0 1 2)) ; => (1 2)
;(next '(0 2 3)) ; => (2 3) ; looks same as rest but next returns false (nil) when
; there are no other elements left, while rest returns empty list (true)

;(first '())
;(last '())
;(count '(0 1 232 3))
;(sort '(0 2 3 4)) ; returns sorted value

;#{:a :b :c} ; this is a set. entries never repeat

;(disj (0 2 3) 2) ; removes a number for sequence (instead of conj, which adds to it)

;(contains? '(0 2 3) 2) ; returns true or false, depending if the value is in it

; add a value to a given map with assoc:
;user=> 
;(assoc  {:bolts 1088} :camshafts 3)
;=> {:camshafts 3 :bolts 1088}

; combine maps together using merge
; remove items from map ussing dissoc
; use map keys as getters as in javascript, to get the specific pair

; (def binds a value to a "variable"

; create a set from a vector or list:
;(set  [3 3 3 4 4])
; => #{3 4}

; destructuring

; functions take parameters with []
; vectors are defined as [] as well
; thus, to destructure vectors as function parameters we use [[]]
;; Return the first element of a collection
(defn my-first
    [[first-thing]] ; Notice that first-thing is within a vector
    first-thing) ; will return only the first thing from a vector

; theres also map destructuring:

;[{lat :lat lng :lng}]
; basically when receiving a map that has :lat and :lng keys, we bind them to 
; lat and lng on the functions for easier access to these 2 known map keys

;or, if the key and bound var are the same, just use:

(defn announce-treasure-location
    [{:keys  [lat lng]}]
    (println  (str "Treasure lat: " lat))
    (println  (str "Treasure lng: " lng)))

;You can retain access to the original map argument by using the :as keyword
(defn receive-treasure-location
    [{:keys  [lat lng] :as treasure-location}]
    (println  (str "Treasure lat: " lat))
    (println  (str "Treasure lng: " lng))

    ;; One would assume that this would put in new coordinates for your ship
   ; (steer-ship! treasure-location))
)
;(#(str %1 " and " %2) "cornbread" "butter beans")
; => "cornbread and butter beans"

;; Function call
;(* 8 3)

;; Anonymous function
;#(* % 3)

;You can also pass a rest parameter with %&


;(into  {}  (seq  {:a 1 :b 2 :c 3}))
; => {:a 1, :c 3, :b 2}

;(map str  ["a" "b" "c"]  ["A" "B" "C"])
; => ("aA" "bB" "cC")

(def human-consumption   [8.1 7.3 6.6 5.0])
(def critter-consumption  [0.0 0.2 0.3 1.1])
(defn unify-diet-data
    [human critter]
    {:human human
        :critter critter})

;(map unify-diet-data human-consumption critter-consumption)
; => ({:human 8.1, :critter 0.0}
;      {:human 7.3, :critter 0.2}
;      {:human 6.6, :critter 0.3}
;      {:human 5.0, :critter 1.8}


(def sum #(reduce + %))
(def avg #(/  (sum %)  (count %)))
(defn stats
    [numbers]
    (map #(% numbers)  [sum count avg]))

;(stats  [3 4 10])
; => (17 3 17/3)

;(stats  [80 1 44 13 6])
; => (144 5 144/5)

(def identities
    [{:alias "Batman" :real "Bruce Wayne"}
        {:alias "Spider-Man" :real "Peter Parker"}
        {:alias "Santa" :real "Your mom"}
        {:alias "Easter Bunny" :real "Your dad"}])

;; ---------- other uses for `reduce`

;(map :real identities)
; => ("Bruce Wayne" "Peter Parker" "Your mom" "Your dad"
(defn reduce-sample1
  []
(reduce
  (fn [new-map [key val]]
    (assoc new-map key (inc val)))
  {}
  {:max 30 :min 10})
)
; => {:max 31, :min 11}

(defn reduce-sample2
  []
  (reduce
      (fn [new-map [key val]]
      (if (> val 4)
        (assoc new-map key val)
        new-map))
    {}
    {:human 4.1
     :critter 3.9
     :mom 4.8}
  ))

;take returns the first n elements of the sequence
;whereas drop returns the sequence with the first n elements removed

; take-while receives a function that tests a sequence and we only take those values that receive a boolean true from that function.

; for example:

(def food-journal
    [{:month 1 :day 1 :human 5.3 :critter 2.3}
        {:month 1 :day 2 :human 5.1 :critter 2.0}
        {:month 2 :day 1 :human 4.9 :critter 2.1}
        {:month 2 :day 2 :human 5.0 :critter 2.5}
        {:month 3 :day 1 :human 4.2 :critter 3.3}
        {:month 3 :day 2 :human 4.0 :critter 3.8}
        {:month 4 :day 1 :human 3.7 :critter 3.9}
        {:month 4 :day 2 :human 3.7 :critter 3.6}])

;(take-while #(< (:month %) 3) food-journal)
; => returns the maps on the vector, where each map as month less than 3

; drop-while works almost similar. but stops dropping when finds a true value
;(drop-while #(< (:month %) 3) food-journal)


; to take and drop values, finding something in between, need to use both:

;(take-while #(< (:month %) 4)
;                        (drop-while #(< (:month %) 2) food-journal))


; an easier version of this, we can use filter:
(def food-small-humans (filter #(< (:human % 5)) food-journal))
;but filter processes all the data. we might want to stop at a given place.
;thats one  of the reasons of why take-while and drop-while exists, among others

; some returns true if theres any value that meets a function on a sequence
; it will return true or false if it finds any value.
; to return the actual value, we muse use `and` so that since its true it will
; return the last truthy val
; example:

(def some-res (some #(and (> (:critter %) 3) %) food-journal))

; sort sorts by alphatebticall order, sort-by sorts using a function like:

; (sort-by count ["aaa" "c" "bb"])
; => ("c" "bb" "aaa")

(def concatenated-res (concat [1 2] [3 4]))



