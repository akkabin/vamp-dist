import sbt.Keys._
import com.typesafe.sbt.packager.archetypes.ServerLoader.SystemV


enablePlugins(JavaServerAppPackaging)

version in ThisBuild := "0.7.6"


// ### Organisation
organization in ThisBuild := "io.vamp"

name := "vamp-cli"

description := "Vamp CLI"

packageDescription := "Very Awsome Microservices Platform CLI"

// ###  Debian
serverLoading in Debian := SystemV
packageSummary in Linux := "Vamp Core"
maintainer in Debian := "Matthijs Dekker<matthijs@magnetic.io>"

// ## RMP
rpmVendor := "Magnetic.io"


// ###  Windows

//maintainer in Windows := "magnetic.io"
packageSummary in Windows := "Vamp Core"
wixProductId := "e407be71-510d-414a-82d4-dff47631848a"
wixProductUpgradeId := "9752fb0e-e257-8dbd-9ecb-dba9dbacf424"


// ### Docker
maintainer in Docker := "Matthijs Dekker<matthijs@magnetic.io>"
packageSummary in Docker := "Vamp CLI"
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
  "Typesafe Repo" at "http://repo.typesafe.com/typesafe/releases/",
  Resolver.jcenterRepo
)

libraryDependencies ++=Seq(
  "io.vamp" %% "core-cli" % "0.7.6.3a04e66"
)

scalacOptions += "-target:jvm-1.8"

javacOptions ++= Seq("-encoding", "UTF-8")

scalacOptions in ThisBuild ++= Seq(Opts.compile.deprecation, Opts.compile.unchecked) ++
  Seq("-Ywarn-unused-import", "-Ywarn-unused", "-Xlint", "-feature")
