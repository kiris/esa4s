organization := "com.github.kiris"
name := "esa4s"

scalaVersion := "2.12.8"
scalacOptions ++= Seq(
  "-Ypartial-unification",
  "-language:higherKinds"
)

version := "latest"
autoCompilerPlugins := true

resolvers ++= Seq(
  "Sonatype releases" at "https://oss.sonatype.org/content/repositories/releases/",
  "Typesafe Repo" at "http://repo.typesafe.com/typesafe/releases/"
)


libraryDependencies += "org.typelevel" %% "cats-core" % "1.6.0"
libraryDependencies += "org.typelevel" %% "cats-effect" % "1.2.0"
libraryDependencies += "org.scalaj" %% "scalaj-http" % "2.3.0"
libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.5" % "test"

val circeVersion = "0.10.0"
libraryDependencies ++= Seq(
  "io.circe" %% "circe-core",
  "io.circe" %% "circe-generic",
  "io.circe" %% "circe-parser",
  "io.circe" %% "circe-generic-extras"
).map(_ % circeVersion)

addCompilerPlugin("org.scalamacros" % "paradise" % "2.1.1" cross CrossVersion.full)