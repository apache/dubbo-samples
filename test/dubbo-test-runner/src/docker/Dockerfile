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

RUN apt-get update && \
    apt-get upgrade -y && \
    apt-get install -y telnet && \
    apt-get clean

VOLUME /usr/local/dubbo/app

# scenario home dir in host os, such as 'some_project/target'
ENV SCENARIO_HOME=""

# classpath
# assert mounting $SCENARIO_HOME:/usr/local/dubbo/app/
ENV TEST_CLASSES_DIR="app/test-classes"
ENV APP_CLASSES_DIR="app/classes"
ENV APP_DEPENDENCY_DIR="app/dependency"

# service type: app, test
ENV SERVICE_TYPE="app"
ENV WAIT_PORTS_BEFORE_RUN=""

# dubbo provider bootstrap class
ENV APP_MAIN_CLASS=""
ENV CHECK_PORTS_AFTER_RUN=""
ENV CHECK_LOG=""

# test envs
ENV TEST_PATTERNS="**/*IT.class"

# Jvm flags
ENV JAVA_OPTS=""


ADD *.sh /usr/local/dubbo/
ADD dubbo-test-runner-*-jar-with-dependencies.jar /usr/local/dubbo/dubbo-test-runner.jar

WORKDIR /usr/local/dubbo/
#ENTRYPOINT exec java $JAVA_OPTS -jar dubbo-test-runner.jar $TEST_CLASSES_DIR $APP_CLASSES_DIR $APP_DEPENDENCY_DIR
ENTRYPOINT ["bash", "-x", "./run.sh"]


