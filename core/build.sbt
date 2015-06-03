import sbt.Keys._
import com.typesafe.sbt.packager.archetypes.ServerLoader.SystemV


enablePlugins(JavaServerAppPackaging)

version in ThisBuild := "0.7.6"

libraryDependencies ++=Seq(
  "io.vamp" %% "core-bootstrap" % "0.7.6.3a04e66"
)


// ### Organisation
organization in ThisBuild := "io.vamp"
name := "vamp-core"
description := "The brain of Vamp"
packageDescription := "Very Awsome Microservices Platform"
packageSummary := "Vamp Core"
maintainer := "Matthijs Dekker <matthijs@magnetic.io>"

// ###  Debian
serverLoading in Debian := SystemV


// ## RMP
rpmVendor := "magnetic.io"
rpmUrl := Some("https://github.com/magneticio/vamp")
rpmLicense := Some("Apache 2")

// ###  Windows
maintainer in Windows := "magnetic.io"
wixProductId := "e407be71-510d-414a-82d4-dff47631848a"
wixProductUpgradeId := "9752fb0e-e257-8dbd-9ecb-dba9dbacf424"


// ### Docker
packageSummary in Docker := "Vamp Core"
packageName in Docker := "vamp-core" // Only add this if you want to rename your docker image name
daemonUser in Docker := normalizedName.value // user in the Docker image which will execute the application (must already exist)

//dockerBaseImage := "dockerfile/java" // Docker image to use as a base for the application image

//dockerExposedPorts in Docker := Seq(9000, 9443) // Ports to expose from container for Docker container linking

//dockerExposedVolumes in Docker := Seq("/opt/docker/logs") // Data volumes to make available in image

//dockerRepository := Some("dockerusername") // Repository used when publishing Docker image


// Creating custom packageOutputs formats

addCommandAlias("packageDebianAll", "; clean " +
  "; set serverLoading in Debian := com.typesafe.sbt.packager.archetypes.ServerLoader.SystemV" +
  "; packageDebianSystemV " +
  "; clean " +
  "; set serverLoading in Debian := com.typesafe.sbt.packager.archetypes.ServerLoader.Upstart" +
  "; packageDebianUpstart")

lazy val packageDebianUpstart = taskKey[File]("creates deb-upstart package")
lazy val packageDebianSystemV = taskKey[File]("creates deb-systenv package")
lazy val packageRpmSystemD = taskKey[File]("creates rpm-systenv package")

packageDebianUpstart := {
  val output = baseDirectory.value / "package" / "deb-upstart"
  val debianFile = (packageBin in Debian).value
  IO.move(debianFile, output)
  output
}

packageDebianSystemV := {
  val output = baseDirectory.value / "package" / "deb-systemv"
  val debianFile = (packageBin in Debian).value
  IO.move(debianFile, output)
  output
}

packageRpmSystemD := {
  val output = baseDirectory.value / "package" / "rpm-systemd"
  val rpmFile = (packageBin in Rpm).value
  IO.move(rpmFile, output)
  output
}






// ###  Build
scalaVersion := "2.11.5"
scalaVersion in ThisBuild := scalaVersion.value

resolvers in ThisBuild ++= Seq(
  "Typesafe Repo" at "http://repo.typesafe.com/typesafe/releases/",
  Resolver.jcenterRepo
)

// we specify the name for our fat jar
assemblyJarName in assembly := "vamp-core.jar"

// removes all jar mappings in universal and appends the fat jar
mappings in Universal := {
  // universalMappings: Seq[(File,String)]
  val universalMappings = (mappings in Universal).value
  val fatJar = (assembly in Compile).value
  // removing means filtering
  val filtered = universalMappings filter {
    case (file, fileName) =>  ! fileName.endsWith(".jar")
  }
  // add the fat jar
  filtered :+ (fatJar -> ("lib/" + fatJar.getName))
}

// the bash scripts classpath only needs the fat jar
scriptClasspath := Seq( (assemblyJarName in assembly).value )



scalacOptions += "-target:jvm-1.8"

javacOptions ++= Seq("-encoding", "UTF-8")

scalacOptions in ThisBuild ++= Seq(Opts.compile.deprecation, Opts.compile.unchecked) ++
  Seq("-Ywarn-unused-import", "-Ywarn-unused", "-Xlint", "-feature")
