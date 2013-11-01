package models

import scala.reflect.macros.Context
import scala.language.experimental.macros
import scala.annotation.StaticAnnotation

import scala.io._

object helloMacro {
  def impl(c: Context)(annottees: c.Expr[Any]*): c.Expr[Any] = {
    import c.universe._
    import Flag._

    val x = "x"//scala.io.Source.fromFile("input.txt").mkString
    val result = {
      annottees.map(_.tree).toList match {

        case q"$mods class $name[..$tparams](..$first)(...$rest) extends ..$parents { $self => ..$body }" :: Nil =>
          val CASEACCESSOR = (1 << 24).toLong.asInstanceOf[FlagSet]
          val PARAMACCESSOR = (1 << 29).toLong.asInstanceOf[FlagSet]
          val helloMods = Modifiers(CASEACCESSOR | PARAMACCESSOR | DEFAULTPARAM)
          val helloVal = q"""$helloMods val x: String = "hello macro!""""
          q"$mods class $name[..$tparams](..$first, $helloVal)(...$rest) extends ..$parents { $self => ..$body }"

      }
    }
    c.Expr[Any](result)
  }
}

class hello extends StaticAnnotation {
  def macroTransform(annottees: Any*) = macro helloMacro.impl
}
