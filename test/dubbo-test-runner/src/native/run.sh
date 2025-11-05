#!/bin/bash
set -x
echo "Start at: $(date "+%Y-%m-%d %H:%M:%S")"
echo "Current "`sysctl net.ipv4.ip_local_reserved_ports`

DIR=/usr/local/dubbo
cd $DIR

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

if [ "$SERVICE_TYPE" == "nativeApp"  ]; then
  script_file=$DIR/run-dubbo-native-app.sh
elif [ "$SERVICE_TYPE" == "nativeTest"  ]; then
  script_file=$DIR/run-dubbo-native-test.sh
fi

/bin/bash $script_file 2>&1
