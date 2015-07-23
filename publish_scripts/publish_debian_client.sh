#!/usr/bin/env bash
set -e
# Check number of args
if [ "$#" -ne 2 ]; then
    SCRIPTNAME=$(basename "$0")
    echo "Usage: ${SCRIPTNAME} <directory-name> <package-name>"
    exit 1
fi

# Check bintray credentials
: ${BINTRAY_USER:?"No BINTRAY_USER set"}
: ${BINTRAY_API_KEY:?"No BINTRAY_API_KEY set"}

#arg 1 = directory
cd $1
#arg 2 = package
PACKAGE=$2

echo "*** Building ${PACKAGE} ***"

sbt debian:packageBin

DISTRIBUTABLE=`ls target/${PACKAGE}_*_all.deb | xargs -n1 basename`
VERSION=`echo ${DISTRIBUTABLE:${#PACKAGE}+1} | sed s/_all.deb//g`

: ${DISTRIBUTABLE:?"DISTRIBUTABLE not set"}
: ${VERSION:?"VERSION not set"}

echo "*** Publishing ${DISTRIBUTABLE}, version ${VERSION} ***"

../publish_scripts/bintray_debian.sh wheezy ${PACKAGE} ${DISTRIBUTABLE} target ${VERSION} upstart main i386,amd64
../publish_scripts/bintray_debian.sh jessie  ${PACKAGE} ${DISTRIBUTABLE} target ${VERSION} systemv main i386,amd64
../publish_scripts/bintray_debian.sh systemd ${PACKAGE} ${DISTRIBUTABLE} target ${VERSION} systemd main i386,amd64

cd ..