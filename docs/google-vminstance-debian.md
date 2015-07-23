#Installing on Google VM Instance (Debian)

Here are some instructions to setup a Google VM instance (debian)

```bash

# enable https downloads
sudo apt-get install apt-transport-https

# add vamp repo
sudo echo "deb https://dl.bintray.com/plamola/jessie systemv main" | sudo tee -a /etc/apt/sources.list

#add java
sudo echo "deb http://ppa.launchpad.net/webupd8team/java/ubuntu trusty main" | sudo tee /etc/apt/sources.list.d/webupd8team-java.list
sudo echo "deb-src http://ppa.launchpad.net/webupd8team/java/ubuntu trusty main" | sudo tee -a /etc/apt/sources.list.d/webupd8team-java.list
sudo apt-key adv --keyserver hkp://keyserver.ubuntu.com:80 --recv-keys EEA14886
sudo apt-get update
sudo apt-get install oracle-java8-installer

#add docker
sudo apt-get install docker.io

#install vamp
sudo apt-get install vamp-pulse vamp-cli vamp-core

```
