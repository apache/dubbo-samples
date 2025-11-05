This example shows how you can use triple protocol with pure Java interface and pojos.

As described in [the Triple protocol documentation](https://dubbo.apache.org/zh-cn/overview/reference/protocols/triple/), Dubbo triple protocol is a better gRPC implementation which can be accessed by cURL and web browsers directly. 

The Java implementation gives you the option of using Triple and HTTP/2 RPC without the need of caring about Protocol Buffers, no IDL, just standard java interface and pojos.

## Run The Demo
Detailed explanation of this demo can be found [here](https://dubbo.apache.org/zh-cn/overview/mannual/java-sdk/quick-start).

Make sure you are in `dubbo-samples-triple-no-idl` before running the following commands

```shell
mvn clean compile #Compile and generate code
```

### Start server
```shell
$ mvn compile exec:java -Dexec.mainClass="org.apache.dubbo.samples.tri.noidl.TriPojoServer"
```

### Start Client

There are two ways to test the server works as expected:
* For unary rpc, you can use any standard HTTP tools like cURL.
* Dubbo sdk client.

#### cURL
```shell
curl \
    --header "Content-Type: application/json" \
    --data '["Dubbo"]' \
    http://localhost:50052/org.apache.dubbo.samples.tri.noidl.api.PojoGreeter/greet/
```

#### Start client
```shell
$ mvn compile exec:java -Dexec.mainClass="org.apache.dubbo.samples.tri.noidl.TriPojoClient"
```

