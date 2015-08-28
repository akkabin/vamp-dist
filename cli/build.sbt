import sbt.Keys._
import sbtassembly.AssemblyPlugin.autoImport._


enablePlugins(JavaAppPackaging)

version in ThisBuild := "0.7.10"

libraryDependencies ++=Seq(
  "io.vamp" %% "core-cli" % "0.7.10"
)


// ### Organisation
organization in ThisBuild := "io.vamp"
name := "vamp-cli"
description := "This is the command line interface for VAMP"
packageDescription := "CLI for the Very Awesome Microservices Platform"
packageSummary := "The Vamp CLI"
maintainer :=  "Matthijs Dekker <matthijs@magnetic.io>"

executableScriptName := "vamp"

// ## RPM
rpmVendor := "Magnetic.io"
rpmUrl := Some("http://vamp.io")
rpmLicense := Some("Apache 2")

// ### Docker
packageName in Docker := "vamp-cli" // Only add this if you want to rename your docker image name
dockerRepository := Some("magneticio") // Repository used when publishing Docker image
dockerUpdateLatest := true

// ###  Build
scalaVersion := "2.11.7"
scalaVersion in ThisBuild := scalaVersion.value

resolvers in ThisBuild += Resolver.url("magnetic-io ivy resolver", url("http://dl.bintray.com/magnetic-io/vamp"))(Resolver.ivyStylePatterns)

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

mappings in Universal <+= (packageBin in Compile, sourceDirectory ) map { (_, src) =>
  val conf = src / "scripts" / "brew_vamp"
  conf -> "brew/vamp"
}


// the bash scripts classpath only needs the fat jar
scriptClasspath := Seq( (assemblyJarName in assembly).value )

// Add check for Java 8 (not for windows)
bashScriptExtraDefines ++= IO.readLines(baseDirectory.value / "scripts" / "java_check.sh")

val downloadLicense = taskKey[File]("Downloads the latest license file.")

downloadLicense := {
  val location = target.value / "downloads" / "LICENSE"
  location.getParentFile.mkdirs()
  IO.download(url("https://raw.githubusercontent.com/magneticio/vamp-dist/master/LICENSE"), location)
  location
}

mappings in Universal += downloadLicense.value -> "LICENSE"