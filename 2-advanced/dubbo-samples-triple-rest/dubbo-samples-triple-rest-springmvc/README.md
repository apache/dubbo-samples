# Dubbo Triple Rest Spring Web Example

This example shows how to export rest API using the spring web annotation in triple protocol.

## How to run

Step into 'dubbo-samples-triple-rest-springmvc' directory
then, run the following command to start application:

```shell
mvn spring-boot:run
```

Run the following command to see server works as expected:

```shell
# Passing parameter via query string
curl 'http://localhost:50052/demo/hello?name=world'
# Expected output: "Hello world"
# With double quotes because the default output content-type is 'application/json'
```

Or, you can visit the following link by using web browser: `http://localhost:50052/demo/hello?name=world`
