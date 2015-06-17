# vamp-dist

This project contains the definitions for distributing the different packages of Vamp.

We ship Vamp in 4 flavors:

- universal zip, 
  the zip file contains a fat jar, scripts to run it on Linux (OS X) and Windows & configuration files
- .deb, 
  for Debian based Linux systems (Debian / Ubuntu)
- .rpm, 
  for the RPM based Linux systems (Red Hat / CentOS)
- ~~Docker images, 
  published on Docker hub~~ Coming soon

Vamp consists of 4 packages:

- Core
- Pulse
- Router
- Cli

Read more about how to create the different packages on our [build page](https://github.com/magneticio/vamp-dist/blob/master/docs//build.md)

Instructions on how to install the packages on each platform cna be found at our [installation page](https://github.com/magneticio/vamp-dist/blob/master/docs/install.md)






