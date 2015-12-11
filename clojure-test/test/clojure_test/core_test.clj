(ns clojure-test.core-test
  (:require [clojure.test :refer :all]
            [clojure-test.core :refer :all]))

(deftest a-test
  (testing "FIXME, I fail."
    (is (= 1 1))))

(deftest nt-test
  (testing "should return nth element of a sequence"
    (is (= 7 (clojure-test.core/nt '(0 1 7 3) 2)))))
