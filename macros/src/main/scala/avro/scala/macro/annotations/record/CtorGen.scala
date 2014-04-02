package com.julianpeeters.avro.annotations

package record
import scala.reflect.macros.Context
import scala.language.experimental.macros


object CtorGen {

  def generateNewCtors(c: Context) = {
    import c.universe._
    import Flag._

    val newCtorDef = {
      q"""
         def this() = this("","", 0L)
      """
    }

    //thanks again to Eugene Burmako for the workaround to put the new ctor in the correct position
    val defaultCtorPos = c.enclosingPosition

    val newCtorPos = defaultCtorPos
      .withEnd(defaultCtorPos.endOrPoint + 1)
      .withStart(defaultCtorPos.startOrPoint + 1)
      .withPoint(defaultCtorPos.point + 1)

    List( atPos(newCtorPos)(newCtorDef) )
  }

}
