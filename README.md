# vamp-dist

This project contains the definitions for distributing the different flavors in which we ship Vamp.

To build for all different platforms, you'll need to have access to different (virtual) machines.


## Applications
Currently, there are two applications supported: 

- vamp-core
- vamp-cli


## Supported packages

The following packaging commands are supported through sbt:

- debian:packageBin
- rpm:packageBin
- windows:packageBin
- universal:packageOsxDmg
- docker:publishLocal
- universal:packageZipTarball

If a specific command can be run successful depends on the platform you are using.
For Linux packages, a Linux machine is required; for the Windows package, a Windows machine is needed.

For more details, see: http://www.scala-sbt.org/sbt-native-packager/gettingstarted.html


## Debian
Create your own Debian machine, by using the scripts in ansible/playbooks/vamp-debian-build.

First you need to setup your vagrant environment for this. See the `ansible/README.md`


### Build vamp-core
`cd vamp-dist/core`

`sbt debian:packageBin`

The .deb package can be found in `core/target`


## Installing
You can install a downloaded .deb file by issueing the command:

`dpkg -i target/vamp-core_0.7.6_all.deb`


Or add our vamp debian repo and install it from there

`echo "deb http://dl.bintray.com/magneticio/debian /" | tee -a /etc/apt/sources.list.d/vamp.list`

`apt-get update`

`apt-get install vamp-core`






