# Asynchronous Dubbo calls with Spring Boot

This sample demonstrates consumer-side asynchronous calls using Spring Boot 3 and Java 17.
The consumer invokes a regular Dubbo service with `@DubboReference(async = true)`, obtains a
`CompletableFuture` through `RpcContext`, and demonstrates `asyncCall` and a one-way call.

## Modules

- `dubbo-samples-async-simple-boot-interface`: the shared `HiService` contract.
- `dubbo-samples-async-simple-boot-provider`: publishes `HiService` and registers with ZooKeeper.
- `dubbo-samples-async-simple-boot-consumer`: discovers the provider and demonstrates asynchronous calls.

## Prerequisites

- JDK 17, with `JAVA_HOME` pointing to the JDK directory.
- Maven 3.6.3 or later, or the repository Maven Wrapper (Maven 3.8.6).
- ZooKeeper listening on `127.0.0.1:2181` for local execution.
- Docker and Docker Compose for the container-based integration scenario.

The sample uses Spring Boot 3.2.3 and Dubbo 3.3.1. The provider does not start the included
`EmbeddedZooKeeper` helper by default.

The commands below use Bash and start from the repository root. On Windows, Maven commands can
be run in PowerShell using `.\mvnw.cmd` instead of `./mvnw`. In each new PowerShell terminal,
define `$SAMPLE = '2-advanced/dubbo-samples-async/dubbo-samples-async-simple-boot'`.
Join Bash commands continued with `\` onto a single line when using PowerShell.

## Build

Define the sample directory in each terminal:

```bash
SAMPLE=2-advanced/dubbo-samples-async/dubbo-samples-async-simple-boot
```

Build and install the sample modules without executing tests:

```bash
./mvnw -B -f "$SAMPLE/pom.xml" clean install -DskipTests
```

This also compiles the test sources. Building the entire repository is unnecessary.

## Run locally

If ZooKeeper is not already running locally, start a disposable instance:

```bash
docker run --rm -d --name dubbo-async-simple-boot-zookeeper \
  -p 127.0.0.1:2181:2181 zookeeper:latest
```

Start the provider in one terminal:

```bash
./mvnw -f "$SAMPLE/dubbo-samples-async-simple-boot-provider/pom.xml" \
  org.springframework.boot:spring-boot-maven-plugin:3.2.3:run
```

Wait for `Started ProviderApplication` and `Current Spring Boot Application is await...` in its logs.
Then start the consumer in another terminal:

```bash
./mvnw -f "$SAMPLE/dubbo-samples-async-simple-boot-consumer/pom.xml" \
  org.springframework.boot:spring-boot-maven-plugin:3.2.3:run
```

Expected consumer output includes the following lines; their order may vary:

```text
return value: hi, your name is: world
async call returned: hi, your name is: async call request
```

Both applications use ZooKeeper for discovery. The provider chooses an available Dubbo port
for local execution; the Docker scenario explicitly sets it to `20880` for its readiness checks.

Stop the applications with `Ctrl+C`. If you started the disposable ZooKeeper container above, stop it with:

```bash
docker stop dubbo-async-simple-boot-zookeeper
```

## Verify locally

Keep ZooKeeper and the provider running, and execute:

```bash
./mvnw -B -f "$SAMPLE/pom.xml" verify
```

Surefire runs `ConsumerApplicationTest`; Failsafe runs the two JUnit 5 tests in `ConsumerIT`.
The integration tests assert the returned values from both asynchronous invocation styles,
with a five-second timeout for each future. Expect three tests in total with no failures,
errors, or skipped tests, followed by `BUILD SUCCESS`.

## Docker integration scenario

The repository scripts require Bash, `mvn`, Java 17, and Docker Compose on the host.
Use a Linux environment and a checkout path without spaces. Run them in an isolated Docker
environment: `test/run-tests.sh` cleans up Dubbo-named containers and prunes unused networks.

Build only the Java 17 test-runner image, then run this sample with explicit candidate versions:

```bash
JAVA_VER=17 ./test/dubbo-test-runner/build.sh

JAVA_VER=17 \
CANDIDATE_VERSIONS='dubbo.version:3.3.1;spring.version:6.1.4;spring-boot.version:3.2.3;java.version:17' \
SHOW_ERROR_DETAIL=1 \
./test/run-tests.sh 2-advanced/dubbo-samples-async/dubbo-samples-async-simple-boot
```

This scenario starts its own ZooKeeper and provider containers, waits for provider readiness,
and selects `ConsumerIT` through `**/*IT.class`. Expect `Junit5: TEST SUCCESS` with two tests
and a successful scenario result. The runner also scans for JUnit 4 tests; finding none is
expected for this sample.

Inspect logs under the sample's `target/logs` directory if the scenario fails. See the
[repository test documentation](../../../test/README.md) for details about the scenario runner.
