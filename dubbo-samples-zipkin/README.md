# Dubbo zipkin example

This example demonstrates the basic usage of zipkin in Dubbo application. This example contains three parts, a client calls GreetingService, and then GreetingService calls HelloService. In order to make the demo simple, this example sticks to the classic spring XML configuration. There's no use of annotation or spring boot involved at all. If you prefer to use annotation or spring boot, you may need to refer to the documentation for the advanced usage in both Dubbo and Zipkin.

## How to run

### Install Zipkin

Follow [Zipkin's quick start](https://zipkin.io/pages/quickstart.html) to install zipkin.

```bash
curl -sSL https://zipkin.io/quickstart.sh | bash -s
```

Zipkin supports various backend storages including Cassandra, ElasticSearch and MySQL. Here we use the simplest storage - in-memory for demo purpose.

```bash
java -jar zipkin.jar
```

Once the process starts, you can verify zipkin server works by access http://localhost:9411

### Build

Step into `dubbo-samples-zipkin` and execute the following command to build the project:

```bash
mvn clean package
```

### Run

This example relies on external Zookeeper server for service discovery, and assumes it has already been installed. If not, pls. download and install it from [Zookeeper's home](https://zookeeper.apache.org).

Start Zookeeper server:

```bash
zkServer start
```

Alternatively a docker compose file is provided in this example in order to start up both Zipkin server and Zookeeper server together.

```bash
cd main/resources/docker
docker-compose up
```

Start `org.apache.dubbo.samples.service.hello.Application` in command line, you can also start it directly from IDE:

```bash
mvn exec:java -Dexec.mainClass=org.apache.dubbo.samples.service.hello.Application
```

Start `org.apache.dubbo.samples.service.greeting.Application` in command line, you can also start it directly from IDE:

```bash
mvn exec:java -Dexec.mainClass=org.apache.dubbo.samples.service.greeting.Application
```

Start client in command line, you can also start it directly from IDE:

```bash
mvn exec:java -Dexec.mainClass=org.apache.dubbo.samples.client.Application
```

"greeting, hello, world" should be print on the screen, then open http://localhost:9411 to check the trace. The sample trace in JSON format may look like this:

```json
[
  {
    "traceId": "a9aaaff4efda4419",
    "parentId": "a9aaaff4efda4419",
    "id": "d1022ceacb4d6c85",
    "kind": "SERVER",
    "name": "helloservice/hello",
    "timestamp": 1539078437286470,
    "duration": 3065,
    "localEndpoint": {
      "serviceName": "hello-service",
      "ipv4": "192.168.99.1"
    },
    "remoteEndpoint": {
      "ipv4": "192.168.99.1",
      "port": 50765
    },
    "shared": true
  },
  {
    "traceId": "a9aaaff4efda4419",
    "parentId": "a9aaaff4efda4419",
    "id": "d1022ceacb4d6c85",
    "kind": "CLIENT",
    "name": "helloservice/hello",
    "timestamp": 1539078437197061,
    "duration": 117181,
    "localEndpoint": {
      "serviceName": "greeting-service",
      "ipv4": "192.168.99.1"
    },
    "remoteEndpoint": {
      "ipv4": "192.168.99.1",
      "port": 20880
    }
  },
  {
    "traceId": "a9aaaff4efda4419",
    "id": "a9aaaff4efda4419",
    "kind": "SERVER",
    "name": "greetingservice/greeting",
    "timestamp": 1539078437192629,
    "duration": 127749,
    "localEndpoint": {
      "serviceName": "greeting-service",
      "ipv4": "192.168.99.1"
    },
    "remoteEndpoint": {
      "ipv4": "192.168.99.1",
      "port": 50767
    },
    "shared": true
  },
  {
    "traceId": "a9aaaff4efda4419",
    "id": "a9aaaff4efda4419",
    "kind": "CLIENT",
    "name": "greetingservice/greeting",
    "timestamp": 1539078437064641,
    "duration": 266242,
    "localEndpoint": {
      "serviceName": "client",
      "ipv4": "192.168.99.1"
    },
    "remoteEndpoint": {
      "ipv4": "192.168.99.1",
      "port": 20881
    }
  }
]
```


