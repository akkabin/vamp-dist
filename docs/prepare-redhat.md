#How to build on Red Hat (7)

This document explains how to setup a build environment on Red Hat Linux. The intended target audience are developers, who wish to create .RPM packages for distribution purposes.


## Prerequisites

You will need to have access to a RPM based machine, in order to build .RPM packages.

This document has been tested with RH7, but should also work with CentOS.


## Add Java 8
If Java 8 is not installed, please do so before continuing.
To check your Java version, type: `java -version`

For detail on how to install Java 8, check the following page: http://tecadmin.net/install-java-8-on-centos-rhel-and-fedora/


## Add sbt repo + install
```bash
curl https://bintray.com/sbt/rpm/rpm | sudo tee /etc/yum.repos.d/bintray-sbt-rpm.repo
sudo yum install sbt
```

# Add git & rpmdevtools

```bash
sudo yum install git rpmdevtools
```



