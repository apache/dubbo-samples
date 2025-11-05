# About this sample

This sample code demonstrates building up dubbo service provider and service consumer with the pure API approach.   

## About Request and Response Entity
Google protocol file is in protobuf-json-serialization-api\src\main\resources\protobuf\googleProtobufBasic.proto.
when protocol be modified, should refresh **org.apache.dubbo.sample.protobuf.GoogleProtobufBasic**.
1. modify googleProtobufBasic.proto
2. add <plugin> protobuf-maven-plugin</plugin> to dubbo-samples-protobuf\protobuf-json-serialization-api\pom.xml
3. run bash ```
            mvn generate-sources
            ```
4. copy code generated located in target directory to  dubbo-samples-protobuf\protobuf-json-serialization-api\src\main\java

## Start the service provider

```
Main class: org.apache.dubbo.sample.protobuf.provider.ProviderStarter
```

## Invoke the service consumer

```
normar client
Main class: org.apache.dubbo.sample.protobuf.consumer.ConsumerStarter

generic reference
Main class: org.apache.dubbo.sample.protobuf.genericCall.GenericClient
```

## About Service Metadata of protobuf Service
When invoke service with generic reference, we can construct request by referencing service meta data.
Demo show how to use service meta data.
```
org.apache.dubbo.sample.protobuf.genericCall.GenericClient.printServiceData
``` 
