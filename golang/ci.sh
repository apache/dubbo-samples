allSamples=`find .|grep /assembly/linux/dev.sh | awk '{gsub("/assembly/linux/dev.sh","");print}'`
currentFolder=`pwd`

for singleSample in $allSamples
do

cd $singleSample

sh ./assembly/linux/dev.sh

cd $currentFolder

done