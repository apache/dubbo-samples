#!/bin/bash

TEST_DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"
WORK_DIR="$(pwd)"
echo "WorkDir: $WORK_DIR"

#-----------------------------------#
# environments
#-----------------------------------#
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

DUBBO_VERSION=${DUBBO_VERSION:-3.2.6}
if [ "$CANDIDATE_VERSIONS" == "" ];then
  CANDIDATE_VERSIONS="dubbo.version:$DUBBO_VERSION;spring.version:5.3.30;spring-boot.version:1.5.13.RELEASE,2.7.16"
#  CANDIDATE_VERSIONS="dubbo.version:2.7.12;spring.version:4.3.16.RELEASE,5.3.3;spring-boot.version:1.5.13.RELEASE,2.1.1.RELEASE"
fi
JAVA_VERSION="java.version"
if [[ $CANDIDATE_VERSIONS != *$JAVA_VERSION* ]];then
  CANDIDATE_VERSIONS="$CANDIDATE_VERSIONS;java.version:$JAVA_VER"
fi
export CANDIDATE_VERSIONS=$CANDIDATE_VERSIONS
echo "CANDIDATE_VERSIONS: ${CANDIDATE_VERSIONS[@]}"

# test combination versions limit of single case
#VERSIONS_LIMIT=${VERSIONS_LIMIT:-4}
#export VERSIONS_LIMIT=$VERSIONS_LIMIT
#echo "VERSIONS_LIMIT: $VERSIONS_LIMIT"

if [ "$MVN_OPTS" != "" ]; then
  export MVN_OPTS=$MVN_OPTS
  echo "MVN_OPTS: $MVN_OPTS"
fi

if [ "$BUILD_OPTS" == "" ]; then
  BUILD_OPTS="$MVN_OPTS --batch-mode --no-snapshot-updates --no-transfer-progress clean package dependency:copy-dependencies -DskipTests"
fi
export BUILD_OPTS=$BUILD_OPTS
echo "BUILD_OPTS: $BUILD_OPTS"


#-----------------------------------#
# constants
#-----------------------------------#
TEST_SUCCESS="TEST SUCCESS"
TEST_FAILURE="TEST FAILURE"
TEST_IGNORED="TEST IGNORED"

ERROR_MSG_FLAG=":ErrorMsg:"

CONFIG_FILE="case-configuration.yml"
VERSONS_FILE="case-versions.conf"
RUNTIME_PARAMETER_FILE="case-runtime-parameter.conf"
VERSIONS_SOURCE_FILE="case-version-sources.conf"

# Exit codes
# version matrix not match
EXIT_UNMATCHED=100
# ignore testing
EXIT_IGNORED=120


