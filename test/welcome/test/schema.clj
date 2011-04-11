(ns welcome.test.schema
  (:use [welcome.schema] :reload)
  (:use [clojure.test]))

(deftest ordered-list
  (is (= "<xs:element name=\"ordered-list\"><xs:complexType><xs:sequence></xs:sequence></xs:complexType></xs:element>"
         (schema [:ordered-list! {:name "ordered-list"}] "xs"))))

(deftest unordered-list
  (is (= "<xs:element name=\"unordered-list\"><xs:complexType><xs:all></xs:all></xs:complexType></xs:element>"
         (schema [:unordered-list! {:name "unordered-list"}] "xs"))))

(deftest choice-list
  (is (= "<xs:element name=\"choice\"><xs:complexType><xs:choice></xs:choice></xs:complexType></xs:element>"
         (schema [:choice! {:name "choice"}] "xs"))))

(deftest restricted-simple
  (is (= "<xs:element name=\"restricted\"><xs:simpleType><xs:restriction base=\"xs:string\"></xs:restriction></xs:simpleType></xs:element>"
         (schema [:restricted! {:name "restricted" :base "xs:string"}] "xs"))))

(deftest restricted-complex
  (is (= "<xs:element name=\"restricted\"><xs:complexType><xs:restriction base=\"xs:string\"></xs:restriction></xs:complexType></xs:element>"
         (schema [:restricted! {:name "restricted" :base "xs:string" :type "xs:complexType"}] "xs"))))
