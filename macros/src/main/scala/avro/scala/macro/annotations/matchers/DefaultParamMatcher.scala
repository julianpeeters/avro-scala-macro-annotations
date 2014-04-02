package com.julianpeeters.avro.annotations
package matchers

import scala.reflect.macros.Context

object DefaultParamMatcher {

  def asDefaultParam(fieldTypeName: String, c: Context) = {
    import c.universe._
    import Flag._

    fieldTypeName match {
      case "Unit"    => q"()"
      case "Boolean" => q""" true """
      case "Int"     => q"1"
      case "Long"    => q"1L"
      case "Float"   => q"1F"
      case "Double"  => q"1D"
      case "String"  => q""" "" """
      case x         => q"""${newTermName(x)}(..${ApplyParamMatcher.getApplyParams(x, c)})"""
    }
  }

  def asParameterizedDefaultParam(fieldTypeName: String, c: Context) : c.Tree = {
    import c.universe._
    import Flag._

    fieldTypeName match {
      //List
      case  l: String if l.startsWith("List[") => {
        if (getBoxed(l).endsWith("]")) q"""List(${asParameterizedDefaultParam(getBoxed(l), c)})"""
        else q"""List(${asDefaultParam(getBoxed(l), c)})"""
      }
      //Option
      case  o: String if o.startsWith("Option[") => { 
        if(getBoxed(o).endsWith("]")) q"""Some(${asParameterizedDefaultParam(getBoxed(o), c)})"""
        else q"""Some(${asDefaultParam(getBoxed(o), c)})"""
      }
      //User-Defined
      case  x: String if x.startsWith(x + "[") => { 
        if(getBoxed(x).endsWith("]")) q"""${newTermName(x)}(${asParameterizedDefaultParam(getBoxed(x), c)})"""
        else q"""${newTermName(x)}(${asDefaultParam(getBoxed(x), c)})"""
      }
    }
  }

  def getBoxed(typeName: String) = {
    typeName.dropWhile( c => (c != '[') ).drop(1).dropRight(1)
  }

}
