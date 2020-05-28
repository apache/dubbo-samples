set -e

export GOOS=darwin
export GOARCH=amd64

export PROFILE="dev"

export PROJECT_HOME=`pwd`

if [ -f "${PROJECT_HOME}/assembly/common/app.properties" ]; then
	. ${PROJECT_HOME}/assembly/common/app.properties
fi


if [ -f "${PROJECT_HOME}/assembly/common/build.sh" ]; then
	sh ${PROJECT_HOME}/assembly/common/build.sh
fi