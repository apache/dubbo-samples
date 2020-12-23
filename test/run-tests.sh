#!/bin/bash

DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"

DUBBO_VERSION=2.7.9-SNAPSHOT

FAIL_FAST=${FAIL_FAST:-0}
echo "FAIL_FAST: $FAIL_FAST"

SHOW_ERROR_DETAIL=${SHOW_ERROR_DETAIL:-1}
export SHOW_ERROR_DETAIL=$SHOW_ERROR_DETAIL
echo "SHOW_ERROR_DETAIL: $SHOW_ERROR_DETAIL"

maxForks=${FORK_COUNT:-2}
echo "Fork count: $maxForks"

echo "Test logs dir: \${project.basedir}/target/logs"
echo "Test reports dir: \${project.basedir}/target/test-reports"

# build scenario-builder
SCENARIO_BUILDER_DIR=$DIR/dubbo-scenario-builder
echo "Building scenario builder .."
cd $SCENARIO_BUILDER_DIR
mvn clean package &> $SCENARIO_BUILDER_DIR/mvn.log
result=$?
if [ $result -ne 0 ]; then
  echo "Build dubbo-scenario-builder failure"
  exit $result
fi

# find jar
TEST_BUILDER_JAR=`ls $SCENARIO_BUILDER_DIR/target/dubbo-scenario-builder*-with-dependencies.jar`
if [ "$TEST_BUILDER_JAR" == "" ]; then
  echo "dubbo-scenario-builder jar not found"
  exit 1
else
  echo "Found test builder : $TEST_BUILDER_JAR"
fi

cd $DIR

testListFile=$DIR/testcases.txt

targetTestcases=$1
if [ "$targetTestcases" != "" ];then
  echo "Target testcase: $targetTestcases"
  echo $targetTestcases > $testListFile
else
  # find all case-configuration.yml
  TEST_BASE_DIR="$( cd $DIR/.. && pwd )"
  echo "Searching all 'case-configuration.yml' under dir $TEST_BASE_DIR .."
  find $TEST_BASE_DIR -name 'case-configuration.yml' | grep -v "$DIR" > $testListFile
fi

caseCount=`grep "" -c $testListFile`
echo "Total test cases : $caseCount"

#clear test results
testResultFile=$DIR/testcases-result.txt
rm -f $testResultFile

# constant
TEST_SUCCESS="TEST SUCCESS"
TEST_FAILURE="TEST FAILURE"

function process_case() {
  file=$1
  case_no=$2
  project_home=`dirname $file`
  scenario_home=$project_home/target
  scenario_name=`basename $project_home`
  log_prefix="[${case_no}/${caseCount}] [$scenario_name]"
  echo "$log_prefix Processing : $project_home .."

  # mvn build
  echo "$log_prefix Building project .."
  building_time=$SECONDS
  cd $project_home
  mvn package dependency:copy-dependencies &> $project_home/mvn.log
  result=$?
  if [ $result -ne 0 ]; then
    echo "$log_prefix $TEST_FAILURE: Build failure, please check log: $project_home/mvn.log" | tee -a $testResultFile
    return
  fi

  # generate case configuration
  echo "$log_prefix Generating test case configuration .."
  config_time=$SECONDS
  mkdir -p $scenario_home
  java -Dconfigure.file=$file \
    -Dscenario.home=$scenario_home \
    -Dscenario.name=$scenario_name \
    -Dscenario.version=$DUBBO_VERSION \
    -jar $TEST_BUILDER_JAR  &> $scenario_home/scenario-builder.log
  result=$?
  if [ $result -ne 0 ]; then
    echo "$log_prefix $TEST_FAILURE: Generate case configuration failure: $scenario_home/scenario-builder.log" | tee -a $testResultFile
    return
  fi

  # run test
  echo "$log_prefix Running test case .."
  running_time=$SECONDS
  bash $scenario_home/scenario.sh
  result=$?
  end_time=$SECONDS

  if [ $result == 0 ]; then
    echo "$log_prefix $TEST_SUCCESS: total cost $((end_time - building_time)) s "\
      "(build: $((running_time - building_time)), test: $((end_time - running_time)) )" | \
      tee -a $testResultFile
  else
    echo "$log_prefix $TEST_FAILURE, please check logs: $scenario_home/logs" | \
      tee -a $testResultFile

    # show test log
    if [ "$SHOW_ERROR_DETAIL" == "1" ] && [ -f $scenario_home/logs/container.log ]; then
      echo ""
      echo "----------------------------------------------------------"
      echo "app log of $scenario_name :"
      echo "----------------------------------------------------------"
      cat $scenario_home/logs/app.log
      echo ""
      echo "----------------------------------------------------------"
      echo "container log of $scenario_name :"
      echo "----------------------------------------------------------"
      cat $scenario_home/logs/container.log
      echo ""
    fi
  fi
}

# start run tests
testStartTime=$SECONDS

#counter
allTest=0
finishedTest=0

while read file
do
  allTest=$((allTest + 1))
  # fork process testcase
  process_case $file $allTest &
  sleep 1

  #wait for tests finished
  delta=$maxForks
  if [ $allTest == $caseCount ];then
    delta=0
  fi
  while [ $finishedTest -lt $caseCount ] && [ $((allTest - finishedTest)) -ge $delta ]
  do
    sleep 1
    if [ -f $testResultFile ]; then
      finishedTest=`grep "" -c $testResultFile`
      # check fail fast
      if [ "$FAIL_FAST" == "1" ]; then
        failedTest=`grep "$TEST_FAILURE" -c $testResultFile`
        if [ $failedTest -ne 0 ]; then
          echo "Aborting, wait for subprocess finished .."
          wait
          echo "----------------------------------------------------------"
          echo "Test is aborted cause some testcase is failed (fail-fast mode). "
          echo "Fail tests:"
          grep "$TEST_FAILURE" $testResultFile
          echo "----------------------------------------------------------"
          exit 1
        fi
      fi
    fi
  done

done < $testListFile

successTest=`grep "$TEST_SUCCESS" -c $testResultFile`
failedTest=`grep "$TEST_FAILURE" -c $testResultFile`

echo "----------------------------------------------------------"
echo "Test logs dir: \${project.basedir}/target/logs"
echo "Test reports dir: \${project.basedir}/target/test-reports"
echo "Test results: $testResultFile"
echo "Total cost: $((SECONDS - testStartTime)) seconds"
echo "All tests count: $allTest"
echo "Success tests count: $successTest"

if [ $successTest == $allTest ]
then
   echo "All tests pass"
   echo "----------------------------------------------------------"
   exit 0
else
   echo "Some tests fail: $failedTest"
   echo "----------------------------------------------------------"
   echo "Fail tests:"
   grep "$TEST_FAILURE" $testResultFile
   echo "----------------------------------------------------------"
   exit 1
fi

