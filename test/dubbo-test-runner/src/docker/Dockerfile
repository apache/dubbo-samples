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

FROM openjdk:8

ARG DEBIAN_MIRROR
RUN if [ -n "$DEBIAN_MIRROR" ]; then \
  sed -i "s@http://deb.debian.org@${DEBIAN_MIRROR}@g" /etc/apt/sources.list && \
  sed -i "s@http://security.debian.org@${DEBIAN_MIRROR}@g" /etc/apt/sources.list && \
  cat /etc/apt/sources.list ; fi

RUN apt-get update && \
  apt-get install -y telnet && \
  rm -rf /var/lib/apt/lists/*

VOLUME /usr/local/dubbo/app
VOLUME /usr/local/dubbo/logs

# Jvm flags
ENV JAVA_OPTS=""
ENV DEBUG_OPTS=""

# service name
ENV SERVICE_NAME=""

# classpath
# assert mounting $project_dir/target:/usr/local/dubbo/app/
ENV TEST_CLASSES_DIR="/usr/local/dubbo/app/test-classes"
ENV APP_CLASSES_DIR="/usr/local/dubbo/app/classes"
ENV APP_DEPENDENCY_DIR="/usr/local/dubbo/app/dependency"

# check ports/log timeout
ENV CHECK_TIMEOUT=60

# service type: app, test
ENV SERVICE_TYPE="app"
ENV WAIT_PORTS_BEFORE_RUN=""

# dubbo provider bootstrap class
ENV APP_MAIN_CLASS=""
ENV CHECK_PORTS_AFTER_RUN=""
ENV CHECK_LOG=""

# test envs
ENV TEST_PATTERNS="**/*IT.class"


ADD *.sh /usr/local/dubbo/
ADD dubbo-test-runner-*-jar-with-dependencies.jar /usr/local/dubbo/dubbo-test-runner.jar

WORKDIR /usr/local/dubbo/
#ENTRYPOINT exec java $JAVA_OPTS -jar dubbo-test-runner.jar $TEST_CLASSES_DIR $APP_CLASSES_DIR $APP_DEPENDENCY_DIR
ENTRYPOINT ["bash", "-x", "./run.sh"]


