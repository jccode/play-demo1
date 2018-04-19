name := "play-demo1"

version := "0.1"

scalaVersion := "2.12.5"

val slickVersion = "3.2.1"

lazy val `play-demo1` = (project in file(".")).enablePlugins(PlayScala)

libraryDependencies ++= Seq(
  guice,

//  jdbc,
//  evolutions,

  "org.scalatestplus.play" %% "scalatestplus-play" % "3.1.2" % "test",
  "org.webjars" % "bootstrap" % "4.0.0-2",

  "com.typesafe.play" %% "play-slick" % "3.0.3",
  "com.typesafe.play" %% "play-slick-evolutions" % "3.0.3",
  "com.h2database" % "h2" % "1.4.196",

  "com.typesafe.slick" %% "slick" % slickVersion,
  "com.typesafe.slick" %% "slick-hikaricp" % slickVersion,
  "com.typesafe.slick" %% "slick-codegen" % slickVersion,
)


lazy val slick = taskKey[Seq[File]]("gen-tables")  // register manual sbt command
slick := {
  val (dir, cp, r, s) = ((sourceManaged in Compile).value, (dependencyClasspath in Compile).value, (runner in Compile).value, streams.value)

  val pkg = "dao"
  val slickProfile = "slick.jdbc.H2Profile"
  val jdbcDriver = "org.h2.Driver"
  val url = "jdbc:h2:./play.db;MODE=MYSQL;"
  val user = "sa"
  val password = ""

  r.run("slick.codegen.SourceCodeGenerator", cp.files, Array(slickProfile, jdbcDriver, url, dir.getPath, pkg, user, password), s.log)
  val outputFile = dir / pkg.replace(".", "/") / "Tables.scala"
  Seq(outputFile)
}

//sourceGenerators in Compile += slick  // register automatic code generation on every compile, remove for only manual use
