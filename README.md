# Dubbo Samples

Samples for Apache Dubbo.

![Build Status](https://github.com/apache/dubbo-samples/workflows/Dubbo%203.2/badge.svg)
![Build Status](https://github.com/apache/dubbo-samples/workflows/Dubbo%203.3/badge.svg)
![license](https://img.shields.io/github/license/apache/dubbo-samples.svg)

This repository contains a number of projects to illustrate various usages of Dubbo from basic to advanced, pls. check README in each individual sub projects. It is also helpful to cross reference to [Dubbo User Manual](https://dubbo.apache.org/zh-cn/overview/tasks/) to understand the features demoed in this project.

What's more, [dubbo-go](https://github.com/apache/dubbo-go) samples are moved to [dubbo-go-samples](https://github.com/apache/dubbo-go-samples).

## Build and Run Samples

To compile all samples, run the following command in the top directory of this project, or step into the sub directories to compile one single sample:

**It is highly not recommend to build the entire project from the root directory, as building the entire samples can take a long time. Each module in Samples is designed independently so you can first go to the demo directory you care about, then execute the build and run the demo.**

For example,

```bash
$ cd 1-basic/dubbo-samples-spring-boot
$ mvn clean package
```

You may need to read each individual README under the sub directories if you have to understand how to build and run.

## Integration Test

This project is also used for integration tests for dubbo. If you are just learning how to use Dubbo you don't have to care about this part.

**How to build and run a integration test**

Dubbo integration test base on docker container, and relies on an image used to run the provider application and test cases.

Integration test leverages [docker](https://docs.docker.com/get-started/) to setup test environment, more accurately, to start dubbo provider instance, and any other supporting systems like registry center if necessary, in docker.

Please install `docker` and `docker-compose` first, then build the test image `dubbo/sample-test`.

```bash
cd dubbo-samples
./test/build-test-image.sh
```

Use a debian mirror through env `DEBIAN_MIRROR` if apt download files slowly,
the following example uses aliyun mirror server [http://mirrors.aliyun.com/ubuntu/](http://mirrors.aliyun.com/ubuntu/) :

```bash
cd dubbo-samples
DEBIAN_MIRROR=http://mirrors.aliyun.com ./test/build-test-image.sh
```

Then we use the `run-tests.sh` script to run the test cases.

* Run single test case

  ```bash
  cd dubbo-samples
  ./test/run-tests.sh <project.basedir>
  ```

  For example, run the `dubbo-samples-annotation` test case:

  ```
  ./test/run-tests.sh 2-advanced/dubbo-samples-annotation
  ```

* Run all test cases

  ```bash
  cd dubbo-samples
  ./test/run-tests.sh
  ```

If docker container fails to startup successfully in any case, you can check log files in directory `${project.basedir}/target/logs` to understand what happens.

Pls. note integration tests rely on a Docker environment, make sure the docker environment is available before running them.

**How to add more integration test**

If you are interested in contributing more integration test for dubbo, pls. read further to understand how to enable integration test for one particular sample from the scratch.

Please follow the steps below:

1. Add a file named `case-configuration.yml` to test project.

   This file is used to configure the test modules and environment, including dubbo provider / test services,
   dependent third-party services.

2. Add a file named `case-versions.conf` to test project.

   This file is used to configure the supported component version rules to support multi-version testing.

**Details of `case-configuration.yml`:**

Take the case `dubbo-samples-annotation` as an example:

```yaml
services:
  dubbo-samples-annotation:
    type: app
    basedir: .
    mainClass: org.apache.dubbo.samples.annotation.AnnotationProviderBootstrap

  dubbo-samples-annotation-test:
    type: test
    basedir: .
    tests:
      - "**/*IT.class"
    systemProps:
      - zookeeper.address=dubbo-samples-annotation
      - zookeeper.port=2181
      - dubbo.address=dubbo-samples-annotation
      - dubbo.port=20880
    waitPortsBeforeRun:
      - dubbo-samples-annotation:2181
      - dubbo-samples-annotation:20880
    depends_on:
      - dubbo-samples-annotation
```

The project contains a dubbo provider `AnnotationProviderBootstrap` and an embedded zookeeper server,
as well as a test class `AnnotationServicesIT`.

Therefore, we have to define two services, one service runs `AnnotationProviderBootstrap`,
and the other service runs test classes.

The service `type` of running dubbo provider is `app`, and the service `type` of running test is `test`.

The project directory is the same as the case configuration directory, so `basedir` is `.` .

Use hostname to access between containers, the default `hostname` of the container is the same as serviceName.

So through `dubbo-samples-annotation:2181`, the embedded zookeeper server can be accessed from the test container.

There are many test cases similar to this example, only need to modify the `mainClass` and hostname.
Extract the changed as variables, and the unchanged content as templates.
When using the template, you only need to modify the variable value, which makes the case configuration easier.

The above example can use a template `app-builtin-zookeeper.yml`, use `from` to reference it and override the variable value in `props`:

```yaml
from: app-builtin-zookeeper.yml

props:
  project_name: dubbo-samples-annotation
  main_class: org.apache.dubbo.samples.annotation.AnnotationProviderBootstrap
  zookeeper_port: 2181
  dubbo_port: 20880
```

Another template is `app-external-zookeeper.yml`, which supports an external zookeeper service.
you can find all the templates in the directory `test/dubbo-scenario-builder/src/main/resources/configs`.

**Details of `case-versions.conf`:**

Version rules for spring app:

```
# Spring app
dubbo.version=2.7.*, 3.*
spring.version=4.*, 5.*
```

Version rules for spring-boot 1.x app:

```
# SpringBoot app
dubbo.version=2.7.*, 3.*
spring-boot.version=1.*
```

Version rules for spring-boot 2.x app:

```
# SpringBoot app
dubbo.version=2.7.*, 3.*
spring-boot.version=2.*
```

For more details, please refer to the following case configurations:

 * [dubbo-samples-annotation](2-advanced/dubbo-samples-annotation/case-configuration.yml) : A simple provider service with builtin zookeeper.
 * [dubbo-samples-api](1-basic/dubbo-samples-api/case-configuration.yml) : A simple provider service with external zookeeper.
 * [dubbo-samples-chain](2-advanced/dubbo-samples-chain/case-configuration.yml) : A multiple services with external zookeeper.
 * [dubbo-samples-migration](2-advanced/dubbo-samples-migration/README.md) : A compatibility test with the provider and consumer have different dubbo verison.

That's it, then feel free to add more integration test for the Dubbo project, have fun.
