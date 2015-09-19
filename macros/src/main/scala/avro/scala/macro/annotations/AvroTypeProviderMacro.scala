package com.julianpeeters.avro.annotations

import provider._

import scala.reflect.macros.Context
import scala.language.experimental.macros
import scala.annotation.StaticAnnotation

import collection.JavaConversions._
import java.io.File

object AvroTypeProviderMacro {

  def impl(c: Context)(annottees: c.Expr[Any]*): c.Expr[Any] = {
    import c.universe._
    import Flag._ 

    val result = { 
      annottees.map(_.tree).toList match {
        case q"$mods class $name[..$tparams](..$first)(...$rest) extends ..$parents { $self => ..$body }" :: Nil => {
         
          // get the namespace from the context and passing it around instead of using schema.getNamespace
          // in order to read schemas that omit namespace (e.g. nested schemas or python's avrostorage default)
          val namespace = NamespaceProbe.getNamespace(c)

          val fullName: String = {
            if (namespace == null) name.toString
            else s"$namespace.$name"
          }

          //currently, having a `@AvroRecord` the only thing that will trigger the writing of vars instead of vals
          val isImmutable: Boolean = {
            !mods.annotations.exists(mod => mod.toString == "new AvroRecord()" | mod.toString =="new AvroRecord(null)")
          }

          // helpful for IDE users who may not be able to easily see where their files live
          println(s"Current path: ${new File(".").getAbsolutePath}") 
           
          val avroFilePath = FilePathProbe.getPath(c) 
          val infile = new File(avroFilePath)
          val fileSchema = FileParser.getSchema(infile)
          val nestedSchemas = NestedSchemaExtractor.getNestedSchemas(fileSchema)
          val classSchema = nestedSchemas.find(s => s.getFullName == fullName)
           .getOrElse(sys.error("no record found with full name " + fullName))

          //wraps each schema field in a quasiquote, returning immutable val defs if immutable flag is true
          val newFields: List[ValDef] = ValDefGenerator.asScalaFields(classSchema, namespace, isImmutable, c)

          //Here's the updated class def:
          q"$mods class $name[..$tparams](..${newFields:::first})(...$rest) extends ..$parents { $self => ..$body }"
        }
      }
    }

    c.Expr[Any](result)
  }
}

class AvroTypeProvider(inputPath: String) extends StaticAnnotation {
  def macroTransform(annottees: Any*): Any = macro AvroTypeProviderMacro.impl
}
