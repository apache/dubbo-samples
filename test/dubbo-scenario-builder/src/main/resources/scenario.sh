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

# set vars from freemarker
testcase_name=${scenario_name}-${scenario_version}
config_debug_mode=${debug_mode}
config_timeout=${timeout}
scenario_name=${scenario_name}
compose_file="${docker_compose_file}"
project_name=$(echo "${scenario_name}_${scenario_version}" |sed -e "s/\.//g" |awk '{print tolower($0)}')
test_service_name="${test_service_name}_1"
network_name="${network_name}"

<#noparse>
status=1
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
    result=$?
    if [ $result -ne 0 ];then
      echo "check container status failure: $result"
      return 1
    fi
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

#Starting test containers
container_name="${project_name}_${test_service_name}"

#kill and clean first
echo "[$scenario_name] Killing test containers .." | tee -a $scenario_log
docker-compose -p ${project_name} -f ${compose_file} kill 2>&1 | tee -a $scenario_log > /dev/null

echo "[$scenario_name] Removing test containers .." | tee -a $scenario_log
docker-compose -p ${project_name} -f ${compose_file} rm -f 2>&1 | tee -a $scenario_log > /dev/null

# complete pull fail interactive by <<< "NN"
echo "[$scenario_name] Starting test containers .." | tee -a $scenario_log
docker-compose -p ${project_name} -f ${compose_file} up -d --no-build 2>&1 <<< "NN" | tee -a $scenario_log > /dev/null

container_id=`docker ps -qf "name=${container_name}"`
if [[ -z "${container_id}" ]]; then
    echo "[$scenario_name] docker startup failure!" | tee -a $scenario_log
    status=1
else
    # check and get exit code
    wait_container_exit ${container_name} $start $timeout
    result=$?
    if [ $result -eq 0 ]; then
        result=`docker inspect ${container_name} --format='{{.State.ExitCode}}'`
        if [ $result -eq 0 ]; then
            status=0
            echo "[$scenario_name] Run tests successfully" | tee -a $scenario_log
        else
            status=$result
            echo "[$scenario_name] Run tests failed" | tee -a $scenario_log
        fi
    else
        status=1
        echo "[$scenario_name] Run tests timeout" | tee -a $scenario_log
    fi

    echo "[$scenario_name] Stopping test containers .." | tee -a $scenario_log
    docker-compose -p ${project_name} -f ${compose_file} kill 2>&1 | tee -a $scenario_log > /dev/null

fi

</#noparse>

#copy logs
echo "Copying container logs .." >> $scenario_log

<#list services as service>
service_name="${service.name}"
<#noparse>
docker logs ${project_name}_${service_name}_1 &> $SCENARIO_HOME/logs/${service_name}.log
</#noparse>

</#list>

<#noparse>
if [[ "$debug_mode" != "1" && $status == 0 ]];then
    docker-compose -p $project_name -f $compose_file rm -f 2>&1 | tee -a $scenario_log > /dev/null
    ${removeImagesScript}
fi

# clear network
docker network rm $network_name 2>&1 | tee -a $scenario_log > /dev/null

exit $status

</#noparse>