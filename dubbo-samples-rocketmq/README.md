
### 特性说明

Dubbo 自 2.7.5 版本开始支持 RocketMQ 协议。RocketMQ协议是基于RocketMQ-功能进行通信，适合需要通信信息记录或则金融场景。

1. [RokcetMQ基本兼容dubbo的原生dubbo协议与序列化协议[请点击]](https://dubbo.apache.org/zh/docs3-v2/java-sdk/reference-manual/protocol/dubbo/)
2. [RocketMQ官方安装教程[请点击]](https://rocketmq.apache.org/zh/docs/quickStart/02quickstart)

### 协议配置
最简单配置
```
dubbo.registry.address=nameservice://{nameservice-address}
dubbo.protocol.name=rocketmq
```
#### 配置注册中心

因为RocketMQ的borker元数据都由RocketMQ管理组件name-service负责，所以RocketMQ协议的注册中心只能使用name-service
因自动创建topic方式，在分布式集群下可能有问题，所以有自动创建topic与手动创建配置两种模式
```
dubbo.registry.address=nameservice://localhost:9876
dubbo.registry.parameters.route=true  // 默认为true，自动创建topic，如果手动创建请设为false
```


#### Topic的命令规则以及分组细节

topic创建规则有两种, 

- topic模式：就是以topic区别version与group
   - topic命名规则是："providers-{serviceName}-{version}-{group}-{CRC32}"
- select模式：使用RocketMQ的MessageSelector来区别version与group
   - topic命名规则是："providers-{serviceName}-{CRC32}"
   - 注意：需要RocketMQ4.9.2以上版本才支持select模式

| 字段 | 说明 |
| --- | --- |
| serviceName | 接口名 |
| version | 版本号 |
| group | 分组名 |
| CRC32 | providers-{serviceName}-{version}-{group}或则providers-{serviceName} 的校验码，是用于防止在复杂情况下topic重复的问题 |


```
dubbo.protocol.parameters.groupModel=topic // topic 是第一种模式，参数为select 为第二种模式。
```

#### RocketMQ-Client配置
生产端配置
```
dubbo.provider.parameters.enableMsgTrace=topic //Switch flag instance for message trace
dubbo.provider.parameters.namespace=topic  //Namespace for this MQ Producer instance
dubbo.provider.parameters.customizedTraceTopic=topic //The name value of message trace topic.If you don't config,you can use the default trace topic name
```
消费端配置
```
dubbo.consumer.parameters.enableMsgTrace=topic //Switch flag instance for message trace
dubbo.consumer.parameters.namespace=topic  //Namespace for this MQ Producer instance
dubbo.consumer.parameters.customizedTraceTopic=topic //The name value of message trace topic.If you don't config,you can use the default trace topic name
```