在已经有一套 Spring Cloud 微服务体系的情况下，演示如何使用 Dubbo 调用 Spring Cloud 服务（包含自动的地址发现与协议传输）。在注册中心方面，本示例使用 Nacos 作为注册中心，对于 Zookeeper、Consul 等两种体系都支持的注册中心同样适用。

![dubbo-call-springcloud.png](./doc/images/dubbo-call-springcloud.png)

设想你已经有一套 Spring Cloud 的微服务体系，现在我们将引入 Dubbo 框架，让 Dubbo 应用能够正常的调用到 Spring Cloud 发布的服务。本示例完整源码请参见 [samples/dubbo-call-sc](https://github.com/apache/dubbo-samples/tree/master/2-advanced/dubbo-samples-springcloud/dubbo-call-sc)。
### 启动 Spring Cloud Server
应用配置文件如下：
```yaml
server:
  port: 8099
spring:
  application:
    name: spring-cloud-provider-for-dubbo
  cloud:
    nacos:
      serverAddr: 127.0.0.1:8848 #注册中心
```

以下是一个非常简单的 Controller 定义，发布了一个 `/users/list/`的 http 端点
```java
@RestController
@RequestMapping("/users")
public class UserController {
    @GetMapping("/list")
    public List<User> getUser() {
        return Collections.singletonList(new User(1L, "spring cloud server"));
    }
}
```

启动 `SpringCloudApplication`，通过 cURL 或浏览器访问 `http://localhost:8099/users/list` 可以测试应用启动成功。
### 使用 Dubbo Client 调用服务
Dubbo client 也是一个标准的 Dubbo 应用。

其中，一个比较关键的是如下接口定义（正常情况下，以下接口可以直接从原有的 Spring Cloud client 应用中原样拷贝过来即可，无需做任何修改）。
> 如果之前没有基于 OpenFeign 的 Spring Cloud 消费端应用，那么就需要自行定义一个接口，此时不一定要使用 OpenFeign 注解，使用 Spring MVC 标准注解即可。

```java
@FeignClient(name = "spring-cloud-provider-for-dubbo")
public interface UserServiceFeign {
    @RequestMapping(value="/users/list", method = RequestMethod.GET, produces = "application/json")
    List<User> users();
}
```

通过 `DubboReference` 注解将 UserServiceFeign 接口注册为 Dubbo 服务
```java
@DubboReference
private UserServiceFeign userService;
```

接下来，我们就可以用 Dubbo 标准的方式对服务发起调用了
```java
List<User> users = userService.users();
```

通过 `DubboConsumerApplication` 启动 Dubbo 应用，验证可以成功调用到 Spring Cloud 服务。