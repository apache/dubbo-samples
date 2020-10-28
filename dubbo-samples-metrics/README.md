# 使用Metrics模块进行数据采集
Dubbo使用开源的[Dubbo Metrics](https://github.com/alibaba/metrics)进行数据埋点，并且通过服务暴露，使用的时候，首先需要进行配置:  

* 依赖(其中dubbo版本在2.7.2及以后)
```xml
<dependency>
    <groupId>org.apache.dubbo</groupId>
    <artifactId>dubbo-monitor-default</artifactId>
    <version>${dubbo.version}</version> 
</dependency>
```
* 服务端
```xml
<dubbo:metrics port="20880" protocol="dubbo"/>
<dubbo:provider filter="metrics" />
```

* 客户端
```xml
<dubbo:metrics port="20880" protocol="dubbo"/>
<dubbo:consumer filter="metrics" />
```
其中，`<dubbo:metrics />`的配置指定了metrics暴露的协议和端口，这些信息会被放在元数据里面，而`<dubbo:provider />`和`<dubbo: consuer />`则在服务端和客户端分别激活了metrics的filter，如果你的服务分别含有服务端和客户端，选择在一端激活就可以。

* dubbo admin  
完成这些配置后，获取[dubbo admin](https://github.com/apache/dubbo-admin)在`develop`分支的最新代码，启动后，在左侧Metrics(统计)标签输入目标机器的IP，可以看到调用，线程池等Metrics信息
