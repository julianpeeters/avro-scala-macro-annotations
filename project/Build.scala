import sbt._
import Keys._

object BuildSettings {
  val buildSettings = Defaults.defaultSettings ++ Seq(
    organization := "com.julianpeeters",
    version := "0.1-SNAPSHOT",
    scalacOptions ++= Seq(),
    scalaVersion := "2.10.3",
    resolvers += Resolver.sonatypeRepo("snapshots"),
    resolvers += "spray" at "http://repo.spray.io/",
    libraryDependencies += "io.spray" %%  "spray-json" % "1.2.5",
    libraryDependencies += "com.gensler" %% "scalavro" % "0.4.0",
    libraryDependencies += "org.json4s" %% "json4s-native" % "3.2.6",
    libraryDependencies += "org.specs2" %% "specs2" % "2.2" % "test",
    libraryDependencies += "com.novus" %% "salat" % "1.9.7-SNAPSHOT" % "test",
    libraryDependencies += "org.scalamacros" % "quasiquotes" % "2.0.0-M6" cross CrossVersion.full,
    addCompilerPlugin("org.scalamacros" % "paradise" % "2.0.0-M6" cross CrossVersion.full)
  )
}

object MyBuild extends Build {
  import BuildSettings._
  import Dependencies._

  // Configure prompt to show current project
  override lazy val settings = super.settings :+ {
    shellPrompt := { s => Project.extract(s).currentProject.id + " > " }
  }

  lazy val root: Project = Project(
    "avro-scala-macro-annotations",
    file("."),
    settings = buildSettings ++ Seq(
     // run <<= run in Compile in AvroTypeProviderTest
      run <<= run in Compile in AvroRecordTest
    )
  ) aggregate(macros, AvroTypeProviderTest, AvroRecordTest)

  lazy val macros: Project = Project(
    "macros",
    file("macros"),
    settings = buildSettings ++ Seq(
      libraryDependencies <+= (scalaVersion)("org.scala-lang" % "scala-reflect" % _))
  )

  lazy val AvroTypeProviderTest: Project = Project(
    "AvroTypeProviderTest",
    file("AvroTypeProviderTest"),
    settings = buildSettings ++ Seq(
      libraryDependencies ++=   Seq(
        "com.novus" %% "salat" % "1.9.7-SNAPSHOT"))
  ) dependsOn(macros) settings(
   // include the macro classes and resources in the main jar
   mappings in (Compile, packageBin) ++= mappings.in(macros, Compile, packageBin).value,
   // include the macro sources in the main source jar
   mappings in (Compile, packageSrc) ++= mappings.in(macros, Compile, packageSrc).value
  )


  /*
  *AvroRecordTests project build def was added to the macros build, and could probably be "re-styled" to integrate more cleanly
  */
  lazy val AvroRecordTest: Project = Project(
    "AvroRecordTest",
    file("AvroRecordTest"))
    .settings(ScaldingBuildSettings.buildSettings: _*)
    .settings(
      libraryDependencies ++= Seq(
        Libraries.scaldingCore,
        Libraries.scaldingAvro,
        Libraries.hadoopCore,
        Libraries.specs2,
        // Add your additional libraries here (comma-separated)...
        "org.scalamacros" % "quasiquotes" % "2.0.0-M6" cross CrossVersion.full)
    
     )
     .settings(addCompilerPlugin("org.scalamacros" % "paradise" % "2.0.0-M6" cross CrossVersion.full)
     ) dependsOn(macros) settings(
   // include the macro classes and resources in the main jar
   mappings in (Compile, packageBin) ++= mappings.in(macros, Compile, packageBin).value,
   // include the macro sources in the main source jar
   mappings in (Compile, packageSrc) ++= mappings.in(macros, Compile, packageSrc).value
  )
}
