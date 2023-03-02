# Thrift samples

## How to build

1. Install thrift

Download thrift binary from [here](https://thrift.apache.org/download). If you work on Mac OS, you can simply install it with the following command:

```bash
brew install thrift
```

2. Generate org.apache.dubbo.samples.local.api.LocalService.java

Once you update the location of the thrift binary downloaded just now in pom.xml:

```xml
<properties>
    <thrift.path>/usr/local/bin/thrift</thrift.path>
</properties>
```

then you can execute the following command to generate *org.apache.dubbo.samples.rpc.nativethrift.api.org.apache.dubbo.samples.local.api.LocalService* from *thrift/org.apache.dubbo.samples.local.api.LocalService.thrift*:

```bash
mvn -Pthrift-gen clean package
```

Alternatively you can use the following script to achieve the same purpose:

```bash
#!/usr/bin/env bash
service_dir="./java/"
for thrift_file in ./idls/*
do
    if test -f ${thrift_file}
    then
        thrift --gen java --out ${service_dir} ${thrift_file}
    fi
done
```

Once the *org.apache.dubbo.samples.local.api.LocalService.java* is generated, you can use the following command for the continuous build:

```bash
mvn clean package
```
