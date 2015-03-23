name := "avro-example"

version := "0.0.1-SNAPSHOT"

organization := "com.julianpeeters"

scalaVersion := "2.10.3"

libraryDependencies += "com.julianpeeters" %% "avro-scala-macro-annotations" % "0.4"

libraryDependencies += "com.gensler" %% "scalavro" % "0.6.2"

addCompilerPlugin("org.scalamacros" % "paradise" % "2.0.0" cross CrossVersion.full)
