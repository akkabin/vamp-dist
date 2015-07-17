#!/usr/bin/env bash
set -e

echo "Testing CLI"
cd cli
sbt clean test
cd ..

echo "Testing CORE"
cd core
sbt clean test
cd ..

echo "Testing PULSE"
cd pulse
sbt clean test
cd ..

echo "Done testing"
