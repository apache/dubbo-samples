#!/bin/bash

# Note: modify follow comment when need refresh github actions docker layer caching
# Docker layer caching updated: 2020.12.23

DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"

$DIR/dubbo-test-runner/build.sh