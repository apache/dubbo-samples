#### introduction

Dubbo Security is based on spring security built rpc communication authentication module , following spring security configuration to complete the client and server authorization access through the Spring security context authorization information .

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
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class ProviderApplication {

}
```

+ dubbo-samples

### custom serialization

+  Add a configuration class.

```java
@Configuration
public class SecuritySerializationConfig {

    @Bean
    public DefaultObjectMapperCodecCustomer objectMapperCodecCustomer() {
        return new DefaultObjectMapperCodecCustomer();
    }
}
```

```java
public class DefaultObjectMapperCodecCustomer implements ObjectMapperCodecCustomer {
    @Override
    public void customize(ObjectMapperCodec objectMapperCodec) {
         //Add custom codec
    }
}
```

#### notes

+ Most implementations of Authentication objects for Spring Security use a parameterized constructor. If you customize the Authentication object and use a parameterized constructor, you must register the deserializer for the ObjectMapper when deserializing.In a Dubbo application, you can use the ObjectMapperCodecCustomer extension to customize the serialization and deserialization of objects。

+ If you do not have a custom implementation of deserializer errors, Dubbo ignores the current error

+ In spring security, custom deserialization implementation references `UsernamePasswordAuthenticationTokenDeserializer`
