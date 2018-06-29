# dubbo-samples-rest
RPC-Rest samples for Apache Dubbo (incubating)

## Get started

Git clone and Package this demo project:
```
git clone https://github.com/dubbo/dubbo-samples
cd dubbo-samples/dubbo-samples-rest
mvn package
```

We will find target/dubbo-samples-rest-1.0-SNAPSHOT.war
1. Then we modify tomcat server port to 8888, 
2. and rename dubbo-samples-rest-1.0-SNAPSHOT.war to ROOT.war,
3. and put ROOT.war into tomcat webapps directory, 
4. download and start a zookeeper server,
5. run bin/startup.sh or bin/startup.bat to start tomcat.
6. Access http://localhost:8888/swagger/ to see Rest APIs.
7. We can try to invoke rest web service here.

![](https://raw.githubusercontent.com/dubbo/dubbo-samples/master/dubbo-samples-rest/screenshots/01.png)
![](https://raw.githubusercontent.com/dubbo/dubbo-samples/master/dubbo-samples-rest/screenshots/02.png)

## Export Rest web services 

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

We can use two step to integration with Swagger UI in your webapps:

Step 1: copy dubbo-samples-rest\src\main\webapp\swagger to your webapps ROOT Directory.

Step 2: copy these config to your spring config file:

```
<!-- SwaggerUI -->
    <bean id="swaggerService" class="com.alibaba.dubbo.integration.swagger.DubboSwaggerApiListingResource" />
    <bean id="beanConfig" class="io.swagger.jaxrs.config.BeanConfig">
        <property name="schemes" value="http" />
        <property name="resourcePackage" value="com.alibaba.dubbo.samples.rest.api"/>
        <property name="version" value="2.0"/>
        <property name="host" value="localhost:8888"/>
        <property name="basePath" value="/services/"/>
        <property name="title" value="title"/>
        <property name="description" value="desc"/>
        <property name="contact" value="abc"/>
        <property name="license" value="Apache 2.0"/>
        <property name="licenseUrl" value="http://www.apache.org/licenses/LICENSE-2.0.html"/>
        <property name="scan" value="true" />
    </bean>
    <dubbo:service interface="com.alibaba.dubbo.integration.swagger.DubboSwaggerService" ref="swaggerService" protocol="rest" />

```

Any question could be discuss in Issue Board.
