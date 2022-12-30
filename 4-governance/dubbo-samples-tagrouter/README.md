# governance dubbo using tag router
1. step1 run `BasicProvider.java`

> it will start an zookeeper and register two service with dubbo port 20880
2. step2 run `BasicProviderOtherPort.java`
> it will register another two service with dubbo port 20881
3. step3 run `RuleUtil.java`,
>  it will read the route rule defined by [dubbo-routers-tag.yml](src%2Fmain%2Fresources%2Fdubbo-routers-tag.yml)
```yaml
---
force: false
runtime: true
enabled: true
priority: 1
key: governance-tagrouter-provider
tags:
- name: tag1
  addresses: ["*.*.*.*:20881"]
- name: tag2
  addresses: ["*.*.*.*:20880"]
...
```

6. step4 run `BasicConsumer.java`
>  it will consume service by rule defined by yml file
> * All consumer taged by `tag1` come from provider in port 20881
> * All consumer taged by `tag2` come from provider in port 20880