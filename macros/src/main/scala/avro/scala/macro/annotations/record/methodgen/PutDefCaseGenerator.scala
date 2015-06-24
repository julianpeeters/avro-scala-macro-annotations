package com.julianpeeters.avro.annotations
package record
package methodgen

import scala.reflect.macros.blackbox.Context

import collection.JavaConversions._
 
import org.apache.avro.Schema

abstract class PutDefCaseGenerator {


  //necessary for type refinement when trying to pass dependent types
  val context: Context

  import context.universe._
  import Flag._

//expands to cases used in a pattern match, e.g. case 1 => this.username = value.asInstanceOf[String]
        def asPutCase(nme: TermName, tpe: Type, idx: Int) = {
          def convertToScala(fieldType: Type, tree: Tree): Tree = {  
            fieldType match {
              case s @ TypeRef(pre, symbol, args) if (s =:= typeOf[String]) => {
                q"""$tree match {
                  case x: org.apache.avro.util.Utf8 => $tree.toString
                  case _ => $tree
                } """
              }
              case o @ TypeRef(pre, symbol, args) if (o <:< typeOf[Option[Any]] && args.length == 1) => {
                if (args.head <:< typeOf[Option[Any]]) { 
                  throw new UnsupportedOperationException("Implementation limitation: Cannot immediately nest Option types")
                } 
                else  q"""Option(${convertToScala(args.head, tree)})""" 
              }
              case o @ TypeRef(pre, symbol, args) if (o <:< typeOf[List[Any]] && args.length == 1) => {
                q"""$tree match {
                  case null => null
                  case array: org.apache.avro.generic.GenericData.Array[_] => {
                    scala.collection.JavaConversions.asScalaIterator(array.iterator).toList.map(e => ${convertToScala(args.head, q"e")}) 
                  }
                }"""
              }
              case o @ TypeRef(pre, symbol, args) if (o <:< typeOf[Map[String,Any]] && args.length == 2) => {
                q"""$tree match {
                  case null => null
                  case map: java.util.Map[_,_] => {
                    scala.collection.JavaConversions.mapAsScalaMap(map).toMap.map(kvp => {
                      val key = kvp._1.toString
                      val value = kvp._2 
                      (key, ${convertToScala(args(1), q"value")})
                    }) 
                  }
                }"""
              }
              case _ => tree
            }
          }
          cq"""pos if (pos == ${idx}) => this.${nme} = ${convertToScala(tpe, q"value")}.asInstanceOf[${tpe}] """
        }

      

  

}