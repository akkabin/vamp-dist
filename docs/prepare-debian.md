# How to build on Debian

This document explains how to setup a build environment on Debian Linux. The intended target audience are developers, who wish to create .deb packages for distribution purposes.


## Prerequisites

You will need to have access to a Debian based machine, in order to build .deb packages.

This document has been tested with Debian, but should also work with Ubuntu.

## Add Java 8
If Java 8 is not installed, please do so before continuing.
To check your Java version, type: `java -version`

For detail on how to install Java 8, check the following page: http://www.webupd8.org/2014/03/how-to-install-oracle-java-8-in-debian.html

## Add sbt repo + install
```bash
sudo echo "deb http://dl.bintray.com/sbt/debian /" | sudo tee -a /etc/apt/sources.list.d/sbt.list
sudo apt-get update
sudo apt-get install sbt -y
```

# Add git & fakeroot
```bash
sudo apt-get install git fakeroot
```