ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.13.12"

lazy val root = (project in file("."))
  .settings(
    name := "MyGroceris"
  )
val AkkaVersion = "2.9.0"
val AkkaHttpVersion = "10.6.0"
resolvers += "Akka library repository".at("https://repo.akka.io/maven")

libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-http-spray-json" % AkkaHttpVersion,
  "com.typesafe.akka" %% "akka-actor-typed" % AkkaVersion,
  "com.typesafe.akka" %% "akka-stream" % AkkaVersion,
  //  "com.typesafe.akka" %% "akka-http-spray-json" % AkkaHttpVersion, //ussed for mashmaling ..
  "com.typesafe.akka" %% "akka-http" % AkkaHttpVersion,
"io.spray" % "spray-can" % "1.3.1",
  "io.spray" % "spray-http" % "1.3.1",
  "io.spray" % "spray-routing" % "1.3.1",
  "com.typesafe.akka" %% "akka-actor" % "2.8.0",
  "com.typesafe.akka" %% "akka-slf4j" % "2.8.0",
  "net.liftweb" %% "lift-json" % "3.5.0",
  "ch.qos.logback" % "logback-classic" % "1.4.7",

"com.typesafe.slick" %% "slick" % "3.4.1",
  "org.postgresql" % "postgresql" % "42.5.4",
  "com.typesafe.slick" %% "slick-hikaricp" % "3.4.1",
  "com.github.tminglei" %% "slick-pg" % "0.21.1",
  "com.github.tminglei" %% "slick-pg_play-json" % "0.21.1"
)

resolvers ++= Seq(
  "Spray repository" at "http://repo.spray.io",
  "Typesafe repository" at "http://repo.typesafe.com/typesafe/releases/"
).map(_.withAllowInsecureProtocol(true))




//libraryDependencies += "com.typesafe.akka" %% "akka-http-spray-json" % AkkaHttpVersion

