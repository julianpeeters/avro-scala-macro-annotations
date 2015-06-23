package com.julianpeeters.avro.annotations
package provider
package matchers


import org.apache.avro.Schema

import scala.reflect.macros.Context
import scala.language.experimental.macros
import scala.collection.JavaConversions._

object AvroTypeMatcher {

  def toScala(namespace: String, schema: org.apache.avro.Schema, c: Context): c.universe.Tree = {
    import c.universe._
    import Flag._

    def expandNestedTypes(typeTree: Tree) = {
      c.typeCheck(q"type T = $typeTree") match {
        case x @ TypeDef(mods, name, tparams, rhs)  => rhs
      }
    }

    schema.getType match { 
      
      case Schema.Type.ARRAY    => {
        val typeTree = tq"List[${toScala(namespace, schema.getElementType, c)}]"
        //expandNestedTypes(typeTree)
        typeTree
      } 
      case Schema.Type.BOOLEAN  => tq"Boolean"
      case Schema.Type.DOUBLE   => tq"Double"
      case Schema.Type.FLOAT    => tq"Float"
      case Schema.Type.LONG     => tq"Long"
      case Schema.Type.INT      => tq"Int"
      case Schema.Type.NULL     => tq"Null"
      case Schema.Type.RECORD   => { 
        schema.getName match {
          //cases where a record is found as a field vs found as a member of a union vs found as an element of an array
          case "array" | "union" => tq"${schema.getName}"
          case recordName        => {
            val fullName = namespace match {
              case null       => recordName
              case ns: String => (ns + "." + recordName)
            }
            try {
              tq"${c.mirror.staticClass(fullName)}"
            }
            catch {
              case scala.ScalaReflectionException(_) => {
                sys.error("no case class " + fullName +  " corresponds to field type: " + recordName + ": record")
              }
            }
          }
        }
      }
      case Schema.Type.STRING   => tq"String"
      
      case Schema.Type.UNION    => { 
        val unionSchemas = schema.getTypes.toList
        if (unionSchemas.length == 2 && 
            unionSchemas.exists(schema => schema.getType == Schema.Type.NULL) &&
            unionSchemas.exists(schema => schema.getType != Schema.Type.NULL)) {
          val maybeSchema = unionSchemas.find(schema => schema.getType != Schema.Type.NULL)
          val typeTree = tq"Option[${toScala(namespace, maybeSchema.get, c)}]"
          if (maybeSchema.isDefined) typeTree//expandNestedTypes(typeTree)
          else sys.error("no avro type found in this union")  
        }
        else sys.error("not a union field")
      }
      case Schema.Type.MAP      => {
        val typeTree = tq"Map[String, ${toScala(namespace, schema.getValueType, c)}]"
       // expandNestedTypes(typeTree)
       typeTree
      }
      
      case Schema.Type.BYTES    => sys.error("BYTES is not yet supported")
      case Schema.Type.FIXED    => sys.error("FIXED is not yet supported")
      case Schema.Type.ENUM     => sys.error("ENUM is not yet supported")
    }
  }
}
