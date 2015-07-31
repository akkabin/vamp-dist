import sbt.Keys._
import com.typesafe.sbt.packager.archetypes.ServerLoader
import com.typesafe.sbt.packager.linux.LinuxSymlink

enablePlugins(JavaServerAppPackaging)

version in ThisBuild := "0.7.9.1"
val vampRouterVersion = "0.7.9"


val platform = "amd64"
val rpmArchitecture="x86_64"
val debianPlatform = "amd64"


// ### Organisation
organization in ThisBuild := "io.vamp"
name := "vamp-router"
description := "The router of Vamp"
packageDescription := "Router component of the Very Awesome Microservices Platform"
packageSummary := "The router of Vamp"
maintainer := "Tim Nolet <tnolet@magnetic.io>"


// ## RPM
rpmVendor := "magnetic.io"
rpmUrl := Some("http://vamp.io")
rpmLicense := Some("Apache 2")
packageArchitecture in Rpm := rpmArchitecture
serverLoading in Rpm := ServerLoader.Systemd

// ### Docker
packageSummary in Docker := "Vamp router"
packageName in Docker := "magneticio/vamp-router" // Only add this if you want to rename your docker image name
dockerBaseImage := "ubuntu:latest" // Docker image to use as a base for the application image
dockerExposedPorts in Docker := Seq(80,1988,10001) // Ports to expose from container for Docker container linking
dockerEntrypoint in Docker:= Seq("/vamp-router")
dockerUpdateLatest := true


resourceGenerators in Compile += Def.task {
  val location = url(s"https://bintray.com/artifact/download/magnetic-io/downloads/vamp-router/vamp-router_${vampRouterVersion}_linux_$platform.zip")
  IO.unzipURL(location, target.value / platform).toSeq
}.taskValue


// removes all jar mappings in universal
mappings in Universal := {
  val universalMappings = (mappings in Universal).value
  val filtered = universalMappings filter {
    case (file, fileName) =>  ! fileName.endsWith(".jar")
  }
  filtered
}

// Add an empty folder for the data
linuxPackageMappings += packageTemplateMapping(s"/usr/share/${name.value}/data")() withUser(name.value) withGroup(name.value)

// Make the configuration directory writeable
linuxPackageMappings += packageTemplateMapping(s"/usr/share/${name.value}/configuration")() withUser(name.value) withGroup(name.value)

// copy vamp-router from the extracted bintray zip
linuxPackageMappings += packageMapping( (target.value / platform / "vamp-router",  "/usr/share/vamp-router/vamp-router") ) withPerms "755"

// Add the script file to which starts vamp-router
mappings in Universal <+= (packageBin in Compile, sourceDirectory ) map { (_, src) =>
  val bin = src / "templates" / "bash-template"
  bin -> "bin/vamp-router"
}

// Add the config files
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


// ## Debian
debianPackageDependencies in Debian ++= Seq("haproxy", "bash (>= 2.05a-11)")

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
  val output = baseDirectory.value / "package" / "upstart" / s"${name.value}-${version.value}_$debianPlatform.deb"
  val debianFile = (packageBin in Debian).value
  IO.move(debianFile, output)
  output
}

packageDebianSystemV := {
  val output = baseDirectory.value / "package" / "systemv" / s"${name.value}-${version.value}_$debianPlatform.deb"
  val debianFile = (packageBin in Debian).value
  IO.move(debianFile, output)
  output
}

packageDebianSystemD := {
  val output = baseDirectory.value / "package" / "systemd" / s"${name.value}-${version.value}_$debianPlatform.deb"
  val debianFile = (packageBin in Debian).value
  IO.move(debianFile, output)
  output
}


