(ns welcome.test.schema
  (:use [welcome.schema] :reload)
  (:use [clojure.test]))

(deftest ordered-list
  (is (= "<xs:element name=\"ordered-list\"><xs:complexType><xs:sequence><content/></xs:sequence></xs:complexType></xs:element>"
         (schema [:ordered-list! {:name "ordered-list"} [:content]] "xs"))))

(deftest unordered-list
  (is (= "<xs:element name=\"unordered-list\"><xs:complexType><xs:all><content/></xs:all></xs:complexType></xs:element>"
         (schema [:unordered-list! {:name "unordered-list"} [:content]] "xs"))))

(deftest choice-list
  (is (= "<xs:element name=\"choice\"><xs:complexType><xs:choice><content/></xs:choice></xs:complexType></xs:element>"
         (schema [:choice! {:name "choice"} [:content]] "xs"))))

(deftest restricted-simple
  (is (= "<xs:element name=\"restricted\"><xs:simpleType><xs:restriction base=\"xs:string\"><content/></xs:restriction></xs:simpleType></xs:element>"
         (schema [:restricted! {:name "restricted" :base "xs:string"} [:content]] "xs"))))

(deftest restricted-complex
  (is (= "<xs:element name=\"restricted\"><xs:complexType><xs:restriction base=\"xs:string\"><content/></xs:restriction></xs:complexType></xs:element>"
         (schema [:restricted! {:name "restricted" :base "xs:string" :type ":complexType"} [:content]] "xs"))))
