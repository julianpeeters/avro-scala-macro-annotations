package models

import scala.reflect.macros.Context
import scala.language.experimental.macros
import scala.annotation.StaticAnnotation

import java.io.File
import org.apache.avro.Schema

import org.json4s._
import org.json4s.native.JsonMethods._

object valProviderMacro {

  def impl(c: Context)(annottees: c.Expr[Any]*): c.Expr[Any] = {
    import c.universe._
    import Flag._


    def getSchemaAsString(infile: java.io.File): String = {
      val bufferedInfile = scala.io.Source.fromFile(infile, "iso-8859-1")
      val parsable = new String(bufferedInfile.getLines.mkString.dropWhile(_ != '{').toCharArray)
      val avroSchema = new Schema.Parser().parse(parsable)
      avroSchema.toString
    }

    //val infile = new File("enron_head.avro")
    val infile = new File("input.avro")
    //val infile = new File("input2.avro")
    //val infile = new File("input3.avro")
    //val infile = new File("twitter.avro")
    val outfile = new File("output.avro")


    val jsonSchema: String = getSchemaAsString(infile)
  println("json " + jsonSchema)


case class AvroRecord(namespace: Option[String], name: String, fields: List[AvroField])
case class AvroField(name: String, `type`: Either[String, AvroRecord])

implicit val formats = org.json4s.DefaultFormats

val json = parse(jsonSchema).extract[AvroRecord]
println("json " + json)


 for {
         JObject(e) <- parse(jsonSchema)
         val entry = e.toMap
         if ( entry("type") == JString("record") )
         val cls = JObject(e).extract[AvroRecord]
         val clsFields = cls.fields.map(f => f.`type` match { 
           case Left(typeName) => FieldData(f.name, avroToScalaType(typeName)) 
           case Right(record)  => FieldData(f.name, record.name) //if the field type is a record, change it to the record's name
         })
       } FieldStore.fields += (cls.name -> clsFields)//Map( cls.name -> ClassData(cls.namespace, cls.name, clsFields) )


println("_________________________")



    def avroToScalaType(fieldType: String): String = {
      fieldType match {
      //Thanks to @ConnorDoyle for the mapping: https://github.com/GenslerAppsPod/scalavro
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


    def asDefaultParam(fieldTypeName: Name) = {

      val g = q""" 1 """
      val h = "rec"
      val i = newTermName(h)

      fieldTypeName.toString match {
        case "Int" => q""" $g """
        case x        => q"""$i($g)"""//"MyRec(hello macro)" // x+ """(hello macro)"""
      }
    }


    val result = {
      annottees.map(_.tree).toList match {

        case q"$mods class $name[..$tparams](..$first)(...$rest) extends ..$parents { $self => ..$body }" :: Nil => {
          val className = name.toString
          val vals = FieldStore.fields.get(className).get.map(field => {
            val valProviderMods = Modifiers(DEFAULTPARAM)
            val fieldTermName = newTermName(field.fieldName)
            val fieldTypeName = newTypeName(field.fieldType)
            val defaultParam = asDefaultParam(fieldTypeName)
            q"""$valProviderMods val $fieldTermName: $fieldTypeName = $defaultParam"""
          })
          q"$mods class $name[..$tparams](..$vals)(...$rest) extends ..$parents { $self => ..$body }"
        }
      }
    }
    c.Expr[Any](result)
  }
}


class valProvider extends StaticAnnotation {
  def macroTransform(annottees: Any*) = macro valProviderMacro.impl
}
