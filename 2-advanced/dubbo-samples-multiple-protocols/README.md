# Dubbo Multiple Protocol Example

This example shows how the `multiple protocols on one port` works in Dubbo. We will
1. Start one provider which supports `dubbo` and `tri` protocol at the same time on one port.
2. Start two consumers that consumes `dubbo` and `tri` protocol respectively.

# How to run

## Start Nacos
This example replies on Nacos as service discovery registry center, so you need to run the Nacos server first, there are two ways to do so:
1. [Download Nacos binary and start it directly](https://dubbo-next.staged.apache.org/zh-cn/overview/reference/integrations/nacos/#本地下载)
2. [Start Nacos using docker](https://dubbo-next.staged.apache.org/zh-cn/overview/reference/integrations/nacos/#docker)

## Install dependencies
Enter 'dubbo-samples-multiple-protocols' directory, run the following command:

```shell
$ mvn clean install
```

## Start provider
Enter provider directory:

```shell
$ cd dubbo-samples-multiple-protocols-provider
```

then, run the following command to start provider:
```shell
$ mvn compile exec:java -Dexec.mainClass="org.apache.dubbo.protocol.multiple.demo.provider.ProviderApplication"
```

Run the following command to see server works as expected:
```shell
curl \
    --header "Content-Type: application/json" \
    --data '["Dubbo"]' \
    http://localhost:20880/org.apache.dubbo.protocol.multiple.demo.DemoService/sayHello
```

## Start `dubbo` consumer
Enter `dubbo` consumer directory:
```shell
$ cd dubbo-samples-multiple-protocols-dubbo-consumer
```

then, run the following command to start consumer:
```shell
$ mvn compile exec:java -Dexec.mainClass="org.apache.dubbo.protocol.multiple.demo.consumer.DubboConsumerApplication"
```

## Start `triple` consumer
Enter `triple` consumer directory:
```shell
$ cd dubbo-samples-multiple-protocols-triple-consumer
```

then, run the following command to start consumer:
```shell
$ mvn compile exec:java -Dexec.mainClass="org.apache.dubbo.protocol.multiple.demo.consumer.TripleConsumerApplication"
```

