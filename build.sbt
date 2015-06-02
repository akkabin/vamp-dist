import com.typesafe.sbt.SbtNativePackager.packageArchetype
import sbt.Keys._

import com.typesafe.sbt.packager.archetypes.ServerLoader.{SystemV, Upstart}

serverLoading in Debian := SystemV


enablePlugins(JavaAppPackaging)
//enablePlugins(JDebPackaging)


lazy val commonSettings = Seq(
  organization in ThisBuild := "io.vamp.core",
  version in ThisBuild := "0.7.6",
  scalaVersion := "2.11.5" ,
  maintainer in Windows := "magnetic.io",
  maintainer in Debian := "Matthijs Dekker<matthijs@magnetic.io>"
)

name := """vamp-dist"""

scalaVersion in ThisBuild := scalaVersion.value

publishMavenStyle := true

// This has to be overridden for sub-modules to have different description
description in ThisBuild:= """Vamp distributables"""

pomExtra in ThisBuild := <url>http://vamp.io</url>
  <licenses>
    <license>
      <name>The Apache License, Version 2.0</name>
      <url>http://www.apache.org/licenses/LICENSE-2.0.txt</url>
    </license>
  </licenses>
  <developers>
    <developer>
      <name>Matthijs Dekker</name>
      <email>matthijs@magnetic.io</email>
      <organization>VAMP</organization>
      <organizationUrl>http://vamp.io</organizationUrl>
    </developer>
  </developers>
  <scm>
    <connection>scm:git:git@github.com:magneticio/vamp-dist.git</connection>
    <developerConnection>scm:git:git@github.com:magneticio/vamp-dist.git</developerConnection>
    <url>git@github.com:magneticio/vamp-dist.git</url>
  </scm>

// Use local maven repository
resolvers in ThisBuild ++= Seq(
  "Typesafe Repo" at "http://repo.typesafe.com/typesafe/releases/",
  Resolver.jcenterRepo
)

lazy val cli = project.in(file("cli")).
  settings(commonSettings: _*).
  settings(
    packageArchetype.java_application,
    name := "vamp-cli",
    description := "Vamp CLI",
    packageSummary in Linux := "Vamp Command Line Interface",
    packageSummary in Windows := "Vamp Command Line Interface",
    packageDescription := "The command line for Vamp",
    wixProductId := "ce07be71-510d-414a-82d4-dff47631848a",
    wixProductUpgradeId := "4552fb0e-e257-8dbd-9ecb-dba9dbacf424",
    libraryDependencies ++=Seq(
      "io.vamp" %% "core-cli" % "0.7.6.3a04e66"
    )
  )

lazy val core = project.in(file("core")).
  settings(commonSettings: _*).
  settings(
    packageArchetype.java_server,
    name := "vamp-core",
    description := "Vamp Core",
    packageSummary in Linux := "Vamp Core",
    packageSummary in Windows := "Vamp Core",
    packageDescription := "Very Awsome Microservices Platform",
    wixProductId := "e407be71-510d-414a-82d4-dff47631848a",
    wixProductUpgradeId := "9752fb0e-e257-8dbd-9ecb-dba9dbacf424",
    libraryDependencies ++=Seq(
      "io.vamp" %% "core-bootstrap" % "0.7.6.140"
    )
  )


// Java version and encoding requirements
scalacOptions += "-target:jvm-1.8"

javacOptions ++= Seq("-encoding", "UTF-8")

scalacOptions in ThisBuild ++= Seq(Opts.compile.deprecation, Opts.compile.unchecked) ++
  Seq("-Ywarn-unused-import", "-Ywarn-unused", "-Xlint", "-feature")



