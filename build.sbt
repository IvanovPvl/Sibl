name := "Sibl"
version := "0.1"
scalaVersion := "2.12.4"

resolvers += "jmcardon at bintray" at "https://dl.bintray.com/jmcardon/tsec"

libraryDependencies ++= {
  val tsecV = "0.0.1-M9"
  val rocksV = "5.14.2"
  val pureconfigV = "0.9.1"
  Seq(
    "io.github.jmcardon" %% "tsec-common" % tsecV,
    "io.github.jmcardon" %% "tsec-md" % tsecV,
    "org.rocksdb" % "rocksdbjni" % rocksV,
    "com.github.pureconfig" %% "pureconfig" % pureconfigV
  )
}
