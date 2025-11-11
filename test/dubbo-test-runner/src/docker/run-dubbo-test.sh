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
  split_and_check_tcp_ports "$WAIT_PORTS_BEFORE_RUN" $SECONDS $WAIT_TIMEOUT
  result=$?
  if [ $result -ne 0 ]; then
    echo "Wait ports before run test failure"
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

# run testcase
echo "Running tests ..."
report_dir=$DIR/app/test-reports

# Fix Class loading problem in java9+: CompletableFuture.supplyAsync() is executed in the ForkJoinWorkerThread
# and it only uses system classloader to load classes instead of the IsolatedClassLoader
classpath=dubbo-test-runner.jar:$TEST_CLASSES_DIR:$APP_CLASSES_DIR:$APP_DEPENDENCY_DIR/*:$APP_CLASSES_DIR/../../jctools-core.jar
java $JAVA_OPTS $DEBUG_OPTS -cp $classpath org.apache.dubbo.test.runner.TestRunnerMain "$TEST_CLASSES_DIR" "$APP_CLASSES_DIR" "$APP_DEPENDENCY_DIR" "$report_dir" "$TEST_PATTERNS" 2>&1
result=$?
if [ $result -ne 0 ]; then
  echo "Run tests failure"
  exit $result
else
  echo "Run tests successfully"
fi
