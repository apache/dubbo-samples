This sample shows how to use Dubbo by annotation-driven configuration.

## Provider Configuration

First, there have to be an overall configuration of provider:

```Java
@Configuration
@EnableDubbo(scanBasePackages = "org.apache.dubbo.samples.annotation.impl")
@PropertySource("classpath:/spring/dubbo-provider.properties")
static class ProviderConfiguration {
}
``` 

`@EnableDubbo` will enable Spring `org.apache.dubbo.samples.annotation.impl` package to find anything annotated by Dubbo annotation.

As a provider, the interface implementation class have to be annotated by `@Service`:

```Java
@Service
public class AnnotatedGreetingService implements GreetingService {

    public String sayHello(String name) {
        System.out.println("greeting service received: " + name);
        return "hello, " + name;
    }

}
```

## Consumer Configuration

The overall configuration for consumer is very smilier to provider's:

```Java
@Configuration
@EnableDubbo(scanBasePackages = "org.apache.dubbo.samples.annotation.action")
@PropertySource("classpath:/spring/dubbo-consumer.properties")
@ComponentScan(value = {"org.apache.dubbo.samples.annotation.action"})
static class ConsumerConfiguration {

}
```

And you can use `@Reference` annotation to autowire the provider into consumer:

```Java
@Component("annotatedConsumer")
public class GreetingServiceConsumer {

    @Reference
    private GreetingService greetingService;
    
    ...
}
```


