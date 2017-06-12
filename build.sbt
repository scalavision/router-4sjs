lazy val commonSettings = Seq(
  organization := "scalavision",
  name := "Router4S",
  version := "0.1-SNAPSHOT",
  scalacOptions ++= compileOptions.value,
  libraryDependencies ++= Seq(
    "com.lihaoyi" %%% "sourcecode" % "0.1.2"
  )  ++ scalaWebRxDependencies.value 
) ++ ScalaJSDefaultsPlugin.scalaJsDefaults

lazy val router4S = project.in(file("."))
  .enablePlugins(ScalaJSPlugin)
  .settings(commonSettings :_*)
