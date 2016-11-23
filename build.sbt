name := """play-scala-sensors-monitor"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file("."))
  .enablePlugins(PlayScala)
  .enablePlugins(SbtWeb)

scalaVersion := "2.11.7"

libraryDependencies ++= Seq(
  jdbc,
  cache,
  ws,
  "org.reactivemongo" %% "play2-reactivemongo" % "0.12.0",
  "org.webjars" % "angularjs" % "1.3.15"
)

routesGenerator := InjectedRoutesGenerator
