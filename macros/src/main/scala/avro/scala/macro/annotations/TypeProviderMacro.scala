package com.julianpeeters.avro.annotations

import util._
import store.ClassFieldStore

import scala.reflect.macros.Context
import scala.language.experimental.macros
import scala.annotation.StaticAnnotation

import java.io.File

object AvroTypeProviderMacro {

  def impl(c: Context)(annottees: c.Expr[Any]*): c.Expr[Any] = {

    import c.universe._
    import Flag._

    val avroFilePath = c.prefix.tree match { //here's how we get the value of the filepath, it's the arg to the annotation
      case Apply(_, List(Literal(Constant(x)))) => x.toString
      case _ => c.abort(c.enclosingPosition, "file path not found, annotations argument must be a constant")
    }
    val infile = new File(avroFilePath)
    val schema = SchemaParser.getSchemaFromFile(infile)
    ClassFieldStore.storeClassFields(schema)

    val result = { 
      annottees.map(_.tree).toList match {
        case q"$mods class $name[..$tparams](..$first)(...$rest) extends ..$parents { $self => ..$body }" :: Nil => {
          //Currently, having a `@AvroRecord` the only thing that will trigger the writing of vars instead of vals
          val isImmutable: Boolean = !mods.annotations.exists(mod => mod.toString == "new AvroRecord()")

          val newFields: List[c.Tree] = {//Prep fields for splicing by getting fields and mapping each to a quasiquote
            if ( ClassFieldStore.fields.get(name.toString).isDefined ) { 
              ClassFieldStore.fields.get(name.toString).get.map( field => Quasiquoter.quotifyField(field, isImmutable, c) ) 
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
