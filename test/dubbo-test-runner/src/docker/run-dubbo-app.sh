#!/bin/bash

DIR=/usr/local/dubbo/
cd $DIR

source $DIR/utils.sh

if [ "$APP_MAIN_CLASS" == "" ]; then
  echo "Missing env 'APP_MAIN_CLASS' for app service"
  return 1
fi

# wait ports before run app: WAIT_PORTS_BEFORE_RUN=host:port;host:port
if [ "$WAIT_PORTS_BEFORE_RUN" != "" ]; then
  echo "Waiting ports before run app .."
  # check ports
  split_and_check_tcp_ports "$WAIT_PORTS_BEFORE_RUN" $SECONDS $CHECK_TIMEOUT
  result=$?
  if [ $result -ne 0 ]; then
    echo "Wait ports before run app failure"
    exit $result
  fi
fi

echo "Running app : [$APP_MAIN_CLASS] ..."
start=$SECONDS
java $JAVA_OPTS -cp "$APP_CLASSES_DIR:$APP_DEPENDENCY_DIR/*" $APP_MAIN_CLASS 2>&1 &
pid=$!

sleep 20

# check ports after run
if [ "$CHECK_PORTS_AFTER_RUN" != "" ]; then
  split_and_check_tcp_ports "$CHECK_PORTS_AFTER_RUN" $start $CHECK_TIMEOUT
  result=$?
  if [ $result -ne 0 ]; then
    echo "Wait ports after run app failure"
    exit $result
  fi
fi

echo "Wait for process to exit: $pid .."
wait $pid
result=$?
if [ $result -ne 0 ]; then
  exit $result
fi

