
## Dubbo Native Integration Test Quick Start

### Building Test Image

```shell
cd dubbo-samples
./test/build-native-test-image.sh
```

For users in China, mirror acceleration can be utilized:

```shell
docker pull ghcr.m.daocloud.io/graalvm/native-image-community:17-ol7
docker tag ghcr.m.daocloud.io/graalvm/native-image-community:17-ol7 ghcr.io/graalvm/native-image-community:17-ol7
cd dubbo-samples
./test/build-native-test-image.sh
```

### Running Test Cases

The script `run-native-tests.sh` is used to execute a single test case, a list of test cases, or all test cases.

The script `kill-tests.sh` is used to forcefully terminate all test cases, stop all dubbo containers, and all processes of run-native-tests.sh and scenario.sh.

Note: After interrupting the execution of run-native-tests.sh with Ctrl+C, the kill-tests.sh script should be executed.

#### Running Methods

* Running a Single Test Case

  ``` shell
  ./test/run-native-tests.sh <project.basedir>
  ```

  For example, to run the dubbo-samples-native-image-registry test case:

  ```shell
  ./test/run-native-tests.sh 2-advanced/dubbo-samples-native-image-registry
  ```

* Running a Specified List of Test Cases

  ```shell
  TEST_CASE_FILE=testcases1.txt ./test/run-native-tests.sh
  ```

* Running All Test Cases

  ```shell
   ./test/run-native-tests.sh
  ```

The principle behind running all test cases with run-native-tests.sh:
1. Search for all `native-case-configuration.yml` files.
2. Fork multiple processes to run the test cases sequentially.

### Adding Test Cases

Ensure that the versions in case-versions.conf are correct: `dubbo.version=3.3.*, spring.version=6.*, java.version= >= 17`.

The test case configuration file should be named native-case-configuration.yml and placed in the basedir of each project that requires testing.

The basic usage should remain consistent with the [quick-start](./quick-start_cn.md) guide,
with details directly referable to the official documentation on [Support for GraalVM Native Image](https://cn.dubbo.apache.org/zh-cn/overview/mannual/java-sdk/advanced-features-and-usage/performance/support-graalvm/).

Note:

* The `native-case-configuration.yml` file should add config `timeout: 600` because native testing requires packaging a native Image, which is time-consuming. The default timeout may not be sufficient for the packaging process, leading to premature timeouts.
* Ensure that project dependencies meet the minimum version requirements. Use the latest version of maven-compiler-plugin to prevent unnecessary issues, and use the latest version of native-maven-plugin for better support. Refer to the example in 2-advanced/dubbo-samples-native-image-registry/dubbo-samples-native-image-registry-provider for version details.
* For normal application main startup classes, the pom.xml must include a profile named native, which will be used during the packaging and compilation phase. Projects that require running unit tests should have a nativeTest profile. Refer to the example in 2-advanced/dubbo-samples-native-image-registry/dubbo-samples-native-image-registry-consumer.
* The matching rule for test classes (includes) is "*IT.java"，The default matching rule is："**/*IT.class". In the native test scenario, this is activated through the configuration of the test plugin.
    ```xml
    <plugin>
        <groupId>org.apache.maven.plugins</groupId>
        <artifactId>maven-surefire-plugin</artifactId>
        <version>3.1.2</version>
        <configuration>
            <includes>
                <include>${env.TEST_PATTERNS}</include>
            </includes>
        </configuration>
    </plugin>
    ```
  Corresponding to the native-case-configuration.yml configuration is
  ```yaml
  dubbo-samples-native-image-registry-test:
      type: nativeTest
      basedir: dubbo-samples-native-image-registry-consumer
      tests:
          - "**/*IT.class" // Configure the matching rules of the test class, this is also the default value
  ```
    If the unit test runs incompletely you need to check these configurations

* Examples that rely on zookeeper need to configure the zookeeper address in the native-maven-plugin channel, otherwise the actual zookeeper address may not be requested.
    ```xml
    <plugin>
        <groupId>org.graalvm.buildtools</groupId>
        <artifactId>native-maven-plugin</artifactId>
        <version>${native-maven-plugin.version}</version>
        <extensions>true</extensions>  <!-- required -->
        <configuration>
            <buildArgs>
                -H:+ReportExceptionStackTraces
            </buildArgs>
            <systemProperties>
                <zookeeper.address>${zookeeper.address}</zookeeper.address> <!-- external zookeeper address -->
            </systemProperties>
        </configuration>
    </plugin>
    ```
  Correspondingly, it can be configured like this in application.yml
    ```yaml
      registry:
        address: zookeeper://${zookeeper.address:127.0.0.1}:2181
    ```
  In the code, the environment variable can be used to retrieve the value:
    ```java
    private static String zookeeperHost = System.getProperty("zookeeper.address", "127.0.0.1");
    ```
* The test case running logs are stored in the directory specified by ${basedir}/logs/${sampleName}/*.log. For example, in the case of dubbo-samples-native-image-registry:
    ```text
    dubbo-samples-native-image-registry // basedir
    ├── logs
    │   ├── dubbo-samples-native-image-registry-consumer.log // Consumer native build and runtime logs
    │   ├── dubbo-samples-native-image-registry-provider.log // Provider native build and runtime logs
    │   ├── dubbo-samples-native-image-registry-test.log // Native test build and runtime logs
    │   ├── scenario-builder.log // Logs generated from native-case-configuration.yml
    │   ├── scenario.log // Overall runtime logs for all cases under this basedir
    │   └── zookeeper.log // ZooKeeper logs
    ├── mvn.log // Logs for building the source code with mvn
    ├── version-matrix.log // Logs for version matrix
    └── version-matrix.txt // Actual dependency versions for running examples
    ```
