## Consumer behavior of upgrading to Dubbo3

Below is one normal registry configuration of Dubbo2, if upgrading to Dubbo3 without adding any changes, Dubbo3 will try
to subscribe both app-level and interface-level addresses by default.

After received address notifications from both side, the Consumer will decide to use which one as the valid address pool
based on some default conditions pre-set by the framework or users, the whole subscription and decision made process is
called `APPLICATION_FIRST`.

> 1 The checking conditions can be set through an SPI called `org.apache.dubbo.registry.client.migration.MigrationAddressComparator`
> 2 There are 3 ways of migration, thery are `FORCE_APPLICATION`, `FORCE_INTERFACE`, `APPLICATION_FIRST`, based on how many apps have been upgraded to Dubbo3. And `APPLICATION_FIRST` is the most convenient way to achieve that.

```xml
<!-- Subscribe both interface-level and app-level addresses from registry by default -->
<dubbo:registry address="zookeeper://127.0.0.1:2181"/>
```

By changing the registry address to `zookeeper://127.0.0.1:2181?registry-type=service`, the Consumer will only subscribe
application-level addresses from the registry, regardless all other migration rules and configurations. This can be
useful for

## More details

You can change the default subscription behaviour by specifying using different ways, for example by using xml,
properties or config center.

The main difference is if you want to change the subscription behavior on the fly or not, simply put is:

* By using dynamic migration rule, with the help of config center like Zookeeper or Nacos, you will be able to control
  the Consumer without restarting it.
* By using xml, properties and JVM args, you need to restart the process to make it work.

### Using xml, properties or JVM args

```xml
<!-- Register app-level address only -->
<dubbo:registry address="zookeeper://127.0.0.1:2181" >
    <!-- the migration step to use -->
    <dubbo:parameter key="migration.step" value="FORCE_APPLICATION"/>
    <!-- wait for address notification before migration -->
    <dubbo:parameter key="migration.delay" value="10000"/>
    <!-- check or not before migration -->
    <dubbo:parameter key="migration.force" value="true"/>
    <!-- only works when migration.force=false is set -->
    <dubbo:parameter key="migration.threshold" value="1"/>
</dubbo:registry>
```

```properties
dubbo.registry.parameters.migration.step=FORCE_APPLICATION
dubbo.registry.parameters.migration.delay=10000
dubbo.registry.parameters.migration.force=true
```

### Using dynamic migration rule

Users can choose to use dynamic migration rule by using Dubbo Admin or by writing to config center server directly.

Following is one demo rule:

```yaml
# key = demo-consumer.migration
# group = DUBBO_SERVICEDISCOVERY_MIGRATION
# content
key: demo-consumer
step: APPLICATION_FIRST
threshold: 1.0
proportion: 60
delay: 60
force: false
interfaces: # Interface level migration
  - serviceKey: org.apache.dubbo.demo.DemoService:1.0.0
    threshold: 1
    delay: 30
    step: APPLICATION_FIRST
  - serviceKey: org.apache.dubbo.demo.GreetingService:1.0.0
    step: FORCE_APPLICATION
applications: # Application level migration
 - serviceKey: demo-provider # app name of provider
   step: FORCE_APPLICATION
```
