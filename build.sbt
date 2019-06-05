name := """payment-api"""

version := "0.1"

lazy val root = (project in file(".")).enablePlugins(PlayJava, PlayEbean)

scalaVersion := "2.12.4"


libraryDependencies ++= Seq(
    javaJdbc,
    cacheApi,
javaWs
)

libraryDependencies += guice
libraryDependencies += "com.typesafe.play" %% "play-json" % "2.6.0"
libraryDependencies += "org.projectlombok" % "lombok" % "1.16.18" % "provided"

// Test Database
libraryDependencies += "com.h2database" % "h2" % "1.4.194"


// Testing libraries for dealing with CompletionStage...
libraryDependencies += "org.assertj" % "assertj-core" % "3.6.2" % Test
libraryDependencies += "org.awaitility" % "awaitility" % "2.0.0" % Test


// Make verbose tests
testOptions in Test := Seq(Tests.Argument(TestFrameworks.JUnit, "-a", "-v"))
