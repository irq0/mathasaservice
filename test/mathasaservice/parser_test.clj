(ns mathasaservice.parser-test
  (:require [clojure.test :refer :all]
            [mathasaservice.parser :refer :all]))

(deftest arith-parser-test
  (testing "Basics"
    (is (= 4 (parse-eval-infix-arith "2+2")))
    (is (= 4 (parse-eval-infix-arith "6-2")))
    (is (= 4 (parse-eval-infix-arith "2*2")))
    (is (= 4 (parse-eval-infix-arith "8/2"))))
  (testing "Integer Math"
    (is (= 0 (parse-eval-infix-arith "1/2"))))
  (testing "Whitespace"
    (is (= 42 (parse-eval-infix-arith "                                   21    * 2"))))
  (testing "Example"
    (is (= -134 (parse-eval-infix-arith "2 * (23/(3*3))- 23 * (2*3)")))))
