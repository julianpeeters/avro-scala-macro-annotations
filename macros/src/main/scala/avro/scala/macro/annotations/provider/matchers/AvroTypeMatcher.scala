package com.julianpeeters.avro.annotations
package provider

import org.apache.avro.Schema

import scala.reflect.macros.blackbox.Context
import scala.language.experimental.macros
import scala.collection.JavaConversions._

object AvroTypeMatcher {

  def toScala(namespace: String, schema: org.apache.avro.Schema, c: Context): c.universe.Type = {
    import c.universe._
    import Flag._

    def expandNestedTypes(typeTree: Tree) = {
      c.typecheck(q"type T = $typeTree") match {
        case x @ TypeDef(mods, name, tparams, rhs)  => rhs.tpe
      }
    }

    schema.getType match {
      case Schema.Type.ARRAY    => {
        val typeTree = tq"List[${toScala(namespace, schema.getElementType, c)}]"
        expandNestedTypes(typeTree)
      }
      case Schema.Type.BOOLEAN  => typeOf[Boolean]
      case Schema.Type.DOUBLE   => typeOf[Double]
      case Schema.Type.FLOAT    => typeOf[Float]
      case Schema.Type.LONG     => typeOf[Long]
      case Schema.Type.INT      => typeOf[Int]
      case Schema.Type.NULL     => typeOf[Null]
      case Schema.Type.RECORD   => {
        schema.getName match {
          //cases where a record is found as a field vs found as a member of a union vs found as an element of an array
          case "array" | "union" => tq"schema.getName".tpe
          case recordName        => {
            // Prefer the namespace of the type, can be different.
            val ns = Seq(schema.getNamespace, namespace).
              map(Option(_)).flatten.filterNot(_.isEmpty).headOption

            val fullName = ns match {
              case None     => recordName
              case Some(ns) => (ns + "." + recordName)
            }
            try {
              c.mirror.staticClass(fullName).toType
            }
            catch {
              case scala.ScalaReflectionException(_) => {
                sys.error("no case class " + fullName +  " corresponds to field type: " + recordName + ": record")
              }
            }
          }
        }
      }
      case Schema.Type.STRING   => typeOf[String]
      case Schema.Type.UNION    => { 
        val unionSchemas = schema.getTypes.toList
        if (unionSchemas.length == 2 && 
            unionSchemas.exists(schema => schema.getType == Schema.Type.NULL) &&
            unionSchemas.exists(schema => schema.getType != Schema.Type.NULL)) {
          val maybeSchema = unionSchemas.find(schema => schema.getType != Schema.Type.NULL)
          val typeTree = tq"Option[${toScala(namespace, maybeSchema.get, c)}]"
          if (maybeSchema.isDefined) expandNestedTypes(typeTree)
          else sys.error("no avro type found in this union")  
        }
        else sys.error("not a union field")
      }
      case Schema.Type.MAP      => {
        val typeTree = tq"Map[String, ${toScala(namespace, schema.getValueType, c)}]"
        expandNestedTypes(typeTree)
      }
      case Schema.Type.BYTES    => sys.error("BYTES is not yet supported")
      case Schema.Type.FIXED    => sys.error("FIXED is not yet supported")
      case Schema.Type.ENUM     => sys.error("ENUM is not yet supported")
    }
  }
}
