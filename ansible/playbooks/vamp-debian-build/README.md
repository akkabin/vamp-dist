
# Preparing your environment


### vagrant-hostsupdater
Make sure to install the vagrant-hostsupdater:

`vagrant plugin install vagrant-hostsupdater`

### Update .ssh/config

Add the following section to the ~/.ssh/config

```
# For vagrant virtual machines
Host 192.168.35.* *.vamp.dev
  StrictHostKeyChecking no
  UserKnownHostsFile=/dev/null
  User root
  LogLevel ERROR
```

### Usefull links
[6 practices for super smooth Ansible experience](http://hakunin.com/six-ansible-practices)