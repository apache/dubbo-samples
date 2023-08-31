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
compose_file="${kubernetes_manifest_file}"
project_name=$(echo "${scenario_name}_${scenario_version}" |sed -e "s/\.//g" |awk '{print tolower($0)}')
test_service_name="${test_service_name}"
namespace_name="${namespace_name}"

service_names=( \
<#list services as service>
  "${service.name}" \
</#list>
  "${test_service_name}"
  )

service_size=$(expr ${services?size} + 1)

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
      return
    fi

    sleep 3
  done
}

function redirect_container_logs() {
  service_name=$1
  pod_name=$(kubectl get pod -l app=${service_name} -o jsonpath='{.items[0].metadata.name}' -n ${namespace_name})

  # Check if logs are already being redirected
  if kubectl logs $pod_name &>/dev/null; then
    return 0
  fi

  # Redirect container logs to a file
  echo "Redirect container logs for pod $pod_name" >> $scenario_log
  if [ "$debug_mode" == "1" ]; then
    # Redirect with debug message
    kubectl logs -f $pod_name -n ${namespace_name} 2>&1 | tee $SCENARIO_HOME/logs/${service_name}.log | grep "dt_socket" &
  else
    kubectl logs -f $pod_name -n ${namespace_name} &> $SCENARIO_HOME/logs/${service_name}.log &
  fi
  return 0
}


function wait_pod_completion() {
  test_pod_name=$1
  start=$2
  timeout=$3

  # Check and wait for pod completion
  while [ 1 = 1 ];
  do
    sleep 2
    duration=$(( SECONDS - start ))
    if [ $duration -gt $timeout ]; then
      echo "Waiting for job is timeout: $duration s"
      return 1
    fi

    pod_status=$(kubectl get job ${test_service_name} -o jsonpath='{.status.conditions[0].type}' -n ${namespace_name})
    if [[ "$pod_status" == "Complete" || "$pod_status" == "Failed" ]]; then
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


#Delete the resources in Kubernetes-manifest first.
echo "[$scenario_name] Deleting resources .." | tee -a $scenario_log
kubectl delete -f ${compose_file} --grace-period=0 --force 2>&1 | tee -a $scenario_log > /dev/null

# start time
start=$SECONDS

# Apply Kubernetes manifest
echo "[$scenario_name] Creating resources .." | tee -a $scenario_log
kubectl apply -f ${compose_file} 2>&1 | tee -a $scenario_log > /dev/null

sleep 8

redirect_all_container_logs &

# Get the name of the test Pod
test_pod_name=$(kubectl get pod -l app=${test_service_name} -o jsonpath='{.items[0].metadata.name}' -n ${namespace_name})

if [ -z "$test_pod_name" ]; then
    echo "[$scenario_name] Test Pod not found" | tee -a $scenario_log
    exit 1
fi

echo "[$scenario_name] Waiting for test pod .." | tee -a $scenario_log
wait_pod_completion $test_pod_name $start $timeout
result=$?
if [ $result -eq 0 ]; then
    # Since the number of retries of test is set to 1, it is only necessary to judge here.
    succeeded_count=$(kubectl get job ${test_service_name} -o jsonpath='{.status.succeeded}' -n $namespace_name)
    if [ -z "$succeeded_count" ]; then
        succeeded_count=0
    fi
    if [ "$succeeded_count" -eq 1 ]; then
        status=0
        echo "[$scenario_name] Run tests successfully" | tee -a $scenario_log
    else
        status=1
        echo "[$scenario_name] $ERROR_MSG_FLAG Run tests failed" | tee -a $scenario_log
    fi

else
    status=1
    echo "[$scenario_name] $ERROR_MSG_FLAG Run tests timeout" | tee -a $scenario_log
fi

# TODO: Handle debugging in Kubernetes (if needed)
# if [[ "$debug_mode" == "1" ]]; then
#     echo "[$scenario_name] Waiting for debugging .." | tee -a $scenario_log
#     echo "[$scenario_name] Please debug the pod using 'kubectl exec' or other tools .." | tee -a $scenario_log
#      echo "[$scenario_name] Please type 'Ctrl + C' or run the script './kill-tests.sh' to end debugging .." | tee -a $scenario_log
#     Implement interactive debugging logic if needed
# fi



if [[ $status == 0 ]]; then
    echo "[$scenario_name] Deleting resources .." | tee -a $scenario_log
    kubectl delete -f ${compose_file} --grace-period=0 --force 2>&1 | tee -a $scenario_log > /dev/null
else
    for service_name in ${service_names[@]}; do
        service_pod_name=$(kubectl get pod -l app=${service_name} -o jsonpath='{.items[0].metadata.name}' -n ${namespace_name})

        if [ -n "$service_pod_name" ]; then
            kubectl wait pod $service_pod_name --for=delete > /dev/null

            echo "kubectl describe pod $service_pod_name -n ${namespace_name}  :" >> $scenario_log
            kubectl describe pod $service_pod_name -n ${namespace_name}>> $scenario_log
            echo "" >> $scenario_log

            kubectl logs -f $service_pod_name -n ${namespace_name} > $SCENARIO_HOME/logs/${service_name}.log
        fi
    done
    echo "[$scenario_name] Deleting resources .." | tee -a $scenario_log
    kubectl delete -f ${compose_file} --grace-period=0 --force 2>&1 | tee -a $scenario_log > /dev/null
fi

exit $status

</#noparse>