#!/bin/bash

DIR=/usr/local/dubbo/
cd $DIR

source $DIR/utils.sh

# build native image first
cd $DIR/src/${SERVICE_DIR}
echo "Build native app : ..."
mvn package native:compile -Dmaven.test.skip=true -Pnative 2>&1
result=$?
if [ $result -ne 0 ]; then
  echo "Build native app failure "
  exit 1
fi

# wait ports before run app: WAIT_PORTS_BEFORE_RUN=host:port;host:port
if [ "$WAIT_PORTS_BEFORE_RUN" != "" ]; then
  echo "Waiting ports before run native app .."
  split_and_check_tcp_ports "$WAIT_PORTS_BEFORE_RUN" $SECONDS $WAIT_TIMEOUT
  result=$?
  if [ $result -ne 0 ]; then
    echo "Wait ports before run native app failure"
    exit $result
  fi
fi

#delay start
if [ $RUN_DELAY -gt 0 ]; then
  echo "Delay $RUN_DELAY s."
  sleep $RUN_DELAY
fi

echo "Running native app : ..."
start=$SECONDS
./target/${SERVICE_NAME} 2>&1 &
pid=$!

echo "Wait for process to exit: $pid .."
wait $pid
result=$?
if [ $result -ne 0 ]; then
  exit $result
fi

