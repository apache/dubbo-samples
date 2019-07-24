#!/usr/bin/env bash

ps ax | grep 'java' | grep 'org.apache.dubbo.samples' | awk '{ print $1 }' | xargs echo
ps ax | grep 'java' | grep 'org.apache.dubbo.samples' | awk '{ print $1 }' | xargs kill -9
