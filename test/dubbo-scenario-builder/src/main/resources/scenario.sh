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
namespace_name="${namespace_name}"

service_names=( \
<#list services as service>
  "${service.name}" \
</#list>
<#list test_services as service>
  "${service.name}" \
</#list>
)

test_service_names=(\
<#list test_services as service>
  "${service.name}" \
</#list>
)

test_service_size=${test_services?size}
service_size=$(expr ${services?size} + $test_service_size)

export service_names=$service_names
export service_size=$service_size
export test_service_size=$test_service_size

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
  pod_name=$(kubectl get pod -l app=${service_name} -o jsonpath='{.items[0].metadata.name}' -n ${namespace_name} 2>/dev/null)
  if [ -z "$pod_name" ]; then
     return 1
  fi
  # Check if logs are already being redirected
  if kubectl logs $pod_name &>/dev/null; then
    return 0
  fi

  # Check if the pod is still in ContainerCreating state
  pod_status=$(kubectl get pod $pod_name -n ${namespace_name} -o jsonpath='{.status.phase}' 2>/dev/null)
  if [ "$pod_status" == "Pending" ]; then
    echo "Pod $pod_name is still in Pending state. Cannot redirect logs."
    return 1
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

function wait_pods_completion() {
  test_pod_names=$1
  start=$2
  timeout=$3

  job_pids=()
  for test_pod_name in "${test_pod_names[@]}"; do
    # Start the wait_pod_completion function as a background job
    wait_pod_completion "$test_pod_name" "$start" "$timeout" &

    # Store the background job PID
    job_pids+=($!)
  done

  # Wait for all background jobs to finish
  for job_pid in "${job_pids[@]}"; do
    wait "$job_pid"
    test_results+=($?)
  done
  result=0
  for test_result in "${test_results[@]}"; do
    if [[ "$test_result" -ne 0 ]]; then
      result=1
      break
    fi
  done
  return $result
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

    pod_status=$(kubectl get job ${test_service_name} -o jsonpath='{.status.conditions[0].type}' -n ${namespace_name} 2>/dev/null)
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

cat ${compose_file}
apt-get install -y tree
tree /home/runner/work/dubbo-samples/dubbo-samples/99-integration/dubbo-samples-registry-test-curator/target

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

# Initialize an empty array to store test_pod_names
test_pod_names=()
for test_service_name in ${test_service_names[@]}; do

  test_pod_name=$(kubectl get pod -l app=${test_service_name} -o jsonpath='{.items[0].metadata.name}' -n ${namespace_name} 2> /dev/null)
  if [ -z "$test_pod_name" ]; then
      echo "[$scenario_name] Test Pod $test_service_name not found" | tee -a $scenario_log
      exit 1
  fi
   # Add test_pod_name to the test_pod_names array
   test_pod_names+=("$test_pod_name")
done

echo "Test Pod Names: ${test_pod_names[@]}"

echo "[$scenario_name] Waiting for test pod .." | tee -a $scenario_log
wait_pods_completion $test_pod_names $start $timeout
result=$?
if [ $result -eq 0 ]; then
    succeeded_count=0
    for test_service_name in "${test_service_names[@]}"; do
      test_succeeded=$(kubectl get job "$test_service_name" -o jsonpath='{.status.succeeded}' -n "$namespace_name" 2> /dev/null)
      if [ -z "$test_succeeded" ]; then
        test_succeeded=0
      fi
      succeeded_count=$((succeeded_count + test_succeeded))
    done

    if [ "$succeeded_count" -eq $test_service_size ]; then
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
        service_pod_name=$(kubectl get pod -l app=${service_name} -o jsonpath='{.items[0].metadata.name}' -n ${namespace_name} 2>&1  > /dev/null)

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