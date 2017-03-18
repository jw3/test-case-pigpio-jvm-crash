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

    "-Ywarn-unused-import",
    "-Xfatal-warnings",
    "-Xlint:_"
  ),
  resolvers += Resolver.mavenLocal
)

lazy val commonLibraries = {
  Seq(
    "com.typesafe.akka" %% "akka-actor" % "2.4.17",
    "org.bytedeco" % "javacpp" % "1.3.3-SNAPSHOT"
  )
}

lazy val `white-whale` =
  project.in(file("."))
  .aggregate(`javacpp-test-case`)
  .settings(commonSettings: _*)

lazy val `javacpp-test-case` =
  project.in(file("javacpp-test-case"))
  .settings(commonSettings: _*)
  .settings(libraryDependencies ++= commonLibraries)
