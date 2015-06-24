package com.julianpeeters.avro.annotations
package record
package schemagen

import scala.reflect.macros.blackbox.Context

import org.codehaus.jackson.JsonNode
import org.codehaus.jackson.node._

import collection.JavaConversions._
 
abstract class ToJsonMatcher {

    //necessary for type refinement when trying to pass dependent types
    val c: Context
    val ns: String

    import c.universe._
    import Flag._

      def toJsonNode(dv: Tree) : JsonNode = {
        lazy val jsonNodeFactory = JsonNodeFactory.instance 
        dv match {
          // use of null here is for Java interop, builds Avro FieldConstructor w/o default value
          case EmptyTree                                   => null 
          case Literal(Constant(x: Unit))                  => jsonNodeFactory.nullNode
          case Literal(Constant(x: Boolean))               => jsonNodeFactory.booleanNode(x)
          case Literal(Constant(x: Int))                   => jsonNodeFactory.numberNode(x)
          case Literal(Constant(x: Long))                  => jsonNodeFactory.numberNode(x)
          case Literal(Constant(x: Float))                 => jsonNodeFactory.numberNode(x)
          case Literal(Constant(x: Double))                => jsonNodeFactory.numberNode(x)
          case Literal(Constant(x: String))                => jsonNodeFactory.textNode(x)
          case Literal(Constant(null))                     => jsonNodeFactory.nullNode
          case Ident(TermName("None"))                     => jsonNodeFactory.nullNode
          case Apply(Ident(TermName("Some")), List(x))     => toJsonNode(x)
          case Apply(Ident(TermName("List")), xs)          => {
            val jsonArray = jsonNodeFactory.arrayNode
            xs.map(x => toJsonNode(x)).map(v => jsonArray.add(v))
            jsonArray
          }
          case Apply(Ident(TermName("Map")), kvps)         => {
            val jsonObject = jsonNodeFactory.objectNode
            kvps.foreach(kvp => kvp match {
              case Apply(Select(Literal(Constant(key: String)), TermName(tn)), List(x)) =>  {
                jsonObject.put(key, toJsonNode(x))
              }
            })
            jsonObject
          }
          // if the default value is another (i.e. nested) record/case class
          case Apply(Ident(TermName(name)), xs) if SchemaStore.schemas.contains(ns + "." + name) => {
            val jsonObject = jsonNodeFactory.objectNode
            xs.zipWithIndex.map( x => {
              val value = x._1
              val index = x._2
              val nestedRecordField = SchemaStore.schemas(ns + "." + name).getFields()(index)
              // values from the tree, field names from cross referencing tree's pos with schema field pos
              // (they always correspond since the schema is defined based on the fields in a class def)
              jsonObject.put(nestedRecordField.name, toJsonNode(value))
            })
            jsonObject
          }
          case x => sys.error("Could not extract default value. Found: " + x + ", " + showRaw(x))
        }
      } 



}