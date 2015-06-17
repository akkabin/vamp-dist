# Your own Debian machine to build Vamp distributables


## Build

Perform the following commands:

`vagrant up`

`ansible-playbook --private-key=~/.vagrant.d/insecure_private_key  -u vagrant debian-vamp.yml`


# Access

You can access the machine using either

 `vagrant ssh`

or

 `ssh debian.vamp.dev`


## Remove

To completely remove this machine, just run:

`vagrant destroy`