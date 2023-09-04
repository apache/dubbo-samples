# 使用Metrics模块进行数据采集然后暴漏数据给普罗米修斯监控
Dubbo使用开源的[Dubbo Metrics](https://github.com/alibaba/metrics)进行数据埋点，并且通过服务暴露，使用的时候，首先需要进行配置:  

* 依赖(其中Dubbo版本在3.2.0及以后)
```xml
  <dependency>
  <groupId>org.apache.dubbo</groupId>
  <artifactId>dubbo-spring-boot-observability-starter</artifactId>
</dependency>

```
* 服务端
```xml
<dubbo:metrics protocol="prometheus">
    <dubbo:aggregation enabled="true"/>
</dubbo:metrics>

```

* 客户端
```xml
 <dubbo:metrics protocol="prometheus">
    <dubbo:aggregation enabled="true"/>
</dubbo:metrics>

```
先启动服务端，然后启动客户端，
提供端监控指标：http://localhost:20888/metrics
消费端监控指标：http://localhost:20889/metrics

 
可观测性文档如下链接：
  [https://cn.dubbo.apache.org/zh-cn/overview/tasks/observability/metrics-start/](https://cn.dubbo.apache.org/zh-cn/overview/tasks/observability/metrics-start/)