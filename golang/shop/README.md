# Shop sample

An example that simulates a real service invocation.

# How to run it

## With a register

Must set the same registry item in client.yml or provider.yml for config file. like
```yaml
registries:
  "demoZk":
    protocol: "zookeeper"
    timeout: "3s"
    address: "127.0.0.1:2181"
```

## Start service product(provider side)

Run /XXX/dubbo-samples/golang/shop/dubbo/go-service-product/app/service.go

export CONF_PROVIDER_FILE_PATH="/XXX/dubbo-samples/golang/shop/dubbo/go-service-product/profiles/server.yml"
export APP_LOG_CONF_FILE="/XXX/dubbo-samples/golang/shop/dubbo/go-service-product/profiles/log.yml"

## Start service order(consumer and provider side)

Run /XXX/dubbo-samples/golang/shop/dubbo/go-service-order/app/service.go

export CONF_PROVIDER_FILE_PATH="/XXX/dubbo-samples/golang/shop/dubbo/go-service-order/profiles/server.yml"
export CONF_CONSUMER_FILE_PATH="/XXX/dubbo-samples/golang/shop/dubbo/go-service-order/profiles/client.yml"
export APP_LOG_CONF_FILE="/XXX/dubbo-samples/golang/shop/dubbo/go-service-order/profiles/log.yml"

## Start service user(consumer side)

Run /XXX/dubbo-samples/golang/shop/dubbo/go-service-user/app/service.go

export CONF_CONSUMER_FILE_PATH="/XXX/dubbo-samples/golang/shop/dubbo/go-service-user/profiles/client.yml"
export APP_LOG_CONF_FILE="/XXX/dubbo-samples/golang/shop/dubbo/go-service-user/profiles/log.yml"

## The call chain

user(order_consumer) --> order(order_provider --> product_consumer) --> product(product_provider)

if run success, you can see user and order model print the rpc result.