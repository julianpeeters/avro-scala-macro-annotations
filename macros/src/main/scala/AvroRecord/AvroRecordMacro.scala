package avro.provider

import models._

import scala.reflect.macros.Context
import scala.language.experimental.macros
import scala.annotation.StaticAnnotation

import com.gensler.scalavro.types._

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
          val baseClasses = List( tq"SpecificRecordBase", tq"SpecificRecord" )
          val newParents  = parents ::: baseClasses
      
          //Method Gen
          val getDef = {
            q"""
              def get(field: Int): AnyRef = { 
                val fields = this.getClass.getDeclaredFields()
                fields(field).get(this)
              }
            """
          }

          val getSchemaDef = {
            q"""
              def getSchema: Schema = new Schema.Parser().parse(AvroType[Twitter_Schema].schema.toString)
            """
          }

          val putDef = {
            q"""
              def put(field: Int, value: scala.Any) = {
                val fields = (this.getClass.getDeclaredFields())
                fields(field).set(this,value)
              }
            """
          }


          val newDefs = List(getDef, getSchemaDef, putDef)
          val newBody = body ::: newDefs


          //Here's the updated class def:
   //       q"$mods class $name[..$tparams](..$first)(...$rest) extends ..$parents { $self => ..$body }"
          q"$mods class $name[..$tparams](..$first)(...$rest) extends ..$newParents { $self => ..$newBody }"
        //  q"$mods class $name[..$tparams](..$first)(...$rest) extends ..$newParents { $self => ..$body }"
        }
      }
    }

    c.Expr[Any](result)
  }
}

class AvroRecord extends StaticAnnotation {
  def macroTransform(annottees: Any*) = macro AvroRecordMacro.impl
}
