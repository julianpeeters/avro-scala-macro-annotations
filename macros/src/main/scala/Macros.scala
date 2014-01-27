package models

import scala.reflect.macros.Context
import scala.language.experimental.macros
import scala.annotation.StaticAnnotation

import java.io.File
import org.apache.avro.Schema


object valProviderMacro {

  def impl(c: Context)(annottees: c.Expr[Any]*): c.Expr[Any] = {

    import c.universe._
    import Flag._

    //val infile = new File("enron_head.avro")
    val infile = new File("input.avro")
    //val infile = new File("twitter.avro")

    def getSchemaAsString(infile: java.io.File): String = {
      val bufferedInfile = scala.io.Source.fromFile(infile, "iso-8859-1")
      val parsable = new String(bufferedInfile.getLines.mkString.dropWhile(_ != '{').toCharArray)
      val avroSchema = new Schema.Parser().parse(parsable)
      avroSchema.toString
    }

    val jsonSchema: String = getSchemaAsString(infile)

    JSONParser.storeClassFields(jsonSchema)

    val result = {
      annottees.map(_.tree).toList match {

        case q"$mods class $name[..$tparams](..$first)(...$rest) extends ..$parents { $self => ..$body }" :: Nil => {
          val className = name.toString 
          val classFields = {           //prep each field to be spliced as a list of ValDefs
            if ( ClassFieldStore.fields.get(className).isDefined ) { 
              ClassFieldStore.fields.get(className).get.map(field => {
                val providerMods  = Modifiers(DEFAULTPARAM)
                val fieldTermName = newTermName(field.fieldName)
                val fieldTypeName = newTypeName(field.fieldType)
                if (fieldTypeName.toString.endsWith("]")) {
                  val defaultParam  = DefaultParamMatcher.asParameterizedDefaultParam(fieldTypeName.toString, c)
                  q"""$providerMods val $fieldTermName: $fieldTypeName = $defaultParam"""
                }
                else {
                  val defaultParam  = DefaultParamMatcher.asDefaultParam(fieldTypeName.toString, c)
                  q"""$providerMods val $fieldTermName: $fieldTypeName = $defaultParam"""
                }
              })
            }
            else error("No entry found in the ClassFieldStore for this class. Perhaps class and record names do not correspond.")
          }
          q"$mods class $name[..$tparams](..$classFields)(...$rest) extends ..$parents { $self => ..$body }"
        }
      }
    }
    c.Expr[Any](result)
  }
}


class valProvider extends StaticAnnotation {
  def macroTransform(annottees: Any*) = macro valProviderMacro.impl
}
