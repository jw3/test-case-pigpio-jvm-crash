organization := "com.github.jw3"
name := "test-case-pigpio-jvm-crash"
version := "1.0"

scalaVersion := "2.11.8"
resolvers += Resolver.mavenLocal

libraryDependencies ++= {
  Seq(
    "com.typesafe.akka" %% "akka-actor" % "2.4.17",
    "org.bytedeco" % "javacpp" % "1.3.3-SNAPSHOT"
  )
}
