package com.julianpeeters.avro.annotations
package util

import models.FieldData
import matchers.AvroTypeMatcher

import java.util.concurrent.ConcurrentHashMap
import collection.JavaConversions._
 
import org.apache.avro.Schema

object SchemaStore {

  val schemas: scala.collection.concurrent.Map[String, Schema] = scala.collection.convert.Wrappers.JConcurrentMapWrapper(new ConcurrentHashMap[String, Schema]())
/*
  //parse each field into a list of FieldData, then add it to the store with it's name as the key
  def storeClassFields(schema: Schema): Unit = {
    val fields = schema.getFields.map( field => AvroTypeMatcher.parseField(field) ).toList
    ClassFieldStore.fields += (schema.getName -> fields)
  }
*/
}
