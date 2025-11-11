#!/bin/bash

DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"

JAVA_VER=${JAVA_VER:-8}

DOCKER_DIR=$DIR/target/docker

echo "Building dubbo-test-runner .."
echo "Java version: $JAVA_VER"
cd $DIR
mvn clean package -DskipTests
result=$?
if [ $result -ne 0 ]; then
  echo "Build dubbo-test-runner failure"
  exit $result
fi

mkdir -p $DOCKER_DIR
cp -r $DIR/src/docker/* $DOCKER_DIR/
cp $DIR/target/dubbo-test-runner-*-jar-with-dependencies.jar $DOCKER_DIR/dubbo-test-runner.jar

cd $DOCKER_DIR
wget https://repo1.maven.org/maven2/org/jctools/jctools-core/4.0.5/jctools-core-4.0.5.jar -O jctools-core.jar
docker build -t dubbo/sample-test:$JAVA_VER . --build-arg DEBIAN_MIRROR=$DEBIAN_MIRROR  --build-arg JAVA_VER=$JAVA_VER
