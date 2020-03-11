### 1.Run apollo as config center

* download this folder as below

[docker-quick-start](https://github.com/ctripcorp/apollo/tree/master/scripts/docker-quick-start)

* enter docker-quick-start folder and run

```docker-compose up```

### 2.Run java server & java client following [README](https://github.com/dubbogo/dubbo-samples/blob/master/golang/README.md)

* Set config item **app.id**  in /resources/META-INF/app.properties like

```
app.id=application
```

* Set apollo meta url in  /resources/META-INF/spring/dubbo.provider.xml and /resources/META-INF/spring/dubbo.consumer.xml like

```
<dubbo:config-center protocol="apollo" address="1.1.1.1:8080"/>
```

Build and run it , enjoy.
 
### 3.Run java server & go client 

Stop java client. Copy go client configuration file [dubbo.properties](https://github.com/dubbogo/dubbo-samples/golang/blob/master/configcenter/apollo/dubbo/go-client/profiles/dev/dubbo/config/user-info-client/dubbo.properties) as 
namespace dubbo.properties in apollo.

Then start go client following [README](https://github.com/dubbogo/dubbo-samples/blob/master/golang/README.md).

### 4.Run go server

The same as step 3. Copy go server configuration file [dubbo.properties](https://github.com/dubbogo/dubbo-samples/blob/master/golang/configcenter/apollo/dubbo/go-server/profiles/dev/dubbo/config/user-info-server/dubbo.properties) as 
namespace dubbo.properties in apollo.

