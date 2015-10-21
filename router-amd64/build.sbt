import sbt.Keys._
import com.typesafe.sbt.packager.archetypes.ServerLoader

import com.typesafe.sbt.packager.docker._

enablePlugins(JavaServerAppPackaging)

version in ThisBuild := "0.7.11"
val vampRouterVersion = "0.7.11"


val platform = "amd64"
val rpmArchitecture="x86_64"
val debianArchitecture = "amd64"


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
val dockerInstallLocation = "/opt/docker" //  differs from the linux packaging, which uses /usr/share/vamp-router

packageSummary in Docker := "Vamp router"
dockerRepository := Some("magneticio") // Repository used when publishing Docker image
dockerBaseImage := "ubuntu:latest" // Docker image to use as a base for the application image
dockerExposedPorts in Docker := Seq(80,1988,10001) // Ports to expose from container for Docker container linking
dockerEntrypoint := Seq(s"$dockerInstallLocation/vamp-router")
dockerUpdateLatest := true
daemonUser in Docker := "root"  // Set to root, so we don't need to setup sudo for installing haproxy
defaultLinuxInstallLocation in Docker := dockerInstallLocation

// Add the executable in docker
mappings in Docker <+= (packageBin in Compile, target ) map { (_, target) =>
  target / platform / "vamp-router" -> s"$dockerInstallLocation/vamp-router"
}

dockerCommands ++= Seq(
  //Add haproxy repo
  ExecCmd("RUN", "sh", "-c","echo 'deb http://ppa.launchpad.net/vbernat/haproxy-1.5/ubuntu trusty main' >> /etc/apt/sources.list"),
  ExecCmd("RUN", "apt-key", "adv", "--keyserver", "keyserver.ubuntu.com","--recv-keys", "1C61B9CD"),

  // Add haproxy
  ExecCmd("RUN", "apt-get", "update"),
  ExecCmd("RUN", "apt-get", "install", "-y", "haproxy"),

  // make the executable executable
  ExecCmd("RUN", "chmod", "u+x", s"$dockerInstallLocation/vamp-router"),

  // Create data dir and make it writable
  ExecCmd("RUN", "mkdir", s"$dockerInstallLocation/data") ,
  ExecCmd("RUN", "chmod", "u+w", s"$dockerInstallLocation/data")
)


// ## Universal section, used by both Linux & Docker packaging

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



// ## Linux section

// Add an empty folder for the data
linuxPackageMappings += packageTemplateMapping(s"/usr/share/${name.value}/data")() withUser(name.value) withGroup(name.value)

// Make the configuration directory writeable
linuxPackageMappings += packageTemplateMapping(s"/usr/share/${name.value}/configuration")() withUser(name.value) withGroup(name.value)

// copy vamp-router from the extracted bintray zip
linuxPackageMappings += packageMapping( (target.value / platform / "vamp-router",  s"/usr/share/${name.value}/vamp-router") ) withPerms "755"

linuxPackageMappings += packageMapping( (sourceDirectory.value / "templates" / "bash-template",  s"/usr/share/${name.value}/bin/vamp-router") ) withPerms "755"


// ## Debian
debianPackageDependencies in Debian ++= Seq("haproxy", "bash (>= 2.05a-11)")
packageArchitecture in Debian := debianArchitecture
debianSection in Debian := "net"

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
  val output = baseDirectory.value / "package" / "upstart" / s"${name.value}-${version.value}_$debianArchitecture.deb"
  val debianFile = (packageBin in Debian).value
  IO.move(debianFile, output)
  output
}

packageDebianSystemV := {
  val output = baseDirectory.value / "package" / "systemv" / s"${name.value}-${version.value}_$debianArchitecture.deb"
  val debianFile = (packageBin in Debian).value
  IO.move(debianFile, output)
  output
}

packageDebianSystemD := {
  val output = baseDirectory.value / "package" / "systemd" / s"${name.value}-${version.value}_$debianArchitecture.deb"
  val debianFile = (packageBin in Debian).value
  IO.move(debianFile, output)
  output
}

// Creating custom packageOutputs formats

addCommandAlias("packageRpmAll", "; clean " +
  "; set serverLoading in Rpm := com.typesafe.sbt.packager.archetypes.ServerLoader.Upstart" +
  "; packageRpmUpstart"  +
  "; clean " +
  "; set serverLoading in Rpm := com.typesafe.sbt.packager.archetypes.ServerLoader.Systemd" +
  "; packageRpmSystemD"
)

lazy val packageRpmUpstart = taskKey[File]("creates rpm-upstart package")
lazy val packageRpmSystemD = taskKey[File]("creates rpm-systemd package")

packageRpmUpstart := {
  val output = baseDirectory.value / "package" / "upstart" / s"${name.value}-${version.value}-1.$rpmArchitecture.rpm"
  val rpmFile = (packageBin in Rpm).value
  IO.move(rpmFile, output)
  output
}
packageRpmSystemD := {
  val output = baseDirectory.value / "package" / "systemd" / s"${name.value}-${version.value}-1.$rpmArchitecture.rpm"
  val rpmFile = (packageBin in Rpm).value
  IO.move(rpmFile, output)
  output
}