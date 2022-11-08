## Triple-Samples

[Triple](https://dubbo.apache.org/zh/docs/concepts/rpc-protocol/) is an application layer's protocol based on HTTP/2,
which supports interoperating with GRPC protocol naturally using Protobuf serialization. For non-protobuf applications,
triple provides a compatible mode to upgrade without code modifications. So it is smooth to migrate to triple.

This sample project contains most scenarios of Dubbo and non-Dubbo interoperation.

## Before Start

Generate protobuf class for IDE hints.

```shell
mvn clean -DskipTests=true
```

### For beginners

1. Start a zookeeper or config other registry in `org.apache.dubbo.sample.tri.stub.TriStubServer`
   and `org.apache.dubbo.sample.tri.stub.TriStubClient`
2. Build with `mvn clean install` to generate protobuf classes.
3. Run `org.apache.dubbo.sample.tri.stub.TriStubServer`
4. Run `org.apache.dubbo.sample.tri.stub.TriStubClient`

### For dubbo2 or non-prototbuf users

Run `org.apache.dubbo.sample.tri.pojo.TriPojoServer` and `org.apache.dubbo.sample.tri.pojo.TriPojoClient`.

Dubbo3 will wrap request and response with a protobuf wrapper automatically, so it is still compatible with GRPC.

### For Dubbo developers

Code under `test` folder is a general interoperation test suite for both Dubbo/Dubbo and Dubbo/GRPC.

#### Dubbo/Dubbo test

1. Run `org.apache.dubbo.sample.tri.TriGrpcProvider`
2. Run `org.apache.dubbo.sample.tri.TriClientTest` , `org.apache.dubbo.sample.tri.TriPojoClientTest`
   and `org.apache.dubbo.sample.tri.TriGenericTest`

#### Dubbo/GRPC test

##### GRPC --> Dubbo

1. Run `org.apache.dubbo.sample.tri.TriGrpcProvider`
2. Run `org.apache.dubbo.sample.tri.grpc.GrpcClientTest`

##### Dubbo --> GRPC

1. Run `org.apache.dubbo.sample.tri.grpc.GrpcServer`
2. Run `org.apache.dubbo.sample.tri.TriClientTest`
