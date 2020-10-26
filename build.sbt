name := "SCALA_2_13_x"

version := "0.1"

scalaVersion := "2.13.3"

// Scalactic dependency
libraryDependencies += "org.scalactic" %% "scalactic" % "3.2.0"
libraryDependencies += "com.typesafe.akka" %% "akka-actor" % "2.6.10"

//libraryDependencies ++= Seq(
//  "com.typesafe.akka" %% "akka-actor" % "2.5.4",
//  "com.typesafe.akka" %% "akka-testkit" % "2.5.4" % Test
//)

// SuperSafe Community Edition Scalactic Scala Compiler - Flags errors in Scalactic code at runtime
//resolvers += "Artima Maven Repository" at "http://repo.artima.com/releases"
