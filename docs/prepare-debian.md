# How to build on Debian



## Add sbt repo + install
```bash
echo "deb http://dl.bintray.com/sbt/debian /" | tee -a /etc/apt/sources.list.d/sbt.list
apt-get update
apt-get install sbt
```

# Add git & fakeroot
```bash
apt-get install git fakeroot
```