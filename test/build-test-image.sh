#!/bin/bash

# Note: modify follow comment when need refresh github actions docker layer caching
# dubbo/sample-test updated: 2020.12.27

# Usages:
# DEBIAN_MIRROR=http://mirrors.aliyun.com ./build-test-image.sh

DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"

$DIR/dubbo-test-runner/build.sh