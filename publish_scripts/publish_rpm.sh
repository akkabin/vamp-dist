#!/usr/bin/env bash
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

sbt rpm:packageBin

DISTRIBUTABLE=`ls target/rpm/RPMS/noarch${PACKAGE}-*.rpm | xargs -n1 basename`
VERSION=`echo ${DISTRIBUTABLE:${#PACKAGE}+1} | sed s/-1.noarch.rpm//g`

: ${DISTRIBUTABLE:?"DISTRIBUTABLE not set"}
: ${VERSION:?"VERSION not set"}

echo "*** Publishing ${DISTRIBUTABLE}, version ${VERSION} ***"

../publish_scripts/bintray_rpm.sh ${PACKAGE} ${DISTRIBUTABLE} target/rpm/RPMS/noarch ${VERSION}

cd ..
