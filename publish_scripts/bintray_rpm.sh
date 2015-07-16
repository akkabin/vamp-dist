#!/usr/bin/env bash
# Check number of args
if [ "$#" -ne 4 ]; then
    SCRIPTNAME=$(basename "$0")
    echo "Usage: ${SCRIPTNAME} <package-name> <distributable> <sourcepath> <version>"
    exit 1
fi

# Check bintray credentials
: ${BINTRAY_USER:?"No BINTRAY_USER set"}
: ${BINTRAY_API_KEY:?"No BINTRAY_API_KEY set"}

PACKAGE=$1
DISTRIBUTABLE=$2
SOURCEPATH=$3        # target/rpm/RPMS/noarch
VERSION=$4


: ${PACKAGE:?"Not set"}
: ${DISTRIBUTABLE:?"Not set"}
: ${SOURCEPATH:?"Not set"}
: ${VERSION:?"Not set"}

curl -v -T ${SOURCEPATH}/${DISTRIBUTABLE} \
     -u${BINTRAY_USER}:${BINTRAY_API_KEY} \
     https://api.bintray.com/content/magnetic-io/rpm/${PACKAGE}/${VERSION}/${DISTRIBUTABLE}