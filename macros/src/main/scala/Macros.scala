package models

import scala.reflect.macros.Context
import scala.language.experimental.macros
import scala.annotation.StaticAnnotation

import scala.io._

//case class Val(fieldName: String, fieldType: String)

object valProviderMacro {




  def impl(c: Context)(annottees: c.Expr[Any]*): c.Expr[Any] = {
    import c.universe._
    import Flag._

    val fieldData = List(("x", "String"), ("myRec", "MyRec"))
    def asParam(fieldTypeName: Name) = {
println(fieldTypeName)
val h = "myRec"
val g = q""""hello macro""""
val i = newTypeName("MyRec")
      fieldTypeName.toString match {
        case "String" =>   {println("asPAram " ); q""" $h """}
        case x  =>{   println("asPAram x " ); q"""$i($g)"""}//"MyRec(hello macro)" // x+ """(hello macro)"""
      }
    }

    val result = {
      annottees.map(_.tree).toList match {

        case q"$mods class $name[..$tparams](..$first)(...$rest) extends ..$parents { $self => ..$body }" :: Nil =>
          val CASEACCESSOR = (1 << 24).toLong.asInstanceOf[FlagSet]
          val PARAMACCESSOR = (1 << 29).toLong.asInstanceOf[FlagSet]
          val valProviderMods = Modifiers(CASEACCESSOR | PARAMACCESSOR | DEFAULTPARAM)
if (name.toString == "MyRec") { println("MyRec")
          val fieldTermName = newTermName(fieldData(1)._1)
          val fieldTypeName = newTypeName(fieldData(1)._2)
println(fieldTypeName)
          val param = asParam(fieldTypeName)
          val valProviderVal = q"""$valProviderMods val $fieldTermName: $fieldTypeName = $param"""
          q"$mods class $name[..$tparams](..$first, $valProviderVal)(...$rest) extends ..$parents { $self => ..$body }"
}
else { println("not my rec")
          val fieldTermName = newTermName(fieldData(0)._1)
          val fieldTypeName = newTypeName(fieldData(0)._2)

println(fieldTypeName)
          val param = asParam(fieldTypeName)
          val valProviderVal = q"""$valProviderMods val $fieldTermName: $fieldTypeName = $param"""
          q"$mods class $name[..$tparams](..$first, $valProviderVal)(...$rest) extends ..$parents { $self => ..$body }"
}




      }
    }
    c.Expr[Any](result)
  }
}


class valProvider extends StaticAnnotation {
  def macroTransform(annottees: Any*) = macro valProviderMacro.impl
}
