package models

import scala.reflect.macros.Context

object ApplyMatcher {

  def getApplyParams(typeName: String, c: Context) = {
    import c.universe._
    import Flag._

    def scalaTypeToLiteral(typeName: String) = {

      typeName match {
        case "Unit"    => q"()"
        case "Boolean" => q""" true """
        case "Int"     => q"1"
        case "Long"    => q"1L"
        case "Float"   => q"1F"
        case "Double"  => q"1D"
        case "String"  => q""" "" """
      }
    }

    if (ClassFieldStore.fields.get(typeName).isDefined) {
      ClassFieldStore.fields.get(typeName).get.map(f => {
        if (f.fieldType.endsWith("]"))  DefaultParamMatcher.asParameterizedDefaultParam(f.fieldType, c)
        else  scalaTypeToLiteral(f.fieldType)
      })
    }
    else List(scalaTypeToLiteral(typeName))
  }
}
