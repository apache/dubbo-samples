# Dubbo Zookeeper Example

This example shows how a global transaction manager works in Dubbo framework. Here we use [seata](https://github.com/seata/seata) as an example, but other transaction managers are possible to work with Dubbo too if they provide the corresponding Dubbo filter implementation.

## How To Run

#### Step 1. Start Zookeeper as Registry Center

In this example, a docker compose file is provided to start the required zookeeper easily. But at the same time, it requires you to prepare docker environment as a prerequisite. You can refer to [docker quick start](https://www.docker.com/get-started) to install.

```bash
cd src/main/resources/docker
docker-compose up
```

#### Step 2. Build Examples

Execute the following maven command under *dubbo-samples-zookeeper* directory, or simply import the whole sample project into IDE.

```bash
mvn clean package
```

#### Step 3. Run Examples

0. Run *ProviderBootstrap* to start service provider
0. Run *ConsumerBootstrap* to start service consumer, you should see the following result in the console:
```bash
result: hello, zookeeper
```
