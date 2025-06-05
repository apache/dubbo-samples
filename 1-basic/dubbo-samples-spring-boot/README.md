# Dubbo Spring Boot Example

This example shows how to use Dubbo Spring Boot Starter to develop Dubbo application. For the underlying RPC protocol, we are using triple and at the same time, we use java interface as the way to define service. It's a more convenient way to develop Dubbo application if there's no cross-language communication requirement.

Please refer to
* [the official documentation](https://dubbo.apache.org/zh-cn/overview/quickstart/java/spring-boot/) for more details of developing Dubbo with Spring Boot.
* [dubbo-samples-spring-boot-idl](../dubbo-samples-spring-boot-idl) for how to use IDL(Protobuf) together with triple protocol.


## Modules
* interface, provides Dubbo service definition
* provider, implements Dubbo service
* consumer, consumes Dubbo service

# How to run

## Start Nacos
This example replies on Nacos as service discovery registry center, so you need to run the Nacos server first, there are two ways to do so:
1. [Download Nacos binary and start it directly](https://dubbo-next.staged.apache.org/zh-cn/overview/reference/integrations/nacos/#本地下载)
2. [Start Nacos using docker](https://dubbo-next.staged.apache.org/zh-cn/overview/reference/integrations/nacos/#docker)

## Install dependencies
Step into 'dubbo-samples-spring-boot' directory, run the following command:

```shell
$ mvn clean install
```

## Start provider
Enter provider directory:
```shell
$ cd dubbo-samples-spring-boot-provider
```

then, run the following command to start provider:
```shell
$ mvn compile exec:java -Dexec.mainClass="org.apache.dubbo.springboot.demo.provider.ProviderApplication"
```

Run the following command to see server works as expected:
```shell
curl \
    --header "Content-Type: application/json" \
    --data '["Dubbo"]' \
    http://localhost:50052/org.apache.dubbo.springboot.demo.DemoService/sayHello/
```

## Start consumer
Enter provider directory:
```shell
$ cd dubbo-samples-spring-boot-consumer
```

then, run the following command to start consumer:
```shell
$ mvn compile exec:java -Dexec.mainClass="org.apache.dubbo.springboot.demo.consumer.ConsumerApplication"
```

