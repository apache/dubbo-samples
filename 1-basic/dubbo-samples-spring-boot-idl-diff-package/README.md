# Dubbo Spring Boot Example using Triple and Protobuf with different package and java_package

This example uses triple as the underlying RPC protocol and IDL to define services. It's highly recommend to use triple and IDL for cross-language communication scenarios. Please refer to [dubbo-samples-spring-boot](../dubbo-samples-spring-boot) for how to use pure Java interface to define services for triple protocol.

This example define the Java package name separately from the service prefix, which is useful in some cross-language calling scenarios.And  For instance, in the following IDL definition:

1. The complete service name is `idl.Greeter`, which will be used during rpc calls and service discovery.
2. The Java package name is defined as `org.apache.dubbo.springboot.demo.idl.Greeter`, where the generated Java code will be placed.

# How to run

## Start Nacos
This example replies on Nacos as service discovery registry center, so you need to run the Nacos server first, there are two ways to do so:
1. [Download Nacos binary and start it directly](https://dubbo-next.staged.apache.org/zh-cn/overview/reference/integrations/nacos/#本地下载)
2. [Start Nacos using docker](https://dubbo-next.staged.apache.org/zh-cn/overview/reference/integrations/nacos/#docker)

## Compile

Step into 'dubbo-samples-spring-boot-idl-diff-package' directory, run the following command:

```shell
$ mvn clean compile
```

## Start provider

Enter provider directory:
```shell
$ cd dubbo-samples-spring-boot-idl-diff-package-provider
```

then, run the following command to start provider:
```shell
$ mvn compile exec:java -Dexec.mainClass="org.apache.dubbo.springboot.demo.provider.ProviderApplication"
```

Run the following command to see server works as expected:
```shell
curl \
    --header "Content-Type: application/json" \
    --data '{"name":"Dubbo"}' \
    http://localhost:50052/idl.Greeter/greet/
```

## Start consumer

Enter provider directory:
```shell
$ cd dubbo-samples-spring-boot-idl-diff-package-consumer
```

then, run the following command to start consumer:
```shell
$ mvn compile exec:java -Dexec.mainClass="org.apache.dubbo.springboot.demo.consumer.ConsumerApplication"
```



