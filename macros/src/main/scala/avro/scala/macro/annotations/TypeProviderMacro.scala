package com.julianpeeters.avro.annotations

import util.{AvroTypeMatcher, SchemaParser}

import scala.reflect.macros.blackbox.Context
import scala.language.experimental.macros
import scala.annotation.StaticAnnotation

import collection.JavaConversions._

import java.util.concurrent.ConcurrentHashMap
import java.io.File

import org.apache.avro.Schema
import org.codehaus.jackson.JsonNode
import org.codehaus.jackson.node._


object AvroTypeProviderMacro {

  def impl(c: Context)(annottees: c.Expr[Any]*): c.Expr[Any] = {
    import c.universe._
    import Flag._

    case class FieldData(fieldName: String, fieldType: c.universe.Type, fieldDefault: Tree)  

    //here's how we get the value of the filepath, it's the arg to the annotation
    val avroFilePath = c.prefix.tree match { 
      case Apply(_, List(Literal(Constant(x)))) => x.toString
      case _ => c.abort(c.enclosingPosition, "file path not found, annotations argument must be a constant")
    }

    def getNestedSchemas(schema: Schema): List[Schema] = {
      val fields: List[org.apache.avro.Schema.Field] = schema.getFields.toList
      val fieldSchemas:List[org.apache.avro.Schema]  = fields.map(field => field.schema())

      def flattenSchema(fieldSchema: Schema): List[Schema] = {
        fieldSchema.getType match {
          case Schema.Type.ARRAY  => flattenSchema(fieldSchema.getElementType)
          case Schema.Type.RECORD => getNestedSchemas(fieldSchema); List(fieldSchema)
          case Schema.Type.UNION  => fieldSchema.getTypes.toList.flatMap(x => flattenSchema(x))
          case _                  => List(fieldSchema)
        }
      }

      val flatSchemas = fieldSchemas.flatMap(fieldSchema => flattenSchema(fieldSchema))
      val nestedRecordSchemas = flatSchemas.filter(x => x.getType == Schema.Type.RECORD)

      nestedRecordSchemas
    }


    val infile  = new File(avroFilePath)
    val schema  = SchemaParser.getSchema(infile)
    val schemas = schema+:getNestedSchemas(schema)


    // getting the namespace from the scala package instead of the avro schema allows namespace-less 
    // avros to be imported, not stuck in the default package          
    val freshName = c.freshName(TypeName("Probe$")) 
    val probe = c.typecheck(q""" {class $freshName; ()} """)  // Thanks again to Eugene Burmako 
    val freshSymbol = probe match {
      case Block(List(t), r) => t.symbol
    }
    val fullFreshName = freshSymbol.fullName.toString
    val namespace = c.prefix.tree match { 
      //case Apply(_, List(Literal(Constant(x)))) => null //if there's an arg, force the omission of a namespace in the schema
      case _ => {      
        if (fullFreshName.contains('.')) { fullFreshName.replace("." + freshName.toString, "")} //strips dot and class name
        else null
      }
    }

    val result = { 
      annottees.map(_.tree).toList match {
        case q"$mods class $name[..$tparams](..$first)(...$rest) extends ..$parents { $self => ..$body }" :: Nil => {
         
          //Currently, having a `@AvroRecord` the only thing that will trigger the writing of vars instead of vals
          val isImmutable: Boolean = {
            !mods.annotations.exists(mod => mod.toString == "new AvroRecord()" | mod.toString =="new AvroRecord(null)")
          }

          //wraps a single field in a quasiquote, returning immutable field defs if immutable flag is true
          def quotifyField(f: FieldData, immutable: Boolean): ValDef = { 
            import c.universe._
            import Flag._

            if (immutable == false) q"""var ${TermName(f.fieldName)}: ${q"${f.fieldType}"} = ${f.fieldDefault}""" 
            else q"""val ${TermName(f.fieldName)}: ${q"${f.fieldType}"} = ${f.fieldDefault}""" 
          }

          val newFields: List[ValDef] = {//Prep fields for splicing by mapping each to a quasiquote
            def extractFieldData(field: Schema.Field): FieldData = {

              def fromJsonNode(node: JsonNode, schema: Schema): Tree = {
                schema.getType match {
                  case _ if node == null   => EmptyTree //not `default=null`, but no default
                  case Schema.Type.INT     => q"${node.getIntValue}"
                  case Schema.Type.FLOAT   => q"${node.getDoubleValue.asInstanceOf[Float]}"
                  case Schema.Type.LONG    => q"${node.getLongValue}"
                  case Schema.Type.DOUBLE  => q"${node.getDoubleValue}"
                  case Schema.Type.BOOLEAN => q"${node.getBooleanValue}"
                  case Schema.Type.STRING  => q"${node.getTextValue}"
                  case Schema.Type.NULL    => q"null"
                  case Schema.Type.UNION   => {
                    val unionSchemas = schema.getTypes.toList
                    if (unionSchemas.length == 2 && 
                        unionSchemas.exists(schema => schema.getType == Schema.Type.NULL) &&
                        unionSchemas.exists(schema => schema.getType != Schema.Type.NULL)) {
                      val maybeSchema = unionSchemas.find(schema => schema.getType != Schema.Type.NULL)
                      maybeSchema match {
                        case Some(unionSchema) => {
                          node match {
                            case nn: NullNode => q"None"
                            case nonNullNode  => q"Some(${fromJsonNode(nonNullNode, unionSchema)})"
                          }
                        }
                        case None => sys.error("no avro type found in this union") 
                      }
                    }
                    else sys.error("not a union field")
                  }
                  case Schema.Type.ARRAY   => {
                    q"List(..${node.getElements.toList.map(e => fromJsonNode(e, schema.getElementType))})"
                  }
                  case Schema.Type.MAP   => {
                  	val kvps = node.getFields.toList.map(e => q"${e.getKey} -> ${fromJsonNode(e.getValue, schema.getValueType)}")
                    q"Map(..$kvps)"
                  }
                  case Schema.Type.RECORD  => {
                    val fields  = schema.getFields
                    val fieldValues = fields.map(f => fromJsonNode(node.get(f.name), f.schema))
                    q"${TermName(schema.getName)}(..${fieldValues})"
                  }
                  case x => sys.error("Can't extract a default field, type not yet supported: " + x)
                }
              }
              val fieldName = field.name
              val fieldType = AvroTypeMatcher.avroToScalaType(namespace, field.schema, c)
              val fieldDefault = q"${fromJsonNode(field.defaultValue, field.schema)}"
              FieldData(fieldName, fieldType, fieldDefault)
            }

            val schema = schemas.find(s => {
              s.getName == name.toString
            }).getOrElse(sys.error("attempted to create new fields, no schema found for type " + name))
            val fieldData = schema.getFields.map( field => extractFieldData(field) ).toList
            val valDefs = fieldData.map(f => quotifyField(f, isImmutable))
            valDefs
          }

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
