
resolvers += "Onema Snapshots" at "s3://s3-us-east-1.amazonaws.com/ones-deployment-bucket/snapshots"

lazy val root = (project in file("."))
  .settings(
    organization := "onema",

    name := "ServerlessApiMeetup",

    version := "0.1.0",

    scalaVersion := "2.12.5",

    javacOptions ++= Seq("-source", "1.8", "-target", "1.8", "-Xlint"),

    libraryDependencies ++= {
      Seq(
        // Serverless Base!
        "onema"                     % "serverless-base_2.12"      % "0.6.0",

        // AWS Clients
        "com.amazonaws"             % "aws-java-sdk-dynamodb"     % "1.11.301",

        // Logging
        "com.typesafe.scala-logging"% "scala-logging_2.12"        % "3.7.2",
        "ch.qos.logback"            % "logback-classic"           % "1.1.7",

        // Testing
        "org.scalatest"             % "scalatest_2.12"            % "3.0.5"   % "test",
        "org.scalamock"             %% "scalamock"                % "4.1.0"   % Test
      )
    }
  )
//  .dependsOn(serverlessBase)

// Sub-projects
//lazy val serverlessBase = RootProject(file("../ServerlessBase"))
assemblyMergeStrategy in assembly := {
  case PathList("META-INF", xs @ _*) => MergeStrategy.discard
  case x => MergeStrategy.first
}
// Assembly
assemblyJarName in assembly := "app.jar"
