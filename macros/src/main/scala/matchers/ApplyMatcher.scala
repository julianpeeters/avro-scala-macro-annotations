package avro.provider

package models

import scala.reflect.macros.Context

object ApplyMatcher {

  def getApplyParams(typeName: String, c: Context): List[c.universe.Literal] = {
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
      ClassFieldStore.fields.get(typeName).get.map(field => {
        if (field.fieldType.endsWith("]"))  newTypeName(field.fieldType)//DefaultParamMatcher.asParameterizedDefaultParam(field.fieldType, c)
        else  scalaTypeToLiteral(field.fieldType)
      }).asInstanceOf[List[c.universe.Literal]]
    }
    else List(scalaTypeToLiteral(typeName))
  }
}
