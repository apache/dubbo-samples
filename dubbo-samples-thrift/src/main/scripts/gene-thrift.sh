#!/usr/bin/env bash
service_dir="./java/"
for thrift_file in ./idls/*
do
    if test -f $file
    then
        thrift --gen java --out ${service_dir} ${thrift_file}
    fi
done