# Dubbo Triple Rest Basic Example

This example shows how to export rest API using the built-in annotation in triple protocol.

## How to run

Step into 'dubbo-samples-triple-rest-basic' directory
then, run the following command to start application:

```shell
mvn spring-boot:run
```

Run the following command to see server works as expected:

```shell
# Passing parameter via query string
curl -i 'http://localhost:50052/org.apache.dubbo.rest.demo.DemoService/hello?name=world'
# Expected output: "Hello world"
# With double quotes because the default output content-type is 'application/json'

# Passing parameter via body
curl -i -H 'content-type: application/json' -d '{"title":"Mr","name":"Yang"}' 'http://localhost:50052/org.apache.dubbo.rest.demo.DemoService/helloUser'
# Expected output: "Hello Mr. Yang"

# Multiple ways to pass parameters
curl -i -H "c: 3" -d 'name=Yang' "http://localhost:50052/org.apache.dubbo.rest.demo.DemoService/hi.txt?title=Mr"
# Expected output: Hello Mr. Yang, 3
# No double quotes because we specify the output content-type is 'text/plain' by suffixing '.txt'
```

Or, you can visit the following link by using web browser: `http://localhost:50052/demo/hello?name=world`
