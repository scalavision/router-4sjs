scalaVersion in ThisBuild := "2.11.8"

lazy val scalaMetaVersion = "1.4.0.544"
lazy val metaParadiseVersion = "3.0.0.132"

lazy val compilerOptions = Seq[String](
  "-deprecation",
  "-encoding",
  "UTF-8",
  "-feature",
  "-language:existentials",
  "-language:higherKinds",
  "-language:implicitConversions",
  "-unchecked",
  "-Yno-adapted-args",
  "-Ywarn-dead-code",
  "-Ywarn-numeric-widen",
  "-Xfuture",
  "-Xlint"
)

lazy val commonSettings = Seq(
  organization := "scalavision",
  name := "Router4S",
  scalaVersion := "2.11.8",
  version := "0.1-SNAPSHOT",
  triggeredMessage in ThisBuild := Watched.clearWhenTriggered,
  scalacOptions ++= compilerOptions,
  libraryDependencies ++= Seq(
    "com.google.guava" % "guava" % "latest.integration",
    "com.geirsson" %% "scalafmt" % "0.2.10",
    "org.specs2" %% "specs2-core" % "latest.integration" % "test",
    "org.scalactic" %% "scalactic" % "3.0.1",
    "org.scalatest" %% "scalatest" % "3.0.1" % "test"
  )
)

lazy val routerSettings = Seq(
  jsDependencies += RuntimeDOM,
  version := "0.0.1-SNAPSHOT",
  scalacOptions := Seq("-unchecked", "-deprecation", "-encoding", "utf8", "-feature"),
  scalacOptions in Test ++= Seq("-Yrangepos"),
  offline := true,
  scalaJSUseRhino in Global := false,
  emitSourceMaps := true,
  relativeSourceMaps := true,
  skip in packageJSDependencies := false,
  jsDependencies += "org.webjars" % "jquery" % "2.1.4" / "2.1.4/jquery.js",
  testFrameworks += new TestFramework("utest.runner.Framework"),
  libraryDependencies ++= Seq(
    "org.scala-js" %%% "scalajs-dom" % "latest.integration",
    "com.lihaoyi" %%% "scalatags" % "latest.integration",
    "com.lihaoyi" %%% "scalarx" % "latest.integration",
    "be.doeraene" %%% "scalajs-jquery" % "latest.integration",
    "com.scalavision" %%% "rxlib" % "0.1-SNAPSHOT",
    "com.propensive" %%% "rapture-i18n" % "latest.integration",
    "com.lihaoyi" %%% "upickle" % "latest.integration",
    "com.lihaoyi" %%% "utest" % "latest.integration" % "test",
    "org.scalatest" %% "scalatest" % "latest.integration" % "test",
    "org.scalacheck" %% "scalacheck" % "latest.integration" % "test",
    "org.specs2" %% "specs2-core" % "latest.integration" % "test"
))

lazy val router4S = project.in(file("."))
  .enablePlugins(ScalaJSPlugin)
  .settings(commonSettings :_*)
  .settings(routerSettings :_*)
