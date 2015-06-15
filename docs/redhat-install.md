#How to install on Red Hat (7)


This document details how to install Vamp on a Red Hat Linux machine. It uses the Vamp .RPM published on Bintray.



## Prerequisites

Before installing Vamp, make sure your system has the required software installed.

### Java 8
If Java 8 is not installed, please do so before continuing.
To check your Java version, type: `java -version`

For detail on how to install Java 8, check the following page: http://tecadmin.net/install-java-8-on-centos-rhel-and-fedora/

### Docker | Marathon Cluster

You either will need to have Docker install, or have access to a Marathon Cluster.
The default Vamp install uses a Docker installation.


## Install Core
```bash
sudo yum install vamp-core-0.7.7-1.noarch.rpm

sudo service vamp-core start
```


## Install Cli
```bash
sudo yum install vamp-cli-0.7.7-1.noarch.rpm

vamp info
```