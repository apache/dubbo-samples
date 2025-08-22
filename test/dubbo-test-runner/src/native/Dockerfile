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

FROM ghcr.io/graalvm/native-image-community:17-ol7

RUN yum -y update && \
    yum install -y telnet && \
    yum install -y wget && \
    yum install -y gzip

RUN wget https://dlcdn.apache.org/maven/maven-3/3.8.9/binaries/apache-maven-3.8.9-bin.tar.gz && \
    tar xvf apache-maven-3.8.9-bin.tar.gz  -C /opt && \
    mv /opt/apache-maven-3.8.9/ /opt/maven

ENV MAVEN_HOME /opt/maven
ENV PATH $MAVEN_HOME/bin:$PATH

VOLUME /usr/local/dubbo/src /usr/local/dubbo/logs

# JAVA_OPTS/DEBUG_OPTS: Jvm flags
# SERVICE_TYPE: nativeApp, nativeTest
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
    SERVICE_TYPE="nativeApp" \
    WAIT_TIMEOUT=600 \
    WAIT_PORTS_BEFORE_RUN="" \
    RUN_DELAY=0 \
    APP_MAIN_CLASS="" \
    CHECK_PORTS="" \
    CHECK_LOG="" \
    TEST_PATTERNS="**/*IT.class"

ADD *.sh /usr/local/dubbo/

WORKDIR /usr/local/dubbo/
ENTRYPOINT ["bash", "./run.sh"]


