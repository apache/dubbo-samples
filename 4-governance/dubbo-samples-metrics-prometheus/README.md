# 使用Metrics模块进行数据采集然后暴漏数据给普罗米修斯监控
Dubbo使用开源的[Dubbo Metrics](https://github.com/alibaba/metrics)进行数据埋点，并且通过服务暴露，使用的时候，首先需要进行配置:  

* 依赖(其中Dubbo版本在3.2.0-beta及以后)
```xml
  <dependency>
    <groupId>org.apache.dubbo</groupId>
    <artifactId>dubbo-spring-boot-actuator</artifactId>
</dependency>

<dependency>
    <groupId>org.apache.dubbo</groupId>
    <artifactId>dubbo-metrics-prometheus</artifactId>
</dependency>

```
* 服务端
```xml
<dubbo:metrics protocol="prometheus">
    <dubbo:aggregation enabled="true"/>
    <dubbo:prometheus-exporter enabled="true"/>
</dubbo:metrics>

```

* 客户端
```xml
 <dubbo:metrics protocol="prometheus">
    <dubbo:aggregation enabled="true"/>
    <dubbo:prometheus-exporter enabled="true" metrics-port="20889"/>
</dubbo:metrics>

```
先启动服务端，然后启动客户端，
提供端监控指标：http://localhost:20888/metrics
消费端监控指标：http://localhost:20889/metrics

其中，`<dubbo:metrics />`的配置指定了metrics暴露的协议和端口，这些信息会被放在元数据里面，而`<dubbo:provider />`和`<dubbo: consumer />`则在服务端和客户端分别激活了metrics的filter，如果你的服务分别含有服务端和客户端，选择在一端激活就可以。

可观测性文档如下链接：
  [https://cn.dubbo.apache.org/zh/docs3-v2/java-sdk/advanced-features-and-usage/observability/](https://cn.dubbo.apache.org/zh/docs3-v2/java-sdk/advanced-features-and-usage/observability/)