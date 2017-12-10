name := "ScalaProducer"

version := "0.1"

scalaVersion := "2.12.4"

libraryDependencies ++= {
  Seq(
    "org.apache.kafka" % "kafka-clients" % "1.0.0",
    "com.github.tototoshi" %% "scala-csv" % "1.3.5",
    "net.liftweb" %% "lift-json" % "3.2.0-M3"
  )
}
