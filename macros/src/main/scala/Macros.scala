package avro.provider

import models._

import scala.reflect.macros.Context
import scala.language.experimental.macros
import scala.annotation.StaticAnnotation

import java.io.File


object valProviderMacro {

  def impl(c: Context)(annottees: c.Expr[Any]*): c.Expr[Any] = {

    import c.universe._
    import Flag._

    def boxTypeTrees(typeName: String) = {
      val unboxedStrings = typeName.dropRight(typeName.count( c => c == ']')).split('[')
      val types = unboxedStrings.map(g => newTypeName(g)).toList  
      val typeTrees: List[Tree] = types.map(t => tq"$t")
      typeTrees.reduceRight((a, b) => tq"$a[$b]")
    }

    val avroFilePath = c.prefix.tree match {
      case Apply(_, List(Literal(Constant(x)))) => x.toString
      case _ => c.abort(c.enclosingPosition, "annotation argument needs to be a constant") //due to Scala as of 2.10?
    }

    val infile = new File(avroFilePath)
    val jsonSchema = SchemaString.getSchemaStringFromFile(infile)

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


                if (field.fieldType.endsWith("]")) {
                  val box = newTypeName(field.fieldType.takeWhile(c => c != '['))
                  val boxed = newTypeName(DefaultParamMatcher.getBoxed(field.fieldType))
                  val fieldTypeName = boxTypeTrees(field.fieldType)
                  val defaultParam  = DefaultParamMatcher.asParameterizedDefaultParam(fieldTypeName.toString, c)
                  q"""$providerMods val $fieldTermName: $fieldTypeName = $defaultParam"""
                }
                else {
                  val fieldTypeName = newTypeName(field.fieldType)
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


class valProvider(inputPath: String) extends StaticAnnotation {
  def macroTransform(annottees: Any*) = macro valProviderMacro.impl
}
