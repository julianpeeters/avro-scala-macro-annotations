package com.julianpeeters.avro.annotations
package matchers

import models.FieldData
import util.ClassFieldStore

import scala.reflect.macros.Context

object ApplyParamMatcher {

  def getApplyParams(typeName: String, c: Context): List[c.universe.Tree] = {
    import c.universe._
    import Flag._

    def asApplyParam(field: FieldData, c: Context) = {
      if (field.fieldType.endsWith("]")) DefaultParamMatcher.asParameterizedDefaultParam(field.fieldType, c)
      else  DefaultParamMatcher.asDefaultParam(field.fieldType, c)
    }

    /*
    * Use the typename (here a class name) as a key to get a list of its fields,
    * map each field to a its apply parameter, giving a list of q"Literals".
    */
    if (ClassFieldStore.fields.get(typeName).isDefined) {
      ClassFieldStore.fields.get(typeName).get.map(field => asApplyParam(field, c))
    }
    else error("uh oh, didn't find a class corresponding to that type name in the ClassFieldStore: " + typeName)
  }
}
