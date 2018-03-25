name := "play-demo1"

version := "0.1"

scalaVersion := "2.12.5"

lazy val `play-demo1` = (project in file(".")).enablePlugins(PlayScala)

libraryDependencies ++= Seq(
  guice, specs2 % Test,
  "org.webjars" % "bootstrap" % "4.0.0-2"
)

