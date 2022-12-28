# governance dubbo using tag router static
the same effect with project [dubbo-samples-tagrouter](..%2Fdubbo-samples-tagrouter),but use static tag in xml file
```xml
<beans>
    <bean id="demoService" class="org.apache.dubbo.samples.governance.impl.DemoServiceImpl"/>
    <dubbo:service interface="org.apache.dubbo.samples.governance.api.DemoService" 
                   ref="demoService" 
                   tag="tag2"/>
</beans>

```
1. step1 run `BasicProvider.java`

> it will start an zookeeper and register two service with dubbo port 20880 with `tag2`
2. step2 run `BasicProviderOtherPort.java`
> it will register another two service with dubbo port 20881 with `tag1`
3. step3 run `BasicConsumer.java` or [DemoServiceIT.java](src%2Ftest%2Fjava%2Forg%2Fapache%2Fdubbo%2Fsamples%2Fgovernance%2FDemoServiceIT.java)
>  it will consume as blow role
> * All consumer taged by `tag1` come from provider taged by `tag1` in port 20881
> * All consumer taged by `tag2` come from provider taged by `tag2` in port 20880

This strategy can be used to reduce `time to return` by route service in some IDC through tag the same tags