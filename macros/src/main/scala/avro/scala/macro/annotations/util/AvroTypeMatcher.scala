package com.julianpeeters.avro.annotations
package util

import org.apache.avro.Schema

import scala.reflect.macros.Context
import scala.language.experimental.macros
import scala.collection.JavaConversions._

object AvroTypeMatcher {

  def avroToScalaType(schema: org.apache.avro.Schema, c: Context): c.universe.Type = {

    import c.universe._
    import Flag._

      schema.getType match { 
        case Schema.Type.ARRAY    => {
          val t = tq"List[${avroToScalaType(schema.getElementType, c)}]"
          c.typeCheck(q"type T = $t") match {
            case x @ TypeDef(mods, name, tparams, rhs)  => rhs.tpe
          }
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
               c.mirror.staticClass(schema.getNamespace + "." + recordName).toType
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
            val t = tq"Option[${avroToScalaType(maybeSchema.get, c)}]"
            if (maybeSchema.isDefined) c.typeCheck(q"""type T = $t""") match {
              case x @ TypeDef(mods, name, tparams, rhs)  => rhs.tpe
            }
            else error("no avro type found in this union")  
          }
          else error("not a union field")
        }
        case Schema.Type.BYTES    => sys.error("BYTES is not yet supported")
        case Schema.Type.FIXED    => sys.error("FIXED is not yet supported")
        case Schema.Type.MAP      => sys.error("MAP is not yet supported")
        case Schema.Type.ENUM     => sys.error("ENUM is not yet supported")
      }
    }
}
