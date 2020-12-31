#!/bin/bash
DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"

test_image_log=build-test-image.log
export DIR=$DIR
export test_image_log=$test_image_log

cd $DIR
echo "Build test image in background .."
bash ./build-test-image.sh &> $test_image_log &
build_image_pid=$!

echo "Build samples .."
BUILD_OPTS="-U --batch-mode --no-transfer-progress --settings .mvn/settings.xml"
BUILD_OPTS="$BUILD_OPTS clean package dependency:copy-dependencies -DskipTests"
cd $DIR/..
./mvnw $BUILD_OPTS
result=$?
if [ $result -ne 0 ];then
  echo "Build dubbo-samples with maven failed"
  exit $result
fi

echo "-------------------------------------------------------------------"
echo "Waiting test image building: $build_image_pid .."
tail -f $DIR/$test_image_log &
wait $build_image_pid
grep "Successfully tagged dubbo/sample-test" $DIR/$test_image_log > /dev/null
