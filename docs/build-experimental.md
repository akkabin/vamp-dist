
## Setup your build street

You will need to create some docker images to build the debian & redhat packages.

```bash
docker build -t vamp-debian-builder dockerfiles/debian-builder
docker build -t vamp-redhat-builder dockerfiles/redhat-builder
```


## Build

To create all distributables for an application, first `cd` to the application directory. The available directories are:
- cli
- core
- pulse
- router

Once inside the directory, execute the following commands

```bash
sbt clean universal:packageBin
docker run -it -v $HOME/.sbt:/sbt -v $HOME/.ivy2:/ivy2 -v $PWD:/project vamp-debian-builder debian:packageBin
docker run -it -v $HOME/.sbt:/sbt -v $HOME/.ivy2:/ivy2 -v $PWD:/project vamp-redhat-builder rpm:packageBin
```

** WARNING ** The docker run commands will take quite some time to complete. A good time to get some coffee and maybe update some of the documentation or write some new tests.





## Applications
Currently, there are three applications supported: 

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




## Debian
Create your own Debian machine, by using the scripts in ansible/playbooks/vamp-debian-build.

First you need to setup your vagrant environment for this. See the `ansible/README.md`


### Build vamp-cli
`cd vamp-dist/cli`

`sbt debian:packageBin`

The .deb package can be found in the `target` directory




