package com.julianpeeters.avro.annotations
package record
package ctorgen

import scala.reflect.macros.blackbox.Context

import collection.JavaConversions._
 
abstract class CtorGenerator {

    //necessary for type refinement when trying to pass dependent types
    val context: Context

    import context.universe._
    import Flag._

	  def toZeroArg(defaultParams: List[Tree]) = { 
	    val newCtorDef = q"""def this() = this(..$defaultParams)"""
	    val defaultCtorPos = context.enclosingPosition //thanks to Eugene Burmako for the workaround to position the ctor correctly
	    val newCtorPos = defaultCtorPos
	      .withEnd(defaultCtorPos.endOrPoint + 1)
	      .withStart(defaultCtorPos.startOrPoint + 1)
	      .withPoint(defaultCtorPos.point + 1)
	    List( atPos(newCtorPos)(newCtorDef) ) 
	  }
}