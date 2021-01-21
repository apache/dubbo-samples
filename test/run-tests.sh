#!/bin/bash

DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"

abspath () { case "$1" in /*)printf "%s\n" "$1";; *)printf "%s\n" "$PWD/$1";; esac; }

trim() {
    local var="$*"
    # remove leading whitespace characters
    var="${var#"${var%%[![:space:]]*}"}"
    # remove trailing whitespace characters
    var="${var%"${var##*[![:space:]]}"}"
    printf '%s' "$var"
}

JAVA_VER=${JAVA_VER:-8}
echo "JAVA_VER: $JAVA_VER"

FAIL_FAST=${FAIL_FAST:-0}
echo "FAIL_FAST: $FAIL_FAST"

SHOW_ERROR_DETAIL=${SHOW_ERROR_DETAIL:-0}
export SHOW_ERROR_DETAIL=$SHOW_ERROR_DETAIL
echo "SHOW_ERROR_DETAIL: $SHOW_ERROR_DETAIL"

maxForks=${FORK_COUNT:-2}
echo "FORK_COUNT: $maxForks"


#debug DEBUG=service1,service2
#deubg all duboo-xxx: DEBUG=dubbo*
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
test_image="dubbo/sample-test:$JAVA_VER"
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
VERSONS_FILE="case-versions.conf"

mkdir -p $DIR/jobs
testListFile=$DIR/jobs/testjob.txt
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
testResultFile=${testListFile%.*}-result-java${JAVA_VER}.txt
rm -f $testResultFile
touch $testResultFile
echo "Test results: $testResultFile"

if [ "$CANDIDATE_VERSIONS" == "" ];then
  CANDIDATE_VERSIONS="dubbo.version:2.7.8;spring.version:4.3.16.RELEASE;spring-boot.version:1.5.13.RELEASE,2.1.1.RELEASE"
#  CANDIDATE_VERSIONS="dubbo.version:2.7.8;spring.version:4.3.16.RELEASE,5.3.3;spring-boot.version:1.5.13.RELEASE,2.1.1.RELEASE"
fi
export CANDIDATE_VERSIONS=$CANDIDATE_VERSIONS
echo "CANDIDATE_VERSIONS: ${CANDIDATE_VERSIONS[@]}"

# test combination versions limit of single case
VERSIONS_LIMIT=${VERSIONS_LIMIT:-4}
export VERSIONS_LIMIT=$VERSIONS_LIMIT
echo "VERSIONS_LIMIT: $VERSIONS_LIMIT"


if [ "$MVN_OPTS" != "" ]; then
  export MVN_OPTS=$MVN_OPTS
  echo "MVN_OPTS: $MVN_OPTS"
fi

if [ "$BUILD_OPTS" == "" ]; then
  BUILD_OPTS="$MVN_OPTS -U --batch-mode --no-transfer-progress clean package dependency:copy-dependencies -DskipTests"
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
  case_dir=$1
  case_no=$2

  if [ -f $case_dir ]; then
    case_dir=`dirname $case_dir`
  fi

  file=$case_dir/$CONFIG_FILE
  if [ ! -f $file ]; then
    echo "$TEST_FAILURE: case config not found: $file" | tee -a $testResultFile
    return 1
  fi

  ver_file=$case_dir/$VERSONS_FILE
  if [ ! -f $ver_file ]; then
    echo "$TEST_FAILURE: case versions config not found: $ver_file" | tee -a $testResultFile
    return 1
  fi

  case_start_time=$SECONDS
  project_home=`dirname $file`
  scenario_home=$project_home/target
  scenario_name=`basename $project_home`
  log_prefix="[${case_no}/${caseCount}] [$scenario_name]"
  echo "$log_prefix Processing : $project_home .."

  # generate version matrix
  version_matrix_file=$project_home/version-matrix.txt
  java -DcandidateVersions="$CANDIDATE_VERSIONS" \
    -DcaseVersionsFile="$ver_file" \
    -DoutputFile="$version_matrix_file" \
    -DversionsLimit=$VERSIONS_LIMIT \
    -cp $test_builder_jar \
    org.apache.dubbo.scenario.builder.VersionMatcher &> $project_home/version-matrix.log
  result=$?
  if [ $result -ne 0 ]; then
    echo "$log_prefix $TEST_FAILURE: Generate version matrix failure: $project_home/version-matrix.log" | tee -a $testResultFile
    return 1
  fi

  version_count=`grep -c "" $version_matrix_file `
  echo "$log_prefix Version matrix: $version_count"
  cat $version_matrix_file

  version_no=0
  while read -r version_profile; do
    start_time=$SECONDS
    version_no=$((version_no + 1))
    log_prefix="[${case_no}/${caseCount}] [$scenario_name:$version_no/$version_count]"

    # run test using version profile
    echo "$log_prefix Building project : $scenario_name with version: $version_profile .."
    cd $project_home

    # clean target manual, avoid 'mvn clean' failed with 'Permission denied' in github actions
    find . -name target -d | xargs -I {} rm -rf {}
    target_dirs=`find . -name target -d`
    if [ "$target_dirs" != "" ]; then
      echo "$log_prefix Force delete target dirs"
      find . -name target -d | xargs -I {} sudo rm -rf {}
    fi

    mvn $BUILD_OPTS $version_profile &> $project_home/mvn.log
    result=$?
    if [ $result -ne 0 ]; then
      echo "$log_prefix $TEST_FAILURE: Build failure, please check log: $project_home/mvn.log" | tee -a $testResultFile
      if [ "$SHOW_ERROR_DETAIL" == "1" ];then
        cat $project_home/mvn.log
      fi
      return 1
    fi

    # generate case configuration
    mkdir -p $scenario_home/logs
    echo "$log_prefix Generating test case configuration .."
    config_time=$SECONDS
    java -Dconfigure.file=$file \
      -Dscenario.home=$scenario_home \
      -Dscenario.name=$scenario_name \
      -Dscenario.version=$version \
      -Dtest.image.version=$JAVA_VER \
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
      echo "$log_prefix $TEST_SUCCESS: cost $((end_time - start_time)) s"
    else
      echo "$log_prefix $TEST_FAILURE, please check logs: $scenario_home/logs" | tee -a $testResultFile

      # show test log
      if [ "$SHOW_ERROR_DETAIL" == "1" ]; then
        for log_file in $scenario_home/logs/*.log; do
          # ignore scenario-builder.log
          if [[ $log_file != *scenario-builder.log ]]; then
            print_log_file "$scenario_name : `basename $log_file`" $log_file
          fi
        done
      fi
      return 1
    fi

  done < $version_matrix_file

  log_prefix="[${case_no}/${caseCount}] [$scenario_name]"
  echo "$log_prefix $TEST_SUCCESS: versions: $version_count, total cost $((end_time - case_start_time)) s" | tee -a $testResultFile

  # clean log files
  rm -f $project_home/*.log $project_home/version-matrix.*
}

# build scenario-builder
SCENARIO_BUILDER_DIR=$DIR/dubbo-scenario-builder
echo "Building scenario builder .."
cd $SCENARIO_BUILDER_DIR
mvn $BUILD_OPTS &> $SCENARIO_BUILDER_DIR/mvn.log
result=$?
if [ $result -ne 0 ]; then
  echo "Build dubbo-scenario-builder failure, please check logs: $SCENARIO_BUILDER_DIR/mvn.log"
  cat $SCENARIO_BUILDER_DIR/mvn.log
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


# start run tests
cd $DIR
testStartTime=$SECONDS

#counter
allTest=0
finishedTest=0

while read path
do
  allTest=$((allTest + 1))

  if [ -f $path ];then
    path=`dirname $path`
  fi
  # fork process testcase
  process_case $path $allTest &
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
      if [ "$finishedTest" == "" ];then
        finishedTest=0
        continue
      fi
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

