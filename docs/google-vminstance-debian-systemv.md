#Installing on Google VM Instance (Debian - Wheezy)

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
sudo curl -sSL https://get.docker.com/ | sudo sh

#install vamp
sudo apt-get install vamp-pulse vamp-cli vamp-core

#give vamp-core access to docker 
sudo usermod -aG docker vamp-core 
sudo service vamp-core restart


#install vamp-router
curl -L -o /tmp/vamp-router.zip https://bintray.com/artifact/download/magnetic-io/downloads/vamp-router/vamp-router_0.7.8_linux_amd64.zip
sudo apt-get install unzip haproxy
sudo unzip /tmp/vamp-router -d /usr/share/vamp-router/
sudo useradd -d /bin/false vamp-router

#run vamp-router non daemonized
cd /usr/share/vamp-router/
./vamp-router







```
