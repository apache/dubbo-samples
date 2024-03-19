# Dubbo Triple Rest JAX-RS Example

This example shows how to use Spring Web annotation to add rest-style access to triple protocol.

# How to run

## Start Zookeeper
This example replies on Zookeeper(3.8.0+) as service discovery registry center, so you need to run the Zookeeper server first, there are two ways to do so:
1. [Download zookeeper binary and start it directly](https://dubbo-next.staged.apache.org/zh-cn/overview/reference/integrations/zookeeper/#本地下载)
2. [Start zookeeper using docker](https://dubbo-next.staged.apache.org/zh-cn/overview/reference/integrations/zookeeper/#docker)

## Install dependencies
Step into 'dubbo-samples-triple-rest' directory, run the following command:

```shell
$ mvn clean install
```

## Start provider
Step into 'dubbo-samples-triple-rest-jaxrs' directory

then, run the following command to start application:
```shell
$ mvn compile exec:java -Dexec.mainClass="org.apache.dubbo.rest.demo.JaxrsRestApplication"
```

Run the following command to see server works as expected:
```shell
curl \
    --header "Content-Type: application/json" \
    'http://localhost:50052/demo/hello?name=world'
```

Or, you can visit the following link by using web browser: `http://localhost:50052/demo/hello?name=world`
