allTestFolders=`ls | grep dubbo-samples`

echo "extra params: $*"

allTest=0
allSuccess=0
failedTest=()

for testFolder in $allTestFolders
do


echo "Running test for $testFolder"

./mvnw -pl $testFolder $*

testResult=$?

echo "testResult is $testResult for $testFolder"
allTest=$((allTest + 1))

if [ $testResult == 0 ]
then
   allSuccess=$((allSuccess + 1))
else
   failedTest+=($testFolder)
fi

done


echo "All test count: $allTest"
echo "Success test count: $allSuccess"

if [ $allSuccess == $allTest ]
then
   echo "All test pass"
   exit 0
else
   echo "Some test fail: ${failedTest[@]}"
   exit 1
fi

