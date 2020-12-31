#!/bin/bash
DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"

test_image_log=$DIR/test-image.log
samples_log=$DIR/samples.log

export DIR=$DIR
export test_image_log=$test_image_log
export samples_log=$samples_log

cd $DIR
echo "Build test image .."
bash ./build-test-image.sh &> $test_image_log &
build_image_pid=$!

echo "Build samples .."
BUILD_OPTS="-U --batch-mode --no-transfer-progress --settings .mvn/settings.xml"
BUILD_OPTS="$BUILD_OPTS clean package dependency:copy-dependencies -DskipTests"
cd $DIR/..
./mvnw $BUILD_OPTS &> $samples_log &
build_samples_pid=$!

echo "-------------------------------------------------------------------"
echo "Waiting test image building: $build_image_pid .."
tail -n 1000 -f $test_image_log &
wait $build_image_pid
result=$?
if [ $result -ne 0 ];then
  echo "Build test image failed"
  exit $result
fi

echo ""
echo "-------------------------------------------------------------------"
echo "Waiting samples building: $build_samples_pid .."
tail -n 500 -f $samples_log &
wait $build_samples_pid
result=$?
if [ $result -ne 0 ];then
  echo "Build dubbo-samples with maven failed"
  exit $result
fi
