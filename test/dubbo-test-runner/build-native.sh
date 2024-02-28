#!/bin/bash

DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"

JAVA_VER=${JAVA_VER:-17}

DOCKER_DIR=$DIR/target/docker

echo "Building native dubbo-test-runner .."
echo "Java version: $JAVA_VER"
cd $DIR

mkdir -p $DOCKER_DIR
cp -r $DIR/src/native/* $DOCKER_DIR/

cd $DOCKER_DIR
docker build -t dubbo/native-sample-test:$JAVA_VER .  --build-arg JAVA_VER=$JAVA_VER
