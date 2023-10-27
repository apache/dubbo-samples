# Dubbo Spring Boot Example

This example shows how to use Dubbo Spring Boot Starter to develop Dubbo application. Please read [the official documentation](https://dubbo.apache.org/zh-cn/overview/quickstart/java/spring-boot/) for more details of how to use.

## Modules
* interface, provides Dubbo service definition
* provider, implements Dubbo service
* consumer, consumes Dubbo service

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

