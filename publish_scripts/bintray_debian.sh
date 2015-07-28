#!/usr/bin/env bash
# Check number of args
if [ "$#" -ne 8 ]; then
    SCRIPTNAME=$(basename "$0")
    echo "Usage: ${SCRIPTNAME} <repo> <package-name> <distributable> <sourcepath> <version> <distro> <component> <architecture>"
    exit 1
fi

# Check bintray credentials
: ${BINTRAY_USER:?"No BINTRAY_USER set"}
: ${BINTRAY_API_KEY:?"No BINTRAY_API_KEY set"}

REPO=$1             #upstart / systemv / systemd
PACKAGE=$2
DISTRIBUTABLE=$3
SOURCEPATH=$4        # target
VERSION=$5
DISTRO=$6            #trusty / wheezy / jessie
COMPONENT=$7         #main
ARCH=$8              #i386,amd64

: ${REPO:?"Not set"}
: ${PACKAGE:?"Not set"}
: ${DISTRIBUTABLE:?"Not set"}
: ${SOURCEPATH:?"Not set"}
: ${VERSION:?"Not set"}
: ${DISTRO:?"Not set"}
: ${COMPONENT:?"Not set"}
: ${ARCH:?"Not set"}



curl -v -T ${SOURCEPATH}/${DISTRIBUTABLE} \
  -u${BINTRAY_USER}:${BINTRAY_API_KEY} \
   https://api.bintray.com/content/plamola/${REPO}/pool/${COMPONENT}/v/${PACKAGE}/${DISTRIBUTABLE} \
   -H "X-Bintray-Package:${PACKAGE}" \
   -H "X-Bintray-Version:${VERSION}" \
   -H "X-Bintray-Publish:1" \
   -H "X-Bintray-Debian-Distribution:${DISTRO}" \
   -H "X-Bintray-Debian-Component:${COMPONENT}" \
   -H "X-Bintray-Debian-Architecture:${ARCH}"

   #https://api.bintray.com/content/magnetic-io/${REPO}/pool/vamp/v/${PACKAGE}/${DISTRIBUTABLE} \


