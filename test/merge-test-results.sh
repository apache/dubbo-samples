#!/bin/bash

DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"
cd $DIR

TEST_SUCCESS="TEST SUCCESS"
TEST_FAILURE="TEST FAILURE"

JAVA_VER=${JAVA_VER:-8}
echo "JAVA_VER: $JAVA_VER"

RESULT_SUFFIX=result-java${JAVA_VER}.txt
mergedTestResultFile=jobs/merged-test-$RESULT_SUFFIX
rm -rf $mergedTestResultFile

echo "All test results:"
for resultFile in jobs/*$RESULT_SUFFIX; do
  echo "$resultFile:"
  cat $resultFile
  echo ""
done

cat jobs/*$RESULT_SUFFIX > $mergedTestResultFile
successTest=`grep -c "$TEST_SUCCESS" $mergedTestResultFile`
failedTest=`grep -c "$TEST_FAILURE" $mergedTestResultFile`
totalCount=`grep -c "" $mergedTestResultFile`

echo "----------------------------------------------------------"
echo "All tests count: $totalCount"
echo "Success tests count: $successTest"

if [ $successTest == $totalCount ]; then
  if [ $successTest -gt 0 ]; then
    echo "All tests pass"
    echo "----------------------------------------------------------"
    exit 0
  else
    echo "None test pass, test fail"
    echo "----------------------------------------------------------"
    exit 1
  fi
else
  echo "Exception : some tests fail: $failedTest"
  echo "----------------------------------------------------------"
  echo "Fail tests:"
  grep "$TEST_FAILURE" jobs/testjob*$RESULT_SUFFIX
  echo "----------------------------------------------------------"
  exit 1
fi

