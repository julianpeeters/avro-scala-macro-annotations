package com.julianpeeters.avro.annotations
package provider
package matchers

import scala.reflect.macros.blackbox.Context
import collection.JavaConversions._


import org.apache.avro.Schema
import org.codehaus.jackson.JsonNode
import org.codehaus.jackson.node._

object JsonMatcher {

  def getDefaultValue(field: Schema.Field, c: Context) = {

    import c.universe._
    import Flag._

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
    
    q"${fromJsonNode(field.defaultValue, field.schema)}"
  }

}