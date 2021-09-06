## Triple-Samples

### For beginners

1. Start a zookeeper or config other registry in `org.apache.dubbo.sample.tri.ApiProvider`
   and `org.apache.dubbo.sample.tri.ApiConsumer`
2. Build with `mvn clean install` to generate protobuf classes.
3. Run `org.apache.dubbo.sample.tri.ApiProvider`
4. Run `org.apache.dubbo.sample.tri.ApiConsumer`

### For dubbo2 or non-prototbuf users
Run `org.apache.dubbo.sample.tri.ApiWrapperProvider` and `org.apache.dubbo.sample.tri.ApiWrapperConsumer`.

Dubbo3 will wrap request and response with a protobuf wrapper automatically, so it is still compatible with GRPC.

### For Dubbo developers
Code under `test` folder is a general interoperation test suite for both Dubbo/Dubbo and Dubbo/GRPC.

#### Dubbo/Dubbo test
1. Run `org.apache.dubbo.sample.tri.TriProvider`
2. Run `org.apache.dubbo.sample.tri.TriPbConsumerTest` , `org.apache.dubbo.sample.tri.TriWrapConsumerTest`  and `org.apache.dubbo.sample.tri.TriGenericTest`

#### DUbbo/GRPC test

##### GRPC --> Dubbo

1. Run `org.apache.dubbo.sample.tri.TriProvider`
2. Run `org.apache.dubbo.sample.tri.GrpcConsumerTest`

##### Dubbo --> GRPC

1. Run `org.apache.dubbo.sample.tri.GrpcProvider`
2. Run `org.apache.dubbo.sample.tri.TriPbConsumerTest`




