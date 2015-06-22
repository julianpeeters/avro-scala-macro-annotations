package com.julianpeeters.avro.annotations
package provider
import matchers._

import org.apache.avro.Schema

import scala.reflect.macros.blackbox.Context
import scala.language.experimental.macros
import scala.collection.JavaConversions._

object ValDefGenerator {


  def asScalaFields(schema: Schema, namespace: String, isImmutable: Boolean, c: Context) = {
    import c.universe._
    import Flag._ 

    schema.getFields.toList.map(f => {
      val fieldName    = f.name
      val fieldType    = AvroTypeMatcher.toScala(namespace, f.schema, c)
      val fieldDefault = JsonMatcher.getDefaultValue(f, c)
      isImmutable match {
        case true  => q"""val ${TermName(fieldName)}: ${q"$fieldType"} = $fieldDefault"""
        case false => q"""var ${TermName(fieldName)}: ${q"$fieldType"} = $fieldDefault"""
      }
    })
  }

}