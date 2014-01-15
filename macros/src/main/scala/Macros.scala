package models

import scala.reflect.macros.Context
import scala.language.experimental.macros
import scala.annotation.StaticAnnotation

//import scala.io._


object valProviderMacro {

  def impl(c: Context)(annottees: c.Expr[Any]*): c.Expr[Any] = {
    import c.universe._
    import Flag._

//    val fieldData = List(("x", "String"), ("myRec", "MyRec"))

    def asDefaultParam(fieldTypeName: Name) = {

      val g = q""" "hello macro" """
      val h = "MyRec"
      val i = newTermName(h)

      fieldTypeName.toString match {
        case "String" => q""" $g """
        case x        => q"""$i($g)"""//"MyRec(hello macro)" // x+ """(hello macro)"""
      }
    }

    val l = Map("MyRec" -> FieldData("x", "String"), "MyRecord" -> FieldData("myRec", "MyRec"))


    val result = {
      annottees.map(_.tree).toList match {

        case q"$mods class $name[..$tparams](..$first)(...$rest) extends ..$parents { $self => ..$body }" :: Nil => {
          val className = name.toString
          val fieldTermName = newTermName(if(l.get(className).isDefined) l.get(className).get.fieldName; else error("not found"))
          val fieldTypeName = newTypeName(if(l.get(className).isDefined) l.get(className).get.fieldType; else error("not found"))
          val defaultParam = asDefaultParam(fieldTypeName)
          val valProviderMods = Modifiers( DEFAULTPARAM )
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
