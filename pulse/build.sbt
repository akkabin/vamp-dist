import sbt.Keys._
import com.typesafe.sbt.packager.archetypes.ServerLoader.SystemV


enablePlugins(JavaServerAppPackaging)

version in ThisBuild := "0.7.10"

libraryDependencies ++=Seq(
  "io.vamp" %% "pulse-server" % "0.7.10"
)


// ### Organisation
organization in ThisBuild := "io.vamp"
name := "vamp-pulse"
description := "Pulse is an event consumption/retrieval/aggregation application"
packageDescription := "Pulse component of the Very Awesome Microservices Platform"
packageSummary := "The event store of Vamp"
maintainer := "Matthijs Dekker <matthijs@magnetic.io>"

// ###  Debian
serverLoading in Debian := SystemV
val debianPlatform = "all"


// ## RMP
rpmVendor := "magnetic.io"
rpmUrl := Some("http://vamp.io")
rpmLicense := Some("Apache 2")
val rpmArchitecture="noarch"

// ### Docker
packageSummary in Docker := "Vamp Pulse"
dockerExposedPorts in Docker := Seq(8083, 9300, 9200) // Ports to expose from container for Docker container linking
dockerRepository := Some("magneticio") // Repository used when publishing Docker image
dockerUpdateLatest := true


mappings in Universal <+= (packageBin in Compile, sourceDirectory ) map { (_, src) =>
  val conf = src / "main" / "resources" / "reference.conf"
  conf -> "conf/application.conf"
}

mappings in Universal <+= (packageBin in Compile, sourceDirectory ) map { (_, src) =>
  val conf = src / "main" / "resources" / "logback.xml"
  conf -> "conf/logback.xml"
}

// Add an empty folder to mappings
linuxPackageMappings += packageTemplateMapping(s"/usr/share/${name.value}/data")() withUser(name.value) withGroup(name.value)

bashScriptExtraDefines += """addJava "-Dlogback.configurationFile=${app_home}/../conf/logback.xml""""
bashScriptExtraDefines += """addJava "-Dconfig.file=${app_home}/../conf/application.conf""""
bashScriptExtraDefines +="""cd ${app_home}/../"""

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
lazy val packageDebianSystemV = taskKey[File]("creates deb-systemv package")
lazy val packageDebianSystemD = taskKey[File]("creates deb-systemd package")

packageDebianUpstart := {
  val output = baseDirectory.value / "package" / "upstart" / s"${name.value}-${version.value}_all.deb"
  val debianFile = (packageBin in Debian).value
  IO.move(debianFile, output)
  output
}

packageDebianSystemV := {
  val output = baseDirectory.value / "package" / "systemv" / s"${name.value}-${version.value}_all.deb"
  val debianFile = (packageBin in Debian).value
  IO.move(debianFile, output)
  output
}

packageDebianSystemD := {
  val output = baseDirectory.value / "package" / "systemd" / s"${name.value}-${version.value}_all.deb"
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


// ###  Build
scalaVersion := "2.11.7"
scalaVersion in ThisBuild := scalaVersion.value

resolvers in ThisBuild += Resolver.url("magnetic-io ivy resolver", url("http://dl.bintray.com/magnetic-io/vamp"))(Resolver.ivyStylePatterns)
resolvers in ThisBuild ++= Seq(
  "Typesafe Repo" at "http://repo.typesafe.com/typesafe/releases/",
  Resolver.jcenterRepo
)

// we specify the name for our fat jar
assemblyJarName in assembly := "vamp-pulse.jar"
assemblyExcludedJars in assembly := {
  val cp = (fullClasspath in assembly).value
  cp filter {_.data.getName == "joda-convert-1.6.jar"}
}

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
