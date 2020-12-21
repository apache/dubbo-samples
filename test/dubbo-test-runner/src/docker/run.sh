#!/bin/bash

DIR=/usr/local/dubbo/
cd $DIR

source $DIR/utils.sh

if [ "$APP_CLASSES_DIR" == "" ]; then
  echo "Missing env 'APP_CLASSES_DIR'"
  return 1
fi

if [ "$APP_DEPENDENCY_DIR" == "" ]; then
  echo "Missing env 'APP_DEPENDENCY_DIR'"
  return 1
fi

if [ "$SERVICE_TYPE" == "app"  ]; then
  /bin/bash -x $DIR/run-dubbo-app.sh
  #/bin/bash $DIR/run-dubbo-app.sh | while IFS= read -r line; do printf '[%s] %s\n' "$(date '+%Y-%m-%d %H:%M:%S')" "$line"; done
elif [ "$SERVICE_TYPE" == "test"  ]; then
  /bin/bash -x $DIR/run-dubbo-test.sh
  #/bin/bash $DIR/run-dubbo-test.sh | while IFS= read -r line; do printf '[%s] %s\n' "$(date '+%Y-%m-%d %H:%M:%S')" "$line"; done
fi

