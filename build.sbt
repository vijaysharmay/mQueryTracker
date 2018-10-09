name := "mQueryTracker"

version := "0.1"

scalaVersion := "2.12.6"

resolvers += "Typesafe Repository" at "http://repo.typesafe.com/typesafe/releases/"

libraryDependencies ++= Seq(
  "com.typesafe.akka"          %% "akka-actor"            % "2.5.12",
  "com.typesafe.akka"          %% "akka-stream"           % "2.5.12",
  "com.typesafe.akka"          %% "akka-http"             % "10.1.1",
  "org.mongodb.scala"          %% "mongo-scala-driver"    % "2.2.1",
  "com.typesafe.akka"          %% "akka-http-spray-json"  % "10.1.1",
  "ch.qos.logback"              % "logback-classic"       % "1.2.3",
  "com.typesafe.scala-logging" %% "scala-logging"         % "3.9.0",
  "com.typesafe.slick"         %% "slick"                 % "3.2.3",
  "com.typesafe.slick"         %% "slick-hikaricp"        % "3.2.3",
  "org.xerial"                  % "sqlite-jdbc"           % "3.21.0.1"
)

assemblyJarName in assembly := "mQueryTracker.jar"
test in assembly := {}
mainClass in assembly := Some("com.mquerytracker.Server")
scalaVersion in ThisBuild := "2.12.6"
assemblyMergeStrategy in assembly := {
  case "reference.conf" => MergeStrategy.concat
  case x =>
    val oldStrategy = (assemblyMergeStrategy in assembly).value
    oldStrategy(x)
}