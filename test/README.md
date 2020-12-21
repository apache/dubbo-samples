## Dubbo Integration Test

### Test steps

Follow the 3 steps below：

#### Step 1 - Build test image

Please install `docker` and `docker-compose` first, then build the test image.

```
cd dubbo-samples/test
./build-test-image.sh
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
  service_port: 20880

```

Some example projects: 

 * [dubbo-samples-annotation](../dubbo-samples-annotation/case-configuration.yml) : A simple provider service with builtin zookeeper.
 * [dubbo-samples-chain](../dubbo-samples-chain/case-configuration.yml) : A multiple services with external zookeeper.

#### Step 3 - Generate and test scenario

```
cd dubbo-samples/test
./run-tests.sh
```



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
| `image` | docker image name of container | app/test service is implicitly set to `dubbo-sample-test`. |
| `environment` |  |
| `depends_on` | | 
| `hostname` | container hostname | app/test service is implicitly set to service id |
| `volumes` | container mount points | app/test service automatically mounts directory: `$basedir/target:/usr/local/dubbo/app`  |
| `links` | |
| `expose` | |
| `entrypoint` | |
| `healthcheck` | |

for example:


`app-builtin-zookeeper.yml`: 

```
props:
  project_name: dubbo-samples-xxx
  main_class: org.apache.dubbo.samples.xxx.XxxProviderBootstrap
  service_port: 20880
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
      - ${project_name}:${service_port}
    depends_on:
      - ${project_name}
```

`dubbo-samples-annotation` configuration:

```
from: app-builtin-zookeeper.yml
props:
  project_name: dubbo-samples-annotation
  main_class: org.apache.dubbo.samples.annotation.AnnotationProviderBootstrap
  zookeeper_port: 2181
  service_port: 20880

```

### Some tricks


#### logs


#### forks


### dubbo-scenario-builder
Build dubbo test scenario, generating docker/docker-compose script files and 
start script files. 


### dubbo-test-runner
A Junit test runner, execute a set of testcases, replacement for maven-safefail-plugin. 
Also include files for `dubbo-test-image`.


