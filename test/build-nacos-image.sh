#!/bin/bash

mkdir nacos-mysql
curl -o nacos-mysql/Dockerfile https://raw.githubusercontent.com/nacos-group/nacos-docker/master/example/image/mysql/5.7/Dockerfile
docker build -t nacos-mysql:5.7 nacos-mysql/