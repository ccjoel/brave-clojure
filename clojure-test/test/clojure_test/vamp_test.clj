(ns clojure-test.vamp-test
  (:require [clojure.test :refer :all]))

(deftest vamp-test
 (is  (=  (count clojure-test.vamp/vampire-database) 4))) 
