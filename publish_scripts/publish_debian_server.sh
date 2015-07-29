#!/usr/bin/env bash
set -e
# Check number of args
if [ "$#" -ne 3 ]; then
    SCRIPTNAME=$(basename "$0")
    echo "Usage: ${SCRIPTNAME} <directory-name> <package-name> <architecture>"
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

sbt packageDebianAll

# publish upstart version
DISTRIBUTABLE=`ls package/upstart/${PACKAGE}-*.deb | xargs -n1 basename`
VERSION=`echo ${DISTRIBUTABLE:${#PACKAGE}+1} | sed s/\_.*//`

: ${DISTRIBUTABLE:?"not set"}
: ${VERSION:?"not set"}

echo "*** Publishing ${DISTRIBUTABLE} [version ${VERSION}, upstart] ***"

../publish_scripts/bintray_debian.sh upstart ${PACKAGE} ${DISTRIBUTABLE} package/upstart ${VERSION} trusty main ${ARCH}


# publish systemv version
DISTRIBUTABLE=`ls package/systemv/${PACKAGE}-*.deb | xargs -n1 basename`
VERSION=`echo ${DISTRIBUTABLE:${#PACKAGE}+1} | sed s/\_.*//`

: ${DISTRIBUTABLE:?"not set"}
: ${VERSION:?"not set"}

echo "*** Publishing ${DISTRIBUTABLE} [version ${VERSION}, systemv] ***"

../publish_scripts/bintray_debian.sh  systemv ${PACKAGE} ${DISTRIBUTABLE}  package/systemv ${VERSION} wheezy main ${ARCH}

# publish systemv version
DISTRIBUTABLE=`ls package/systemd/${PACKAGE}-*.deb | xargs -n1 basename`
VERSION=`echo ${DISTRIBUTABLE:${#PACKAGE}+1} | sed s/\_.*//g`

: ${DISTRIBUTABLE:?"not set"}
: ${VERSION:?"not set"}

echo "*** Publishing ${DISTRIBUTABLE} [version ${VERSION}, systemd] ***"

../publish_scripts/bintray_debian.sh systemd ${PACKAGE} ${DISTRIBUTABLE}  package/systemd ${VERSION} jessie main ${ARCH}


cd ..