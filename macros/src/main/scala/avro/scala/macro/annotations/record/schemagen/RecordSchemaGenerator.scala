package com.julianpeeters.avro.annotations
package record
package schemagen

import collection.JavaConversions._
import java.util.{Arrays => JArrays}
import org.apache.avro.Schema
import org.apache.avro.Schema.Field

import collection.JavaConversions._
 
object RecordSchemaGenerator {


    def createSchema(className: String, namespace: String, avroFields: List[Field]) = {
    	val avroSchema = Schema.createRecord(className, "Auto-Generated Schema", namespace, false)
      avroSchema.setFields(JArrays.asList(avroFields.toArray:_*))
      SchemaStore.accept(avroSchema)
      avroSchema
    }
  

}