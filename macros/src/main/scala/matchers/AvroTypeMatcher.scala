package avro.provider

package models

object AvroTypeMatcher {

  def avroToScalaType(fieldType: String): String = {
    fieldType match {    //Thanks to @ConnorDoyle for the mapping: https://github.com/GenslerAppsPod/scalavro
      case "null"    => "Unit"
      case "boolean" => "Boolean"
      case "int"     => "Int"
      case "long"    => "Long"
      case "float"   => "Float"
      case "double"  => "Double"
      case "string"  => "String"
      case x: String => x
      case _         => error("JSON parser found an unsupported type")
    }
  }

}
