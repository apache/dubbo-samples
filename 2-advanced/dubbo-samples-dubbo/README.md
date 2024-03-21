# Dubbo Protocol Example

This example shows how to use dubbo tcp protocol to communicate. For pure rpc protocol demonstration, this example does not rely on any registry center.

## Modules
* interface, provides Dubbo service definition
* provider, implements Dubbo service
* consumer, consumes Dubbo service

## Install dependencies
Step into 'dubbo-samples-dubbo' directory, run the following command:

```shell
$ mvn clean install
```

## Start provider
Enter provider directory:
```shell
$ cd dubbo-samples-dubbo-provider
```

then, run the following command to start provider:
```shell
$ mvn compile exec:java -Dexec.mainClass="org.apache.dubbo.protocol.dubbo.demo.provider.ProviderApplication"
```


## Start consumer
Enter provider directory:
```shell
$ cd dubbo-samples-dubbo-consumer
```

then, run the following command to start consumer:
```shell
$ mvn compile exec:java -Dexec.mainClass="org.apache.dubbo.protocol.dubbo.demo.consumer.ConsumerApplication"
```

