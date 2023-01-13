val scala3Version = "3.2.0"
val circeVersion = "0.14.1"

lazy val root = project
.in(file("."))
.settings(
        name := "Quoridor",
        version := "0.10.0-SNAPSHOT",
        assembly / assemblyJarName := "quoridor.jar",
        scalaVersion := scala3Version,
        libraryDependencies += "org.scalactic" %% "scalactic" % "3.2.14",
        libraryDependencies += "org.scalatest" %% "scalatest" % "3.2.14" % "test",
        libraryDependencies += "org.scala-lang.modules" %% "scala-xml" % "2.1.0" cross CrossVersion.for2_13Use3,
        libraryDependencies ++= Seq("io.circe" %% "circe-core", "io.circe" %% "circe-generic", "io.circe" %% "circe-parser")
        .map(_ % circeVersion),
        libraryDependencies ++= {
        lazy val osName = System.getProperty("os.name") match {
        case n if n.startsWith("Linux") => "linux"
        case n if n.startsWith("Mac") => "mac-aarch64"
        case n if n.startsWith("Windows") => "win"
        case _ => throw new Exception("Unknown platform!")
        }
        Seq("base", "controls", "fxml", "graphics", "media", "swing", "web")
        .map(m => "org.openjfx" % s"javafx-$m" % "19" classifier osName)
        },
        libraryDependencies += "org.scalafx" %% "scalafx" % "19.0.0-R30")

assemblyMergeStrategy in assembly:= {
    case PathList("META-INF", _*) => MergeStrategy.discard
        case _ => MergeStrategy.first
}
