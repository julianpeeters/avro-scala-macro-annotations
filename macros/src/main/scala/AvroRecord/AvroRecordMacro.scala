package avro.provider

import models._

import scala.reflect.macros.Context
import scala.language.experimental.macros
import scala.annotation.StaticAnnotation

import java.io.File

import org.apache.avro.specific.{SpecificRecord, SpecificRecordBase}

object AvroRecordMacro {

  def impl(c: Context)(annottees: c.Expr[Any]*): c.Expr[Any] = {

    import c.universe._
    import Flag._

 





    val result = { 
      annottees.map(_.tree).toList match {

        case q"$mods class $name[..$tparams](..$first)(...$rest) extends ..$parents { $self => ..$body }" :: Nil => {
println(first)
//println(parents.map(_.g))

          //Extender
          val baseClasses = List(tq"SpecificRecordBase", tq"SpecificRecord")
          val newParents  = parents ::: baseClasses
   /*     
          //Method Gen
          val getDef = {
            q"def get(x: String) = x"
          }

          val getSchemaDef = {
            q"def getSchema(y: String) = y"
          }

          val putDef = {
            q"def get(x: String) = x"
          }


          val newDefs = List(getDef, getSchemaDef, putDef)
          val newBody = body ::: newDefs
*/

          //Here's the updated class def:
//          q"$mods class $name[..$tparams](..$newFields)(...$rest) extends ..$parents { $self => ..$body }"
          q"$mods class $name[..$tparams](..$first)(...$rest) extends ..$newParents { $self => ..$body }"
        }
      }
    }

    c.Expr[Any](result)
  }
}

class AvroRecord extends StaticAnnotation {
  def macroTransform(annottees: Any*) = macro AvroRecordMacro.impl
}
