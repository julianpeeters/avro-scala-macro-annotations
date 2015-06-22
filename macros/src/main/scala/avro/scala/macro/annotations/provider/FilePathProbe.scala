package com.julianpeeters.avro.annotations
package provider

import scala.reflect.macros.blackbox.Context

object FilePathProbe {

  //here's how we get the value of the filepath, it's the arg to the annotation
  def getPath(c: Context) = {
    import c.universe._
    import Flag._

    c.prefix.tree match { 
      case Apply(_, List(Literal(Constant(x)))) => x.toString
      case _ => c.abort(c.enclosingPosition, "file path not found, annotation argument must be a constant")
    }
  }
  
}