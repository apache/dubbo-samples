# Dubbo Samples

Samples for Apache Dubbo

[![Build Status](https://travis-ci.org/apache/dubbo-samples.svg?branch=master)](https://travis-ci.org/apache/dubbo-samples) 
![license](https://img.shields.io/github/license/apache/dubbo-samples.svg)

This repository contains a number of projects to illustrate various usages of Dubbo from basic to advanced, pls. check README in each individual sub projects. It is also helpful to cross reference to [Dubbo User Manual](http://dubbo.apache.org/en-us/docs/user/quick-start.html) to understand the features demoed in this project.

## Build and Run Samples

To compile all samples, run the following command in the top directory of this project, or step into the sub directories to compile one single sample:

```bash
mvn clean package
```

You may need to read each individual README under the sub directories if it has to understand how to build and run.

## Integration Test

This project is also used for integration test for dubbo. 

**How to build and run a integration test**

Most of integration tests will reply on a home-brew maven plugin to perform correctly when dubbo service is deployed in docker environment. This maven plugin is provided in 'dubbo-maven-address-plugin' module and should be installed before running any integration test:

```bash
cd dubbo-maven-address-plugin
mvn clean install
```

It is as simple as stepping into a sub directory and then executing the following command, for example:

```bash
cd dubbo-samples-annotation
mvn -Pdubbo-integration-test clean verify
```

If docker container fails to startup successfully in any case, you can use *-Ddocker.showLogs* to check its logging output to understand what happens.

```bash
mvn -Ddocker.showLogs -Pdubbo-integration-test clean verify
```

Pls. note integration test relies on docker environment, make sure docker environment is available before run it.

**How to add more integration test**

If you are interested in contributing more integration test for dubbo, pls. read further to understand how to enable integration test for one particular sample from the scratch.

1. Related maven properties relevant to integration test:

```xml
<spring.version>4.3.16.RELEASE</spring.version>
<junit.version>4.12</junit.version>
<docker-maven-plugin.version>0.30.0</docker-maven-plugin.version>
<jib-maven-plugin.version>1.2.0</jib-maven-plugin.version>
<maven-failsafe-plugin.version>2.21.0</maven-failsafe-plugin.version>
<image.name>${artifactId}:${dubbo.version}</image.name>
<dubbo.port>20880</dubbo.port>
<main-class>org.apache.dubbo.samples.attachment.AttachmentProvider</main-class>
```

Integration test leverages [docker](https://docs.docker.com/get-started/) to setup test environment, more accurately, to start dubbo provider instance, and any other supporting systems like registry center if necessary, in docker. Therefore, there are two maven plugins required for composing docker image and start-and-stop the docker instances before-and-after the integration test: 1. [jib-maven-plugin](https://github.com/GoogleContainerTools/jib/tree/master/jib-maven-plugin) from google 2. [docker-maven-plugin](https://github.com/fabric8io/docker-maven-plugin) from fabric8.

2. Configure maven profile:

Since we use profile 'dubbo-integration-test' to enable integration test, make sure the following plugins are configured under the desire profile, which is **'dubbo-integration-test'**:

```xml
<profiles>
    <profile>
    <id>dubbo-integration-test</id>
    <build>
        <plugins><!-- declare maven plugins here --></plugins>
    </build> 
    </profile>
</profiles>
```

3. Configure dubbo-maven-address-plugin

```xml
<plugin>
    <groupId>org.apache.dubbo</groupId>
    <artifactId>dubbo-maven-address-plugin</artifactId>
    <version>1.0-SNAPSHOT</version>
    <executions>
        <execution>
            <goals>
                <goal>local-address</goal>
            </goals>
            <configuration>
                <localAddress>dubbo-local-address</localAddress>
            </configuration>
            <phase>initialize</phase>
        </execution>
    </executions>
</plugin>
```

'dubbo-local-address' is a maven property in which dubbo provider's IP address is stored. 

4. Configure jib-maven-plugin

```xml
<plugin>
    <groupId>com.google.cloud.tools</groupId>
    <artifactId>jib-maven-plugin</artifactId>
    <version>${jib-maven-plugin.version}</version>
    <configuration>
        <from>
            <image>${java-image.name}</image>
        </from>
        <to>
            <image>${image.name}</image>
        </to>
        <container>
            <mainClass>${main-class}</mainClass>
            <ports>
                <port>${dubbo.port}</port> <!-- dubbo provider's port -->
                <port>2181</port> <!-- zookeeper's port -->
            </ports>
            <environment>
                <DUBBO_IP_TO_REGISTRY>${dubbo-local-address}</DUBBO_IP_TO_REGISTRY>
            </environment>
        </container>
   </configuration>
    <executions>
        <execution>
            <phase>package</phase>
                <goals>
                    <goal>dockerBuild</goal>
                </goals>
        </execution>
    </executions>
</plugin>
```

'<DUBBO_IP_TO_REGISTRY>' is an environment variable to instruct dubbo provider the IP address used for registering to service registration center. Since the dubbo provider will run within a docker instance, a host's IP address (detected from dubbo-maven-address-plugin) must be used in order to allow it discovered by the dubbo client running outside docker instance. 

5. Configure docker-maven-plugin

```xml
<plugin>
    <groupId>io.fabric8</groupId>
    <artifactId>docker-maven-plugin</artifactId>
    <version>${docker-maven-plugin.version}</version>
    <configuration>
        <images>
            <image>
                <name>${image.name}</name>
                <run>
                    <ports>
                        <port>${dubbo.port}:${dubbo.port}</port> <!-- expose dubbo port -->
                        <port>2181:2181</port> <!-- expose zookeeper port -->
                    </ports>
                    <wait>
                        <!-- wait until the message output in stdout, and it requires dubbo's provider 
                        explicitly prints out this message at the very end of main() -->
                        <log>dubbo service started</log> 
                    </wait>
                </run>
            </image>
        </images>
    </configuration>
    <executions>
        <execution>
            <id>start</id>
            <phase>pre-integration-test</phase>
            <goals>
                <goal>start</goal>
            </goals>
        </execution>
        <execution>
            <id>stop</id>
            <phase>post-integration-test</phase>
            <goals>
                <goal>stop</goal>
            </goals>
        </execution>
    </executions>
</plugin>
```

'docker-maven-plugin' will start the specified docker image before integration test (phase 'pre-integration-test') and stop it after integration test (phase 'post-integration-test').

6. Configure maven-failsafe-plugin

```xml
<plugin>
    <groupId>org.apache.maven.plugins</groupId>
    <artifactId>maven-failsafe-plugin</artifactId>
    <version>${maven-failsafe-plugin.version}</version>
    <executions>
        <execution>
            <goals>
                <goal>integration-test</goal>
                <goal>verify</goal>
            </goals>
            <configuration>
                <includes>
                    <include>**/*IT.java</include>
                </includes>
            </configuration>
        </execution>
    </executions>
</plugin>
```

A integration test is basically a JUnit based test class, but with its name suffixed by 'IT'.

That's it, then feel free to add more integration test for the Dubbo project. You may need to refer to 'dubbo-samples-annotation' or 'dubbo-samples-attachment' for more details, have fun.
