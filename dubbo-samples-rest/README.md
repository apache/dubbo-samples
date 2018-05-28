# dubbo-samples-rest
RPC-Rest samples for Apache Dubbo (incubating)

## Get started

We can use two steps to export Rest Service in Dubbo:
Step 1: Add dependency to the pom file in our project:
```
        <dependency>
            <groupId>com.alibaba</groupId>
            <artifactId>dubbo-rpc-rest</artifactId>
            <version>${dubbo.rpc.version}</version>
        </dependency>
```

Step 2: Export Rest Service in our provider spring config file:

```
    <dubbo:protocol name="rest" port="8888" threads="500" contextpath="services" server="tomcat" accepts="500" />
```

Then we can use the REST web service.

## Integration with Swagger UI


