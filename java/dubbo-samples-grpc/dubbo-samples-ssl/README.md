
## How to run

This sample needs TLS cert files to run.

> This sample has test certs provided in the resources/certs directory, you can use the directory or generate by yourself.

Here's the procedures of how to run.

First, we need to build the executable jar for consumer and provider respectively.

```shell
$ cd dubbo-samples-ssl
$ mvn clean package
```

Then, start the provider

```sh
$ # enter the target directory
$ cd ./dubbo-samples-grpc-ssl-provider/target
$ # run
$ java "-Ddubbo.ssl.server-key-cert-chain-path={your absolute path to}/certs/server0.pem" "-Ddubbo.ssl.server-private-key-path={your absolute path to}/certs/server0.key" -jar dubbo-samples-grpc-ssl-provider-1.0-SNAPSHOT-exec.jar
```

Finally, start the consumer

```sh
$ # enter the target directory
$ cd ./dubbo-samples-grpc-ssl-consumer/target
$ # run
$ java "-Ddubbo.ssl.client-trust-cert-collection-path={your absolute path to}/certs/ca.pem" -jar dubbo-samples-grpc-ssl-consumer-1.0-SNAPSHOT-exec.jar
```

For example, in my own environment, I run 

```sh
$ java "-Ddubbo.ssl.server-key-cert-chain-path=/Users/ken.lj/aliware/dubboprojects/codebase/dubbo-samples/java/dubbo-samples-grpc/dubbo-samples-ssl/dubbo-samples-grpc-ssl-provider/src/main/resources/certs/server0.pem" "-Ddubbo.ssl.server-private-key-path=/Users/ken.lj/aliware/dubboprojects/codebase/dubbo-samples/java/dubbo-samples-grpc/dubbo-samples-ssl/dubbo-samples-grpc-ssl-provider/src/main/resources/certs/server0.key" -jar dubbo-samples-grpc-ssl-provider-1.0-SNAPSHOT-exec.jar
$ # and
$ java "-Ddubbo.ssl.client-trust-cert-collection-path=/Users/ken.lj/aliware/dubboprojects/codebase/dubbo-samples/java/dubbo-samples-grpc/dubbo-samples-ssl/dubbo-samples-grpc-ssl-consumer/src/main/resources/certs/ca.pem" -jar dubbo-samples-grpc-ssl-consumer-1.0-SNAPSHOT-exec.jar
```
