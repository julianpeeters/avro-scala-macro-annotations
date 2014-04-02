package com.julianpeeters.avro.annotations
package record

import matchers.DefaultParamMatcher
import scala.reflect.macros.Context
import scala.language.experimental.macros


object CtorGen {

  def generateNewCtors(typeNames: List[String], c: Context) = {
    import c.universe._
    import Flag._

    def asCtorParam(typeName: String, c: Context): c.universe.Tree = {
      if (typeName.endsWith("]")) DefaultParamMatcher.asParameterizedDefaultParam(typeName, c)
      else  DefaultParamMatcher.asDefaultParam(typeName, c)
    }

    val defaultParams = typeNames.map(field => asCtorParam(field, c))
    val newCtorDef = {
      q"""
         def this() = this(..$defaultParams)
      """
    }

    //thanks again to Eugene Burmako for the workaround to put the new ctor in the correct position
    val defaultCtorPos = c.enclosingPosition
    val newCtorPos = defaultCtorPos
      .withEnd(defaultCtorPos.endOrPoint + 1)
      .withStart(defaultCtorPos.startOrPoint + 1)
      .withPoint(defaultCtorPos.point + 1)

    //Return a list of new CtorDefs
    List( atPos(newCtorPos)(newCtorDef) )
  }

}
