#!/bin/bash

DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"

abspath () { case "$1" in /*)printf "%s\n" "$1";; *)printf "%s\n" "$PWD/$1";; esac; }

FAIL_FAST=${FAIL_FAST:-0}
echo "FAIL_FAST: $FAIL_FAST"

SHOW_ERROR_DETAIL=${SHOW_ERROR_DETAIL:-0}
export SHOW_ERROR_DETAIL=$SHOW_ERROR_DETAIL
echo "SHOW_ERROR_DETAIL: $SHOW_ERROR_DETAIL"

maxForks=${FORK_COUNT:-2}
echo "FORK_COUNT: $maxForks"

#Build mode: all, case, no
BUILD=${BUILD:-case}
export BUILD=$BUILD
echo "BUILD: $BUILD"

#DUBBO_VERSION=
echo "DUBBO_VERSION: $DUBBO_VERSION"

#debug DEBUG=service1,service2
export DEBUG=$DEBUG
echo "DEBUG=$DEBUG"

#TEST_CASE_FILE
if [ "$TEST_CASE_FILE" != "" ]; then
  # convert relative path to absolute path
  if [[ $TEST_CASE_FILE != /* ]]; then
    TEST_CASE_FILE=`abspath $TEST_CASE_FILE`
  fi
  echo "TEST_CASE_FILE: $TEST_CASE_FILE"
fi

echo "Test logs dir: \${project.basedir}/target/logs"
echo "Test reports dir: \${project.basedir}/target/test-reports"


#check dubbo/sample-test image and version
test_image="dubbo/sample-test"
echo "Checking test image [$test_image] .. "
docker images --format 'table {{.Repository}}:{{.Tag}}\t{{.ID}}\t{{.CreatedAt}}\t{{.Size}}' | grep $test_image
result=$?
if [ $result != 0 ];then
  echo "Test image not found: $test_image, please run 'bash ./build-test-image.sh' first."
  exit 1
fi


# prepare testcases
cd $DIR

CONFIG_FILE="case-configuration.yml"

testListFile=$DIR/testcases.txt
targetTestcases=$1
if [ "$targetTestcases" != "" ];then
  targetTestcases=`abspath $targetTestcases`
  if [ -d "$targetTestcases" ] || [ -f "$targetTestcases" ]; then
    echo "Target testcase: $targetTestcases"
    echo $targetTestcases > $testListFile
  else
    echo "Testcase not exist: $targetTestcases"
    exit 1
  fi
else
  # use input testcases file
  if [ "$TEST_CASE_FILE" != "" ]; then
    testListFile=$TEST_CASE_FILE
    if [ ! -f $testListFile ]; then
      echo "Testcases file not found: $testListFile"
      exit 1
    fi
  else
    # find all case-configuration.yml
    test_base_dir="$( cd $DIR/.. && pwd )"
    rm -f $testListFile
    echo "Searching all '$CONFIG_FILE' under dir $test_base_dir .."
    find $test_base_dir -name $CONFIG_FILE | grep -v "$DIR" > $testListFile
  fi
fi

caseCount=`grep "" -c $testListFile`
echo "Total test cases : $caseCount"

if [ "$DEBUG" != "" ] && [ $caseCount -gt 1 ]; then
  echo "Only one case can be debugged"
  exit 1
fi

#clear test results
testResultFile=${testListFile%.*}-result.txt
rm -f $testResultFile
echo "Test results: $testResultFile"

BUILD_OPTS="clean package dependency:copy-dependencies -DskipTests"
if [ "$DUBBO_VERSION" != "" ]; then
  BUILD_OPTS="$BUILD_OPTS -Ddubbo.version=$DUBBO_VERSION"
fi
export BUILD_OPTS=$BUILD_OPTS
echo "BUILD_OPTS: $BUILD_OPTS"

# constant
TEST_SUCCESS="TEST SUCCESS"
TEST_FAILURE="TEST FAILURE"

function print_log_file() {
  title=$1
  file=$2

  if [ -f $file ]; then
    echo ""
    echo "----------------------------------------------------------"
    echo " $title"
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
  start_time=$SECONDS
  echo "$log_prefix Processing : $project_home .."

  # mvn build
  if [ "$BUILD" == "case" ]; then
    echo "$log_prefix Building project : $scenario_name .."
    cd $project_home
    mvn $BUILD_OPTS &> $project_home/mvn.log
    result=$?
    if [ $result -ne 0 ]; then
      echo "$log_prefix $TEST_FAILURE: Build failure, please check log: $project_home/mvn.log" | tee -a $testResultFile
      return 1
    fi
  fi

  #check build
  echo "$log_prefix Checking project artifacts .."
  if [ ! -d "$project_home/target" ]; then
    echo "$log_prefix $TEST_FAILURE: Missing artifacts" | tee -a $testResultFile
    return 1
  fi

  # generate case configuration
  mkdir -p $scenario_home/logs
  echo "$log_prefix Generating test case configuration .."
  config_time=$SECONDS
  mkdir -p $scenario_home
  java -Dconfigure.file=$file \
    -Dscenario.home=$scenario_home \
    -Dscenario.name=$scenario_name \
    -Dscenario.version=$DUBBO_VERSION \
    -Ddebug.service=$DEBUG \
    -jar $test_builder_jar  &> $scenario_home/logs/scenario-builder.log
  result=$?
  if [ $result -ne 0 ]; then
    echo "$log_prefix $TEST_FAILURE: Generate case configuration failure: $scenario_home/logs/scenario-builder.log" | tee -a $testResultFile
    return 1
  fi

  # run test
  echo "$log_prefix Running test case .."
  running_time=$SECONDS
  bash $scenario_home/scenario.sh
  result=$?
  end_time=$SECONDS

  if [ $result == 0 ]; then
    echo "$log_prefix $TEST_SUCCESS: total cost $((end_time - start_time)) s" | tee -a $testResultFile
  else
    echo "$log_prefix $TEST_FAILURE, please check logs: $scenario_home/logs" | tee -a $testResultFile

    # show test log
    if [ "$SHOW_ERROR_DETAIL" == "1" ]; then
      for log_file in $scenario_home/logs/*.log; do
        print_log_file "$scenario_name : `basename $log_file`" $log_file
      done
    fi
    return 1
  fi
}

# build scenario-builder
SCENARIO_BUILDER_DIR=$DIR/dubbo-scenario-builder
echo "Building scenario builder .."
cd $SCENARIO_BUILDER_DIR
mvn clean package -DskipTests &> $SCENARIO_BUILDER_DIR/mvn.log
result=$?
if [ $result -ne 0 ]; then
  echo "Build dubbo-scenario-builder failure, please check logs: $SCENARIO_BUILDER_DIR/mvn.log"
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

# build all samples
if [ "$BUILD" == "all" ]; then
  echo "Building dubbo-samples .."
  cd $DIR/..
  mvn $BUILD_OPTS
  result=$?
  if [ $result -ne 0 ]; then
    echo "Build dubbo-samples failure, please check logs"
    exit $result
  fi
fi


# start run tests
cd $DIR
testStartTime=$SECONDS

#counter
allTest=0
finishedTest=0

while read line
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

