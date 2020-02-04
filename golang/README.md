# examples

Examples of dubbo-go

## How to contribute

If you want to add some samples, we hope that you can do this:
1. Adding samples in appropriate directory. If you dont' know which directory you should put your samples into, you can get some advices from dubbo-go community.
2. You must run the samples locally and there must be no any error;
3. If your samples have some third party dependency, including another framework, we hope that you can provide some docs, script is better.

## What does this contain

* helloworld

    A simplest example. It contain 'go-client', 'go-server', 'java-server' of dubbo protocol. 

* general

    A general example. It had validated zookeeper registry and different parameter lists of service. 
  And it has a comprehensive testing with dubbo/jsonrpc protocol. You can refer to it to create your first complete dubbo-go project.

* generic

    A generic reference example. It show how to use generic reference of dubbo-go.

* configcenter

    Some examples of different config center. There are only two -- zookeeper and apollo at present.


* multi_registry

    An example of multiple registries.

* registry

    Some examples of different registry. There is only kubernetes at present.

* filter

    Some examples of different filter. Including custom_filter and tpslimit

## How to build and run

> Take `helloworld` as an example
java server

```bash
cd helloworld/dubbo/java-server
sh build.sh
cd ./target
tar -zxvf user-info-server-0.2.0-assembly.tar.gz
cd ./user-info-server-0.2.0
sh ./bin/server.sh start
```

java client

```bash
cd helloworld/dubbo/java-client
sh build.sh
cd ./target
tar -zxvf user-info-client-0.2.0-assembly.tar.gz
cd ./user-info-client-0.2.0
sh ./bin/server.sh start
```

go server

* $ARCH = [linux, mac, windows] and $ENV = [dev, release, test]

```bash
cd helloworld/dubbo/go-server
sh ./assembly/$ARCH/$ENV.sh
cd ./target/linux/user_info_server-0.3.1-20190517-0930-release
# $SUFFIX is a suffix of config file,
# such as server_zookeeper.yml when $SUFFIX is "zookeeper", 
# if $SUFFIX = "", default server.yml
sh ./bin/load.sh start $SUFFIX
```

go client

* $ARCH = [linux, mac, windows] and $ENV = [dev, release, test]

```bash
cd helloworld/dubbo/go-client
sh ./assembly/$ARCH/$ENV.sh
cd ./target/linux/user_info_client-0.3.1-20190517-0921-release
# $SUFFIX is a suffix of config file,
# such as client_zookeeper.yml when $SUFFIX = zookeeper", 
# if $SUFFIX = "", config file is client.yml
sh ./bin/load_user_info_client.sh start $SUFFIX
```

kubernetes 

```bash 

# create service-account
kubectl create -f ./registry/kubernetes/sa.yaml

# create role 
kubectl create -f ./registry/kubernetes/role.yaml

# bind role and service-account
kubectl create -f ./registry/kubernetes/role-binding.yaml

# create server
kubectl create -f ./registry/kubernetes/server.yaml

# create client
kubectl create -f ./registry/kubernetes/client.yaml

# read the client log
kubectl logs -f client

## uninstall 
kubectl delete -f ./registry/kubernetes/sa.yaml
kubectl delete -f ./registry/kubernetes/role.yaml
kubectl delete -f ./registry/kubernetes/role-binding.yaml
kubectl delete -f ./registry/kubernetes/server.yaml
kubectl delete -f ./registry/kubernetes/client.yaml
```

nacos

You should install the Docker before you run `docker-compose up` to start the nacos, prometheus and grafana.

And the directory you run `docker-compose up` could be bind mounted into containers(If you are macOS user, take care of it).

And then you should create the file which will be mounted into container manually, including ./init.d/custom.properties
and ./prometheus/prometheus-standalone.yaml. In fact, all files(not directory) mentioned in docker-compose.yml should be created manually. 