#!/usr/bin/env bash
set -e
# Check number of args
if [ "$#" -ne 3 ]; then
    SCRIPTNAME=$(basename "$0")
    echo "Usage: ${SCRIPTNAME} <directory-name> <package-name> <architecture> "
    exit 1
fi

# Check bintray credentials
: ${BINTRAY_USER:?"No BINTRAY_USER set"}
: ${BINTRAY_API_KEY:?"No BINTRAY_API_KEY set"}

#arg 1 = directory
cd $1
#arg 2 = package
PACKAGE=$2
#arg 3 = architecture
ARCH=$3


echo "*** Building ${PACKAGE} ***"

sbt packageRpmAll

echo "*** Publishing ${DISTRIBUTABLE}, version ${VERSION} to repo: rpm-upstart ***"

DISTRIBUTABLE=`ls package/upstart/${PACKAGE}-*.rpm | xargs -n1 basename`
VERSION=`echo ${DISTRIBUTABLE:${#PACKAGE}+1} | sed s/-1.${ARCH}.rpm//g`

: ${DISTRIBUTABLE:?"DISTRIBUTABLE not set"}
: ${VERSION:?"VERSION not set"}

../publish_scripts/bintray_rpm.sh rpm-upstart ${PACKAGE} ${DISTRIBUTABLE} target/rpm/RPMS/${ARCH} ${VERSION}


echo "*** Publishing ${DISTRIBUTABLE}, version ${VERSION} to repo: rpm ***"

DISTRIBUTABLE=`ls package/systemd/${PACKAGE}-*.rpm | xargs -n1 basename`
VERSION=`echo ${DISTRIBUTABLE:${#PACKAGE}+1} | sed s/-1.${ARCH}.rpm//g`

: ${DISTRIBUTABLE:?"DISTRIBUTABLE not set"}
: ${VERSION:?"VERSION not set"}

../publish_scripts/bintray_rpm.sh rpm ${PACKAGE} ${DISTRIBUTABLE} target/rpm/RPMS/${ARCH} ${VERSION}

cd ..
