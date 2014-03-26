package avro.provider

package models

import scala.reflect.macros.Context
import scala.quasiquotes.RuntimeLiftables._

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
      case x         => q"""${newTermName(x)}(..${ApplyMatcher.getApplyParams(x, c)})"""
    }
  }


  def asParameterizedDefaultParam(fieldTypeName: String, c: Context) : c.Tree = {
    import c.universe._
    import Flag._

    fieldTypeName match {
      case  l: String if l.startsWith("List[")   =>  q"""List(${asParameterizedDefaultParam(getBoxed(l), c)})"""
      case  o: String if o.startsWith("Option[") => { 
        if(getBoxed(o).endsWith("]")) q"""Some(${asParameterizedDefaultParam(getBoxed(o), c)})"""
        else q"""Some(${asDefaultParam(getBoxed(o), c)})"""
      }
      case _ => error("not a parameterized type")
    }
  }

  def getBoxed(typeName: String) = {
    typeName.dropWhile( c => (c != '[') ).drop(1).dropRight(1)
  }

}
