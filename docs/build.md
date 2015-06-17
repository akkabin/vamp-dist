# About building

Creating packages for multiple platforms is made possible through the use of the sbt native packager plugin.

For more details, see: http://www.scala-sbt.org/sbt-native-packager/gettingstarted.html

Lots of examples on how to use this plugin: https://github.com/muuki88/sbt-native-packager-examples


## Prerequisites

You need at least git, Java 8 & sbt installed on your machine, to create the universal zip packages.
For the docker image, you'll also need to have docker (boot2docker) installed.

The Linux packages can only be build on a machine with the correct package manager. 
In effect, you'll need a debian flavor machine to create .deb packages and a Red Hat flavor machine to create .rpm packages.

Instruction how to prepare your debian machine can be found [here](https://github.com/magneticio/vamp-dist/blob/master/docs/prepare-debian.md).
For Red Hat, use [these](https://github.com/magneticio/vamp-dist/blob/master/docs/prepare-redhat.md) instructions 


## Preparing the build

Start by cloning the vamp-dist repository  on the machine you'll use for building the package(s).

```bash
git clone https://github.com/magneticio/vamp-dist.git
cd vamp-dist
```

To create a distributables for an application, first `cd` to the application directory. The available directories are:
- cli
- core
- pulse
- router


To make sure everything is setup properly and have a clean build, start with

```bash
sbt clean test
```

Now continue with the creating the package(s)


### Creating universal packages

You can build any package using the command

```bash
sbt universal:packageBin
```

The package can be found in the `target/universal` directory and is typically named `vamp-<application>-<version>.zip`


### Creating debian packages

You can build any package using the command

```bash
sbt debian:packageBin
```

The package can be found in the `target/?????` directory and is typically named `vamp-<application>-<version>-all.deb`



### Creating rpm packages

You can build any package using the command

```bash
sbt rpm:packageBin
```

The package can be found in the `target/rpm/RPMS/noarch/` directory and is typically named `vamp-<application>-<version>.rpm`

### Creating docker images

** Warning** This has not been tested yet


```bash
sbt docker:publishLocal
```

## Publish

After successfully building a package, you might want to publish it.
Details on how to do this can be found [here](https://github.com/magneticio/vamp-dist/blob/master/docs/publish.md)





