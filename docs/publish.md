# Publish

After creating a package, you might want to publish it to either Bintray, or Docker Hub.

When you push a package to bintray, using the REST api, you'll still need to 'publish' them on Bintray, via the website.


## Universal

To publish the .zip files on Bintray, you can use one of the following commands, for either core, cli or pulse:

```bash
curl -T target/universal/vamp-core-<version>.zip -u<bintray-user>:<bintray-key> https://api.bintray.com/content/magnetic-io/downloads/vamp-core/vamp-core-<version>.zip -H "X-Bintray-Package:vamp-core" -H "X-Bintray-Version:<version>"
curl -T target/universal/vamp-cli-<version>.zip -u<bintray-user>:<bintray-key> https://api.bintray.com/content/magnetic-io/downloads/vamp-cli/vamp-cli-<version>.zip  -H "X-Bintray-Package:vamp-cli" -H "X-Bintray-Version:<version>"
curl -T target/universal/vamp-pulse-<version>.zip -u<bintray-user>:<bintray-key> https://api.bintray.com/content/magnetic-io/downloads/vamp-pulse/vamp-pulse-<version>.zip -H "X-Bintray-Package:vamp-pulse" -H "X-Bintray-Version:<version>"
```

Replaces all three `<version>` tags with the actual version tag (e.g. 0.7.7) and the `<bintray-user>` and `<bintray-key>` tags with your Bintray credentials.

For example:
```bash
curl -T target/universal/vamp-cli-0.7.7.zip -uplamola:1111222233334444555555666 https://api.bintray.com/content/magnetic-io/downloads/vamp-cli/0.7.7/vamp-cli/vamp-cli-0.7.7.zip
```


## Debian

Since Debian & Ubuntu use different initialisation managers, the daemonized applications (core, pulse & router) require multiple packages, one for systemv & one for upstart.
The systemv packages will be published under `jessie`, while the upstart packages will be published under `wheezy`.

For vamp-cli, a single package will be build, but it must be published to both distributions.


### CLI

To publish the .deb packages on Bintray, you can use the following commands:

```bash
curl -T target/vamp-cli-<version>-all.deb -u:<bintray-user>:<bintray-key> https://api.bintray.com/content/magnetic-io/debian/pool/vamp/v/vamp-cli/vamp-cli-<version>-all.deb -H "X-Bintray-Package:vamp-cli" -H "X-Bintray-Version:<version>" -H "X-Bintray-Debian-Distribution: jessie,wheezy" -H "X-Bintray-Debian-Component: main" -H "X-Bintray-Debian-Architecture: i386,amd64"
```

Replaces all three `<version>` tags with the actual version tag (e.g. 0.7.7) and the `<bintray-user>` and `<bintray-key>` tags with your Bintray credentials.


### Core

For Core you'll have to publish multiple packages, due to the different initialisation managers.
```bash
curl -T package/upstart/vamp-core-<version>.deb -u<bintray-user>:<bintray-key> https://api.bintray.com/content/magnetic-io/debian/pool/vamp/v/vamp-core/vamp-core-<version>_upstart.deb -H "X-Bintray-Package:vamp-core" -H "X-Bintray-Version:<version>" -H "X-Bintray-Debian-Distribution: wheezy" -H "X-Bintray-Debian-Component: main" -H "X-Bintray-Debian-Architecture: i386,amd64"
curl -T package/systemv/vamp-core-<version>.deb -u<bintray-user>:<bintray-key> https://api.bintray.com/content/magnetic-io/debian/pool/vamp/v/vamp-core/vamp-core-<version>_systemv.deb -H "X-Bintray-Package:vamp-core" -H "X-Bintray-Version:<version>" -H "X-Bintray-Debian-Distribution: jessie" -H "X-Bintray-Debian-Component: main" -H "X-Bintray-Debian-Architecture: i386,amd64"
```
Replaces all three `<version>` tags with the actual version tag (e.g. 0.7.7) and the `<bintray-user>` and `<bintray-key>` tags with your Bintray credentials.


### Pulse

