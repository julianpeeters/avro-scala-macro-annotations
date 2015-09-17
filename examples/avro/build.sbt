name := "avro-example"

version := "0.0.1-SNAPSHOT"

organization := "com.julianpeeters"

scalaVersion := "2.11.7"

libraryDependencies += "com.julianpeeters" %% "avro-scala-macro-annotations" % "0.10.2"

libraryDependencies += "org.apache.avro" % "avro" % "1.7.7"

libraryDependencies += "ch.qos.logback" % "logback-classic" % "1.1.2"

addCompilerPlugin("org.scalamacros" % "paradise" % "2.1.0-M5" cross CrossVersion.full)
