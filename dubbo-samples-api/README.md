# About this sample

This sample code demonstrates building up dubbo service provider and service consumer with the pure API approach. In this example, multicast is facilitated as the registration mechanism, therefore it is necessary to explicitly specify system property `java.net.preferIPv4Stack`.

## Start the service provider

```bash
mvn clean package
mvn -Djava.net.preferIPv4Stack=true -Dexec.mainClass=org.apache.dubbo.samples.provider.Application exec:java
```

## Invoke the service consumer

```bash
mvn -Djava.net.preferIPv4Stack=true -Dexec.mainClass=org.apache.dubbo.samples.client.Application exec:java
```