For Pulse you'll have to publish multiple packages, due to the different initialisation managers.
```bash
curl -T package/upstart/vamp-pulse-<version>.deb -u<bintray-user>:<bintray-key> https://api.bintray.com/content/magnetic-io/debian/pool/vamp/v/vamp-pulse/vamp-pulse-<version>_upstart.deb -H "X-Bintray-Package:vamp-pulse" -H "X-Bintray-Version:<version>" -H "X-Bintray-Debian-Distribution: wheezy" -H "X-Bintray-Debian-Component: main" -H "X-Bintray-Debian-Architecture: i386,amd64"
curl -T package/systemv/vamp-pulse-<version>.deb -u<bintray-user>:<bintray-key> https://api.bintray.com/content/magnetic-io/debian/pool/vamp/v/vamp-pulse/vamp-pulse-<version>_systemv.deb -H "X-Bintray-Package:vamp-pulse" -H "X-Bintray-Version:<version>" -H "X-Bintray-Debian-Distribution: jessie" -H "X-Bintray-Debian-Component: main" -H "X-Bintray-Debian-Architecture: i386,amd64"
```

Replaces all three `<version>` tags with the actual version tag (e.g. 0.7.7) and the `<bintray-user>` and `<bintray-key>` tags with your Bintray credentials.


### Router

For Router, 4 packages have been created. For each architecture (i386, amd64) an upstart & systemv version.
```bash
curl -T package/upstart/vamp-router-<version>_i386.deb -u<bintray-user>:<bintray-key> https://api.bintray.com/content/magnetic-io/debian/pool/vamp/v/vamp-router/vamp-router-<version>_upstart_i386.deb -H "X-Bintray-Package:vamp-router" -H "X-Bintray-Version:<version>" -H "X-Bintray-Debian-Distribution: wheezy" -H "X-Bintray-Debian-Component: main" -H "X-Bintray-Debian-Architecture: i386"
curl -T package/systemv/vamp-router-<version>_i386.deb -u<bintray-user>:<bintray-key> https://api.bintray.com/content/magnetic-io/debian/pool/vamp/v/vamp-router/vamp-router-<version>_systemv_i386.deb -H "X-Bintray-Package:vamp-router" -H "X-Bintray-Version:<version>" -H "X-Bintray-Debian-Distribution: jessie" -H "X-Bintray-Debian-Component: main" -H "X-Bintray-Debian-Architecture: i386"
curl -T package/upstart/vamp-router-<version>_amd64.deb -<bintray-user>:<bintray-key> https://api.bintray.com/content/magnetic-io/debian/pool/vamp/v/vamp-router/vamp-router-<version>_upstart_amd64.deb -H "X-Bintray-Package:vamp-router" -H "X-Bintray-Version:<version>" -H "X-Bintray-Debian-Distribution: wheezy" -H "X-Bintray-Debian-Component: main" -H "X-Bintray-Debian-Architecture: amd64"
curl -T package/systemv/vamp-router-<version>_amd64.deb -<bintray-user>:<bintray-key> https://api.bintray.com/content/magnetic-io/debian/pool/vamp/v/vamp-router/vamp-router-<version>_systemv_amd64.deb -H "X-Bintray-Package:vamp-router" -H "X-Bintray-Version:<version>" -H "X-Bintray-Debian-Distribution: jessie" -H "X-Bintray-Debian-Component: main" -H "X-Bintray-Debian-Architecture: amd64"
```

Replaces all three `<version>` tags with the actual version tag (e.g. 0.7.7) and the `<bintray-user>` and `<bintray-key>` tags with your Bintray credentials.


## RPM

To publish the .rpm packages on bintray, you can use one of the following commands, for either core, cli or pulse:

```bash
curl -T target/rpm/RPMS/noarch/vamp-core-<version>-1.noarch.rpm -u<bintray-user>:<bintray-key> https://api.bintray.com/content/magnetic-io/rpm/vamp-core/<version>/vamp-core-<version>-1.noarch.rpm
curl -T target/rpm/RPMS/noarch/vamp-cli-<version>-1.noarch.rpm -u<bintray-user>:<bintray-key> https://api.bintray.com/content/magnetic-io/rpm/vamp-cli/<version>/vamp-cli-<version>-1.noarch.rpm
curl -T target/rpm/RPMS/noarch/vamp-pulse-<version>-1.noarch.rpm -u<bintray-user>:<bintray-key> https://api.bintray.com/content/magnetic-io/rpm/vamp-pulse/<version>/vamp-pulse-<version>-1.noarch.rpm
```

**TODO**: Add vamp-router

Replaces all 3 `<version>` tags with the actual version tag (e.g. 0.7.7) and the `<bintray-user>` and `<bintray-key>` tags with your Bintray credentials.


## Docker

-- TODO --
