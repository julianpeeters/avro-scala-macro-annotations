package com.julianpeeters.avro.annotations
package provider

import scala.reflect.macros.blackbox.Context

object NamespaceProbe {

  def getNamespace(c : Context) = {
    // getting the namespace from the scala package instead of the avro schema allows namespace-less 
    // avros to be imported, not stuck in the default package          
    import c.universe._
    import Flag._

    val freshName = c.freshName(TypeName("Probe$")) 
    val probe = c.typecheck(q""" {class $freshName; ()} """)  // Thanks again to Eugene Burmako 
    val freshSymbol = probe match {
      case Block(List(t), r) => t.symbol
    }
    val fullFreshName = freshSymbol.fullName.toString
    val namespace = c.prefix.tree match { 
      case _ => {      
        if (fullFreshName.contains('.')) { fullFreshName.replace("." + freshName.toString, "")} //strips dot and class name
        else null
      }
    }
    namespace
  }

}