# About building


## Prerequisites

You need at least Java 8 installed on your machine, to create the universal zip packages.
For the docker image, you'll need to have docker (boot2docker) installed.

The Linux packages can only be build on a machine with the correct package manager. 
In effect, you'll need a debian flavor machine to create .deb packages and a Red Hat flavor machine to create .rpm packages.

**Debian / Ubuntu**





## Preparing the build

Start by cloning the vamp-dist repository

```bash
git clone https://github.com/magneticio/vamp-dist.git
cd vamp-dist
```




To create a distributables for an application, first `cd` to the application directory. The available directories are:
- cli
- core
- pulse
- router


### Creating universal packages

```bash
sbt clean universal:packageBin
```


### Creating debian packages

After setting up your [debian build environment](https://github.com/magneticio/vamp-dist/blob/master/docs/prepare-debian.md) you can build any package using the command

```bash
cd <application>
sbt clean debian:packageBin
```

The package can be found in the `target/?????` directory



### Creating rpm packages

```bash
sbt clean rpm:packageBin
```

### Creating docker images

```bash
-- TODO --
```





## Applications
Currently, there are hree applications supported: 

- vamp-cli
- vamp-core
- vamp-pulse


Still under development:


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




