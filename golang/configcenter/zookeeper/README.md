### 1.Run zookeeper as config center

```docker-compose -f docker/docker-compose.yaml up -d```

### 2.Run java server & java client following [README](https://github.com/dubbogo/dubbo-samples/blob/master/golang/README.md)（You must run java program to initialize configuration in zookeeper）

Java program will create consumer & provider common configuration in /dubbo/config/dubbo/dubbo.properties, consumer configuration in /dubbo/config/user-info-client/dubbo.properties
 and provider configuration in /dubbo/config/user-info-server/dubbo.properties.
 
### 3.Run java server & go client 

Stop java client. Copy go client configuration file [dubbo.properties](https://github.com/dubbogo/dubbo-samples/blob/master/golang/configcenter/zookeeper/dubbo/go-client/profiles/dev/dubbo/config/user-info-client/dubbo.properties) as 
zookeeper file /dubbo/config/user-info-client/dubbo.properties in zookeeper node path /dubbo/config/user-info-client/.

Use the script to set configuration file to zookeeper as below
```
./zkCli.sh -server 127.0.0.1:2181 set /dubbo/config/user-info-client/dubbo.properties "dubbo.service.com.ikurento.user.UserProvider.cluster=failback
dubbo.service.com.ikurento.user.UserProvider.protocol=myDubbo
dubbo.protocols.myDubbo.port=20000
dubbo.protocols.myDubbo.name=dubbo
"
```

Then start go client following [README](https://github.com/dubbogo/dubbo-samples/blob/master/golang/README.md).

### 4.Run go server

The same as step 3. Copy go server configuration file [dubbo.properties](https://github.com/dubbogo/dubbo-samples/blob/master/golang/configcenter/zookeeper/dubbo/go-server/profiles/dev/dubbo/config/user-info-server/dubbo.properties) as 
zookeeper file /dubbo/config/user-info-server/dubbo.properties in zookeeper node path /dubbo/config/user-info-server/.

Use the script to set configuration file to zookeeper as below
```
./zkCli.sh -server 127.0.0.1:2181 set /dubbo/config/user-info-server/dubbo.properties "dubbo.service.com.ikurento.user.UserProvider.cluster=failback
dubbo.service.com.ikurento.user.UserProvider.protocol=myDubbo
dubbo.protocols.myDubbo.port=20000
dubbo.protocols.myDubbo.name=dubbo
"
```