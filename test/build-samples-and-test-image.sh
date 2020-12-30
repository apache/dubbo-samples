#!/bin/bash
DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"

cd $DIR
test_image_log=build-test-image.log
echo "Build test image in background .."
bash ./build-test-image.sh &> $test_image_log &

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
echo "Check test image result .."
cd $DIR
cat $test_image_log
grep "Successfully tagged dubbo/sample-test" $test_image_log > /dev/null
