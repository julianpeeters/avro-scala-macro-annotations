package com.julianpeeters.avro.annotations
package record
package ctorgen

import scala.reflect.macros.blackbox.Context

import collection.JavaConversions._
 
abstract class DefaultCtorParamMatcher {

    //necessary for type refinement when trying to pass dependent types
    val context: Context

    import context.universe._
    import Flag._

	// from Connor Doyle, per http://stackoverflow.com/questions/16079113/scala-2-10-reflection-how-do-i-extract-the-field-values-from-a-case-class
    def caseClassParamsOf(tpe: Type): scala.collection.immutable.ListMap[TermName, Type] = {
      val constructorSymbol = tpe.decl(termNames.CONSTRUCTOR)
      val defaultConstructor =
        if (constructorSymbol.isMethod) constructorSymbol.asMethod
        else {
          val ctors = constructorSymbol.asTerm.alternatives
          ctors.map { _.asMethod }.find { _.isPrimaryConstructor }.get
        }

      scala.collection.immutable.ListMap[TermName, Type]() ++ defaultConstructor.paramLists.reduceLeft(_ ++ _).map {
        sym => TermName(sym.name.toString) -> tpe.member(sym.name).asMethod.returnType
      }
    }

    def asDefaultCtorParam(fieldType: context.universe.Type): context.universe.Tree = {
      fieldType match {
        case x if x =:= typeOf[Unit]    => q"()"
        case x if x =:= typeOf[Boolean] => q""" true """
        case x if x =:= typeOf[Int]     => q"1"
        case x if x =:= typeOf[Long]    => q"1L"
        case x if x =:= typeOf[Float]   => q"1F"
        case x if x =:= typeOf[Double]  => q"1D"
        case x if x =:= typeOf[String]  => q""" "" """
        case x if x =:= typeOf[Null]    => q"null"
        // List
        case x @ TypeRef(pre, symbol, args) if (x <:< typeOf[List[Any]] && args.length == 1)  => {
          q"""List(${asDefaultCtorParam(args.head)})"""
        }
        // Option
        case x @ TypeRef(pre, symbol, args) if (x <:< typeOf[Option[Any]] && args.length == 1)  => {
          q"""None"""
        }
        case x @ TypeRef(pre, symbol, args) if (x <:< typeOf[Map[String, Any]] && args.length == 2)  => {
          q"""Map(""->${asDefaultCtorParam(args(1))})"""
        }
        // User-Defined
        case x @ TypeRef(pre, symbol, args) if (x <:< typeOf[Product with Serializable] ) => { 
          val defaultParams = caseClassParamsOf(x).map(p => asDefaultCtorParam(p._2))
          q"""${TermName(symbol.name.toString)}(..$defaultParams)"""
        }
        case x => sys.error("Could not create a default. Not support yet: " + x )
      }
    }


    def matchDefaultParams(tpe: Type, dv: Tree) = dv match { //If there are default vals present in the classdef, use those for 0-arg ctor
      case EmptyTree => {
        asDefaultCtorParam(tpe)
      }
      case defaultValue => defaultValue
    }
  
}