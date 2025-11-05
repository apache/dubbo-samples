#!/bin/bash

DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"

DEBIAN_MIRROR=http://mirrors.aliyun.com $DIR/dubbo-test-runner/build.sh