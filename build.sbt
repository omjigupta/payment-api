name := """payment-api"""

version := "0.1"

lazy val root = (project in file(".")).enablePlugins(PlayJava)

scalaVersion := "2.12.4"


libraryDependencies ++= Seq(
    javaJdbc,
javaWs
)

libraryDependencies += guice
libraryDependencies += "com.typesafe.play" %% "play-json" % "2.8.0-M1"
libraryDependencies += "org.projectlombok" % "lombok" % "1.18.2" % "provided"

libraryDependencies += "org.jooq" % "jooq" % "3.11.11"
libraryDependencies += "org.jooq" % "jooq-meta" % "3.11.11"
libraryDependencies += "org.jooq" % "jooq-codegen" % "3.11.11"

//money and currency calculation
libraryDependencies += "org.javamoney" % "moneta" % "1.3"



// Test Database
libraryDependencies += "com.h2database" % "h2" % "1.4.194"


// Testing libraries for dealing with CompletionStage...
libraryDependencies += "org.assertj" % "assertj-core" % "3.12.2" % Test
libraryDependencies += "org.awaitility" % "awaitility" % "3.1.6" % Test

libraryDependencies += "org.mockito" % "mockito-core" % "2.28.2" % Test
