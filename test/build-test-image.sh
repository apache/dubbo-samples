#!/bin/bash

# Use mirror:
# DEBIAN_MIRROR=http://mirrors.aliyun.com ./build-test-image.sh


DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"

$DIR/dubbo-test-runner/build.sh

docker build -t nacos-mysql:5.7 nacos-mysql/

docker pull busybox
