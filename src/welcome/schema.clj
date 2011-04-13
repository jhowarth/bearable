(ns welcome.schema
 (:use [clojure.contrib.prxml]))

(def *xmlns* nil)

(defn add-xmlns
  "Adds the namespace to a tag."
  [tag]
  (symbol (str *xmlns* tag)))

(defn wrap-seq
  "Returns a vector with contents wrapped in an element
  of complexType with tag tag."
  [tag attrs contents]
  (let [name (:name attrs)
        attrs (dissoc attrs :name)]
    [(add-xmlns :element) {:name name}
                 [(add-xmlns :complexType)
                   [(add-xmlns tag) attrs
                     contents]]]))

(defmethod print-xml-tag :ordered-list!
  "Prints an element that permits only the child elements to appear in a specific order.
  See: http://www.w3schools.com/schema/el_sequence.asp"
  [tag attrs contents]
  (print-xml (wrap-seq :sequence attrs contents)))

(defmethod print-xml-tag :unordered-list! [tag attrs contents]
  "Prints an element that permits only the child elements to appear in any order.
  See: http://www.w3schools.com/schema/el_all.asp"
  (print-xml (wrap-seq :all attrs contents)))

(defmethod print-xml-tag :choice! [tag attrs contents]
  "Prints an element that permits only one of the child elements to appear.
  See: http://www.w3schools.com/schema/el_choice.asp"
  (print-xml (wrap-seq :choice attrs contents)))

(defmethod print-xml-tag :restricted! [tag attrs contents]
  "Prints an element that permits only certain content to appear inside tags.
  See: http://www.w3schools.com/schema/el_restriction.asp"
  (let [element-type (if (:type attrs)
                       (:type attrs)
                       (add-xmlns :simpleType))
        name (:name attrs)
        attrs (dissoc attrs :name :type)]
    (print-xml [(add-xmlns :element) {:name name}
                 [element-type
                   [(add-xmlns :restriction) {:base (:base attrs)}
                     contents]]])))

(defn schema
 "Prints an XML schema based on xml with xmlns as the namespace used for schema tags."
 ([xml xmlns]
  (schema xml xmlns nil))
 ([xml xmlns indent]
   (with-out-str
     (binding [*xmlns* xmlns 
               *prxml-indent* indent]
      (prxml xml)))))
