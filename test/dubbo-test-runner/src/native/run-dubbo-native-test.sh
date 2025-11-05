#!/bin/bash
set -x
DIR=/usr/local/dubbo/

source $DIR/utils.sh

cd $DIR

# wait ports before run app: WAIT_PORTS_BEFORE_RUN=host:port;host:port
if [ "$WAIT_PORTS_BEFORE_RUN" != "" ]; then
  echo "Waiting ports before run native test .."
  # check ports
  split_and_check_tcp_ports "$WAIT_PORTS_BEFORE_RUN" $SECONDS $WAIT_TIMEOUT
  result=$?
  if [ $result -ne 0 ]; then
    echo "Wait ports before run native test failure"
    exit $result
  fi
fi

# At least delay 1 second
if [ $RUN_DELAY -le 0 ]; then
  RUN_DELAY=1
fi

#delay start
if [ $RUN_DELAY -gt 0 ]; then
  echo "Delay $RUN_DELAY s."
  sleep $RUN_DELAY
fi

cd $DIR/src/${SERVICE_DIR}
echo "Build and run native test : ..."
mvn $JAVA_OPTS --no-transfer-progress test -PnativeTest 2>&1 # no clean package here cause run-native-tests.sh has run it
result=$?
if [ $result -ne 0 ]; then
  echo "Run native tests failure"
  exit $result
else
  echo "Run native tests successfully"
fi
