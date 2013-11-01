package models

import scala.reflect.macros.Context
import scala.language.experimental.macros
import scala.annotation.StaticAnnotation

import scala.io._

object nestedInnerMacro {
  def impl(c: Context)(annottees: c.Expr[Any]*): c.Expr[Any] = {
    import c.universe._
    import Flag._

    val x = "x"
    val result = {
      annottees.map(_.tree).toList match {

        case q"$mods class $name[..$tparams](..$first)(...$rest) extends ..$parents { $self => ..$body }" :: Nil =>
          val CASEACCESSOR = (1 << 24).toLong.asInstanceOf[FlagSet]
          val PARAMACCESSOR = (1 << 29).toLong.asInstanceOf[FlagSet]
          val nestedInnerMods = Modifiers(CASEACCESSOR | PARAMACCESSOR | DEFAULTPARAM)
          val nestedInnerVal = q"""$nestedInnerMods val x: String = "hello macro!""""
          q"$mods class $name[..$tparams](..$first, $nestedInnerVal)(...$rest) extends ..$parents { $self => ..$body }"

      }
    }
    c.Expr[Any](result)
  }
}

object nestedOuterMacro {
  def impl(c: Context)(annottees: c.Expr[Any]*): c.Expr[Any] = {
    import c.universe._
    import Flag._

    val x = "myRec"
    val result = {
      annottees.map(_.tree).toList match {

        case q"$mods class $name[..$tparams](..$first)(...$rest) extends ..$parents { $self => ..$body }" :: Nil =>
          val CASEACCESSOR = (1 << 24).toLong.asInstanceOf[FlagSet]
          val PARAMACCESSOR = (1 << 29).toLong.asInstanceOf[FlagSet]
          val nestedInnerMods = Modifiers(CASEACCESSOR | PARAMACCESSOR | DEFAULTPARAM)
          val nestedInnerVal = q"""$nestedInnerMods val x: String = "hello macro!""""
          q"$mods class $name[..$tparams](..$first, $nestedInnerVal)(...$rest) extends ..$parents { $self => ..$body }"

      }
    }
    c.Expr[Any](result)
  }
}

class nestedInner extends StaticAnnotation {
  def macroTransform(annottees: Any*) = macro nestedInnerMacro.impl
}
class nestedOuter extends StaticAnnotation {
  def macroTransform(annottees: Any*) = macro nestedOuterMacro.impl
}
