name := "avro-example"

version := "0.0.1-SNAPSHOT"

organization := "com.julianpeeters"

scalaVersion := "2.11.6"

libraryDependencies += "com.julianpeeters" %% "avro-scala-macro-annotations" % "0.5"

libraryDependencies += "org.apache.avro" % "avro" % "1.7.7"

addCompilerPlugin("org.scalamacros" % "paradise" % "2.1.0-M5" cross CrossVersion.full)
