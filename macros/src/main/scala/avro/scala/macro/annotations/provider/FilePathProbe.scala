package com.julianpeeters.avro.annotations
package provider

import scala.reflect.macros.blackbox.Context
import java.io.File

object FilePathProbe {

  //here's how we get the value of the filepath, it's the arg to the annotation
  def getPath(c: Context) = {
    import c.universe._
    import Flag._

    val path = c.prefix.tree match {
      case Apply(_, List(Literal(Constant(x)))) => x.toString
      case _ => c.abort(c.enclosingPosition, "file path not found, annotation argument must be a constant")
    }

    if (new File(path).exists) path else {
      // Allow paths relative to where the enclosing class is. This allows the working
      // directory to be different from the project base directory, which is useful
      // for example with sbt `ProjectRef` referencing a github URL and compiling the
      // project behind the scenes.
      val callSite = new File(c.macroApplication.pos.source.file.path).getParent
      val relative = new File(callSite, path)
      if (relative.exists) relative.getAbsolutePath else path
    }
  }

}