package com.julianpeeters.avro.annotations
package record
package methodgen

import scala.reflect.macros.blackbox.Context

import collection.JavaConversions._
 
import org.apache.avro.Schema

abstract class GetDefCaseGenerator {


  //necessary for type refinement when trying to pass dependent types
  val context: Context

  import context.universe._
  import Flag._

    def asGetCase(nme: TermName, tpe: Type, idx: Int) = {
      def convertToJava(typeTree: Type, convertable: Tree): Tree = {
        typeTree match { 
          case o @ TypeRef(pre, symbol, args) if (o <:< typeOf[Option[Any]] && args.length == 1) => {
            if (args.head <:< typeOf[Option[Any]]) { 
              throw new UnsupportedOperationException("Implementation limitation: Cannot immediately nest Option types")
            } 
            else q"""
              $convertable match {
                case Some(x) => ${convertToJava(args.head, q"x")}
                case None    => null
              }"""  
          }
          case x @ TypeRef(pre, symbol, args) if (x <:< typeOf[List[Any]] && args.length == 1) => {
            q"""java.util.Arrays.asList($convertable.map(x => ${convertToJava(args.head, q"x")}):_*)"""
          }
          case x @ TypeRef(pre, symbol, args) if (x <:< typeOf[Map[String, Any]] && args.length == 2) => {
            q"""
              val map = new java.util.HashMap[String, Any]()
              $convertable.foreach(x => {
                val key = x._1 
                val value = x._2 
                map.put(key, ${convertToJava(args(1), q"value")})
              })
              map
            """
          }
          case x => convertable
        }
      } 
      val convertedToJava = convertToJava(tpe, q"${nme}") 
      cq"""pos if (pos == ${idx}) => $convertedToJava.asInstanceOf[AnyRef]"""
    }
  



}