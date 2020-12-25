#!/bin/bash

DIR=/usr/local/dubbo/

source $DIR/utils.sh

cd $DIR

if [ "$TEST_CLASSES_DIR" == "" ]; then
  echo "Missing env 'TEST_CLASSES_DIR' for test service"
  return 1
fi

# wait ports before run app: WAIT_PORTS_BEFORE_RUN=host:port;host:port
if [ "$WAIT_PORTS_BEFORE_RUN" != "" ]; then
  echo "Waiting ports before run test .."
  # check ports
  split_and_check_tcp_ports "$WAIT_PORTS_BEFORE_RUN" $SECONDS $CHECK_TIMEOUT
  result=$?
  if [ $result -ne 0 ]; then
    echo "Wait ports before run test failure"
    exit $result
  fi
fi

# run testcase
echo "Running tests ..."
report_dir=$DIR/app/test-reports
java $JAVA_OPTS -jar dubbo-test-runner.jar "$TEST_CLASSES_DIR" "$APP_CLASSES_DIR" "$APP_DEPENDENCY_DIR" "$report_dir" "$TEST_PATTERNS" 2>&1
result=$?
if [ $result -ne 0 ]; then
  echo "Run tests failure"
  exit $result
else
  echo "Run tests successfully"
fi
