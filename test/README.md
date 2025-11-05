## Dubbo Integration Test 

### Test steps

Follow the 3 steps below：

#### Step 1 - Build test image

Please install `docker` and `docker-compose` first, then build the test image.

```
cd dubbo-samples
./test/build-test-image.sh
```

Use a debian mirror through env `DEBIAN_MIRROR` if apt download files slowly, 
the following example uses aliyun mirror server [http://mirrors.aliyun.com/ubuntu/](http://mirrors.aliyun.com/ubuntu/) :

```
cd dubbo-samples
DEBIAN_MIRROR=http://mirrors.aliyun.com ./test/build-test-image.sh
```

Rebuild the image after modify any file of the `dubbo-test-runner` project.

#### Step 2 - Add case configuration
Add a `case-configuration.yml` to the tested project, for examples:

```
from: app-builtin-zookeeper.yml
props:
  project_name: dubbo-samples-annotation
  main_class: org.apache.dubbo.samples.annotation.AnnotationProviderBootstrap
  zookeeper_port: 2181
  dubbo_port: 20880

```

Some example projects: 

 * [dubbo-samples-annotation](../dubbo-samples-annotation/case-configuration.yml) : A simple provider service with builtin zookeeper.
 * [dubbo-samples-api](../dubbo-samples-api/case-configuration.yml) : A simple provider service with external zookeeper.
 * [dubbo-samples-chain](../dubbo-samples-chain/case-configuration.yml) : A multiple services with external zookeeper.

#### Step 3 - Generate and test scenario

Run single test project:

```
cd dubbo-samples
./test/run-tests.sh <project.basedir>
```

Run all tests:

```
cd dubbo-samples
./test/run-tests.sh
```

### Builtin parent configuration

Use `from` directive can import parent configuration, merge into current configuration.

Builtin parent configurations is in directory: `test/dubbo-scenario-builder/src/main/resources/configs`. 

* `app-builtin-zookeeper.yml` 
  
  Applicable scenario: single dubbo provider application with builtin zookeeper
and test case.

* `app-external-zookeeper.yml`
  Applicable scenario: single dubbo provider application, external zookeeper
and test case.


**Examples:** 

* `dubbo-samples-annotation` configuration:

```
from: app-builtin-zookeeper.yml
props:
  project_name: dubbo-samples-annotation
  main_class: org.apache.dubbo.samples.annotation.AnnotationProviderBootstrap
  zookeeper_port: 2181
  dubbo_port: 20880
```

`project_name` : project name of dubbo sample

`main_class` : main class of dubbo provider application

`dubbo_port` : dubbo provider service port

`zookeeper_port` : builtin zookeeper port


* `dubbo-samples-api` configuration:

```
from: app-external-zookeeper.yml

props:
  project_name: dubbo-samples-api
  main_class: org.apache.dubbo.samples.provider.Application
  dubbo_port: 20880
  zookeeper_version: latest
```

`zookeeper_version` : external zookeeper version

External zookeeper service is a fixed port 2181, cause cannot change port unless expose it.


### Case configuration details

Top level directives:

| Name | Description |
| ---- | ----------- |
| `from` |  load parent case configuration, cover its `props` with current configration |
| `props` | Internal properties, automatically replace the current configuration or inherited configuration variables when parsing |
| `services` | A set of app/test services or external services |


Directives for dubbo service:

| Name | Description | Defaults |
| ---- | ----------- | -------- |
| `type` | service type: `app` - dubbo provider application; `test`- dubbo testcase |  |
| `basedir` | project basedir of app/test service | `.` (current dir) |
| `mainClass` | Main class of provider service, only for app service. |  |
| `systemProps` | set Java app system properties, automatically converted to jvm flags: `-Dname=value` |  |
| `jvmFlags` | multiple jvm flags, automatically join as one string  | |
| `waitPortsBeforeRun` | Wait ports before run app/test |  |
| `tests` | Matching test patterns, only for test service. |  |
| | | |


Service directives compatible with `docker-compose`:

| Name | Description |  Defaults |
| ---- | ----------- | -------- | 
| `image` | docker image name of container | app/test service is implicitly set to `dubbo/sample-test`. |
| `environment` |  |
| `depends_on` | | 
| `hostname` | container hostname | app/test service is implicitly set to service id |
| `volumes` | container mount points | app/test service automatically mounts directory: `$basedir/target:/usr/local/dubbo/app`  |
| `links` | |
| `expose` | expose ports between containers |
| `ports` | mapping ports to host os |
| `entrypoint` | |
| `healthcheck` | |


#### Configuration example

`app-external-zookeeper.yml` includes :

* External zookeeper 
* Dubbo provider application
* Dubbo testcase

**Note:**  

* app/test service connect to zookeeper by system property `zookeeper.address` and `zookeeper.port`

* wait zookeeper port before run dubbo provider application

* wait zookeeper port and dubbo provider service port before run dubbo test

```
props:
#  project_name: dubbo-samples-xxx
#  main_class: org.apache.dubbo.samples.xxx.XxxProviderBootstrap
  dubbo_port: 20880
  zookeeper_version: latest

services:
  zookeeper:
    image: zookeeper:${zookeeper_version}

  ${project_name}:
    type: app
    basedir: .
    mainClass: ${main_class}
    systemProps:
      - zookeeper.address=zookeeper
      - zookeeper.port=2181
    waitPortsBeforeRun:
      - zookeeper:2181

  ${project_name}-test:
    type: test
    basedir: .
    tests:
      - "**/*IT.class"
    systemProps:
      - zookeeper.address=zookeeper
      - zookeeper.port=2181
    waitPortsBeforeRun:
      - zookeeper:2181
      - ${project_name}:${dubbo_port}
    depends_on:
      - ${project_name}

```


### Some tricks

#### Scenario

A scenario is a complete test environment, including docker-compose.yml, scenario.sh, app classes, test classes and dependency jars. You can test the scenario separately, just run scenario.sh.

* Scenario home

 `${scenario_home}` default location is: `${project.basedir/target}`.
 
 App / test service automatically mounts directory: `${scenario_home}:/usr/local/dubbo/app`


* Scenario running timeout
 
 Default running timeout is 200s. Some test cases require more time, you can modify it in the following way. 
 
 Set timeout in `case-configuration.yml`:
 
   ```
   timeout: 300
   ```
   
#### Logs

* Container log
  Container log location is: `${scenario_home}/logs/${serviceName}.log`, include of dubbo app/test 
  service and external service. 

* Scenario log  
  Script `scenario.sh` log location: `${scenario_home}/logs/scenario.log`.
  
* Scenario builder log
  Scenario builder log location: `$scenario_home/logs/scenario-builder.log`

#### Test reports

The test reports is in directory: `${scenario_home}/test-reports`

#### Fork run

The fork count is 2 by default, you can modify it by setting env `FORK_COUNT=n`. 
Increasing the fork count may cause the container to run very slowly, 
please set it according to the CPU/IO performance of the operating system.

```
FORK_COUNT=2 bash run-tests.sh
```

#### Fail-fast

Run tests in fail-fast mode, abort testing when any case is failed. 
It's useful when running tests in CI server, such as: Jenkins, Github actions.
Default value: `FAIL_FAST=0`.

```
FAIL_FAST=1 bash run-tests.sh
```

#### Show error detail

Show log detail of failed testcase, including app log and test container log. 
It's useful when running tests in CI server, such as: Jenkins, Github actions.
Default value: `SHOW_ERROR_DETAIL=0`.

```
SHOW_ERROR_DETAIL=1 bash run-tests.sh
```

### Develop

#### dubbo-scenario-builder
Build dubbo test scenario, generating docker/docker-compose script files and 
start script files. 


#### dubbo-test-runner
A Junit test runner, execute a set of testcases, replacement for maven-safefail-plugin. 
Also include files for `dubbo-test-image`.


For graalvm Native Image test Integration please refer to [README-NATIVE](./README-NATIVE.md)
