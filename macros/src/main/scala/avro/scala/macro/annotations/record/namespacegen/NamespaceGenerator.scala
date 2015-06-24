package com.julianpeeters.avro.annotations
package record
package namespacegen

import scala.reflect.macros.blackbox.Context

import collection.JavaConversions._
 
object NamespaceGenerator {

  def probeNamespace(c: Context) = {
    import c.universe._
    import Flag._

    val freshName = c.freshName(TypeName("Probe$")) 
    val probe = c.typecheck(q""" {class $freshName; ()} """)  // Thanks again to Eugene Burmako 
    val freshSymbol = probe match {
      case Block(List(t), r) => t.symbol
    }
    val fullFreshName = freshSymbol.fullName.toString
    val namespace = c.prefix.tree match { 
      case Apply(_, List(Literal(Constant(x)))) => null //if there's an arg, force the omission of a namespace in the schema
      case _ => {      
        if (fullFreshName.contains('.')) { fullFreshName.replace("." + freshName.toString, "")} //strips dot and class name
        else null
      }
    }
    namespace
  }
}