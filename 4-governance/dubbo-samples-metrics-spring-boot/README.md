# 使用Metrics模块进行数据采集然后暴漏数据给普罗米修斯监控
 
* 服务端
```xml
 <dubbo:metrics protocol="prometheus" enable-jvm-metrics="true">
  <dubbo:aggregation enabled="true"/>
  <dubbo:prometheus-exporter enabled="false"  metrics-port="20888"/>
</dubbo:metrics>

```

  
启动MetricsApplication  访问监控指标：http://localhost:8081/management/prometheus

可观测性文档如下链接：
  [https://cn.dubbo.apache.org/zh/docs3-v2/java-sdk/advanced-features-and-usage/observability/](https://cn.dubbo.apache.org/zh/docs3-v2/java-sdk/advanced-features-and-usage/observability/)