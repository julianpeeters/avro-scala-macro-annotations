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
println(rest)
//println(c.eval(body))
//body.map(x => x match {case y=> println(y)})
println(mods)

          //Extender
          val baseClasses = List( tq"SpecificRecordBase", tq"SpecificRecord" )
          val newParents  = parents ::: baseClasses

          //CtorGen
          val newCtorDef = {
            q"""
              def this() = this("","", 0L)
            """
          }
     
          //Method Gen
          val getDef = {
            q"""
              def get(field: Int): AnyRef = { 
                field match {
                  case 0 => username.asInstanceOf[AnyRef]
                  case 1 => tweet.asInstanceOf[AnyRef]
                  case 2 => timestamp.asInstanceOf[AnyRef]
                  case _ => new org.apache.avro.AvroRuntimeException("Bad index")
                }
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
              def put(field: Int, value: scala.Any): Unit = {
                field match {
                  case 1 => this.username  = value.asInstanceOf[String]
                  case 2 => this.tweet     = value.asInstanceOf[String]
                  case 3 => this.timestamp = value.asInstanceOf[Long]
                  case _ => new org.apache.avro.AvroRuntimeException("Bad index")
                }
              }
            """
          }

          //thanks again to Eugene Burmako for the workaround to put the new ctor in the right position
          val defaultCtorPos = c.enclosingPosition

          val newCtorPos = defaultCtorPos
            .withEnd(defaultCtorPos.endOrPoint + 1)
            .withStart(defaultCtorPos.startOrPoint + 1)
            .withPoint(defaultCtorPos.point + 1)

          val newDefs = List(
            getDef,
            getSchemaDef, 
            putDef,
            atPos(newCtorPos)(newCtorDef))

          val newBody = body ::: newDefs

          q"$mods class $name[..$tparams](..$first)(...$rest) extends ..$newParents { $self => ..$newBody }"
        }
      }
    }

    c.Expr[Any](result)
  }
}

class AvroRecord extends StaticAnnotation {
  def macroTransform(annottees: Any*) = macro AvroRecordMacro.impl
}
