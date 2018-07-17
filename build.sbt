lazy val commonSettings = Seq(
  organization := "com.github.jw3",
  version := "0.1",
  scalaVersion := "2.11.8",

  scalacOptions ++= Seq(
    "-encoding", "UTF-8",

    "-feature",
    "-unchecked",
    "-deprecation",

    "-language:postfixOps",
    "-language:implicitConversions",

    //"-Ywarn-unused-import", // JniNative plugin doesnt like this - "Unused import"
    //"-Xfatal-warnings",     // JniNative plugin doesnt like this - Deprecation warning on ProcessBuilder.lines
    "-Xlint:_"
  ),
  resolvers += Resolver.mavenLocal
)

lazy val commonLibraries = {
  Seq(
    "com.typesafe.akka" %% "akka-actor" % "2.5.12",
    "org.bytedeco" % "javacpp" % "1.4.1"
  )
}

lazy val `white-whale` =
  project.in(file("."))
  .aggregate(`javacpp-test-case`, `sbt-jni-sandbox`, `pigpio-jni`)
  .settings(commonSettings: _*)

lazy val `javacpp-test-case` =
  project.in(file("javacpp-test-case"))
  .settings(commonSettings: _*)
  .settings(libraryDependencies ++= commonLibraries)

lazy val `sbt-jni-sandbox` =
  project.in(file("sbt-jni-sandbox"))
  .settings(commonSettings: _*)
  .settings(
    libraryDependencies ++= commonLibraries,
    target in javah := baseDirectory.value / "src" / "native" / "include"
  )
  .enablePlugins(JniNative)

lazy val `pigpio-jni` =
  project.in(file("pigpio-jni"))
  .settings(commonSettings: _*)
  .settings(
    libraryDependencies ++= commonLibraries,
    target in javah := baseDirectory.value / "src" / "native" / "include"
  )
  .enablePlugins(JniNative)
