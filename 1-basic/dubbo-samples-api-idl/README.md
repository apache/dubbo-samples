# Dubbo Triple With Protobuf

This example shows the basic usage of Triple protocol with a typical request-response model demo that uses IDL as the method of defining Dubbo service.

As described in [the Triple protocol documentation](https://dubbo.apache.org/zh-cn/overview/reference/protocols/triple/), Dubbo triple protocol is a better gRPC implementation which can be accessed by cURL and web browsers directly.

More usages of Triple protocol can be found here:
* [Streaming RPCs](../../2-advanced/dubbo-samples-triple-streaming/)
* [Interoperability with standard gRPC clients and servers](../../2-advanced/dubbo-samples-triple-grpc/)
* [Triple without Protobuf (no IDL mode)](../../2-advanced/dubbo-samples-triple-no-idl/)
* [Using triple with other languages and browser](https://dubbo.apache.org/zh-cn/overview/mannual/)
* [Triple without Protobuf (no IDL mode)](../../2-advanced/dubbo-samples-triple-no-idl/)
* [Triple with Spring Boot](../dubbo-samples-spring-boot/) and [Triple+IDL with Spring Boot](../dubbo-samples-spring-boot-idl/)

## Run The Demo
Detailed explanation of this demo can be found [here](https://dubbo.apache.org/zh-cn/overview/quickstart/rpc/java/).

Make sure you are in `dubbo-samples-triple-unary` before running the following commands.

```shell
mvn clean compile #Compile and generate code
```

### Start server
```shell
$ mvn compile exec:java -Dexec.mainClass="org.apache.dubbo.samples.tri.unary.TriUnaryServer"
```

### Start Client

There are two ways to test the server works as expected:
* Standard HTTP tools like cURL.
* Dubbo sdk client.

#### cURL
```shell
curl \
    --header "Content-Type: application/json" \
    --data '{"name": "Dubbo"}' \
    http://localhost:50052/org.apache.dubbo.samples.tri.unary.Greeter/greet/
```

#### Start client
```shell
$ mvn compile exec:java -Dexec.mainClass="org.apache.dubbo.samples.tri.unary.TriUnaryClient"
```

