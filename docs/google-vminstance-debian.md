#Installing on Google VM Instance (Debian)

Here are some instructions to setup a Google VM instance (debian)

```bash

# enable https downloads
sudo apt-get install apt-transport-https

# add vamp repo
sudo echo "deb https://dl.bintray.com/plamola/systemd systemd main" | sudo tee -a /etc/apt/sources.list

#add java
sudo echo "deb http://ppa.launchpad.net/webupd8team/java/ubuntu trusty main" | sudo tee /etc/apt/sources.list.d/webupd8team-java.list
sudo echo "deb-src http://ppa.launchpad.net/webupd8team/java/ubuntu trusty main" | sudo tee -a /etc/apt/sources.list.d/webupd8team-java.list
sudo apt-key adv --keyserver hkp://keyserver.ubuntu.com:80 --recv-keys EEA14886
sudo apt-get update
sudo apt-get install oracle-java8-installer

#add docker
sudo -E sh -c apt-key adv --keyserver hkp://p80.pool.sks-keyservers.net:80 --recv-keys 58118E89F3A912897C070ADBF76221572C52609D
sudo -E sh -c mkdir -p /etc/apt/sources.list.d
sudo -E sh -c echo deb https://apt.dockerproject.org/repo debian-jessie main > /etc/apt/sources.list.d/docker.list
sudo -E sh -c sleep 3; apt-get update; apt-get install -y -q docker-engine

#install vamp
sudo apt-get install vamp-pulse vamp-cli vamp-core


#give vamp-core access to docker 
sudo usermod -aG docker vamp-core 
sudo service vamp-core restart


#start vamp-router
sudo docker run --net=host -d magneticio/vamp-router:0.7.8






```
