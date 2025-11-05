在接下来的示例中，我们将展示如何将 Dubbo server 发布的服务开放给 Spring Cloud client 调用。

![springcloud-call-dubbo.png](./doc/images/springcloud-call-dubbo.png)

### 启动 Dubbo Server
Dubbo server 应用的代码结构非常简单，是一个典型的 Dubbo 应用。

相比于普通的 Dubbo 服务定义，我们要在接口上加上如下标准 Spring MVC 注解：

```java
@RestController
@RequestMapping("/users")
public interface UserService {
    @GetMapping(value = "/list")
    List<User> getUsers();
}
```

除了以上注解之外，其他服务发布等流程都一致，使用 `DubboService` 注解发布服务即可：

```java
@DubboService
public class UserServiceImpl implements UserService {
    @Override
    public List<User> getUsers() {
        return Collections.singletonList(new User(1L, "Dubbo provider!"));
    }
}
```

在服务配置上，特别注意我们需要将服务的协议配置为 rest `protocol: rest`，地址发现模式使用 `register-mode: instance`：

```yaml
dubbo:
  registry:
    address: nacos://127.0.0.1:8848
    register-mode: instance
  protocol:
    name: rest
    port: 8090
```

启动 Dubbo 应用，此时访问以下地址可以验证服务运行正常：`http://localhost:8090/users/list`

### 使用 Spring Cloud 调用 Dubbo
使用 OpenFeign 开发一个标准的 Spring Cloud 应用，即可调用以上发布的 Dubbo 服务。

其中，我们定义了一个 OpenFeign 接口，用于调用上面发布的 Dubbo rest 服务。
```java
@FeignClient(name = "dubbo-provider-for-spring-cloud")
public interface UserServiceFeign {
    @RequestMapping(value = "/users/list", method = RequestMethod.GET)
    List<User> getUsers();
}
```

定义以下 controller 作为 OpenFeign 和 RestTemplate 测试入口。
```java
public class UserController {

    private final RestTemplate restTemplate;
    private final UserServiceFeign userServiceFeign;

    public UserController(RestTemplate restTemplate,
                          UserServiceFeign userServiceFeign) {
        this.restTemplate = restTemplate;
        this.userServiceFeign = userServiceFeign;
    }

    @RequestMapping("/rest/test1")
    public String doRestAliveUsingEurekaAndRibbon() {
        String url = "http://dubbo-provider-for-spring-cloud/users/list";
        System.out.println("url: " + url);
        return restTemplate.getForObject(url, String.class);
    }

    @RequestMapping("/rest/test2")
    public List<User> doRestAliveUsingFeign() {
        return userServiceFeign.getUsers();
    }
}
```

根据以上 Controller 定义，我们可以分别访问以下地址进行验证：

- OpenFeign 方式：`http://localhost:8099/dubbo/rest/test1`、
- RestTemplage 方式：`http://localhost:8099/dubbo/rest/test2`