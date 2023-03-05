# 使用Metrics模块进行数据采集然后暴漏数据给普罗米修斯监控
如果需要在springboot-actuator中返回dubbo的指标信息,dubbo 版本必须为3.2.0-beta.6 以上
* 服务端配置参考
```yaml
management.metrics.tags.application=dubbo-samples-metrics-spring-boot
management.server.port=18081
management.endpoints.web.base-path=/management
management.endpoints.web.exposure.include=info,health,env,prometheus
spring.main.allow-circular-references=true
management.endpoint.metrics.enabled=true
management.endpoint.prometheus.enabled=true
management.metrics.export.prometheus.enabled=true
server.port=18080
dubbo.application.name=metrics-provider
dubbo.registry.address=zookeeper://${ZOOKEEPER_ADDRESS:127.0.0.1}:2181
dubbo.metrics.protocol=prometheus
#如果不使用spring-boot-actuator 可使用下面配置
#dubbo.metrics.enable-jvm-metrics=true
#dubbo.metrics.prometheus.exporter.enabled=true
#dubbo.metrics.prometheus.exporter.metrics-port=20888
#prometheus.exporter.metrics.path=/prometheus
```


启动MetricsApplication  访问监控指标：http://localhost:18081/management/prometheus

可观测性文档如下链接：
[https://cn.dubbo.apache.org/zh/docs3-v2/java-sdk/advanced-features-and-usage/observability/](https://cn.dubbo.apache.org/zh/docs3-v2/java-sdk/advanced-features-and-usage/observability/)

# 部署到K8S
本示例通过[kube-prometheus](https://github.com/prometheus-operator/kube-prometheus)
构建k8s的prometheus环境

为了方便访问验证可以将`alertmanager-service.yaml``grafana-service.yaml``prometheus-service.yaml`设置为NodePort

1. 添加 `dubboPodMoitor.yaml` 到 `kube-prometheus`的`manifests` 目录。配置如下
 ```yaml
apiVersion: monitoring.coreos.com/v1
kind: PodMonitor
metadata:
  name: podmonitor
  labels:
    app: podmonitor
  namespace: monitoring
spec:
  selector:
    matchLabels:
      app-type: dubbo
  namespaceSelector:
    matchNames:
      - monitoring
  podMetricsEndpoints:
    - port: metrics #这里需要通过端口名绑定
      path: /management/prometheus

```
2. 配置`prometheus-prometheus.yaml`添加podMonitorSelector  

```yaml
podMonitorSelector:
  matchLabels:
    app: podmonitor
```
3. 部署prometheus环境  
    详细部署步骤[kube-prometheus](https://github.com/prometheus-operator/kube-prometheus)
4. 使用`./Deployment.yaml` 部署dubbo 应用
5. 打开prometheus查看结果如下
   ![result.png](result.png)
