#!/bin/bash

# apollo config db info
db_address=${DB_ADDRESS:-localhost:3306}

apollo_config_db_url=jdbc:mysql://$db_address/ApolloConfigDB?characterEncoding=utf8
apollo_config_db_username=root
apollo_config_db_password=

# apollo portal db info
apollo_portal_db_url=jdbc:mysql://$db_address/ApolloPortalDB?characterEncoding=utf8
apollo_portal_db_username=root
apollo_portal_db_password=

# =============== Please do not modify the following content =============== #

if [ "$(uname)" == "Darwin" ]; then
    windows="0"
elif [ "$(expr substr $(uname -s) 1 5)" == "Linux" ]; then
    windows="0"
elif [ "$(expr substr $(uname -s) 1 5)" == "MINGW" ]; then
    windows="1"
else
    windows="0"
fi

# meta server url
config_server_url=${CONFIG_SERVER_URL:-http://localhost:8080}
admin_server_url=http://localhost:8090
eureka_service_url=$config_server_url/eureka/
portal_url=http://localhost:8070

# JAVA OPTS
BASE_JAVA_OPTS="-Denv=dev -Xmx200m"
# Disalbe disk free space check of SpringBoot
BASE_JAVA_OPTS="$BASE_JAVA_OPTS -Dmanagement.health.diskspace.enabled=false"
CLIENT_JAVA_OPTS="$BASE_JAVA_OPTS -Dapollo.meta=$config_server_url"
SERVER_JAVA_OPTS="$BASE_JAVA_OPTS -Dspring.profiles.active=github -Deureka.service.url=$eureka_service_url -Deureka.client.registry-fetch-interval-seconds=10"
PORTAL_JAVA_OPTS="$BASE_JAVA_OPTS -Ddev_meta=$config_server_url -Dspring.profiles.active=github,auth -Deureka.client.enabled=false -Dhibernate.query.plan_cache_max_size=192"

# executable
JAR_FILE=apollo-all-in-one.jar
SERVICE_DIR=./service
LOG_DIR=./logs
SERVICE_JAR_NAME=apollo-service.jar
SERVICE_JAR=$SERVICE_DIR/$SERVICE_JAR_NAME
SERVICE_LOG=$LOG_DIR/apollo-service.log
ADMIN_LOG=$LOG_DIR/apollo-admin.log
PORTAL_DIR=./portal
PORTAL_JAR_NAME=apollo-portal.jar
PORTAL_JAR=$PORTAL_DIR/$PORTAL_JAR_NAME
PORTAL_LOG=$LOG_DIR/apollo-portal.log
CLIENT_DIR=./client
CLIENT_JAR=$CLIENT_DIR/apollo-demo.jar

# go to script directory
cd "${0%/*}"

mkdir -p $LOG_DIR

function checkJava {
  if [[ -n "$JAVA_HOME" ]] && [[ -x "$JAVA_HOME/bin/java" ]];  then
      if [ "$windows" == "1" ]; then
        tmp_java_home=`cygpath -sw "$JAVA_HOME"`
        export JAVA_HOME=`cygpath -u "$tmp_java_home"`
        echo "Windows new JAVA_HOME is: $JAVA_HOME"
      fi
      _java="$JAVA_HOME/bin/java"
  elif type -p java > /dev/null; then
    _java=java
  else
      echo "Could not find java executable, please check PATH and JAVA_HOME variables."
      exit 1
  fi

  if [[ "$_java" ]]; then
      version=$("$_java" -version 2>&1 | awk -F '"' '/version/ {print $2}')
      if [[ "$version" < "1.8" ]]; then
          echo "Java version is $version, please make sure java 1.8+ is in the path"
          exit 1
      fi
  fi
}

function checkServerAlive {
  declare -i counter=0
  declare -i max_counter=100 # 24*5=120s
  declare -i total_time=0

  SERVER_URL="$1"

  until [[ (( counter -ge max_counter )) || "$(curl -X GET --silent --connect-timeout 1 --max-time 2 --head $SERVER_URL | grep "HTTP")" != "" ]];
  do
    printf "."
    counter+=1
    sleep 5
  done

  total_time=counter*5

  if [[ (( counter -ge max_counter )) ]];
  then
    return $total_time
  fi

  return 0
}

checkJava

if [ "$1" = "start" ] ; then
  # wait for database ready
  sleep 5

  if [ "$APOLLO_SERVICE" == "1" ];then
    echo "==== starting service ===="
    echo "Service logging file is $SERVICE_LOG"
    export JAVA_OPTS="$SERVER_JAVA_OPTS -Dlogging.file=./apollo-service.log -Dspring.datasource.url=$apollo_config_db_url -Dspring.datasource.username=$apollo_config_db_username -Dspring.datasource.password=$apollo_config_db_password"

    if [[ -f $SERVICE_JAR ]]; then
      rm -rf $SERVICE_JAR
    fi

    ln $JAR_FILE $SERVICE_JAR
    chmod a+x $SERVICE_JAR

    java $JAVA_OPTS -jar $SERVICE_JAR --configservice --adminservice &> $SERVICE_LOG &

    rc=$?
    if [[ $rc != 0 ]];
    then
      echo "Failed to start service, return code: $rc. Please check $SERVICE_LOG for more information."
      exit $rc;
    fi

    printf "Waiting for config service startup"
    checkServerAlive $config_server_url
    rc=$?
    if [[ $rc != 0 ]];
    then
      printf "\nConfig service failed to start in $rc seconds! Please check $SERVICE_LOG for more information.\n"
      exit 1;
    fi
    printf "\nConfig service started. You may visit $config_server_url for service status now!\n"

    printf "Waiting for admin service startup"
    checkServerAlive $admin_server_url
    rc=$?
    if [[ $rc != 0 ]];
    then
      printf "\nAdmin service failed to start in $rc seconds! Please check $SERVICE_LOG for more information.\n"
      exit 1;
    fi
    printf "\nAdmin service started\n"
  fi

  if [ "$APOLLO_PORTAL" == "1" ]; then
    echo "==== starting portal ===="
    echo "Portal logging file is $PORTAL_LOG"
    export JAVA_OPTS="$PORTAL_JAVA_OPTS -Dlogging.file=./apollo-portal.log -Dserver.port=8070 -Dspring.datasource.url=$apollo_portal_db_url -Dspring.datasource.username=$apollo_portal_db_username -Dspring.datasource.password=$apollo_portal_db_password"

    if [[ -f $PORTAL_JAR ]]; then
      rm -rf $PORTAL_JAR
    fi

    ln $JAR_FILE $PORTAL_JAR
    chmod a+x $PORTAL_JAR

    java $JAVA_OPTS -jar $PORTAL_JAR --portal &> $PORTAL_LOG &
    rc=$?
    if [[ $rc != 0 ]];
    then
      echo "Failed to start portal, return code: $rc. Please check $PORTAL_LOG for more information."
      exit $rc;
    fi

    printf "Waiting for portal startup"
    checkServerAlive $portal_url
    rc=$?
    if [[ $rc != 0 ]];
    then
      printf "\nPortal failed to start in $rc seconds! Please check $PORTAL_LOG for more information.\n"
      exit 1;
    fi
    printf "\nPortal started. You can visit $portal_url now!\n"
  fi

  wait
  echo "apollo exit"

elif [ "$1" = "client" ] ; then
  if [ "$windows" == "1" ]; then
    java -classpath "$CLIENT_DIR;$CLIENT_JAR" $CLIENT_JAVA_OPTS com.ctrip.framework.apollo.demo.api.SimpleApolloConfigDemo
  else
    java -classpath $CLIENT_DIR:$CLIENT_JAR $CLIENT_JAVA_OPTS com.ctrip.framework.apollo.demo.api.SimpleApolloConfigDemo
  fi

elif [ "$1" = "stop" ] ; then
  echo "==== stopping portal ===="
  cd $PORTAL_DIR
  ./$PORTAL_JAR_NAME stop

  cd ..

  echo "==== stopping service ===="
  cd $SERVICE_DIR
  ./$SERVICE_JAR_NAME stop

else
  echo "Usage: demo.sh ( commands ... )"
  echo "commands:"
  echo "  start         start services and portal"
  echo "  client        start client demo program"
  echo "  stop          stop services and portal"
  exit 1
fi
