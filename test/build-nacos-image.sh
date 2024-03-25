#!/bin/bash

mkdir -p nacos-mysql
curl -o nacos-mysql/Dockerfile https://raw.gitmirror.com/wxbty/nacos-docker/mirror/example/image/mysql/5.7-m1/Dockerfile
docker build -t nacos-mysql:5.7 nacos-mysql/
