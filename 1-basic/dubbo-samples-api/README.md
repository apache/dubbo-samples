# About this sample

This sample code demonstrates building up dubbo service provider and service consumer with the pure API approach. In this example, multicast is facilitated as the registration mechanism, therefore it is necessary to explicitly specify system property `java.net.preferIPv4Stack`.

## Start Server

```bash
mvn clean package
mvn -Dexec.mainClass=org.apache.dubbo.samples.provider.Application exec:java
```

## Start Client

There are two ways to test the server works as expected:
* Standard HTTP tools like cURL.
* Dubbo sdk client.

### cURL
```shell
curl \
    --header "Content-Type: application/json" \
    --data ["Dubbo"]' \
    http://localhost:50052/org.apache.dubbo.samples.api.GreetingsService/sayHi/
```

### SDK client

```bash
mvn -Dexec.mainClass=org.apache.dubbo.samples.client.Application exec:java
```
