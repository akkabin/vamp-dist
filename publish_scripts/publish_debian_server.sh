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

sbt packageDebianAll

# publish upstart version
DISTRIBUTABLE=`ls package/upstart/${PACKAGE}-*.deb | xargs -n1 basename`
VERSION=`echo ${DISTRIBUTABLE:${#PACKAGE}+1} | sed s/.deb//g`

: ${DISTRIBUTABLE:?"not set"}
: ${VERSION:?"not set"}

echo "*** Publishing ${DISTRIBUTABLE} [version ${VERSION}, upstart] ***"

../publish_scripts/bintray_debian.sh upstart ${PACKAGE} ${DISTRIBUTABLE} package/upstart ${VERSION} wheezy main i386,amd64


# publish systemv version
DISTRIBUTABLE=`ls package/systemv/${PACKAGE}-*.deb | xargs -n1 basename`
VERSION=`echo ${DISTRIBUTABLE:${#PACKAGE}+1} | sed s/.deb//g`

: ${DISTRIBUTABLE:?"not set"}
: ${VERSION:?"not set"}

echo "*** Publishing ${DISTRIBUTABLE} [version ${VERSION}, systemv] ***"

../publish_scripts/bintray_debian.sh systemd ${PACKAGE} ${DISTRIBUTABLE}  package/systemv ${VERSION} jessie main i386,amd64


cd ..