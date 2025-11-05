## Provider

Below is the normal registry config of Dubbo2, if upgrading to Dubbo3 without adding any change, Dubbo3 will register
both interface-level and app-level addresses to the registry by default.

```xml
<!-- Register both interface-level and app-level addresses by default -->
<dubbo:registry address="zookeeper://127.0.0.1:2181"/>
```

## More Details

You can also change the default behaviour by specifying `register-mode` through different ways, like xml, properties or
JVM args.

Take xml as an example:

```xml
<!-- Register app-level address only -->
<dubbo:registry address="zookeeper://127.0.0.1:2181" register-mode="instance"/>
```

```xml
<!-- Register interface-level address only -->
<dubbo:registry address="zookeeper://127.0.0.1:2181" register-mode="interface"/>
```

```xml
<!-- The default value, register both interface-level and app-level addresses -->
<dubbo:registry address="zookeeper://127.0.0.1:2181" register-mode="all"/>
```

Further more, users can choose to set the default behaviour that could apply to all applications by using global config
center. Check [migration doc](https://dubbo.apache.org/zh/docs/migration/migration-service-discovery/) for more details.