(ns welcome.schema
 (:use [clojure.contrib.prxml]))

(def *xmlns* nil)

(defn add-xmlns [tag]
 (symbol (str *xmlns* tag)))

(defn seq-element [tag attrs contents]
  (let [name (:name attrs)
        attrs (dissoc attrs :name)]
    (print-xml [(add-xmlns :element) {:name name}
                 [(add-xmlns :complexType)
                   [(add-xmlns tag) attrs
                     contents]]])))

(defmethod print-xml-tag :ordered-list! [tag attrs contents]
  (seq-element :sequence attrs contents))

(defmethod print-xml-tag :unordered-list! [tag attrs contents]
  (seq-element :all attrs contents))

(defmethod print-xml-tag :choice! [tag attrs contents]
  (seq-element :choice attrs contents))

(defmethod print-xml-tag :restricted! [tag attrs contents]
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
 ([xml xmlns]
  (schema xml xmlns nil))
 ([xml xmlns indent]
   (with-out-str
     (binding [*xmlns* xmlns 
               *prxml-indent* indent]
      (prxml xml)))))
