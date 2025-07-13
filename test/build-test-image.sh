#!/bin/bash

DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"

# Use mirror:
# DEBIAN_MIRROR=http://mirrors.aliyun.com $DIR/dubbo-test-runner/build.sh
# No mirror:
# $DIR/dubbo-test-runner/build.sh

DEBIAN_MIRROR=http://mirrors.aliyun.com $DIR/dubbo-test-runner/build.sh

$DIR/build-nacos-image.sh
