## 1.Run java server & java client following 

[README](https://github.com/dubbogo/dubbogo-samples/blob/master/README.md)（You must run java program to initialize configuration in zookeeper）
 
## 2.Use condition router feature 

### 2.1.with config file 
 
Modify [router_config.yml](go-client/profiles/dev/router_config.yml) what you want to config
 
[How to write router_config.yml](http://dubbo.apache.org/en-us/docs/user/demos/routing-rule.html)

### 2.2.with config center

#### zookeeper

Use [dubbo-admin](https://github.com/apache/dubbo-admin) to set condition router file.

Must set config center item in client.yml for load router file. like 
```
config_center:
     protocol: "zookeeper"
     address: "127.0.0.1:2181"
```

Make sure dubbo-admin, dubbo-server and dubbo-client use the same zookeeper.
 
## 3.go client 

Then start dubbo-go client following [README](https://github.com/dubbogo/dubbogo-samples/blob/master/README.md).

to check your router, enjoy it.

