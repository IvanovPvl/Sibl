name := "Sibl"
version := "0.1"
scalaVersion := "2.12.4"

resolvers += "jmcardon at bintray" at "https://dl.bintray.com/jmcardon/tsec"

val tsecV = "0.0.1-M9"
libraryDependencies ++= Seq(
  "io.github.jmcardon" %% "tsec-common" % tsecV,
  "io.github.jmcardon" %% "tsec-md" % tsecV,
)