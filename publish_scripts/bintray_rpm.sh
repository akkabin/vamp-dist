#!/usr/bin/env bash
# Check number of args
if [ "$#" -ne 5 ]; then
    SCRIPTNAME=$(basename "$0")
    echo "Usage: ${SCRIPTNAME} <repo> <package-name> <distributable> <sourcepath> <version>"
    exit 1
fi

# Check bintray credentials
: ${BINTRAY_USER:?"No BINTRAY_USER set"}
: ${BINTRAY_API_KEY:?"No BINTRAY_API_KEY set"}

REPO=$1
PACKAGE=$2
DISTRIBUTABLE=$3
SOURCEPATH=$4        # target/rpm/RPMS/noarch
VERSION=$5


: ${REPO:?"Not set"}
: ${PACKAGE:?"Not set"}
: ${DISTRIBUTABLE:?"Not set"}
: ${SOURCEPATH:?"Not set"}
: ${VERSION:?"Not set"}

curl -v -T ${SOURCEPATH}/${DISTRIBUTABLE} \
     -u${BINTRAY_USER}:${BINTRAY_API_KEY} \
     -H "X-Bintray-Publish:1" \
     https://api.bintray.com/content/magnetic-io/${REPO}/${PACKAGE}/${VERSION}/${DISTRIBUTABLE}