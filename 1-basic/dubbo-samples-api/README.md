# About this sample

This example demonstrates building up of Dubbo rpc server and client with lightweight API. The API is quite simple and straightforward.

Follow steps below to run this example.

## Start Server
Run the command below to start the Dubbo rpc server

```bash
mvn clean package
mvn -Dexec.mainClass=org.apache.dubbo.samples.provider.Application exec:java
```

Now, you have a server running on port 50052 which accepts triple protocol requests.

More usages of triple protocol can be found here:
* [Triple with Protobuf (IDL mode)](../dubbo-samples-api-idl/)
* [Streaming RPCs](../../2-advanced/dubbo-samples-triple-streaming/)
* [Interoperability with standard gRPC clients and servers](../../2-advanced/dubbo-samples-triple-grpc/)
* [Using triple with other languages and browser](https://dubbo.apache.org/zh-cn/overview/mannual/)
* [Triple with Spring Boot](../dubbo-samples-spring-boot/) and [Triple+IDL with Spring Boot](../dubbo-samples-spring-boot-idl/)

## Start Client

There are two ways to test the server works as expected:
* Standard HTTP tools like cURL.
* Dubbo sdk client.

### cURL
```shell
curl \
    --header "Content-Type: application/json" \
    --data '["Dubbo"]' \
    http://localhost:50052/org.apache.dubbo.samples.api.GreetingsService/sayHi/
```

### SDK client

```bash
mvn -Dexec.mainClass=org.apache.dubbo.samples.client.Application exec:java
```
