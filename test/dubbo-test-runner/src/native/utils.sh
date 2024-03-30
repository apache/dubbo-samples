#!/bin/bash

function check_tcp_port() {
    host=$1
    port=$2
    start=$3
    timeout=$4

    start=${start:-$SECONDS}
    timeout=${timeout:-60}

    if [[ "$host" == "" || "$port" == "" ]]; then
      echo "invalid args, usage: check_tcp_port <host> <port> [start_at_seconds] [timeout_in_seconds]"
      return 1
    fi

    echo "checking tcp port [$host:$port] ..."
    for ((i=1; i<=${timeout}; i++));
    do
      # sometimes nc/ping is too slowly, why?
      #nc -zv -w2 $host $port

      # connect to remote host:port by telnet, and auto close it
      echo -e '\x1dclose\x0d' | telnet $host $port 2>&1
      result=$?
      if [ $result -eq 0 ]; then
        echo "check tcp port [$host:$port] success."
        return 0
      fi

      duration=$(( SECONDS - start ))
      if [ $duration -gt $timeout ];then
        echo "check tcp port [$host:$port] is timeout: $duration s"
        return 1
      fi
      sleep 1
    done

    duration=$(( SECONDS - start ))
    echo "check tcp port [$host:$port] is timeout: $duration s"
    return 1
}

function split_and_check_tcp_ports() {
  addrs_str=$1
  start=$2
  timeout=$3

  start=${start:-$SECONDS}
  timeout=${timeout:-60}
  echo "checking tcp ports: $addrs_str, start at: $start, timeout: $timeout"

  addr_ports=$(echo $addrs_str | tr ";" "\n")
  for addr_port in $addr_ports
  do
    # process
    host=${addr_port%:*}
    port=${addr_port#*:}

    check_tcp_port $host $port $start $timeout
    result=$?
    if [ $result -ne 0 ]; then
      return $result
    fi
  done
}

# examples
#sleep 3
#split_and_check_tcp_ports "$@" $SECONDS  10


# check and wait for log message
function check_log() {
  file="$1"
  match_str="$2"
  start=$3
  timeout=$4

  start=${start:-$SECONDS}
  timeout=${timeout:-60}

  echo "checking log [$file], matching str: $match_str .."
  for ((i=1; i<=${timeout}; i++));
  do
    grep "$match_str" "$file"
    result=$?
    if [ $result -eq 0 ];then
      return 0
    fi

    duration=$(( SECONDS - start ))
    if [ $duration -gt $timeout ];then
      echo "check log [$file] for matching string [$match_str] is timeout: $duration s"
      return 1
    fi

    sleep 2
  done

  duration=$(( SECONDS - start ))
  echo "check log [$file] for matching string [$match_str] is timeout: $duration s"
  return 1
}

# examples
#sleep 3
#check_log "$@" $SECONDS  10
