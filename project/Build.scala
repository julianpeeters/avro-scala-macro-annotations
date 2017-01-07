import sbt._
import Keys._

object BuildSettings {
  val buildSettings = Defaults.defaultSettings ++ Seq(
    organization := "com.julianpeeters",
    version := "0.11.1",
    scalaVersion := "2.11.8",
    crossScalaVersions := Seq("2.11.6", "2.11.7", "2.11.8", "2.12.1"),
    scalacOptions ++= Seq("-unchecked", "-deprecation", "-feature", "-Ywarn-value-discard"),
    resolvers += "scalaz-bintray" at "http://dl.bintray.com/scalaz/releases",
    resolvers += Resolver.sonatypeRepo("releases"),
    addCompilerPlugin("org.scalamacros" % "paradise" % "2.1.0" cross CrossVersion.full),
    libraryDependencies += "org.apache.avro" % "avro" % "1.7.6",
    libraryDependencies := {
      CrossVersion.partialVersion(scalaVersion.value) match {
        case Some((2, scalaMajor)) if scalaMajor >= 11 =>
          libraryDependencies.value ++ Seq (
          "org.specs2" %% "specs2-core" % "3.8.6" % "test")
        case Some((2, 12)) =>
          libraryDependencies.value ++ Seq()
      }
    },
    // publishing
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
      libraryDependencies <+= (scalaVersion)("org.scala-lang" % "scala-reflect" % _),
      libraryDependencies += "org.codehaus.jackson" % "jackson-core-asl" % "1.9.13",
      libraryDependencies += "com.typesafe.scala-logging" %% "scala-logging" % "3.5.0",
      libraryDependencies += "com.typesafe.scala-logging" % "scala-logging-slf4j_2.11" % "2.1.2")
  )

  lazy val tests: Project = Project(
    "tests",
    file("tests"),
    settings = buildSettings)
    .settings(
      publishArtifact := false
        // Add your additional libraries here (comma-separated)...
     ) dependsOn(macros) settings(
   // include the macro classes and resources in the main jar
   mappings in (Compile, packageBin) ++= mappings.in(macros, Compile, packageBin).value,
   // include the macro sources in the main source jar
   mappings in (Compile, packageSrc) ++= mappings.in(macros, Compile, packageSrc).value
  )
}
