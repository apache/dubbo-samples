#### 介绍

Dubbo Security 是基于spring security构建的rpc通信鉴权模块,遵循spring security安全配置,通过Spring security context 授权信息完成客户端与服务端的授权访问。

#### 服务调用

```mermaid
graph LR
A --> B --> C
```

+ 场景(一): A, B, C 依赖 dubbo-spring-security, B,C 启用授权验证，在调用过程中 B 首先进行授权检查，验证成功后调用C进行验证。

+ 场景(二): A,C 依赖 dubbo-spring-security，A调用B，B在调用C的过程中会透传授权信息，C接收到授权信息进行授权验证。

#### 核心依赖

```xml
<dependencyManagement>
    <dependencies>
        <dependency>
            <groupId>org.apache.dubbo</groupId>
            <artifactId>dubbo-dependencies-bom</artifactId>
            <version>${dubbo.dependencies.version}</version>
            <type>pom</type>
            <scope>import</scope>
        </dependency>

        <dependency>
            <groupId>org.apache.dubbo</groupId>
            <artifactId>dubbo-bom</artifactId>
            <version>${dubbo.version}</version>
            <type>pom</type>
            <scope>import</scope>
        </dependency>
    </dependencies>
</dependencyManagement>

<dependency>
    <groupId>org.apache.dubbo</groupId>
    <artifactId>dubbo-spring-security</artifactId>
</dependency>

<dependency>
   <groupId>org.springframework.security</groupId>
    <artifactId>spring-security-config</artifactId>
</dependency>

```

#### provider 启用 Security 配置

```java
@Configuration
@EnableMethodSecurity(jsr250Enabled = true, securedEnabled = true)
public class SecurityConfiguration {

}
```

+ 具体案例参考dubbo-samples

### 自定义序列化

```java
public class DefaultObjectMapperCodecCustomer implements ObjectMapperCodecCustomer {
    @Override
    public void customize(ObjectMapperCodec objectMapperCodec) {
    }
}
```

