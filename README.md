# vamp-dist

This project contains the definitions for distributing the different flavors in which we ship Vamp.

To build for all different platforms, you'll need to have access to different (virtual) machines.


## Applications
Currently, there are two applications supported: 

- vamp-cli
- vamp-core

Still under development:

- vamp-pulse
- vamp-router


## Supported packages

The following packaging commands are supported through sbt:

[Tested]

- universal:packageZipTarball
- debian:packageBin
- rpm:packageBin

[To be developed / tested]
- universal:packageOsxDmg
- docker:publishLocal


If a specific command can be run successful depends on the platform you are using.
For Linux packages, a Linux machine is required; for the OS X, a Mac is needed.

For more details, see: http://www.scala-sbt.org/sbt-native-packager/gettingstarted.html
Lots of examples on how to use this plugin: https://github.com/muuki88/sbt-native-packager-examples


## Debian
Create your own Debian machine, by using the scripts in ansible/playbooks/vamp-debian-build.

First you need to setup your vagrant environment for this. See the `ansible/README.md`


### Build vamp-cli
`cd vamp-dist/cli`

`sbt debian:packageBin`

The .deb package can be found in the `target` directory


## Installing
You can install a downloaded .deb file by issueing the command:

`dpkg -i target/vamp-cli_0.7.7_all.deb`


Or add our vamp debian repo and install it from there
```bash
echo "deb https://dl.bintray.com/magnetic-io/debian wheezy main" | sudo tee -a /etc/apt/sources.list
sudo apt-get update
sudo apt-get install vamp-cli
```





