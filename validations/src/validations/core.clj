(comment "

  @author Joel Quiles
  @since Dec 9 2015


  ")

; --------------------------- Namespace and Main -------------------------------


(ns validations.core
  (:gen-class))



(defn -main
  "Main Validation runner"
  [& args]
  (println "Hello, World!"))


;; --------------------------- Start helper functions --------------------------


(def order-details
  "These details are a sample data to use in functions to be defined below"
  {:name "Mitchard Blimmons"
   :email "mitchard.blimonsgmail.com"})


(def order-details-validations
  {:name
   ["Please enter a name" not-empty]

   :email
   ["Please enter an email address" not-empty

   "Your email address doesn't look like an email address"
   #(or (empty? %) (re-seq #"@" %))]})


; validation will mean two things: one, to validate fields
; two, to validate all fields in total, and append all error messages to a vector:



(defn error-messages-for
  "Return a sequence of error messages"
  [to-validate message-validator-pairs]
  (map first (filter #(not ((second %) to-validate)) ; function
                     (partition 2 message-validator-pairs)))) ;sequence to filter


(comment "
pair format:
error-message validation ..... times as many validation pairs as required
")


(error-messages-for "" ["Please enter name" not-empty])


(defn validate
  "Returns a map with a vector of errors for each key to be validated.
  @param {map} to-validate
  @param {map} validations
  "
  [to-validate validations]
  (reduce (fn [errors validation]
            (let [[fieldname validation-check-groups] validation
                  value (get to-validate fieldname)
                  error-messages (error-messages-for value validation-check-groups)]
              (if (empty? error-messages)
                errors
                (assoc errors fieldname error-messages))))
          {}
          validations))



; Call:
(validate order-details order-details-validations)

(let [errors (validate order-details order-details-validations)]
  (if (empty? errors)
    (println :success)
    (println :failure errors)))


; We want to make a call like this:

(comment
  "
  (if-valid order-details order-details-validation errors
   (render :success)
   (render :failure errors))
  ")

; so we define a macro to implement this
(defmacro if-valid
  "Handle validation more concisely"
  [to-validate validations errors-name & then-else]
  `(let [~errors-name (validate ~to-validate ~validations)]
     (if (empty? ~errors-name)
       ~@then-else)))

(macroexpand
 '(if-valid order-details order-details-validations my-error-name
            (println :success)
            (println :failure my-error-name)))