#-----------------------------------#
# functions
#-----------------------------------#
abspath () { case "$1" in /*)printf "%s\n" "$1";; *)printf "%s\n" "$PWD/$1";; esac; }

function get_error_msg() {
  log_file=$1
  error_msg=`grep $ERROR_MSG_FLAG $log_file`
  error_msg=${error_msg#*$ERROR_MSG_FLAG}
  echo $error_msg
}

function print_log_file() {
  scenario_name=$1
  file=$2

  if [ -f $file ]; then
    title="$scenario_name log: `basename $file`"
    echo ""
    echo "----------------------------------------------------------"
    echo " $title"
    echo "----------------------------------------------------------"
    cat $file
    echo ""
  fi
}

function check_test_image() {
    #check dubbo/sample-test image and version
    test_image="dubbo/sample-test:$JAVA_VER"
    echo "Checking test image [$test_image] .. "
    docker images --format 'table {{.Repository}}:{{.Tag}}\t{{.ID}}\t{{.CreatedAt}}\t{{.Size}}' | grep $test_image
    result=$?
    if [ $result != 0 ];then
      echo "Test image not found: $test_image, please run 'bash ./build-test-image.sh' first."
      exit 1
    fi
}

function run_test_with_version_profile() {
    local version_profile=$1
    local parameter_runtime=$2
    local project_home=$3

    start_time=$SECONDS
    version_no=$((version_no + 1))
    log_prefix="[${case_no}/${totalCount}] [$scenario_name:$version_no/$version_count]"

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

    jvm_opts=$version_profile
    if [ "$parameter_runtime" != "" ]; then
        # 检查参数数量
        IFS=' ' read -ra params <<< "$parameter_runtime"
        num_params=${#params[@]}
        if [ "$num_params" -gt 1 ]; then
            output_params=$(echo "$parameter_runtime" | awk '{ for(i=1; i<=NF; i++) printf "%s%s", $i, (i==NF ? ORS : "|"); }')
        else
            output_params="$parameter_runtime"
        fi
       jvm_opts="$version_profile $parameter_runtime -Dprop=\"$output_params\""
    fi

    echo "jvm_opts=$jvm_opts"
    mvn $BUILD_OPTS $jvm_opts &> $project_home/mvn.log
    result=$?
    if [ $result -ne 0 ]; then
      echo "$log_prefix $TEST_FAILURE: Build failure with version: $jvm_opts, please check log: $project_home/mvn.log" | tee -a $testResultFile
      echo "mvn $BUILD_OPTS $jvm_opts"
      if [ "$SHOW_ERROR_DETAIL" == "1" ];then
        cat $project_home/mvn.log
      fi
      return 1
    fi

    # list files of all dependency directories
    find `pwd` -name dependency -print -exec ls -1 {} \;

     # generate case configuration
     mkdir -p $scenario_home/logs
     scenario_builder_log=$scenario_home/logs/scenario-builder.log
     echo "$log_prefix Generating test case configuration .."
     config_time=$SECONDS
     java -Dconfigure.file=$file \
          -Dscenario.home=$scenario_home \
          -Dscenario.name=$scenario_name \
          -Dscenario.version=$version \
          -Dtest.image.version=$JAVA_VER \
          -Ddebug.service=$DEBUG \
          $version_profile \
          -Dprop="$jvm_opts" \
          -jar $test_builder_jar  &> $scenario_builder_log
     result=$?
     if [ $result -ne 0 ]; then
        error_msg=`get_error_msg $scenario_builder_log`
        if [ $result == $EXIT_IGNORED ]; then
          echo "$log_prefix $TEST_IGNORED: $error_msg" | tee -a $testResultFile
        else
          echo "$log_prefix $TEST_FAILURE: Generate case configuration failure: $error_msg, please check log: $scenario_builder_log" | tee -a $testResultFile
        fi
        return $result
     fi

    mount_msg=`grep mount $scenario_builder_log`
    echo "$log_prefix $mount_msg"

    # run test
    echo "$log_prefix Running test case .."
    running_time=$SECONDS
    bash $scenario_home/scenario.sh
    result=$?
    end_time=$SECONDS

    if [ $result == 0 ]; then
      echo "$log_prefix $TEST_SUCCESS with version: $version_profile, cost $((end_time - start_time)) s"
    else
      scenario_log=$scenario_home/logs/scenario.log
      error_msg=`get_error_msg $scenario_log`
      if [ $result == $EXIT_IGNORED ]; then
        if [ "$error_msg" != "" ];then
          echo "$log_prefix $TEST_IGNORED: $error_msg, version: $version_profile" | tee -a $testResultFile
        else
          echo "$log_prefix $TEST_IGNORED, version: $version_profile, please check logs: $scenario_home/logs" | tee -a $testResultFile
        fi
      else
        if [ "$error_msg" != "" ];then
          echo "$log_prefix $TEST_FAILURE: $error_msg, version: $version_profile, please check logs: $scenario_home/logs" | tee -a $testResultFile
        else
          echo "$log_prefix $TEST_FAILURE, version: $version_profile, please check logs: $scenario_home/logs" | tee -a $testResultFile
        fi
      fi

      # show test log
      if [ "$SHOW_ERROR_DETAIL" == "1" ]; then
        for log_file in $scenario_home/logs/*.log; do
          # ignore scenario-builder.log
          if [[ $log_file != *scenario-builder.log ]]; then
            print_log_file $scenario_name $log_file
          fi
        done
      fi
      return 1
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

  runtime_parameter_file=$case_dir/$RUNTIME_PARAMETER_FILE
  ver_src_file=$case_dir/$VERSIONS_SOURCE_FILE

  case_start_time=$SECONDS
  project_home=`dirname $file`
  scenario_home=$project_home/target
  scenario_name=`basename $project_home`
  log_prefix="[${case_no}/${totalCount}] [$scenario_name]"
  echo "$log_prefix Processing : $project_home .."

  runtime_count=0
  if [ ! -f $runtime_parameter_file ]; then
    echo "case runtime config not found: $runtime_parameter_file"
  else
    content=$(grep -v '^\s*#' "$runtime_parameter_file" | grep -v '^$')
    echo "$content" > "$runtime_parameter_file"
    runtime_count=`grep -vc '^$' $runtime_parameter_file `
    echo "$log_prefix Runtime parameter: $runtime_count"
    cat $runtime_parameter_file
  fi
  echo "runtime_count=$runtime_count"

  includeCaseSpecificVersion=true
  if [ $runtime_count -gt 0 ]; then
    includeCaseSpecificVersion=fasle;
  fi

  # generate version matrix
  version_log_file=$project_home/version-matrix.log
  version_matrix_file=$project_home/version-matrix.txt
  java -DcandidateVersions="$CANDIDATE_VERSIONS" \
    -DcaseVersionsFile="$ver_file" \
    -DcaseVersionSourcesFile="$ver_src_file" \
    -DoutputFile="$version_matrix_file" \
    -DincludeCaseSpecificVersion=$includeCaseSpecificVersion \
    -cp $test_builder_jar \
    org.apache.dubbo.scenario.builder.VersionMatcher &> $version_log_file
  result=$?

  echo "version_log_file=$version_log_file"
  echo "result=$result"
  cat $version_log_file

  if [ $result -ne 0 ]; then
    #extract error msg
    error_msg=`get_error_msg $version_log_file`

    if [ $result -eq $EXIT_UNMATCHED ]; then
      echo "$log_prefix $TEST_IGNORED: Version not match:$error_msg" | tee -a $testResultFile
    else
      echo "$log_prefix $TEST_FAILURE: Generate version matrix failed:$error_msg" | tee -a $testResultFile
      if [ "$SHOW_ERROR_DETAIL" == "1" ];then
        print_log_file $scenario_name $version_log_file
      else
        echo "please check log file: $version_log_file"
      fi
    fi
    return 1
  fi

  version_count=`grep -c "" $version_matrix_file `
  echo "$log_prefix Version matrix: $version_count"
  cat $version_matrix_file

  if [ $runtime_count -gt 0 ]; then
        while read -r version_profile; do
            while read -r parameter_runtime; do
              echo  "do parameter_runtime=$parameter_runtime"
              run_test_with_version_profile "$version_profile" "$parameter_runtime" "$project_home"
            done < "$runtime_parameter_file"
        done < "$version_matrix_file"
  else
        while read -r version_profile; do
              start_time=$SECONDS
              version_no=$((version_no + 1))
              log_prefix="[${case_no}/${totalCount}] [$scenario_name:$version_no/$version_count]"

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
                echo "$log_prefix $TEST_FAILURE: Build failure with version: $version_profile, please check log: $project_home/mvn.log" | tee -a $testResultFile
                if [ "$SHOW_ERROR_DETAIL" == "1" ];then
                  cat $project_home/mvn.log
                fi
                return 1
              fi

              # list files of all dependency directories
              find `pwd` -name dependency -print -exec ls -1 {} \;

              # generate case configuration
              mkdir -p $scenario_home/logs
              scenario_builder_log=$scenario_home/logs/scenario-builder.log
              echo "$log_prefix Generating test case configuration .."
              config_time=$SECONDS
              java -Dconfigure.file=$file \
                -Dscenario.home=$scenario_home \
                -Dscenario.name=$scenario_name \
                -Dscenario.version=$version \
                -Dtest.image.version=$JAVA_VER \
                -Ddebug.service=$DEBUG \
                $version_profile \
                -jar $test_builder_jar  &> $scenario_builder_log
              result=$?
              if [ $result -ne 0 ]; then
                error_msg=`get_error_msg $scenario_builder_log`
                if [ $result == $EXIT_IGNORED ]; then
                  echo "$log_prefix $TEST_IGNORED: $error_msg" | tee -a $testResultFile
                else
                  echo "$log_prefix $TEST_FAILURE: Generate case configuration failure: $error_msg, please check log: $scenario_builder_log" | tee -a $testResultFile
                fi
                return $result
              fi

              mount_msg=`grep mount $scenario_builder_log`
              echo "$log_prefix $mount_msg"

              # run test
              echo "$log_prefix Running test case .."
              running_time=$SECONDS
              bash $scenario_home/scenario.sh
              result=$?
              end_time=$SECONDS

              mkdir $TEST_DIR/logs/$scenario_name/
              cp -f $project_home/target/logs/*.log $TEST_DIR/logs/$scenario_name/

              if [ $result == 0 ]; then
                echo "$log_prefix $TEST_SUCCESS with version: $version_profile, cost $((end_time - start_time)) s"
              else
                scenario_log=$scenario_home/logs/scenario.log
                error_msg=`get_error_msg $scenario_log`
                if [ $result == $EXIT_IGNORED ]; then
                  if [ "$error_msg" != "" ];then
                    echo "$log_prefix $TEST_IGNORED: $error_msg, version: $version_profile" | tee -a $testResultFile
                  else
                    echo "$log_prefix $TEST_IGNORED, version: $version_profile, please check logs: $scenario_home/logs" | tee -a $testResultFile
                  fi
                else
                  if [ "$error_msg" != "" ];then
                    echo "$log_prefix $TEST_FAILURE: $error_msg, version: $version_profile, please check logs: $scenario_home/logs" | tee -a $testResultFile
                  else
                    echo "$log_prefix $TEST_FAILURE, version: $version_profile, please check logs: $scenario_home/logs" | tee -a $testResultFile
                  fi
                fi

                # show test log
                if [ "$SHOW_ERROR_DETAIL" == "1" ]; then
                  for log_file in $scenario_home/logs/*.log; do
                    # ignore scenario-builder.log
                    if [[ $log_file != *scenario-builder.log ]]; then
                      print_log_file $scenario_name $log_file
                    fi
                  done
                fi
                return 1
              fi
        done < "$version_matrix_file"
  fi

  log_prefix="[${case_no}/${totalCount}] [$scenario_name]"
  echo "$log_prefix $TEST_SUCCESS: versions: $version_count, total cost $((end_time - case_start_time)) s" | tee -a $testResultFile
  # clean log files
  rm -f $project_home/*.log $project_home/version-matrix.*
}


#-----------------------------------#
# main
#-----------------------------------#
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

# prepare testcases
mkdir -p $TEST_DIR/jobs
mkdir -p $TEST_DIR/logs
testListFile=$TEST_DIR/jobs/testjob.txt
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
    base_dir="$( dirname $TEST_DIR )"
    rm -f $testListFile
    echo "Searching all '$CONFIG_FILE' under dir $base_dir .."
    find $base_dir -name $CONFIG_FILE | grep -v "$TEST_DIR" > $testListFile
  fi
fi

totalCount=`grep "" -c $testListFile`
echo "Total test cases : $totalCount"

if [ "$DEBUG" != "" ] && [ $totalCount -gt 1 ]; then
  echo "Only one case can be debugged"
  exit 1
fi

#clear test results
testResultFile=${testListFile%.*}-result-java${JAVA_VER}.txt
rm -f $testResultFile
touch $testResultFile
echo "Test results: $testResultFile"

# build scenario-builder
SCENARIO_BUILDER_DIR=$TEST_DIR/dubbo-scenario-builder
echo "Building scenario builder .."
cd $SCENARIO_BUILDER_DIR
mvn $BUILD_OPTS &> $SCENARIO_BUILDER_DIR/mvn.log
result=$?
cd $WORK_DIR
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

#check test image
check_test_image

# start run tests
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
  if [ $allTest == $totalCount ];then
    delta=0
  fi
  while [ $finishedTest -lt $totalCount ] && [ $((allTest - finishedTest)) -ge $delta ]
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
ignoredTest=`grep "$TEST_IGNORED" -c $testResultFile`

echo "----------------------------------------------------------"
echo "Test logs dir: \${project.basedir}/target/logs"
echo "Test reports dir: \${project.basedir}/target/test-reports"
echo "Test results: $testResultFile"
echo "Total cost: $((SECONDS - testStartTime)) seconds"
echo "All tests count: $totalCount"
echo "Success tests count: $successTest"
echo "Ignored tests count: $ignoredTest"
echo "Failed tests count: $failedTest"
echo "----------------------------------------------------------"

if [ $ignoredTest -gt 0 ]; then
  echo "Ignored tests: $ignoredTest"
  grep "$TEST_IGNORED" $testResultFile
  echo "----------------------------------------------------------"
fi

if [ $failedTest -gt 0 ]; then
  echo "Failed tests: $failedTest"
  grep "$TEST_FAILURE" $testResultFile
  echo "----------------------------------------------------------"
fi

echo "Total: $totalCount, Success: $successTest, Failures: $failedTest, Ignored: $ignoredTest"

if [[ $(($successTest + $ignoredTest)) == $totalCount ]]; then
  test_result=0
  echo "All tests pass"
else
  test_result=1
  if [[ $failedTest -gt 0 ]]; then
    echo "Some tests failed: $failedTest"
  elif [ $successTest -eq 0 ]; then
    echo "No test pass"
  else
    echo "Test not completed"
  fi
fi

echo "Test finished. Start to clean up environment."
echo "Killing scenario.sh .."
ps -ef | grep scenario.sh | grep -v grep  | awk '{ print $2 }' | xargs -I {} kill {}

echo "Killing docker logs procs .."
ps -ef | grep "docker logs" | grep -v grep  | awk '{ print $2 }' | xargs -I {} kill {}

echo "Killing docker-compose .."
ps -ef | grep "docker-compose" | grep "dubbo-samples" | grep -v grep  | awk '{ print $2 }' | xargs -I {} kill {}

echo "Killing dubbo containers .."
docker ps -a | grep dubbo | awk '{ print $1}' | xargs -I {} docker kill {}

echo "Removing unused networks .."
docker network prune -f

exit $test_result
