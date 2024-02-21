#!/bin/bash

if [ "$SERVICE_NAME" == "" ]; then
  echo "Missing env 'SERVICE_NAME'"
  return 1
fi

if [ "$CHECK_LOG" == "" ] && [ "$CHECK_PORTS" == "" ]; then
  echo "Require at least one of the env: 'CHECK_LOG' or 'CHECK_PORTS'"
  return 1
fi

DIR=/usr/local/dubbo/
cd $DIR

LOG_DIR=/usr/local/dubbo/logs
if [ ! -d $LOG_DIR ];then
  mkdir -p $LOG_DIR
fi
LOG_FILE=$LOG_DIR/$SERVICE_NAME.log

source $DIR/utils.sh

# check ports
if [ "$CHECK_PORTS" != "" ]; then
  split_and_check_tcp_ports "$CHECK_PORTS" $SECONDS 1
  result=$?
  if [ $result -ne 0 ]; then
    echo "check ports failure"
    exit $result
  fi
fi

# check log
if [ "$CHECK_LOG" != "" ]; then
  check_log "$LOG_FILE" "$CHECK_LOG" $SECONDS 1
  result=$?
  if [ $result -ne 0 ]; then
    echo "check log failure"
    exit $result
  fi
fi

