import sbt.Keys._
import com.typesafe.sbt.packager.archetypes.ServerLoader.SystemV
import sbtassembly.AssemblyPlugin.autoImport._


enablePlugins(JavaAppPackaging)

version in ThisBuild := "0.7.6"

libraryDependencies ++=Seq(
  "io.vamp" %% "core-cli" % "0.7.6.3a04e66"
)


// ### Organisation
organization in ThisBuild := "io.vamp"
name := "vamp-cli"
description := "This is the command line interface for VAMP"
packageSummary := "The Vamp CLI"
packageDescription := "Very Awsome Microservices Platform CLI"
maintainer :=  "Matthijs Dekker <matthijs@magnetic.io>"

executableScriptName := "vamp"
//mainClass in Compile := Some("Boot")

// ###  Debian
//changelog in Debian := "changes.txt"


// ## RMP
rpmVendor := "Magnetic.io"
rpmUrl := Some("https://github.com/magneticio/vamp")
rpmLicense := Some("Apache 2")

// ###  Windows
wixProductId := "e407be71-510d-414a-82d4-dff47631848a"
wixProductUpgradeId := "9752fb0e-e257-8dbd-9ecb-dba9dbacf424"


// ### Docker
packageName in Docker := "vamp-cli" // Only add this if you want to rename your docker image name
daemonUser in Docker := normalizedName.value // user in the Docker image which will execute the application (must already exist)

//dockerBaseImage := "dockerfile/java" // Docker image to use as a base for the application image

//dockerExposedPorts in Docker := Seq(9000, 9443) // Ports to expose from container for Docker container linking

//dockerExposedVolumes in Docker := Seq("/opt/docker/logs") // Data volumes to make available in image

//dockerRepository := Some("dockerusername") // Repository used when publishing Docker image


// ###  Build
scalaVersion := "2.11.5"
scalaVersion in ThisBuild := scalaVersion.value

resolvers in ThisBuild ++= Seq(
  Resolver.typesafeRepo("releases"),
  Resolver.jcenterRepo
)


scalacOptions += "-target:jvm-1.8"

javacOptions ++= Seq("-encoding", "UTF-8")

scalacOptions in ThisBuild ++= Seq(Opts.compile.deprecation, Opts.compile.unchecked) ++
  Seq("-Ywarn-unused-import", "-Ywarn-unused", "-Xlint", "-feature")


 //Make a fat jar
assemblyJarName in assembly := "vamp-cli.jar"

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