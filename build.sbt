lazy val commonSettings = Seq(
  organization := "scalavision",
  name := "Router4S",
  version := "0.1-SNAPSHOT",
//  scalacOptions ++= compileOptions.value,
  libraryDependencies ++= Seq(
    "com.lihaoyi" %%% "sourcecode" % "0.1.4"
  )  
) 


lazy val router4S = project.in(file("."))
  .enablePlugins(ScalaJSPlugin)
  .settings(commonSettings :_*)
  .settings(
    libraryDependencies ++= scalaWebRxDependencies.value    
  ).settings(
    jsEnv := new org.scalajs.jsenv.jsdomnodejs.JSDOMNodeJSEnv,
    jsEnv in Test := new org.scalajs.jsenv.jsdomnodejs.JSDOMNodeJSEnv,
    scalaJSStage in Global := FastOptStage,
    scalaJSUseMainModuleInitializer := true,
    emitSourceMaps := true,
    relativeSourceMaps := true,
    scalacOptions in Test ++= Seq("-Yrangepos"),
    skip in packageJSDependencies := false,
    jsDependencies += "org.webjars" % "jquery" % "2.1.4" / "2.1.4/jquery.js",
    testFrameworks += new TestFramework("utest.runner.Framework")
  )

//router4S.project.projectSettings ++= scalaJSDefaults
