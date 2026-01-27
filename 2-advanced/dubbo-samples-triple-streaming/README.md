This example shows how you can use streaming RPC with Dubbo Triple protocol.

As described in [the Triple protocol documentation](https://dubbo.apache.org/zh-cn/overview/reference/protocols/triple/), Dubbo triple protocol is fully compatible with gRPC, so it supports client streaming, server streaming and bi-streaming.

## Run The Demo
Detailed explanation of this demo can be found [here](https://dubbo.apache.org/zh-cn/overview/mannual/java-sdk/quick-start).

```shell
mvn clean compile #Compile and generate code
```

### Start server
Make sure you are in `dubbo-samples-triple-streaming` directory and then run the following command:

```shell
mvn spring-boot:run
```

#### Start integration test
Open a new terminal, enter `dubbo-samples-triple-streaming` directory and then run the following command:

```shell
mvn verify
```
