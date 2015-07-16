#!/usr/bin/env bash
# Check number of args
if [ "$#" -ne 7 ]; then
    SCRIPTNAME=$(basename "$0")
    echo "Usage: ${SCRIPTNAME} <package-name> <distributable> <sourcepath> <version> <distro> <component> <architecture>"
    exit 1
fi

# Check bintray credentials
: ${BINTRAY_USER:?"No BINTRAY_USER set"}
: ${BINTRAY_API_KEY:?"No BINTRAY_API_KEY set"}

PACKAGE=$1
DISTRIBUTABLE=$2
SOURCEPATH=$3        # target
VERSION=$4
DISTRO=$5            #jessie,wheezy
COMPONENT=$6         #main
ARCH=$7              #i386,amd64

: ${PACKAGE:?"Not set"}
: ${DISTRIBUTABLE:?"Not set"}
: ${SOURCEPATH:?"Not set"}
: ${VERSION:?"Not set"}
: ${DISTRO:?"Not set"}
: ${COMPONENT:?"Not set"}
: ${ARCH:?"Not set"}



curl -v -T ${SOURCEPATH}/${DISTRIBUTABLE} \
  -u${BINTRAY_USER}:${BINTRAY_API_KEY} \
   https://api.bintray.com/content/magnetic-io/debian/pool/vamp/v/${PACKAGE}/${DISTRIBUTABLE}
   -H "X-Bintray-Package:${PACKAGE}" \
   -H "X-Bintray-Version:${VERSION}" \
   -H "X-Bintray-Publish:1" \
   -H "X-Bintray-Debian-Distribution:${DISTRO}" \
   -H "X-Bintray-Debian-Component:${COMPONENT}" \
   -H "X-Bintray-Debian-Architecture:${ARCH}"