#Installing on Google VM Instance (Debian Jessie)

Here are some instructions to setup a Google VM instance (debian)

```bash

# enable https downloads
sudo apt-get install apt-transport-https

#add java
sudo yum install java haproxy


#add docker
sudo su -
cat >/etc/yum.repos.d/docker.repo <<-EOF
[dockerrepo]
name=Docker Repository
baseurl=https://yum.dockerproject.org/repo/main/centos/7
enabled=1
gpgcheck=1
gpgkey=https://yum.dockerproject.org/gpg
EOF
exit
sudo yum update
sudo yum install docker-engine
sudo service docker start

# add vamp repo
sudo curl -o /etc/yum.repos.d/bintray-magnetic-io-rpm.repo https://bintray.com/magnetic-io/rpm/rpm 


#install vamp
sudo yum install vamp-pulse vamp-cli vamp-core  vamp-router


#give vamp-core access to docker 
sudo usermod -aG docker vamp-core 
sudo service vamp-core restart



#install vamp-router
curl -L -o /tmp/vamp-router.zip https://bintray.com/artifact/download/magnetic-io/downloads/vamp-router/vamp-router_0.7.8_linux_amd64.zip
sudo apt-get install unzip
sudo unzip /tmp/vamp-router -d /usr/share/vamp-router/
sudo useradd -d /bin/false vamp-router



#run vamp-router non daemonized
cd /usr/share/vamp-router/
./vamp-router

```
