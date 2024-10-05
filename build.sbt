ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "3.3.4"

lazy val root = (project in file("."))
  .settings(
    name := "generic-api"
  )

libraryDependencies ++= Seq(
  "org.http4s"       %% "http4s-blaze-server" % "0.23.16",  // For server backend
  "org.http4s"       %% "http4s-circe"        % "0.23.16",  // For JSON handling
  "org.http4s"       %% "http4s-dsl"          % "0.23.16",  // DSL for defining routes
  "io.circe"         %% "circe-generic"       % "0.14.6",   // For JSON (optional)
  "org.typelevel"    %% "cats-effect"         % "3.5.2",     // Effect system for managing side effects
)
