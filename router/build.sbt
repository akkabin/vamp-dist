import sbt.Classpaths._
import sbt.Keys._
import com.typesafe.sbt.packager.archetypes.ServerLoader.SystemV


enablePlugins(JavaServerAppPackaging)

version in ThisBuild := "0.7.8.4"
val vampRouterVersion = "0.7.8-dev"



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

rpmVendor := "magnetic.io"
rpmUrl := Some("http://vamp.io")
rpmLicense := Some("Apache 2")

packageArchitecture in Rpm := "x86_64"

addCommandAlias("packageRPMAll", "" +
  "; set packageArchitecture in Rpm := \"x86_64\"" +
  "; packageRPMx86")

lazy val packageRPMx86 = taskKey[File]("creates RPM for the x86_64 platform")

packageRPMx86 := {
  val output = baseDirectory.value / "package" / "x86" / s"${name.value}-${version.value}-1.x86_64.rpm"
  val rpmFile = (packageBin in Rpm).value
  IO.move(rpmFile, output)
  output
}



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


resourceGenerators in Test += Def.task {
  val location = url(s"https://bintray.com/artifact/download/magnetic-io/downloads/vamp-router/vamp-router_${vampRouterVersion}_linux_386.zip")
  IO.unzipURL(location, target.value / "i386").toSeq
}.taskValue


mappings in Universal <+= (packageBin in Compile, target ) map { (_, target) =>
  val bin = target / "i386" / "vamp-router"
  bin -> "vamp-router"
}

mappings in Universal <+= (packageBin in Compile, target ) map { (_, target) =>
  val conf = target / "i386" / "configuration" / "error_pages"  / "500rate.http"
  conf -> "configuration/error_pages/500rate.http"
}

mappings in Universal <+= (packageBin in Compile, target ) map { (_, target) =>
  val conf = target / "i386" / "configuration" / "templates" / "haproxy_config.template"
  conf -> "configuration/templates/haproxy_config.template"
}

mappings in Universal <+= (packageBin in Compile, target ) map { (_, target) =>
  val conf = target / "i386" / "examples" / "example1.json"
  conf -> "examples/example1.json"
}



//mappings in Universal <+= (packageBin in Compile, sourceDirectory ) map { (_, src) =>
//  val conf = src / "main" / "resources" / "reference.conf"
//  conf -> "conf/application.conf"
//}
//
//mappings in Universal <+= (packageBin in Compile, sourceDirectory ) map { (_, src) =>
//  val conf = src / "main" / "resources" / "logback.xml"
//  conf -> "conf/logback.xml"
//}

//bashScriptExtraDefines += """addJava "-Dconfig.file=${app_home}/../conf/logback.xml""""
//bashScriptExtraDefines += """addJava "-Dconfig.file=${app_home}/../conf/application.conf""""
//


debianPackageDependencies in Debian ++= Seq("haproxy", "bash (>= 2.05a-11)")


/**

  touch \"/var/log/vamp-router.log\" && chown \"vamp-router\" \"/var/log/vamp-router.log\""
  update-rc.d \"vamp-router\" defaults"
  service vamp-router start"


 */



// Creating custom packageOutputs formats
addCommandAlias("packageDebianAll", "; clean " +
  "; set serverLoading in Debian := com.typesafe.sbt.packager.archetypes.ServerLoader.SystemV" +
  "; packageDebianSystemV " +
  "; clean " +
  "; set serverLoading in Debian := com.typesafe.sbt.packager.archetypes.ServerLoader.Upstart" +
  "; packageDebianUpstart")



lazy val packageDebianUpstart = taskKey[File]("creates deb-upstart package")
lazy val packageDebianSystemV = taskKey[File]("creates deb-systenv package")

packageDebianUpstart := {
  val output = baseDirectory.value / "package" / "upstart" / s"${name.value}-${version.value}.deb"
  val debianFile = (packageBin in Debian).value
  IO.move(debianFile, output)
  output
}

packageDebianSystemV := {
  val output = baseDirectory.value / "package" / "systemv" / s"${name.value}-${version.value}.deb"
  val debianFile = (packageBin in Debian).value
  IO.move(debianFile, output)
  output
}






