#!/bin/bash

DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"
cd $DIR

TEST_SUCCESS="TEST SUCCESS"
TEST_FAILURE="TEST FAILURE"

testResultFile=jobs/merged-test-result.txt
cat jobs/*-result.txt > $testResultFile
successTest=`grep -c "$TEST_SUCCESS" $testResultFile`
failedTest=`grep -c "$TEST_FAILURE" $testResultFile`
totalCount=`grep -c "" $testResultFile`

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
  grep "$TEST_FAILURE" jobs/testcases-*-result.txt
  echo "----------------------------------------------------------"
  exit 1
fi

