#!/usr/bin/env bash
# Check number of args
if [ "$#" -ne 2 ]; then
    echo "Usage: $0 <directory-name> <package-name>"
    exit 1
fi

# Check bintray credentials
: ${BINTRAY_USER:?"No BINTRAY_USER set"}
: ${BINTRAY_API_KEY:?"No BINTRAY_API_KEY set"}

#arg 1 = directory
cd $1
#arg 2 = package
PACKAGE=$2

echo *** Building ${PACKAGE} ***

sbt packageDebianAll

# publish upstart version
DISTRIBUTABLE=`ls package/upstart/${PACKAGE}-*.deb | xargs -n1 basename`
VERSION=`echo ${DISTRIBUTABLE:${#PACKAGE}+1}} | sed s/.deb//g`

: ${DISTRIBUTABLE:?"not set"}
: ${VERSION:?"not set"}

echo *** Publishing ${DISTRIBUTABLE} [version ${VERSION}, upstart] ***

#curl -v -T package/upstart/${DISTRIBUTABLE} \
#  -u${BINTRAY_USER}:${BINTRAY_API_KEY}  \
#  https://api.bintray.com/content/magnetic-io/debian/pool/vamp/v/${PACKAGE}/${PACKAGE}-${VERSION}_upstart.deb \
#  -H "X-Bintray-Package:${PACKAGE}" \
#  -H "X-Bintray-Version:${VERSION}" \
#  -H "X-Bintray-Publish:1" \
#  -H "X-Bintray-Debian-Distribution:wheezy" \
#  -H "X-Bintray-Debian-Component:main" \
#  -H "X-Bintray-Debian-Architecture:i386,amd64"

# publish systemv version
DISTRIBUTABLE=`ls package/systemv/${PACKAGE}-*.deb | xargs -n1 basename`
VERSION=`echo ${DISTRIBUTABLE:${#PACKAGE}+1} | sed s/.deb//g`

: ${DISTRIBUTABLE:?"not set"}
: ${VERSION:?"not set"}

echo *** Publishing ${DISTRIBUTABLE} [version ${VERSION}, systemv] ***

#curl -v -T package/systemv/${DISTRIBUTABLE} \
#  -u${BINTRAY_USER}:${BINTRAY_API_KEY} \
#  https://api.bintray.com/content/magnetic-io/debian/pool/vamp/v/${PACKAGE}/${PACKAGE}-${VERSION}_systemv.deb \
#  -H "X-Bintray-Package:${PACKAGE}" \
#  -H "X-Bintray-Version:${VERSION}" \
#  -H "X-Bintray-Publish:1" \
#  -H "X-Bintray-Debian-Distribution:jessie" \
#  -H "X-Bintray-Debian-Component:main" \
#  -H "X-Bintray-Debian-Architecture:i386,amd64"

cd ..