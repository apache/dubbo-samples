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

#TEST_CASES_FILE
if [ "$TEST_CASES_FILE" != "" ]; then
  # convert relative path to absolute path
  if [[ $TEST_CASES_FILE != /* ]]; then
    TEST_CASES_FILE=$DIR/$TEST_CASES_FILE
  fi
  echo "TEST_CASES_FILE: $TEST_CASES_FILE"
fi

echo "Test logs dir: \${project.basedir}/target/logs"
echo "Test reports dir: \${project.basedir}/target/test-reports"


#check dubbo-sample-test image and version
test_image="dubbo-sample-test"
echo "Checking test image [$test_image] .. "
docker images --format 'table {{.Repository}}:{{.Tag}}\t{{.ID}}\t{{.CreatedAt}}\t{{.Size}}' | grep $test_image
result=$?
if [ $result != 0 ];then
  echo "Test image not found: $test_image, please run 'bash ./build-test-image.sh' first."
  exit 1
fi

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
test_builder_jar=`ls $SCENARIO_BUILDER_DIR/target/dubbo-scenario-builder*-with-dependencies.jar`
if [ "$test_builder_jar" == "" ]; then
  echo "dubbo-scenario-builder jar not found"
  exit 1
else
  echo "Found test builder : $test_builder_jar"
fi

cd $DIR

CONFIG_FILE="case-configuration.yml"

targetTestcases=$1
if [ "$targetTestcases" != "" ];then
  echo "Target testcase: $targetTestcases"
  echo $targetTestcases > $testListFile
else
  # use input testcases file
  if [ "$TEST_CASES_FILE" != "" ]; then
    testListFile=$TEST_CASES_FILE
    if [ ! -f $testListFile ]; then
      echo "Testcases file not found: $testListFile"
      exit 1
    fi
  else
    # find all case-configuration.yml
    test_base_dir="$( cd $DIR/.. && pwd )"
    testListFile=$DIR/testcases.txt
    rm -f $testListFile
    echo "Searching all '$CONFIG_FILE' under dir $test_base_dir .."
    find $test_base_dir -name $CONFIG_FILE | grep -v "$DIR" > $testListFile
  fi
fi

caseCount=`grep "" -c $testListFile`
echo "Total test cases : $caseCount"

#clear test results
testResultFile=${testListFile%.*}-result.txt
rm -f $testResultFile
echo "Test results: $testResultFile"

# constant
TEST_SUCCESS="TEST SUCCESS"
TEST_FAILURE="TEST FAILURE"

function print_log_file() {
  scenario_name=$1
  file=$2

  if [ -f $file ]; then
    echo ""
    echo "----------------------------------------------------------"
    echo " $scenario_name log file : $file"
    echo "----------------------------------------------------------"
    cat $file
    echo ""
  fi
}

function process_case() {
  file=$1
  case_no=$2

  if [ -d $file ]; then
    file=$file/$CONFIG_FILE
  fi

  if [ ! -f $file ]; then
    echo "$TEST_FAILURE: case config not found: $file" | tee -a $testResultFile
    return 1
  fi

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
    return 1
  fi

  # generate case configuration
  echo "$log_prefix Generating test case configuration .."
  config_time=$SECONDS
  mkdir -p $scenario_home
  java -Dconfigure.file=$file \
    -Dscenario.home=$scenario_home \
    -Dscenario.name=$scenario_name \
    -Dscenario.version=$DUBBO_VERSION \
    -jar $test_builder_jar  &> $scenario_home/scenario-builder.log
  result=$?
  if [ $result -ne 0 ]; then
    echo "$log_prefix $TEST_FAILURE: Generate case configuration failure: $scenario_home/scenario-builder.log" | tee -a $testResultFile
    return 1
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
    if [ "$SHOW_ERROR_DETAIL" == "1" ]; then
      print_log_file $scenario_name $scenario_home/logs/scenario.log
      print_log_file $scenario_name $scenario_home/logs/app.log
      print_log_file $scenario_name $scenario_home/logs/container.log
    fi
    return 1
  fi
}

# start run tests
testStartTime=$SECONDS

#counter
allTest=0
finishedTest=0

while IFS= read -r line
do
  allTest=$((allTest + 1))
  # fork process testcase
  process_case $line $allTest &
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
echo "All tests count: $caseCount"
echo "Success tests count: $successTest"

if [ $successTest == $caseCount ]
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

