#!/bin/bash

echo "Start at: $(date "+%Y-%m-%d %H:%M:%S")"
echo "Current "`sysctl net.ipv4.ip_local_reserved_ports`

DIR=/usr/local/dubbo
cd $DIR

#LOG_DIR=/usr/local/dubbo/logs
#if [ ! -d $LOG_DIR ];then
#  mkdir -p $LOG_DIR
#fi
#LOG_FILE=$LOG_DIR/$SERVICE_NAME.log
#rm -f $LOG_FILE

source $DIR/utils.sh

function print_log() {
    msg=$1
    echo $msg
#    echo $msg | tee -a $LOG_FILE
}

if [ "$SERVICE_NAME" == "" ]; then
  print_log "Missing env 'SERVICE_NAME'"
  return 1
fi

if [ "$APP_CLASSES_DIR" == "" ]; then
  print_log "Missing env 'APP_CLASSES_DIR'"
  return 1
fi

if [ "$APP_DEPENDENCY_DIR" == "" ]; then
  print_log "Missing env 'APP_DEPENDENCY_DIR'"
  return 1
fi

if [ "$SERVICE_TYPE" == "app"  ]; then
  script_file=$DIR/run-dubbo-app.sh
elif [ "$SERVICE_TYPE" == "test"  ]; then
  script_file=$DIR/run-dubbo-test.sh
fi

/bin/bash $script_file 2>&1

#/bin/bash $script_file 2>&1 | tee -a $LOG_FILE
## get proc exitcode before tee, use $PIPESTATUS variable instead of $? (https://stackoverflow.com/a/6871917)
#result=${PIPESTATUS[0]}
#exit $result
