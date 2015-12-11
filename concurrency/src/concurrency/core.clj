(ns concurrency.core
  (:gen-class))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println "Hello, World!"))

(comment
  "
  Concurrency: managing more than one task, while doing one at a time
  Parallelism: managing more than one task at the same time
  Distributed: managing more than one task, one computer does 'one' and they communicate over the network
  Asynchronous: don't wait idle for blocking calls like I/O, instead handle them when an event is fired

  Thread: a subprogram that runs separate of another program or subprograms, whiel sharing memory with its parent process

  Because memory is shared, these problems arise:

  3 problems with threading:

  1. Reference cell
     Two threads modify and read the same memory sell at different times causing
     the read values and output to be inconsistent and unpredictable.

  2. Mutual exclusion
     When two threads share a resource, say, a file in which
     they will read or write, they might both try to modify (e.g. append) the file
     and they might do it at the same time, producing random bytes of interleaved info.

  3. Deadlock, or dinning philosopher's problem
     When each threads uses multiple resources and each one has part of them, and
     each one depend on the one the other has so they block each other indefinitely.

  When writing serial code, one does either of these 3 events:

  1. Task definition
  2. Task execution
  3. Require task's results

  For example a GET to a site. When we make the GET call, we wait until we get the result,
  or, we continue until we are signaled that the result is ready, and then require the task's
  result: asynchronous.

  Futures:

  ```
   (future (Thread/sleep 4000)
        (println 'I'll print after 4 seconds'))
   (println 'I'll print immediately')
  ```

  Futures are like creating a thread, and when the future is dereferenced to get
  the resulting value, that thread returns the value to the calling parent thread.

  or, maybe you dont care for the result, and you just send a thread to do the task
  and not even check when its completed for any result.

  (let [result (future (println 'this prints once')
                     (+ 1 1))]
  (println 'deref: ' (deref result))
  (println '@: ' @result))


  (realized? (future (Thread/sleep 1000)))
  ; => false

  (let [f (future)]
    @f
    (realized? f))
  ; => true


  (def jackson-5-delay
  (delay (let [message 'Just call my name and I'll be there']
           (println 'First deref:' message)
           message)))

  (force jackson-5-delay)
  ; => First deref: Just call my name and I'll be there
  ; => 'Just call my name and I'll be there'

  @jackson-5-delay
  ; => 'Just call my name and I'll be there'

  (def headshots ['nina.jpg' 'joe.jpg' 'alfie.jpg'])

  (defn email-user
    [email-address]
    (println 'Sending headshot to' email-address))

  (defn upload-file
    [file]
    true)

  (let [notify (delay (email-user 'admin@localhost'))]
    (doseq [headshot headshots]
      (future (upload-file headshot) (force notify))))


  "
)


;Promises allow you to express that you expect a result without having to
;define the task that should produce it or when that task should run.

(def my-promise (promise))
(deliver my-promise (+ 1 2))
@my-promise


(def yak-butter-international
  {:store "Yak Butter International"
   :price 99
   :smoothness 90})

(def butter-than-nothing
  {:store "Butter than Nothing"
   :price 150
   :smoothness 83})
;; This is the butter that meets our requirements
(def baby-got-yak
  {:store "Baby Got Yak"
   :price 94
   :smoothness 99
   })

(defn mock-api-call
  [result]
  (Thread/sleep 1000)
  result)

(defn satisfactory?
  "If the butter meets our criteria, return the butter, else return false"
  [butter]
  (and (<= (:price butter) 100)
       (>= (:smoothness butter) 97)
       butter))

;(time (some (comp satisfactory? mock-api-call)
;            [yak-butter-international butter-than-nothing baby-got-yak]))

;(time
; (let [butter-promise (promise)]
;   (doseq [butter [yak-butter-international butter-than-nothing baby-got-yak]]
;     (future (if-let [satisfactory-butter (satisfactory? (mock-api-call butter))]
;               (deliver butter-promise satisfactory-butter))))
;   (println "And the winner is:" @butter-promise)))

(comment
(let [ferengi-wisdom-promise (promise)]
  (future (println "Here's some Ferengi wisdom:" @ferengi-wisdom-promise))
  (Thread/sleep 100)
  (deliver ferengi-wisdom-promise "Whisper your way to success"))
)

(defmacro wait
  "Sleep `timeout` seconds before evaluating body"
  [timeout & body]
  `(do (Thread/sleep ~timeout) ~@body))

(comment
(let [saying3 (promise)]
  (future (deliver saying3 (wait 100 "Cheerio!")))
  @(let [saying2 (promise)]
     (future (deliver saying2 (wait 400 "Pip pip!")))
     @(let [saying1 (promise)]
        (future (deliver saying1 (wait 200 "'Ello, gov'na!")))
        (println @saying1)
        saying1)
     (println @saying2)
     saying2)
  (println @saying3)
  saying3)
)

;; Let's define enqueue

(defmacro enqueue
  ([q concurrent-promise-name concurrent serialized]
   `(let [~concurrent-promise-name (promise)]
      (future (deliver ~concurrent-promise-name ~concurrent))
      (defer ~q)
      ~serialized
      ~concurrent-promise-name))
  ([concurrent-promise-name concurrent serialized]
   `(enqueue (future) ~concurrent-promise-name ~concurrent ~serialized)))


;(comment
;; How we want this macro to work
;(-> (enqueue saying (wait 200 "'Ello, gov'na!") (println @saying))
;        (enqueue saying (wait 400 "Pip pip!") (println @saying))
;        (enqueue saying (wait 100 "Cheerio!") (println @saying)))
;
;(time @(-> (enqueue saying (wait 200 "'Ello, gov'na!") (println @saying))
;                      (enqueue saying (wait 400 "Pip pip!") (println @saying))
;                      (enqueue saying (wait 100 "Cheerio!") (println @saying))))
;
;)

(def fred (atom {:cuddle-hunger-level 0
                                  :percent-deteriorated 0}))

;(let [zombie-state @fred]
;    (if (>= (:percent-deteriorated zombie-state) 50)
;          (future (println (:percent-deteriorated zombie-state)))))

;(swap! fred
;       (fn [current-state]
;         (merge-with + current-state {:cuddle-hunger-level 1
;                                      :percent-deteriorated 1})))

;(swap! fred update-in [:cuddle-hunger-level] + 10)

;(comment
;(let [num (atom 1)
;      s1 @num]
;  (swap! num inc)
;  (println "State 1:" s1)
;  (println "Current State:" @num))

;(reset! fred {:cuddle-hunger-level 0
;              :percent-deteriorated 0})
;)

(comment "
  A watch is a function that takes four arguments: a key, the reference being
  watched, its previous state, and its new state. You can register any
  number of watches with a reference type.
  ")

(defn shuffle-speed
  [zombie]
  (* (:cuddle-hunger-level zombie)
     (- 100 (:percent-deteriorated zombie))))

(defn shuffle-alert
  "alerts when shuffle speed of zombie reaches 5000 shuffles per hour"
  [key watched old-state new-state]
  (let [sph (shuffle-speed new-state)]
    (if (> sph 5000)
      (do
        (println "Run, you fool!")
        (println "The zombie's SPH is now" sph)
        (println "This message brought to you courtesy of " key))
      (do
        (println "All's well with" key)
        (println "Cuddle hunger: " (:cuddle-hunger-level new-state))
        (println "Percent deteriorated: " (:percent-deteriorated new-state))
        (println "SPH: " sph)))))

(comment "
  You can attach this function to fred with add-watch. The general form of
  add-watch is `(add-watch ref key watch-fn)`
  ")

;(reset! fred {:cuddle-hunger-level 22
;              :percent-deteriorated 2})

;(add-watch fred :fred-shuffle-alert shuffle-alert)

;(swap! fred update-in [:percent-deteriorated] + 1)
;(swap! fred update-in [:cuddle-hunger-level] + 30)

(comment "
  Validators let you specify what states are allowable for a reference.
  For example, here’s a validator that you could use to ensure that a zombie’s
  :percent-deteriorated is between 0 and 100:
  ")

(defn percent-deteriorated-validator
  [{:keys [:percent-deteriorated]}]
  (and (>= percent-deteriorated 0)
       (<= percent-deteriorated 100)))
; --^ could also manually throw an exception at last line before ending definition
;     to show a custom error msg plus throw a custom exception

;; You can attach a validator during atom creation

;(comment
;(def bobby
;  (atom
;   {:cuddle-hunger-level 0 :percent-deteriorated 0}
;   :validator percent-deteriorated-validator))
;(swap! bobby update-in [:percent-deteriorated] + 200)
; This throws invalid reference state
;)

(comment "
  These transactions have three features: (ACI from ACID)

1. They are atomic, meaning that all refs are updated or none of them are.
2. They are consistent, meaning that the refs always appear to have valid states.
   A sock will always belong to a dryer or a gnome, but never both or neither.
3. They are isolated, meaning that transactions behave as if they executed
  serially; if two threads are simultaneously running transactions that alter
  the same ref, one transaction will retry. This is similar to the
  compare-and-set semantics of atoms.
  ")

; create socks, gnomes, and count the socks per atom methods

(def sock-variaties
  #{"darned" "argyle" "wool" "horsehair" "mulleted"
    "passive-aggressive" "striped" "polka-dotted"
    "athletic" "business" "power" "invisible" "gollumed"})

(defn sock-count
  [sock-variety count]
  {:variety sock-variety
   :count count})

(defn generate-sock-gnome
  "Create an initial sock gnome state wit no socks"
  [name]
  {:name name
   :socks #{}})

(def sock-gnome (ref (generate-sock-gnome "Barumpharumph")))
(def dryer (ref {:name "LG 1337"
                 :socks (set (map #(sock-count % 2) sock-variaties))}))

(:socks @dryer)

(comment "
  Now everything’s in place to perform the transfer. We’ll want to modify the
  sock-gnome ref to show that it has gained a sock and modify the dryer ref to
  show that it’s lost a sock. You modify refs using alter, and you must use
  alter within a transaction. dosync initiates a transaction and defines its
  extent; you put all transaction operations in its body. Here we use these
  tools to define a steal-sock function, and then call it on our two refs:
  ")

(defn steal-sock
  [gnome dryer]
  (dosync
   (when-let [pair (some #(if (= (count %) 2) %) (:socks @dryer))]
     (let [updated-count (sock-count (:variety pair) 1)]
       (alter gnome update-in [:socks] conj updated-count)
       (alter dryer update-in [:socks] disj pair)
       (alter dryer update-in [:socks] conj updated-count)))))

(defn similar-socks
  [target-sock sock-set]
  (filter #(= (:variety %) (:variety target-sock)) sock-set))

(similar-socks (first (:socks @sock-gnome)) (:socks @dryer))

(comment "

`commute`

commute allows you to update a ref’s state within a transaction, just like alter
However, its behavior at commit time is completely different.

  Here’s how alter behaves:

1. Reach outside the transaction and read the ref’s current state.
2. Compare the current state to the state the ref started with within the transaction.
3. If the two differ, make the transaction retry.
4. Otherwise, commit the altered ref state.

  commute, on the other hand, behaves like this at commit time:

1. Reach outside the transaction and read the ref’s current state.
2. Run the commute function again using the current state.
3. Commit the result.
  ")


(def ^:dynamic *notification-address* "dobby@elf.org")


;; Change value of a dynamic var:

(binding [*notification-address* "test@elf.org"]
  *notification-address*)

;; Change binding of clojure's dynamic var out so that instead of printing to
;  console we print to file instead:

(binding [*out* (clojure.java.io/writer "print-output")]
  (println "A man who carries a cat by the tail learns
something he can learn in no other way.
-- Mark Twain"))
(slurp "print-output")

(def ^:dynamic *troll-thought* nil)

(defn troll-riddle
  [your-answer]
  (let [number "man meat"]
    (when (thread-bound? #'*troll-thought*)
      (set! *troll-thought* number))
    (if (= number your-answer)
      "TROLL: You can cross the bridge!"
      "TROLL: Time yo eat you, succulent human!")))

(binding [*troll-thought* nil]
  (troll-riddle 2)
  (str "SUCCULENT HUMAN: Oooooh! The answer was " *troll-thought*))

; Summary: swap! to update an atom, alter! to update a ref, alter-var-root to update a var:

;; usually we dont even want to do this:
(def power-source "hair")
(alter-var-root #'power-source (fn [_] "7-eleven parking lot"))

power-source

;; or, temporarily alter a var root, which works as bindings for dynamic as well,
;; with:

(with-redefs [*out* *out*]
        (doto (Thread. #(println "with redefs allows me to show up in the REPL"))
          .start
          .join))

(defn always-1
  []
  1)

(take 5 (repeatedly always-1))

(take 5 (repeatedly #(identity 1)))

(take 5 (repeatedly (partial rand-int 10)))

(def alphabet-length 26)

;; Vector of chars, A-Z
(def letters (mapv (comp str char (partial + 65)) (range alphabet-length)))


(mapv (comp str char (partial + 65)) (range alphabet-length))

(defn random-string
  "Returns a random string of specified length"
  [length]
  (apply str (take length (repeatedly #(rand-nth letters)))))

(defn random-string-list
  [list-length string-length]
  (doall (take list-length (repeatedly (partial random-string string-length)))))


(def orc-names (random-string-list 3000 7000))

;(time (dorun (map clojure.string/lower-case orc-names)))

;(time (dorun (pmap clojure.string/lower-case orc-names)))

(pmap (fn [number-group] (doall (map inc number-group)))
      (partition-all 3 numbers))

(time
 (dorun
  (apply concat
         (pmap (fn [name] (doall (map clojure.string/lower-case name)))
               (partition-all 1000 orc-name-abbrevs)))))

;; Instead of doing all this each time, we can create a fuction to reuse:

(defn ppmap
  "Partitioned pmap, for grouping map ops together to make parallel
  overhead worthwhile"
  [grain-size f & colls]
  (apply concat
         (apply pmap
                (fn [& pgroups] (doall (apply map f pgroups)))
                (map (partial partition-all grain-size) colls))))

; Sample use:
; (time (dorun (ppmap 1000 clojure.string/lower-case orc-name-abbrevs)))


