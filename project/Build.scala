import sbt._
import Keys._

object BuildSettings {
  val buildSettings = Defaults.defaultSettings ++ Seq(
    organization := "com.julianpeeters",
    version := "0.1-SNAPSHOT",
    scalacOptions ++= Seq(),
    scalaVersion := "2.10.3",
    libraryDependencies += "org.apache.avro" % "avro" % "1.7.6",
    libraryDependencies += "org.json4s" %% "json4s-native" % "3.2.6",
    libraryDependencies += "org.specs2" %% "specs2" % "2.2" % "test",
    libraryDependencies += "org.scalamacros" % "quasiquotes" % "2.0.0-M6" cross CrossVersion.full,
    addCompilerPlugin("org.scalamacros" % "paradise" % "2.0.0" cross CrossVersion.full),
    publishMavenStyle := true,
    publishArtifact in Test := false,
    publishTo <<= version { (v: String) =>
      val nexus = "https://oss.sonatype.org/"
      if (v.trim.endsWith("SNAPSHOT"))
        Some("snapshots" at nexus + "content/repositories/snapshots")
      else
        Some("releases" at nexus + "service/local/staging/deploy/maven2")
    },
    pomIncludeRepository := { _ => false },
    licenses := Seq("Apache 2.0" -> url("http://www.apache.org/licenses/LICENSE-2.0")),
    homepage := Some(url("https://github.com/julianpeeters/avro-scala-macro-annotations")),
    pomExtra := (
      <scm>
        <url>git://github.com/julianpeeters/avro-scala-macro-annotations.git</url>
        <connection>scm:git://github.com/julianpeeters/avro-scala-macro-annotations.git</connection>
      </scm>
      <developers>
        <developer>
          <id>julianpeeters</id>
          <name>Julian Peeters</name>
          <url>http://github.com/julianpeeters</url>
        </developer>
      </developers>)
  )

}

object MyBuild extends Build {
  import BuildSettings._

  // Configure prompt to show current project
  override lazy val settings = super.settings :+ {
    shellPrompt := { s => Project.extract(s).currentProject.id + " > " }
  }

  lazy val root: Project = Project(
    "root",
    file("."),
    settings = buildSettings ++ Seq(
      publishArtifact := false,
      run <<= run in Compile in tests
    )
  ) aggregate(macros, tests)

  lazy val macros: Project = Project(
    "avro-scala-macro-annotations",
    file("macros"),
    settings = buildSettings ++ Seq(
      libraryDependencies <+= (scalaVersion)("org.scala-lang" % "scala-reflect" % _))
  )

  /*
  *tests project build def was added to the macros build, and could probably be "re-styled" to integrate more cleanly
  */
  lazy val tests: Project = Project(
    "tests",
    file("tests"), 
    settings = buildSettings)
    .settings(
      publishArtifact := false,
      libraryDependencies ++= Seq("org.specs2" %% "specs2" % "1.13" % "test")
        // Add your additional libraries here (comma-separated)...
     )
     .settings(addCompilerPlugin("org.scalamacros" % "paradise" % "2.0.0" cross CrossVersion.full)
     ) dependsOn(macros) settings(
   // include the macro classes and resources in the main jar
   mappings in (Compile, packageBin) ++= mappings.in(macros, Compile, packageBin).value,
   // include the macro sources in the main source jar
   mappings in (Compile, packageSrc) ++= mappings.in(macros, Compile, packageSrc).value
  )
}


