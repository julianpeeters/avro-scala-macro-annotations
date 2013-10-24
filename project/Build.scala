import sbt._
import Keys._

object BuildSettings {
  val buildSettings = Defaults.defaultSettings ++ Seq(
    organization := "org.scala-lang.macroparadise",
    version := "1.0.0",
    scalacOptions ++= Seq(),
    scalaVersion := "2.10.2",
    resolvers += Resolver.sonatypeRepo("snapshots"),
    libraryDependencies += "io.spray" %%  "spray-json" % "1.2.5",
    libraryDependencies += "com.gensler" %% "scalavro" % "0.4.0",
    resolvers += "spray" at "http://repo.spray.io/",
    addCompilerPlugin("org.scala-lang.plugins" % "macro-paradise" % "2.0.0-SNAPSHOT" cross CrossVersion.full)
  )
}

object MyBuild extends Build {
  import BuildSettings._

  lazy val root: Project = Project(
    "root",
    file("."),
    settings = buildSettings ++ Seq(
      run <<= run in Compile in core
    )
  ) aggregate(macros, core)

  lazy val macros: Project = Project(
    "macros",
    file("macros"),
    settings = buildSettings ++ Seq(
      libraryDependencies <+= (scalaVersion)("org.scala-lang" % "scala-reflect" % _))
  )

  lazy val core: Project = Project(
    "core",
    file("core"),
    settings = buildSettings ++ Seq(
      libraryDependencies ++=   Seq(
        "com.novus" %% "salat" % "1.9.2",
       // "com.julianpeeters" %% "artisinal-pickle-maker" % "0.1-SNAPSHOT",
        "org.slf4j" % "slf4j-simple" % "1.7.5"))
  ) dependsOn(macros)
}
