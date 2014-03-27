package avro.provider

import models._

import scala.reflect.macros.Context
import scala.language.experimental.macros
import scala.annotation.StaticAnnotation

import java.io.File

object AvroTypeProviderMacro {

  def impl(c: Context)(annottees: c.Expr[Any]*): c.Expr[Any] = {

    import c.universe._
    import Flag._

    val avroFilePath = c.prefix.tree match {
      case Apply(_, List(Literal(Constant(x)))) => x.toString
      case _ => c.abort(c.enclosingPosition, "file path not found, annotations argument must be a constant")
    }

    val infile = new File(avroFilePath)
    val schema = AvroSchema.getSchemaFromFile(infile)

    ClassFieldStore.storeClassFields(schema)

    val result = { 
      annottees.map(_.tree).toList match {

        case q"$mods class $name[..$tparams](..$first)(...$rest) extends ..$parents { $self => ..$body }" :: Nil => {
          val newFields: List[c.Tree] = {      
            //Prep each field for splicing. If there is an entry for an annotee, get the fields and map each to a quasiquote
            if ( ClassFieldStore.fields.get(name.toString).isDefined ) { 
                                                                         
              ClassFieldStore.fields.get(name.toString).get.map(field => { 

                def boxTypeTrees(typeName: String) = {
                  val unboxedStrings = typeName.dropRight(typeName.count( c => c == ']')).split('[')
                  val types = unboxedStrings.map(g => newTypeName(g)).toList  
                  val typeTrees: List[Tree] = types.map(t => tq"$t")
                  typeTrees.reduceRight((a, b) => tq"$a[$b]")
                }

                if (field.fieldType.endsWith("]")) { //if the field is a parameterized type
                  val box = newTypeName(field.fieldType.takeWhile(c => c != '['))
                  val boxed = newTypeName(DefaultParamMatcher.getBoxed(field.fieldType))
                  val fieldTermName = newTermName(field.fieldName)
                  val fieldTypeName = boxTypeTrees(field.fieldType)
                  val defaultParam  = DefaultParamMatcher.asParameterizedDefaultParam(fieldTypeName.toString, c)
                  q"""val $fieldTermName: $fieldTypeName = $defaultParam"""
                }
                else {
                  val fieldTermName = newTermName(field.fieldName)
                  val fieldTypeName = newTypeName(field.fieldType)
                  val defaultParam  = DefaultParamMatcher.asDefaultParam(fieldTypeName.toString, c)
                  q"""val $fieldTermName: $fieldTypeName = $defaultParam"""
                }
              })
            }
            else error("No entry found in the ClassFieldStore for this class. Perhaps class and record names do not correspond.")
          }
          //Here's the updated class def:
          q"$mods class $name[..$tparams](..$newFields)(...$rest) extends ..$parents { $self => ..$body }"
        }
      }
    }

    c.Expr[Any](result)
  }
}

class AvroTypeProvider(inputPath: String) extends StaticAnnotation {
  def macroTransform(annottees: Any*) = macro AvroTypeProviderMacro.impl
}
