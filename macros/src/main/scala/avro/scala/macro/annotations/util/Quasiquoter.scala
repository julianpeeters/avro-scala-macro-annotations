package com.julianpeeters.avro.annotations
package util

import models.FieldData
import matchers.DefaultParamMatcher

import scala.reflect.macros.Context
import scala.language.experimental.macros


object Quasiquoter {

  //wraps a single field in a quasiquote, returning immutable field defs if immutable flag is true
  def quotifyField(field: FieldData, immutable: Boolean, c: Context) = { 
    import c.universe._
    import Flag._

    def boxTypeTrees(typeName: String) = {//"boxing" in this case is wrapping the string in [] so it looks correct for splicing
      val unboxedStrings = typeName.dropRight(typeName.count( c => c == ']')).split('[')
      val types = unboxedStrings.map(g => newTypeName(g)).toList  
      val typeTrees: List[Tree] = types.map(t => tq"$t")
      typeTrees.reduceRight((a, b) => tq"$a[$b]")
    }

    if (field.fieldType.endsWith("]")) { //if the field is a parameterized type
      val fieldTermName = newTermName(field.fieldName)
      val fieldTypeName = boxTypeTrees(field.fieldType)
      val defaultParam  = DefaultParamMatcher.asParameterizedDefaultParam(fieldTypeName.toString, c)
      if (immutable == false) q"""var $fieldTermName: $fieldTypeName = $defaultParam""" 
      else q"""val $fieldTermName: $fieldTypeName = $defaultParam"""
    }
    else { //if the field is a type that doesn't take type parameters
      val fieldTermName = newTermName(field.fieldName)
      val fieldTypeName = newTypeName(field.fieldType)
      val defaultParam  = DefaultParamMatcher.asDefaultParam(fieldTypeName.toString, c)
      if (immutable == false) q"""var $fieldTermName: $fieldTypeName = $defaultParam""" 
      else q"""val $fieldTermName: $fieldTypeName = $defaultParam"""
    }
  }
}
