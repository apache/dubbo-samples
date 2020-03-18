### 1.Run jaeger with docker

[jaeger-getting-started](https://www.jaegertracing.io/docs/1.17/getting-started/)

use [all-in-one](https://hub.docker.com/r/jaegertracing/all-in-one) image

### 2.Start zookeeper

### 3.Run go server

Set Jaeger ENV variable
```
CONF_PROVIDER_FILE_PATH=xxxxxxxxxxxxx
JAEGER_AGENT_PORT=32769
JAEGER_AGENT_HOST=localhost
JAEGER_SERVICE_NAME=GrpcServer
JAEGER_SAMPLER_PARAM=1
```

Detail: [jaeger-environment-variables](https://github.com/jaegertracing/jaeger-client-go#environment-variables)

PS:
* JAEGER_SAMPLER_PARAM must set ```1``` , it means 100% requests will be used for sample. Set ```0.9``` means 90% requests.
* JAEGER_AGENT_PORT=32769. ```32769``` is docker published port, it map to ```6831```, golang client will use ```6831``` for send tracing data.

Then run go server.

### 4.Run go client

Set Jaeger ENV variable
```
CONF_CONSUMER_FILE_PATH=xxxxxxxxxxxxx
JAEGER_AGENT_PORT=32769
JAEGER_AGENT_HOST=localhost
JAEGER_SERVICE_NAME=GrpcClient
JAEGER_SAMPLER_PARAM=1
```

Detail: [jaeger-environment-variables](https://github.com/jaegertracing/jaeger-client-go#environment-variables).

Then start go client following [README](https://github.com/dubbogo/dubbo-samples/blob/master/golang/README.md).

### 5.Check tracing data on Jeager-UI

Open [http://localhost:32768/search](http://localhost:32768/search)

