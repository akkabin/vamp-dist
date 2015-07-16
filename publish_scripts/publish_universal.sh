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

sbt clean test universal:packageBin

pwd

DISTRIBUTABLE=`ls target/universal/${PACKAGE}-*.zip | xargs -n1 basename`
VERSION=`echo ${DISTRIBUTABLE:${#PACKAGE}+1} | sed s/.zip//g`

: ${DISTRIBUTABLE:?"DISTRIBUTABLE not set"}
: ${VERSION:?"VERSION not set"}

echo *** Publishing ${DISTRIBUTABLE}, version ${VERSION} ***

# curl -v -T target/universal/${DISTRIBUTABLE} \
# -u${BINTRAY_USER}:${BINTRAY_API_KEY} \
# -H "X-Bintray-Package:${PACKAGE}" \
# -H "X-Bintray-Version:${VERSION}" \
# -H "X-Bintray-Publish:1" \
# https://api.bintray.com/content/magnetic-io/downloads/${PACKAGE}/${DISTRIBUTABLE}

cd ..