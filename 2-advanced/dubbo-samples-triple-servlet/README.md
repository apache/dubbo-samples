# Dubbo Triple Servlet Example

This example shows how to enable servlet support for triple protocol.

## Benefits

After enabled Servlet support, triple protocol reuse existing Spring Boot servlet listening ports to handle HTTP traffic, eliminating the need for Netty to listen on new ports,
that allowing user to use features of servlet container such as filters. This also avoids the maintenance costs associated with adding extra server port and uri mapping, makes it easier to penetrate firewalls and gateways.

## How to run

Step into 'dubbo-samples-triple-servlet' directory
then, run the following command to start application:

```shell
mvn spring-boot:run
```

## Testing using curl

Run the following command to see server works as expected:

```shell
# Passing parameter via query string
curl --http2-prior-knowledge -v 'http://localhost:50052/org.apache.dubbo.demo.GreeterService/sayHelloAsync?request=world'

# Expected output:
#* Connected to localhost (::1) port 50052
#* [HTTP/2] [1] OPENED stream for http://localhost:50052/org.apache.dubbo.demo.GreeterService/sayHelloAsync?request=world
#* [HTTP/2] [1] [:method: GET]
#* [HTTP/2] [1] [:scheme: http]
#* [HTTP/2] [1] [:authority: localhost:50052]
#* [HTTP/2] [1] [:path: /org.apache.dubbo.demo.GreeterService/sayHelloAsync?request=world]
#* [HTTP/2] [1] [user-agent: curl/8.7.1]
#* [HTTP/2] [1] [accept: */*]
#> GET /org.apache.dubbo.demo.GreeterService/sayHelloAsync?request=world HTTP/2
#> Host: localhost:50052
#> User-Agent: curl/8.7.1
#> Accept: */*
#>
#* Request completely sent off
#< HTTP/2 200
#< content-type: application/json
#< date: Sun, 25 Aug 2024 03:38:12 GMT
#<
#"Hello world"
```

## How to enable servlet support for triple

### Add maven dependencies

Note that only spring boot3 is required, spring boot2 has been imported by dubbo-spring-boot-starter

```xml

<dependencys>
    <dependency>
        <groupId>org.apache.dubbo</groupId>
        <artifactId>dubbo-spring-boot-3-autoconfigure</artifactId>
    </dependency>
</dependencys>
```

### Add the following configuration to `application.yml` to enable servlet support

Note that when set the triple port to be the same as the servlet server, triple will no longer start netty port listening, all requests come from servlet server.

```yaml
server:
    port: 50052
    http2:
        enabled: true
    tomcat:
        keep-alive-timeout: -1
dubbo:
    protocol:
        name: tri
        port: ${server.port}
        triple:
            servlet:
                enabled: true
```
