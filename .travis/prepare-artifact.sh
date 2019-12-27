#! /usr/bin/env bash
env

if [[ -z "${RELEASE_BRANCH}" ]]; then
  export INTERGARTION_TEST_VERSION=2.7.6-SNAPSHOT
else
  git clone https://github.com/apache/dubbo.git
  cd dubbo
  git checkout ${RELEASE_BRANCH}
  ./mwnw clean install -DskipTests -Drevision=${RELEASE_BRANCH}
  export INTERGARTION_TEST_VERSION=${RELEASE_BRANCH}
fi

env|grep INTERGARTION_TEST_VERSION