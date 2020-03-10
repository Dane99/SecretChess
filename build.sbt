name := "SecretChess"

version := "0.1"

scalaVersion := "2.13.1"

lazy val root = project.
  enablePlugins(ScalaJSPlugin)

libraryDependencies += "org.scala-js" %%% "scalajs-dom" % "1.0.0"