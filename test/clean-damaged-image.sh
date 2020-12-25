#!/bin/bash

DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"

echo "Killing dubbo containers .."
docker ps -a | grep dubbo | awk '{ print $1}' | xargs -I {} docker kill {}

echo "Removing dubbo containers .."
docker ps -a | grep dubbo | awk '{ print $1}' | xargs -I {} docker rm {}

#echo "Removing dubbo images .."
#docker images | grep dubbo | awk '{ print $3}' | xargs -I {} docker rmi {}

echo "Removing damaged images .."
docker image prune -f
