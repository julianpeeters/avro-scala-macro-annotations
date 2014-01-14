package models

import scala.reflect.macros.Context
import scala.language.experimental.macros
import scala.annotation.StaticAnnotation

//import scala.io._


object valProviderMacro {

  def impl(c: Context)(annottees: c.Expr[Any]*): c.Expr[Any] = {
    import c.universe._
    import Flag._

    val fieldData = List(("x", "String"), ("myRec", "MyRec"))

    def asDefaultParam(fieldTypeName: Name) = {

      val g = q""" "hello macro" """
      val h = "MyRec"
      val i = newTermName(h)

      fieldTypeName.toString match {
        case "String" => q""" $g """
        case x        => q"""$i($g)"""//"MyRec(hello macro)" // x+ """(hello macro)"""
      }
    }


    val m = Map("MyRec" -> newTermName("x"), "MyRecord" -> newTermName("myRec"))
    val n = Map("MyRec" -> newTypeName("String"), "MyRecord" -> newTypeName("MyRec"))


    val result = {
      annottees.map(_.tree).toList match {

        case q"$mods class $name[..$tparams](..$first)(...$rest) extends ..$parents { $self => ..$body }" :: Nil => {
          val valProviderMods = Modifiers( DEFAULTPARAM )
          
          val className = name.toString
          val fieldTermName = if(m.get(className).isDefined) m.get(className).get; else newTermName(fieldData(1)._1)
          val fieldTypeName = if(n.get(className).isDefined) n.get(className).get; else newTypeName(fieldData(1)._2)
          val defaultParam = asDefaultParam(fieldTypeName)

          val valProviderVal = q"""$valProviderMods val $fieldTermName: $fieldTypeName = $defaultParam"""
          val vals = List(valProviderVal)

          q"$mods class $name[..$tparams](..$vals)(...$rest) extends ..$parents { $self => ..$body }"
        }
      }
    }
    c.Expr[Any](result)
  }
}


class valProvider extends StaticAnnotation {
  def macroTransform(annottees: Any*) = macro valProviderMacro.impl
}
