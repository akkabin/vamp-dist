## Prerequisites

Before installing Vamp, make sure your system has the required software installed.

### Java 8
If Java 8 is not installed, please do so before continuing.
To check your Java version, type: `java -version`

For detail on how to install Java 8, check the following page: http://www.webupd8.org/2014/03/how-to-install-oracle-java-8-in-debian.html

### Docker | Marathon Cluster

You either will need to have Docker install, or have access to a Marathon Cluster.

The default Vamp install uses a Docker installation.

To install docker, run `sudo apt-get install dockerio`

After installing docker, you need to start it `sudo service docker start` and tell it to start after a reboot `sudo chkconfig docker on`


## Add the Vamp repository

Ubuntu users can add our Wheezy repo
```bash
echo "deb https://dl.bintray.com/magnetic-io/debian wheezy main" | sudo tee -a /etc/apt/sources.list
sudo apt-get update
 ```


Debian users should use our Jessie repo
```bash
echo "deb https://dl.bintray.com/magnetic-io/debian jessie main" | sudo tee -a /etc/apt/sources.list
sudo apt-get update
```

## Install Core

```bash
sudo apt-get install vamp-core
```

Check the `application.conf` file at `/usr/share/vamp-core/conf/` and change when needed.

Start the application with the command:

```bash
sudo service vamp-core start
```


## Install Cli

```bash
sudo apt-get install vamp-cli
```

Type `vamp version` to check if Vamp Cli has been properly installed.