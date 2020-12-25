#!/bin/bash

DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"

DOCKER_DIR=$DIR/target/docker

mvn clean package
result=$?
if [ $result -ne 0 ]; then
  echo "Build dubbo-test-runner failure"
  exit $result
fi

mkdir -p $DOCKER_DIR
cp -r $DIR/src/docker/* $DOCKER_DIR/
cp $DIR/target/dubbo-test-runner-*-jar-with-dependencies.jar $DOCKER_DIR/

cd $DOCKER_DIR
docker build -t dubbo/sample-test .
