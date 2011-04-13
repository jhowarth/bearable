Welcome
=======

A small Clojure library that makes writing XML schemas slightly more bearable.

Usage
-----
Welcome adds several new tags to [prxml](http://richhickey.github.com/clojure-contrib/prxml-api.html).
A good explanation of prxml can be found [here](http://nakkaya.com/2009/10/10/processing-xml-with-clojure/). 

The new tags available through Welcome are:

* :ordered-list! -- requires an element to contain an ordered list of elements
* :unordered-list! -- requires an element to contain a unordered list of elements
* :choice! -- requires an element to contain one element from a list of elements
* :restricted! -- requires an elements to meet certain restrictions


Example
-------

Suppose we want to validate the following:

    <own3dReply>
      <liveEvent>
        <isLive>false</isLive>
        <liveViewers>4825</liveViewers>
      </liveEvent>
    </own3dReply>

We would write the following code:
    
    (use 'welcoma.schema)

    (def xsd 
     [:xs:schema {:xmlns:xs "http://www.w3.org/2001/XMLSchema"}
       [:ordered-list! {:name "own3dreply"}
         [:ordered-list! {:name "liveEvent"}
           [:restricted! {:name "isLive" :base "xs:string"} [:xs:pattern {:value "true|false"}]]
           [:xs:element {:name "liveViewers" :type "xs:integer"}]]]])

    (schema xsd "xs" 2)

It would produce the following schema:

    <xs:schema xmlns:xs="http://www.w3.org/2001/XMLSchema">
      <xs:element name="own3dReply">
        <xs:complexType>
          <xs:sequence>
            <xs:element name="liveEvent">
              <xs:complexType>
                <xs:sequence>
                  <xs:element name="isLive">
                    <xs:simpleType>
                      <xs:restriction base="xs:string">
                        <xs:pattern value="true|false"/>
                      </xs:restriction>
                    </xs:simpleType>
                  </xs:element>
                  <xs:element name="liveViewers" type="xs:integer"/>
                </xs:sequence>
              </xs:complexType>
            </xs:element>
          </xs:sequence>
        </xs:complexType>
      </xs:element>
    </xs:schema>


License
-------

Copyright (C) 2011 Jesse Howarth

Distributed under the Eclipse Public License, the same as Clojure.
