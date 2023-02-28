#### introduction

Dubbo Security is an RPC communication authentication module based on Spring Security. It follows Spring Security's security configuration and uses Spring Security context authorization information to complete authorized access between clients and servers.

#### service call

```mermaid
graph LR
A --> B --> C
```

+ Scene one: A,B,C dependency Dubbo Spring Security. B and C enable authorization config. During the invocation process, B will perform authorization check first, and if the verification is successful, it will call C for verification.

+ Scene two: A,C dependency Dubbo Spring Security. A calls B and during the process of B calling C, the authorization information will be transparently passed through. C will receive the authorization information and perform authorization verification.

#### configuration pom

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

#### provider enable security

```java
@Configuration
@EnableMethodSecurity(jsr250Enabled = true, securedEnabled = true)
public class SecurityConfiguration {

}
```

+ dubbo-samples

### custom serialization

```java
public class DefaultObjectMapperCodecCustomer implements ObjectMapperCodecCustomer {
    @Override
    public void customize(ObjectMapperCodec objectMapperCodec) {
    }
}
```

