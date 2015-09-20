name := "avro-example"

version := "0.0.1-SNAPSHOT"

organization := "com.julianpeeters"

scalaVersion := "2.10.5"

libraryDependencies += "com.julianpeeters" %% "avro-scala-macro-annotations" % "0.4.5"

libraryDependencies += "com.gensler" %% "scalavro" % "0.6.2"

addCompilerPlugin("org.scalamacros" % "paradise" % "2.1.0-M5" cross CrossVersion.full)
