name := "CameraIngester"

version := "1.0-SNAPSHOT"

scalaVersion := "2.12.4"

libraryDependencies += "com.drewnoakes" % "metadata-extractor" % "2.10.1"
libraryDependencies += "org.mp4parser" % "isoparser" % "1.9.31"
libraryDependencies += "org.slf4j" % "slf4j-nop" % "1.7.25"

//libraryDependencies += "org.scalactic" %% "scalactic" % "3.0.1"
//libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.1" % "test"

assemblyJarName in assembly := name.value + "-" + version.value + ".jar"
