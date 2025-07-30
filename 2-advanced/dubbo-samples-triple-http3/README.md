# Dubbo Triple HTTP/3 Example

This example shows how to enable HTTP/3 support for triple protocol.

## Benefits

1. Improved Performance: HTTP/3 uses QUIC, reducing latency and enhancing connection speeds. This results in faster request-response cycles and better performance, particularly in high-latency scenarios.
2. Enhanced Reliability: With connection migration and multiplexing, HTTP/3 prevents head-of-line blocking, ensuring stable connections even in varying network conditions.
3. Resilience in Weak Networks: HTTP/3 is more resilient in environments with high packet loss or fluctuating bandwidth, maintaining connection quality and performance where older protocols might struggle.
4. Better Security: HTTP/3, built on QUIC, integrates encryption by default, offering enhanced security and reducing the attack surface compared to traditional HTTP/2 implementations.
5. Efficient Resource Usage: By minimizing the need for multiple connections and reducing handshake overhead, HTTP/3 optimizes resource utilization, lowering CPU and memory consumption on both client and server sides.

## How to run

Step into 'dubbo-samples-triple-http3' directory
then, run the following command to start application:

```shell
mvn spring-boot:run
```

## Testing using curl

Note that curl needs to upgrade to a new version which supports HTTP/3

See: https://curl.se/docs/http3.html

Run the following command to see server works as expected:

```shell
# Passing parameter via query string
curl --http3 -vk 'https://localhost:50052/org.apache.dubbo.demo.GreeterService/sayHelloAsync?request=world'

# Expected output:
#* QUIC cipher selection: TLS_AES_128_GCM_SHA256:TLS_AES_256_GCM_SHA384:TLS_CHACHA20_POLY1305_SHA256:TLS_AES_128_CCM_SHA256
#* Skipped certificate verification
#* Connected to localhost (127.0.0.1) port 50052
#* using HTTP/3
#* [HTTP/3] [0] OPENED stream for https://localhost:50052/org.apache.dubbo.demo.GreeterService/sayHelloAsync?request=world
#* [HTTP/3] [0] [:method: GET]
#* [HTTP/3] [0] [:scheme: https]
#* [HTTP/3] [0] [:authority: localhost:50052]
#* [HTTP/3] [0] [:path: /org.apache.dubbo.demo.GreeterService/sayHelloAsync?request=world]
#* [HTTP/3] [0] [user-agent: curl/8.7.1]
#* [HTTP/3] [0] [accept: */*]
#> GET /org.apache.dubbo.demo.GreeterService/sayHelloAsync?request=world HTTP/3
#> Host: localhost:50052
#> User-Agent: curl/8.7.1
#> Accept: */*
#>
#* Request completely sent off
#< HTTP/3 200
#< content-type: application/json
#<
#"Hello world"
```

## How to enable HTTP/3 support for triple

### Add maven dependencies

```xml

<dependencys>
    <dependency>
        <groupId>io.netty</groupId>
        <artifactId>netty-codec-http3</artifactId>
        <version>4.2.2.Final</version>
    </dependency>
    <!-- To support self-signed certificates, if the certificate is configured that does not require -->
    <dependency>
        <groupId>org.bouncycastle</groupId>
        <artifactId>bcpkix-jdk15on</artifactId>
        <version>1.70</version>
    </dependency>
</dependencys>
```

### Add the following configuration to `application.yml` to enable HTTP/3

```yaml
dubbo:
    protocol:
        triple:
            http3:
                enabled: true
```

### Disable client negotiation

Since HTTP/3 is based on the QUIC protocol(UDP), it may be blocked by firewall or gateway. Therefore, HTTP/3 negotiation is enabled by default.
The connection is first established via HTTP/2, and if successful and the provider return a [Alt-Svc](https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/Alt-Svc) header indicating HTTP/3 support,
the client will automatically switch to HTTP/3. If you want to skip negotiation, you can disable the negotiation feature through configuration, though it is recommended not to disable it.

```yaml
dubbo:
    protocol:
        triple:
            http3:
                enabled: true
                negotiation: false
```
