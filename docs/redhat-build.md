#How to build on Red Hat (7)

This document explains how to setup a build environment on Red Hat Linux. The intended target audience are developers, who wish to create .RPM packages for distribution purposes.


## Prerequisites

You will need to have access to a RPM based machine, in order to build .RPM packages.

This document has been tested with RH7, but should also work with CentOS.


## Add sbt repo + install
```bash
curl https://bintray.com/sbt/rpm/rpm | sudo tee /etc/yum.repos.d/bintray-sbt-rpm.repo
sudo yum install sbt
```

# Add git & rpmdevtools

```bash
sudo yum install git rpmdevtools
```

## Clone github directory
``` bash
git clone https://github.com/magneticio/vamp-dist.git
cd vamp-dist
```

## Build CLI
```bash
cd cli
sbt rpm:packageBin
```

## Build Core
```bash
cd core
sbt rpm:packageBin
```
