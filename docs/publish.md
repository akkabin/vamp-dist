# Publish

After creating a package, you might want to publish it to either Bintray, or Docker Hub.


## Universal

To publish the .zip files on bintray, you can use one of the following commands, for either core, cli or pulse:

```bash
curl -T target/universal/vamp-core-<version>.zip -u<bintray-user>:<bintray-key> https://api.bintray.com/content/magnetic-io/downloads/vamp-core/<version>/vamp-core
curl -T target/universal/vamp-cli-<version>.zip -u<bintray-user>:<bintray-key> https://api.bintray.com/content/magnetic-io/downloads/vamp-cli/<version>/vamp-cli
curl -T target/universal/vamp-pulse-<version>.zip -u<bintray-user>:<bintray-key> https://api.bintray.com/content/magnetic-io/downloads/vamp-pulse/<version>/vamp-pulse
```

Replaces both `<version>` tags with the actual version tag (e.g. 0.7.7) and the `<bintray-user>` and `<bintray-key>` tags with your bintray credentials.

## Debian

-- TODO --


## RPM

To publish the .rpm packages on bintray, you can use one of the following commands, for either core, cli or pulse:

```bash
curl -T target/rpm/RPMS/noarch/vamp-core-<version>-1.noarch.rpm -u<bintray-user>:<bintray-key> https://api.bintray.com/content/magnetic-io/rpm/vamp-core/<version>/vamp-core-<version>-1.noarch.rpm
curl -T target/rpm/RPMS/noarch/vamp-cli-<version>-1.noarch.rpm -u<bintray-user>:<bintray-key> https://api.bintray.com/content/magnetic-io/rpm/vamp-cli/<version>/vamp-cli-<version>-1.noarch.rpm
curl -T target/rpm/RPMS/noarch/vamp-pulse-<version>-1.noarch.rpm -u<bintray-user>:<bintray-key> https://api.bintray.com/content/magnetic-io/rpm/vamp-pulse/<version>/vamp-pulse-<version>-1.noarch.rpm
```

Replaces all 3 `<version>` tags with the actual version tag (e.g. 0.7.7) and the `<bintray-user>` and `<bintray-key>` tags with your bintray credentials.


## Docker

-- TODO --