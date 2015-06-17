
## Setup your build street

You will need to create some docker images to build the debian & red hat packages.

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
docker run -it -v $HOME/.sbt:/sbt -v $HOME/.ivy2:/ivy2 -v $PWD:/project vamp-debian-builder packageDebianAll
docker run -it -v $HOME/.sbt:/sbt -v $HOME/.ivy2:/ivy2 -v $PWD:/project vamp-redhat-builder rpm:packageBin
```

** WARNING ** The docker run commands will take quite some time to complete. A good time to get some coffee and maybe update some of the documentation or write some new tests.




