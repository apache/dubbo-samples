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

# constants
ERROR_MSG_FLAG=":ErrorMsg:"


PRG="$0"
PRGDIR=`dirname "$PRG"`
[ -z "$SCENARIO_HOME" ] && SCENARIO_HOME=`cd "$PRGDIR" >/dev/null; pwd`
cd $SCENARIO_HOME

# set vars from freemarker
testcase_name=${scenario_name}-${scenario_version}
debug_mode=${debug_mode}
timeout=${timeout}
scenario_name=${scenario_name}
scenario_version=${scenario_version}
compose_file="${docker_compose_file}"
project_name=$(echo "${scenario_name}-${scenario_version}" |sed -e "s/\.//g" |awk '{print tolower($0)}')
test_service_name="${test_service_name}-1"
network_name="${network_name}"

service_names=( \
<#list services as service>
  "${service.name}" \
</#list>
  )

service_size=${services?size}

export service_names=$service_names
export service_size=$service_size

<#noparse>
function redirect_all_container_logs() {
  #redirect logs
  echo "Redirecting all container logs: ${service_names[@]}  .." >> $scenario_log

  while [ 1==1 ]; do
    redirect_count=0
    for service_name in ${service_names[@]};do
      redirect_container_logs "$service_name"
      result=$?
      if [ "$result" -eq 0 ];then
        redirect_count=$(( redirect_count + 1 ))
      fi
    done

    if [ "$redirect_count" == $service_size ];then
      echo "Redirect all containers logs."  >> $scenario_log
      break
    fi

    sleep 3
  done
}

function redirect_container_logs() {
  service_name=$1
  container_name=${project_name}-${service_name}-1
  # only redirect once
  ps -ef | grep "docker logs -f $container_name" | grep -v grep > /dev/null
  result=$?
  if [ $result -eq 0 ]; then
    return 0
  fi

  container_id=`docker ps -aqf "name=${container_name}"`
  if [[ -z "${container_id}" ]]; then
    return 1
  fi

  # can not get logs if status is `created`,sometimes docker-compose `depends_on` will cause a long time `created` status
  container_status=`docker inspect -f '{{.State.Status}}' ${container_name}`
  if [ "$container_status" == "created" ]; then
    echo "Wait container start: $container_name" >> $scenario_log
    return 1
  fi

  echo "Redirect container logs: $container_name" >> $scenario_log
  if [ "$debug_mode" == "1" ]; then
    # redirect container logs to file and display debug message
    docker logs -f $container_name 2>&1 | tee $SCENARIO_HOME/logs/${service_name}.log | grep "dt_socket" &
  else
    docker logs -f $container_name &> $SCENARIO_HOME/logs/${service_name}.log &
  fi
  return 0

}

function wait_container_exit() {
  container_name=$1
  start=$2
  timeout=$3

  # check and get exit code
  while [ 1 = 1 ];
  do
    sleep 2
    duration=$(( SECONDS - start ))
    if [ $duration -gt $timeout ];then
      echo "wait for container is timeout: $duration s"
      return 1
    fi

    container_id=`docker ps -aqf "name=${container_name}"`
    if [[ -z "${container_id}" ]]; then
      continue
    fi

    status=`docker inspect $container_name --format='{{.State.Status}}'`
    # test container may pending start cause by depends_on condition
#    result=$?
#    if [ $result -ne 0 ];then
#      echo "check container status failure: $result"
#      return 1
#    fi
    if [ "$status" == "exited" ];then
        return 0
    fi

  done
}

status=1

mkdir -p ${SCENARIO_HOME}/logs
scenario_log=${SCENARIO_HOME}/logs/scenario.log
rm -f $scenario_log

echo "[$scenario_name] debug_mode: $debug_mode" >> $scenario_log
echo "[$scenario_name] timeout: $timeout" >> $scenario_log

#Starting test containers
container_name="${project_name}-${test_service_name}"

#kill and clean first
echo "[$scenario_name] Killing containers .." | tee -a $scenario_log
docker compose -p ${project_name} -f ${compose_file} kill 2>&1 | tee -a $scenario_log > /dev/null

echo "[$scenario_name] Removing containers .." | tee -a $scenario_log
docker compose -p ${project_name} -f ${compose_file} rm -f 2>&1 | tee -a $scenario_log > /dev/null

# pull images
# TODO check pull timeout?
#pull_time=$SECONDS
#echo "[$scenario_name] Pulling images .." | tee -a $scenario_log
#docker-compose -p ${project_name} -f ${compose_file} pull --ignore-pull-failures 2>&1 <<< "NNN" | tee -a $scenario_log > /dev/null
#echo "Pull images cost: $((SECONDS - pull_time)) s"

#run async, cause depends_on service healthy blocking docker-compose up
redirect_all_container_logs &

# start time
start=$SECONDS

# complete pull fail interactive by <<< "NN"
echo "[$scenario_name] Starting containers .." | tee -a $scenario_log
docker compose -p ${project_name} -f ${compose_file} up -d 2>&1 <<< "NNN" | tee -a $scenario_log > /dev/null

sleep 5

# test container may pending start cause by depends_on condition
#container_id=`docker ps -aqf "name=${container_name}"`
#if [[ -z "${container_id}" ]]; then
#    echo "[$scenario_name] docker startup failure!" | tee -a $scenario_log
#    status=1
#else
    echo "[$scenario_name] Waiting for test container ${container_name} .." | tee -a $scenario_log
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
            echo "[$scenario_name] $ERROR_MSG_FLAG Run tests failed" | tee -a $scenario_log
        fi
    else
        status=1
        echo "[$scenario_name] $ERROR_MSG_FLAG Run tests timeout" | tee -a $scenario_log
    fi

    if [[ "$debug_mode" == "1" ]]; then
      echo "[$scenario_name] Waiting for debugging .." | tee -a $scenario_log
      echo "[$scenario_name] Please type 'Ctrl + C' or run the script './kill-tests.sh' to end debugging .." | tee -a $scenario_log
      # idle waiting for abort from user
      read -r -d '' _ </dev/tty
    fi

    echo "[$scenario_name] Waiting 5 seconds for jacoco agent to finish writing its exec file .." | tee -a $scenario_log
    sleep 5

    echo "[$scenario_name] Stopping containers .." | tee -a $scenario_log
    docker compose -p ${project_name} -f ${compose_file} kill 2>&1 | tee -a $scenario_log > /dev/null
#fi

if [[ $status == 0 ]];then
    docker compose -p $project_name -f $compose_file rm -f 2>&1 | tee -a $scenario_log > /dev/null
    ${removeImagesScript}
else
    for service_name in ${service_names[@]};do
        service_container_name=${project_name}-${service_name}-1
        docker wait $service_container_name > /dev/null

        echo "docker inspect $service_container_name  :" >> $scenario_log
        docker inspect $service_container_name >> $scenario_log
        echo "" >> $scenario_log

        docker logs -f $service_container_name &> $SCENARIO_HOME/logs/${service_name}.log
    done
fi

for service_name in ${service_names[@]};do
    echo "--------------$service_name-----------"
    cat $SCENARIO_HOME/logs/${service_name}.log
    echo "--------------"$service_name"_end-----------"
done

echo "--------------scenario_log-----------"
cat $scenario_log
echo "--------------scenario_log_end-----------"

# rm network
docker network rm $network_name 2>&1 | tee -a $scenario_log > /dev/null

exit $status

</#noparse>
