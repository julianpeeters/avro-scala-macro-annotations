import AssemblyKeys._

assemblySettings

name := "Avro Spark Examples"

version := "0.1-SNAPSHOT"

scalaVersion := "2.11.6"

libraryDependencies += "org.apache.spark" %% "spark-core" % "1.3.0"

libraryDependencies += "org.apache.avro" % "avro-mapred" % "1.7.6" classifier "hadoop2" 

libraryDependencies += "org.apache.hadoop" % "hadoop-client" % "2.4.0"

// see: http://apache-spark-user-list.1001560.n3.nabble.com/SparkContext-startup-time-out-td1753.html

// libraryDependencies += "com.typesafe.akka" % "akka-cluster_2.11" % "2.2.4"

// see: https://issues.apache.org/jira/browse/SPARK-1138

libraryDependencies += "com.julianpeeters" % "avro-scala-macro-annotations_2.11" % "0.6"

addCompilerPlugin("org.scalamacros" % "paradise" % "2.1.0-M5" cross CrossVersion.full)
