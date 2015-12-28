package com.julianpeeters.avro.annotations
package provider

import org.apache.avro.Schema
import org.apache.avro.Schema.Type.{ARRAY, ENUM, MAP, RECORD, UNION}

import scala.collection.JavaConverters._

object NestedSchemaExtractor {
  // if a record is found, extract nested RECORDs and ENUMS (i.e. top-level types)
  def getNestedSchemas(schema: Schema): List[Schema] = {

    def extract(schema: Schema): List[Schema] = {
    schema.getType match {

      case RECORD =>
        val fields: List[Schema.Field] = schema.getFields.asScala.toList
        val fieldSchemas: List[Schema] = fields.map(field => field.schema())

        def flattenSchema(fieldSchema: Schema): List[Schema] = {
          fieldSchema.getType match {
            case ARRAY => flattenSchema(fieldSchema.getElementType)
            case MAP => flattenSchema(fieldSchema.getValueType)
            case RECORD => fieldSchema :: extract(fieldSchema)
            case UNION => fieldSchema.getTypes.asScala.toList.flatMap(x => flattenSchema(x))
            case ENUM => List(fieldSchema)
            case _ => List(fieldSchema)
          }
        }
        val flatSchemas = fieldSchemas.flatMap(fieldSchema => flattenSchema(fieldSchema))

        def topLevelTypes(schema: Schema) = (schema.getType == RECORD | schema.getType == ENUM)
        val nestedTopLevelSchemas = flatSchemas.filter(topLevelTypes)

        nestedTopLevelSchemas
      case ENUM => List(schema)
      case _ => Nil
    }}

    schema::extract(schema)
  }
}
