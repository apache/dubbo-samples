#! /usr/bin/env bash
env

if [[ -z "${RELEASE_BRANCH}" ]]; then
  export INTERGARTION_TEST_VERSION=2.7.8-SNAPSHOT
else
  git clone https://github.com/apache/dubbo.git
  cd dubbo
  git checkout ${RELEASE_BRANCH}
  ls
  ./mvnw clean install -DskipTests -Drevision=${RELEASE_BRANCH}
  export INTERGARTION_TEST_VERSION=${RELEASE_BRANCH}
  cd ..
fi

env|grep INTERGARTION_TEST_VERSION
