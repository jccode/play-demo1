name := "play-demo1"

version := "0.1"

scalaVersion := "2.12.5"

val slickVersion = "3.2.1"

lazy val `play-demo1` = (project in file(".")).enablePlugins(PlayScala)

libraryDependencies ++= Seq(
  guice,
  jdbc,
  evolutions,
  "org.scalatestplus.play" %% "scalatestplus-play" % "3.1.2" % "test",
  "org.webjars" % "bootstrap" % "4.0.0-2",

  "com.typesafe.play" %% "play-slick" % "3.0.3",
  "com.h2database" % "h2" % "1.4.196",

  "com.typesafe.slick" %% "slick" % slickVersion,
  "com.typesafe.slick" %% "slick-hikaricp" % slickVersion,
  "com.typesafe.slick" %% "slick-codegen" % slickVersion,
)



// custom keys
val slick = taskKey[Seq[File]]("slick generate source from tables.")

// helper functions
def slickOutputFile = (basedir:String, pkg:String) => s"${basedir}/${pkg.replace(".","/")}/Tables.scala"
def slickMySQL (url: String, user: String, password: String, pkg: String, srcdir: String) = {
  val profile = "slick.jdbc.MySQLProfile"
  val jdbcDriver = "com.mysql.jdbc.Driver"
  (Array(profile, jdbcDriver, url, srcdir, pkg, user, password), slickOutputFile(srcdir, pkg))
}
def slickH2 (url: String, user: String, password: String, pkg: String, srcdir: String) = {
  val profile = "slick.jdbc.H2Profile"
  val jdbcDriver = "org.h2.Driver"
  (Array(profile, jdbcDriver, url, srcdir, pkg, user, password), slickOutputFile(srcdir, pkg))
}

slick := {
  val dir = (scalaSource in Compile).value
  val cp = (dependencyClasspath in Compile).value
  val r = (runner in Compile).value
  val s = streams.value
  val outputDir = dir.getPath
  val t = slickH2("jdbc:h2:tcp://localhost:9092/~/play-remote", "sa", "", "gen", outputDir)
  r.run("slick.codegen.SourceCodeGenerator", cp.files, t._1, s.log).failed foreach (sys error _.getMessage)
  Seq(file(t._2))
}
