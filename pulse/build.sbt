import sbt.Keys._
import com.typesafe.sbt.packager.archetypes.ServerLoader.SystemV


enablePlugins(JavaServerAppPackaging)

version in ThisBuild := "0.7.8.5"

libraryDependencies ++=Seq(
  "io.vamp" %% "pulse-server" % "0.7.9-rc.ba83547"
)


// ### Organisation
organization in ThisBuild := "io.vamp"
name := "vamp-pulse"
description := "Pulse is an event consumption/retrieval/aggregation application"
packageDescription := "Very Awsome Microservices Platform"
packageSummary := "Vamp Pulse"
maintainer := "Matthijs Dekker <matthijs@magnetic.io>"

// ###  Debian
serverLoading in Debian := SystemV


// ## RMP
rpmVendor := "magnetic.io"
rpmUrl := Some("http://vamp.io")
rpmLicense := Some("Apache 2")

// ### Docker
packageSummary in Docker := "Vamp Pulse"
packageName in Docker := "magneticio/vamp-pulse" // Only add this if you want to rename your docker image name
dockerBaseImage := "java:8" // Docker image to use as a base for the application image
dockerExposedPorts in Docker := Seq(8083, 9300, 9200) // Ports to expose from container for Docker container linking
dockerUpdateLatest := true


mappings in Universal <+= (packageBin in Compile, sourceDirectory ) map { (_, src) =>
  val conf = src / "main" / "resources" / "reference.conf"
  conf -> "conf/application.conf"
}

bashScriptExtraDefines += """addJava "-Dconfig.file=${app_home}/../conf/application.conf""""


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

// ###  Build
scalaVersion := "2.11.6"
scalaVersion in ThisBuild := scalaVersion.value

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
