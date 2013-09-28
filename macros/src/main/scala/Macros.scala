package models

import scala.reflect.macros.Context
import scala.language.experimental.macros
import scala.annotation.StaticAnnotation


object helloMacro {
  def impl(c: Context)(annottees: c.Expr[Any]*): c.Expr[Any] = {
    import c.universe._
    import Flag._
    val result = {
      annottees.map(_.tree).toList match {

        case ClassDef(mods, name, tparams, Template(parents, self, body)) :: Nil =>
          val helloVal = ValDef(NoMods, newTermName("x"), TypeTree(), Literal(Constant("hello macro!")))
          ClassDef(mods, name, tparams, Template(parents, self, body :+ helloVal))

      }
    }
    c.Expr[Any](result)
  }
}

class hello extends StaticAnnotation {
  def macroTransform(annottees: Any*) = macro helloMacro.impl
}
