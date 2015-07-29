import sbt.Keys._

enablePlugins(JavaServerAppPackaging)

version in ThisBuild := "0.7.8.5"
val vampRouterVersion = "0.7.8-dev"


val platform = "amd64"
//val rpmArchitecture="amd64"
val rpmArchitecture="x86_64"
val debianPlatform = "amd64"


// ### Organisation
organization in ThisBuild := "io.vamp"
name := "vamp-router"
description := "The router of Vamp"
packageDescription := "Very Awesome Microservices Platform"
packageSummary := "Vamp Router"
maintainer := "Tim Nolet <tnolet@magnetic.io>"

// ## RPM

// TODO: set file permission on vamp-router executable
// TODO: loop over all architectures, download zip, package.
// TODO: Add symlink /usr/bin/vamp-router

rpmVendor := "magnetic.io"
rpmUrl := Some("http://vamp.io")
rpmLicense := Some("Apache 2")

packageArchitecture in Rpm := rpmArchitecture

// ### Docker
packageSummary in Docker := "Vamp router"
packageName in Docker := "magneticio/vamp-router" // Only add this if you want to rename your docker image name
dockerBaseImage := "ubuntu:latest" // Docker image to use as a base for the application image
dockerExposedPorts in Docker := Seq(80,1988,10001) // Ports to expose from container for Docker container linking
dockerEntrypoint in Docker:= Seq("/vamp-router")
dockerUpdateLatest := true

/**

# This Dockerfile does the basic install of vamp-router and Haproxy. Please see:
# https://github.com/magneticio/vamp-router
#
# HAproxy is currently version 1.5.3 build from source on Ubuntu with the following options
# apt-get install build-essential
# apt-get install libpcre3-dev
# make TARGET=linux26 ARCH=i386 USE_PCRE=1 USE_LINUX_SPLICE=1 USE_LINUX_TPROXY=1
#
#

ADD ./target/linux_i386/vamp-router /vamp-router

ADD ./configuration /configuration

ADD ./examples /examples

ADD ./target/linux_i386/haproxy /usr/sbin/haproxy
*/


// removes all jar mappings in universal
mappings in Universal := {
  val universalMappings = (mappings in Universal).value
  val filtered = universalMappings filter {
    case (file, fileName) =>  ! fileName.endsWith(".jar")
  }
  filtered
}


resourceGenerators in Compile += Def.task {
  val location = url(s"https://bintray.com/artifact/download/magnetic-io/downloads/vamp-router/vamp-router_${vampRouterVersion}_linux_$platform.zip")
  IO.unzipURL(location, target.value / platform).toSeq
}.taskValue

linuxPackageMappings += packageMapping( (target.value / platform / "vamp-router",  "/usr/share/vamp-router/vamp-router") ) withPerms "744"

mappings in Universal <+= (packageBin in Compile, target ) map { (_, target) =>
  val conf = target / platform / "configuration" / "error_pages"  / "500rate.http"
  conf -> "configuration/error_pages/500rate.http"
}

mappings in Universal <+= (packageBin in Compile, target ) map { (_, target) =>
  val conf = target / platform / "configuration" / "templates" / "haproxy_config.template"
  conf -> "configuration/templates/haproxy_config.template"
}

mappings in Universal <+= (packageBin in Compile, target ) map { (_, target) =>
  val conf = target / platform / "examples" / "example1.json"
  conf -> "examples/example1.json"
}

debianPackageDependencies in Debian ++= Seq("haproxy", "bash (>= 2.05a-11)")

//
//addCommandAlias("packageRpmAll", "; clean" +
//  "; packageRpmArchitecture"
//)
//
//lazy val packageRpmArchitecture = taskKey[File]("creates RPM package and stores result in package / $rpmArchitecture")
//
//
//packageRpmArchitecture := {
//  val output = baseDirectory.value / "package" / rpmArchitecture / s"${name.value}-${version.value}-1.$rpmArchitecture.rpm"
//  val rpmFile = (packageBin in Rpm).value
//  IO.move(rpmFile, output)
//  output
//}



// Creating custom packageOutputs formats
addCommandAlias("packageDebianAll", "; clean " +
  "; set serverLoading in Debian := com.typesafe.sbt.packager.archetypes.ServerLoader.SystemV" +
  "; packageDebianSystemV " +
  "; clean " +
  "; set serverLoading in Debian := com.typesafe.sbt.packager.archetypes.ServerLoader.Upstart" +
  "; packageDebianUpstart"  +
  "; clean " +
  "; set serverLoading in Debian := com.typesafe.sbt.packager.archetypes.ServerLoader.Systemd" +
  "; packageDebianSystemD"
)


lazy val packageDebianUpstart = taskKey[File]("creates deb-upstart package")
lazy val packageDebianSystemV = taskKey[File]("creates deb-systenv package")
lazy val packageDebianSystemD = taskKey[File]("creates deb-systemd package")


packageDebianUpstart := {
  val output = baseDirectory.value / "package" / "upstart" / debianPlatform / s"${name.value}-${version.value}.deb"
  val debianFile = (packageBin in Debian).value
  IO.move(debianFile, output)
  output
}

packageDebianSystemV := {
  val output = baseDirectory.value / "package" / "systemv" / debianPlatform / s"${name.value}-${version.value}.deb"
  val debianFile = (packageBin in Debian).value
  IO.move(debianFile, output)
  output
}

packageDebianSystemD := {
  val output = baseDirectory.value / "package" / "systemd" / debianPlatform / s"${name.value}-${version.value}.deb"
  val debianFile = (packageBin in Debian).value
  IO.move(debianFile, output)
  output
}







