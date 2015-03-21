package com.julianpeeters.avro.annotations

import util.{AvroTypeMatcher, SchemaParser}

import scala.reflect.macros.Context
import scala.language.experimental.macros
import scala.annotation.StaticAnnotation

import collection.JavaConversions._

import java.util.concurrent.ConcurrentHashMap
import java.io.File

import org.apache.avro.Schema

object AvroTypeProviderMacro {

  def impl(c: Context)(annottees: c.Expr[Any]*): c.Expr[Any] = {
    import c.universe._
    import Flag._

    case class FieldData(fieldName: String, fieldType: c.universe.Type)  

    val avroFilePath = c.prefix.tree match { //here's how we get the value of the filepath, it's the arg to the annotation
      case Apply(_, List(Literal(Constant(x)))) => x.toString
      case _ => c.abort(c.enclosingPosition, "file path not found, annotations argument must be a constant")
    }
    val infile = new File(avroFilePath)
    val schema = SchemaParser.getSchema(infile)

    val result = { 
      annottees.map(_.tree).toList match {
        case q"$mods class $name[..$tparams](..$first)(...$rest) extends ..$parents { $self => ..$body }" :: Nil => {
          //Currently, having a `@AvroRecord` the only thing that will trigger the writing of vars instead of vals
          val isImmutable: Boolean = {
            !mods.annotations.exists(mod => mod.toString == "new AvroRecord()" | mod.toString =="new AvroRecord(null)")
          }

          //wraps a single field in a quasiquote, returning immutable field defs if immutable flag is true
          def quotifyField(fieldName: String, fieldType: c.universe.Type, immutable: Boolean): ValDef = { 
            import c.universe._
            import Flag._

            def asDefaultParam(ft: c.universe.Type): Tree = {

              // from Connor Doyle, per http://stackoverflow.com/questions/16079113/scala-2-10-reflection-how-do-i-extract-the-field-values-from-a-case-class
              def caseClassParamsOf(tpe: Type): scala.collection.immutable.ListMap[String, Type] = {
                val constructorSymbol = tpe.declaration(nme.CONSTRUCTOR)
                val defaultConstructor =
                  if (constructorSymbol.isMethod) constructorSymbol.asMethod
                  else {
                    val ctors = constructorSymbol.asTerm.alternatives
                    ctors.map { _.asMethod }.find { _.isPrimaryConstructor }.get
                  }

                scala.collection.immutable.ListMap[String, Type]() ++ defaultConstructor.paramss.reduceLeft(_ ++ _).map {
                  sym => sym.name.toString -> tpe.member(sym.name).asMethod.returnType
                }
              }

              ft match {
                case x if x =:= typeOf[Unit]    => q"()"
                case x if x =:= typeOf[Boolean] => q""" true """
                case x if x =:= typeOf[Int]     => q"1"
                case x if x =:= typeOf[Long]    => q"1L"
                case x if x =:= typeOf[Float]   => q"1F"
                case x if x =:= typeOf[Double]  => q"1D"
                case x if x =:= typeOf[String]  => q""" "" """
                case x if x =:= typeOf[Null]    => q"null"
                // List
                case x @ TypeRef(pre, symbol, args) if (x <:< typeOf[List[Any]] && args.length == 1)  => {
                  q"""List(${asDefaultParam(args.head)})"""
                }
                // Option
                case x @ TypeRef(pre, symbol, args) if (x <:< typeOf[Option[Any]] && args.length == 1)  => {
                  q"""Some(${asDefaultParam(args.head)})"""
                }
                // User-Defined
                case x @ TypeRef(pre, symbol, args) if (x <:< typeOf[Product with Serializable] ) => { 
                  val defaultParams = caseClassParamsOf(x).map(p => asDefaultParam(p._2))
                  q"""${newTermName(symbol.name.toString)}(..$defaultParams)"""
                }
                case x => sys.error("cannot support yet: " + x)
              }
            }
            
            if (immutable == false) q"""var ${newTermName(fieldName)}: ${q"$fieldType"} = ${asDefaultParam(fieldType)}""" 
            else q"""val ${newTermName(fieldName)}: ${q"$fieldType"} = ${asDefaultParam(fieldType)}""" 
          }

          val newFields: List[ValDef] = {//Prep fields for splicing by getting fields and mapping each to a quasiquote
            def parseField(field: Schema.Field): FieldData = {
              FieldData(field.name, AvroTypeMatcher.avroToScalaType(field.schema, c))
            }
            val fieldData = schema.getFields.map( field => parseField(field) ).toList
            fieldData.map(f => quotifyField(f.fieldName, f.fieldType, isImmutable))
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
