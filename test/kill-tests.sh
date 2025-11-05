#!/bin/bash

DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"

echo "Killing run-tests.sh .."
ps -ef | grep run-tests.sh | grep -v grep  | awk '{ print $2 }' | xargs -I {} kill {}

echo "Killing scenario.sh .."
ps -ef | grep scenario.sh | grep -v grep  | awk '{ print $2 }' | xargs -I {} kill {}

echo "Killing docker logs procs .."
ps -ef | grep "docker logs" | grep -v grep  | awk '{ print $2 }' | xargs -I {} kill {}

echo "Killing docker-compose .."
ps -ef | grep "docker-compose" | grep "dubbo-samples" | grep -v grep  | awk '{ print $2 }' | xargs -I {} kill {}

echo "Killing dubbo containers .."
docker ps -a | grep dubbo | awk '{ print $1}' | xargs -I {} docker kill {}

echo "Removing unused networks .."
docker network prune -f

