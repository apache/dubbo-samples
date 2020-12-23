#!/usr/bin/env bash

# Licensed to the Apache Software Foundation (ASF) under one
# or more contributor license agreements.  See the NOTICE file
# distributed with this work for additional information
# regarding copyright ownership.  The ASF licenses this file
# to you under the Apache License, Version 2.0 (the
# "License"); you may not use this file except in compliance
# with the License.  You may obtain a copy of the License at
#
#     http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.

PRG="$0"
PRGDIR=`dirname "$PRG"`
[ -z "$SCENARIO_HOME" ] && SCENARIO_HOME=`cd "$PRGDIR" >/dev/null; pwd`
cd $SCENARIO_HOME

testcase_name=${scenario_name}-${scenario_version}
config_debug_mode=${debug_mode}
config_timeout=${timeout}
scenario_name=${scenario_name}

status=1

<#noparse>
start=$SECONDS

mkdir -p ${SCENARIO_HOME}/logs
scenario_log=${SCENARIO_HOME}/logs/scenario.log
rm -f $scenario_log

# overrite configs
debug_mode=${debug_mode:-$config_debug_mode}
timeout=${timeout:-$config_timeout}
echo "[$scenario_name] debug_mode: $debug_mode" >> $scenario_log
echo "[$scenario_name] timeout: $timeout" >> $scenario_log

function wait_container_exit() {
  container_name=$1
  start=$2
  timeout=$3

  # check and get exit code
  while [ 1 = 1 ];
  do
    status=`docker inspect $container_name --format='{{.State.Status}}'`
    if [ "$status" == "exited" ];then
        return 0
    fi

    duration=$(( SECONDS - start ))
    if [ $duration -gt $timeout ];then
      echo "wait for container is timeout: $duration s"
      return 1
    fi
    sleep 2
  done
}
</#noparse>

${running_script}

exit $status

