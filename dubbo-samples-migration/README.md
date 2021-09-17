## Compatibility Testing Scenario Usage
Pre-requirements: Both provider and consumer can not use the same `POM`, because you cannot guarantee that all the codes you write Dubbo 2.0 and 3.0 support at the same time,  they must be divided into two independent projects. This is also applicable in daily business compatibility testing

Add new grammar to `case-versions.conf`:
```yaml
# 原常规配置
# dubbo.version=2.7*, 3.*
spring.version=4.*, 5.*

# 支持不同的 servcie 应用，配置不同的 dubbo 版本依赖，与 dubbo.version 二选一
# 为防止构建用例倍级增长，不建议配置多个版本
# dubbo.{service}.verison 中的 service 可以任意自定义
dubbo.provider.version=3.*
dubbo.consumer.version=2.7.*
```
And then in each consumer and provider project, you can configure `POM`'s properties as follows:
```xml
<!-- consumer side -->
<properties>
    <dubbo.consumer.version>2.7.13</dubbo.consumer.version>
</properties>

<!-- provider side -->
<properties>
    <dubbo.provider.version>2.7.13</dubbo.provider.version>
</properties>
```

If we have the env of `DUBBO_VERSION=3.0.3-SNAPSHOT,2.7.13`, when the Integration Tests trigger, these properties will be overwritten by maven, for example:
```shell
mvn -Ddubbo.provider.version=3.0.3-SNAPSHOT -Ddubbo.consumer.version=2.7.13 -Dspring.version=4.3.16.RELEASE
```

But in the dubbo-samples's Dubbo 3 CI env, there only has `DUBBO_VERSION=3.0.3-SNAPSHOT`, we have to specify one side's version to prevent the test being ignored, for example:
```yaml
dubbo.provider.version=3.*
dubbo.consumer.version=2.7.13 # <= specific version
```