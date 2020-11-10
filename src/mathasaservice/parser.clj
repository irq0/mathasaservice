(ns mathasaservice.parser
  (:require
   [clojure.edn :as edn]
   [instaparse.core :as insta]))

(def arith-parser
  "Arithmatic parser, right recursive"
  (insta/parser
   "expr = add | sub | fact
     add = fact <'+'> expr
     sub = fact <'-'> expr

     <fact> = mult | div | term
     mult = term <'*'> fact
     div  = term <'/'> fact

     <term> = paren-expr | number
     <paren-expr> = <'('> expr <')'>
     number = #'[0-9]+'"

   :auto-whitespace :standard))

(def integer-math-transform
  "Evaluate _integer_ math. Expect cut offs when using division"
  (partial insta/transform
           {:expr identity
            :add +
            :sub -
            :mult *
            :div quot
            :number #(Integer/parseInt %)}))

(def full-math-transform
  "Evaluate with full clojure math"
  (partial insta/transform
           {:expr identity
            :add +
            :sub -
            :mult *
            :div /
            :number edn/read-string}))

(def float-math-transform
  "Floating point eval"
  (partial insta/transform
           {:expr identity
            :add +
            :sub -
            :mult *
            :div /
            :number #(Double/parseDouble %)}))

(defn format-parse-failure [fail]
  (format "Failed to parse arithmetic expression at %d:%d"
          (:line fail)
          (:column fail)))

(defn parse-eval-infix-arith
  "Parse and evaluate infix arithmatic"
  [s]
  (let [result (->>
                (insta/parse
                 arith-parser s
                 :partial false
                 :total false)
                integer-math-transform)]
    (when (insta/failure? result)
      (throw (IllegalArgumentException.
              (format-parse-failure (insta/get-failure result)))))
    result))

