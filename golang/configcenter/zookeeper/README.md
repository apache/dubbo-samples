### 1.Run zookeeper as config center

```docker-compose -f docker/docker-compose.yaml up -d```

### 2.Run java server & java client following [README](https://github.com/dubbogo/dubbogo-samples/blob/master/README.md)（You must run java program to initialize configuration in zookeeper）

Java program will create consumer & provider common configuration in /dubbo/config/dubbo/dubbo.properties, consumer configuration in /dubbo/config/user-info-client/dubbo.properties
 and provider configuration in /dubbo/config/user-info-server/dubbo.properties.
 
### 3.Run java server & go client 

Stop java client. Copy go client configuration file [dubbo.properties](https://github.com/dubbogo/dubbogo-samples/blob/master/configcenter/zookeeper/dubbo/go-client/profiles/dev/dubbo/config/user-info-client/dubbo.properties) as 
zookeeper file /dubbo/config/user-info-client/dubbo.properties in zookeeper node path /dubbo/config/user-info-client/.

Then start go client following [README](https://github.com/dubbogo/dubbogo-samples/blob/master/README.md).

### 4.Run go server

The same as step 3. Copy go server configuration file [dubbo.properties](https://github.com/dubbogo/dubbogo-samples/blob/master/configcenter/zookeeper/dubbo/go-server/profiles/dev/dubbo/config/user-info-server/dubbo.properties) as 
zookeeper file /dubbo/config/user-info-server/dubbo.properties in zookeeper node path /dubbo/config/user-info-server/.

