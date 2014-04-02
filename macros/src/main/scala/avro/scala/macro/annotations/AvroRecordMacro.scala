package com.julianpeeters.avro.annotations

import record._
import scala.reflect.macros.Context
import scala.language.experimental.macros
import scala.annotation.StaticAnnotation

import com.gensler.scalavro.types._

import java.io.File


object AvroRecordMacro {

  def impl(c: Context)(annottees: c.Expr[Any]*): c.Expr[Any] = {

    import c.universe._
    import Flag._

    val result = { 
      annottees.map(_.tree).toList match {

        case q"$mods class $name[..$tparams](..$first)(...$rest) extends ..$parents { $self => ..$body }" :: Nil => {
println(first)
first.map(_.tpe).foreach(println)
first.map(_.name).foreach(println)
first.map(_.symbol).foreach(println)
first.map(_.freeTypes).foreach(println)
first.map(_.tpt).foreach(println)
          val typeNames   = first.map(_.tpt.toString)                    //list of types (Strings to reuse a JSON parsing tool)
          val newCtors    = CtorGen.generateNewCtors(typeNames, c)       //a no-arge ctor so `newInstance()` can be used
          val newDefs     = MethodGen.generateNewMethods(c)              //`get`, `put`, and `getSchema` methods 
          val newBody     = body ::: newCtors ::: newDefs                //add new members to the body 
          val newParents  = parents ::: Extender.generateNewBaseTypes(c) //extend SpecificRecord and SpecificRecordBase
          
          //return an updated class def
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
