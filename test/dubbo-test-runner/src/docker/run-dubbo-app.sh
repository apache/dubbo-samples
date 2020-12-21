#!/bin/bash

DIR=/usr/local/dubbo/
cd $DIR

LOG_DIR=$DIR/app/logs
mkdir -p $LOG_DIR

source $DIR/utils.sh

if [ "$APP_MAIN_CLASS" == "" ]; then
  echo "Missing env 'APP_MAIN_CLASS' for app service"
  return 1
fi

# wait ports before run app: WAIT_PORTS_BEFORE_RUN=host:port;host:port
if [ "$WAIT_PORTS_BEFORE_RUN" != "" ]; then
  echo "Waiting ports before run app .."
  # check ports
  split_and_check_tcp_ports "$WAIT_PORTS_BEFORE_RUN" $SECONDS 60
  result=$?
  if [ $result -ne 0 ]; then
    echo "Wait ports before run app failure"
    exit $result
  fi
fi

echo "Running app : [$APP_MAIN_CLASS] ..."
start=$SECONDS
app_log="$LOG_DIR/app.log"
java $JAVA_OPTS -cp "$APP_CLASSES_DIR:$APP_DEPENDENCY_DIR/*" $APP_MAIN_CLASS &> $app_log &
pid=$!

# wait log
if [ "$CHECK_LOG" != "" ]; then
  check_log $app_log "$CHECK_LOG" $start 60
  result=$?
  if [ $result -ne 0 ]; then
    echo "Wait log after run app failure"
    exit $result
  fi
fi

# wait ports
if [ "$CHECK_PORTS_AFTER_RUN" != "" ]; then
  # check ports
  split_and_check_tcp_ports "$CHECK_PORTS_AFTER_RUN" $start 60
  result=$?
  if [ $result -ne 0 ]; then
    echo "Wait ports after run app failure"
    exit $result
  fi
fi

echo "waiting process: $pid .."
wait $pid
result=$?
if [ $result -ne 0 ]; then
  exit $result
fi

