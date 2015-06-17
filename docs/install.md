# Installation


## Debian

You can install a downloaded .deb file by issueing the command:

`dpkg -i target/vamp-cli_0.7.7_all.deb`


Or add our vamp debian repo and install it from there
```bash
echo "deb https://dl.bintray.com/magnetic-io/debian wheezy main" | sudo tee -a /etc/apt/sources.list
sudo apt-get update
sudo apt-get install vamp-cli
```

## Ubuntu

-- TODO --



## Red Hat / CentOS

### Build CLI
```bash
cd cli
sbt rpm:packageBin
```

### Build Core
```bash
cd core
sbt rpm:packageBin
```

### Build Pulse
```bash
cd pulse
sbt rpm:packageBin
```


## OSX


## Window 
