#   Licensed to the Apache Software Foundation (ASF) under one or more
#   contributor license agreements.  See the NOTICE file distributed with
#   this work for additional information regarding copyright ownership.
#   The ASF licenses this file to You under the Apache License, Version 2.0
#   (the "License"); you may not use this file except in compliance with
#   the License.  You may obtain a copy of the License at
#
#       http://www.apache.org/licenses/LICENSE-2.0
#
#   Unless required by applicable law or agreed to in writing, software
#   distributed under the License is distributed on an "AS IS" BASIS,
#   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
#   See the License for the specific language governing permissions and
#   limitations under the License.

ARG JAVA_VER=8
FROM eclipse-temurin:${JAVA_VER}-jdk

ARG DEBIAN_MIRROR
RUN if [ -n "$DEBIAN_MIRROR" ]; then \
  sed -i "s@http://deb.debian.org@${DEBIAN_MIRROR}@g" /etc/apt/sources.list && \
  sed -i "s@http://security.debian.org@${DEBIAN_MIRROR}@g" /etc/apt/sources.list && \
  cat /etc/apt/sources.list ; fi

RUN apt-get update && \
  apt-get install -y telnet && \
  rm -rf /var/lib/apt/lists/*

VOLUME /usr/local/dubbo/app /usr/local/dubbo/logs

# JAVA_OPTS/DEBUG_OPTS: Jvm flags
# SERVICE_TYPE: app, test
# WAIT_PORTS_BEFORE_RUN: wait ports before run
# WAIT_TIMEOUT: wait ports timeout
# TEST_PATTERNS: test class pattern
# classpath: assert mounting $project_dir/target:/usr/local/dubbo/app/
# CHECK_PORTS: health check ports
# CHECK_LOG: health check log
ENV JAVA_OPTS="" \
    DEBUG_OPTS="" \
    TEST_CLASSES_DIR="/usr/local/dubbo/app/test-classes" \
    APP_CLASSES_DIR="/usr/local/dubbo/app/classes" \
    APP_DEPENDENCY_DIR="/usr/local/dubbo/app/dependency" \
    SERVICE_NAME="" \
    SERVICE_TYPE="app" \
    WAIT_TIMEOUT=180 \
    WAIT_PORTS_BEFORE_RUN="" \
    RUN_DELAY=0 \
    APP_MAIN_CLASS="" \
    CHECK_PORTS="" \
    CHECK_LOG="" \
    TEST_PATTERNS="**/*IT.class"


ADD dubbo-test-runner.jar *.sh jctools-core.jar /usr/local/dubbo/

WORKDIR /usr/local/dubbo/
#ENTRYPOINT exec java $JAVA_OPTS -jar dubbo-test-runner.jar $TEST_CLASSES_DIR $APP_CLASSES_DIR $APP_DEPENDENCY_DIR
ENTRYPOINT ["bash", "./run.sh"]


