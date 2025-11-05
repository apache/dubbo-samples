
## How to convert test cases

下面整理一些常见的修改项

### 如何设置zookeeper地址

某些测试工程在本地开发环境可以正常运行，但在测试框架运行上会出现`zookeeper not connected`错误，原因是在容器网络中运行，访问zookeeper地址发生变化。

本地开发习惯设置zookeeper地址为`127.0.0.1:2181`或`localhost:2181`，这在本地运行是没问题的，但放到容器中就访问不到了。
因为不同的服务组件在不同的容器内，不能简单通过本地地址来访问，而是要改成通过hostname来访问。

**作为全局的一个约定，zookeeper的地址和端口分别使用系统属性`zookeeper.address`和`zookeeper.port`。**

Provider Application 和Test类中需要检查配置，参考下面的配置方式：

  xml 配置：

  ```
  <dubbo:registry address="zookeeper://${zookeeper.address:127.0.0.1}:${zookeeper.port:2181}"/>
  ```
  
  application.properties配置：

  ```
  dubbo.registry.address=zookeeper://${zookeeper.address:127.0.0.1}:${zookeeper.port:2181}
  ```

  Java代码中获取zk地址端口：
  
  ```
  String zookeeperHost = System.getProperty("zookeeper.address", "127.0.0.1"); 
  String zookeeperPort = System.getProperty("zookeeper.port", "2181");
  ```

除了`registry`，还有其它使用到zookeeper的配置，如`config-center`, `metadata-report` 等。

  
### 使用2个zookeeper

如果测试案例使用到两个zk，则需要分别定义不同的系统变量，约定如下：

```
service:
  zookeeper1:
    image: zookeeper

  zookeeper2:
    image: zookeeper

  xxx:
    systemProps:
      - zookeeper.address.1=zookeeper1
      - zookeeper.port.1=2181
      - zookeeper.address.2=zookeeper2
      - zookeeper.port.2=2181
```

代码或者xml配置要分别处理两个zk的address和port，zookeeper.port.2属性为空时，使用2182 ，以便统一本地运行和在容器中运行。

* 本地的两个zk地址为：127.0.0.1:2181, 127.0.0.1:2182
* 容器中的两个zk地址为：zookeeper1:2181, zookeeper2:2181

如果代码中使用到`ZKTools`，请查找`ZKTools2.java`，可以直接复制使用。`ZKTools2`是使用到2个zk的意思，`ZKTools`就是单个zk，方便查找重用。

```java
public class ZKTools2 {
    private static String zookeeperHost1 = System.getProperty("zookeeper.address.1", "127.0.0.1");
    private static String zookeeperPort1 = System.getProperty("zookeeper.port.1", "2181");
    private static String zookeeperHost2 = System.getProperty("zookeeper.address.2", "127.0.0.1");
    private static String zookeeperPort2 = System.getProperty("zookeeper.port.2", "2182");

    public static void setZookeeperServer1(String host, String port) {
        zookeeperHost1 = host;
        zookeeperPort1 = port;
    }

    public static void setZookeeperServer2(String host, String port) {
        zookeeperHost2 = host;
        zookeeperPort2 = port;
    }

    //...
}
```

### 删除testcontainers

新的测试框架不需要使用testcontainers，将相关的配置及代码删除掉，并转换为新的case-configuration.yml配置。

删除pom.xml中的依赖：

```xml
    <dependency>
        <groupId>org.testcontainers</groupId>
        <artifactId>testcontainers</artifactId>
        <version>1.12.3</version>
        <scope>test</scope>
    </dependency>

```

删除Java test类中的调用：

```
import org.testcontainers.containers.FixedHostPortGenericContainer;
import org.testcontainers.containers.GenericContainer;

@ClassRule
public static GenericContainer zookeeper = new FixedHostPortGenericContainer("zookeeper:3.4.9")
        .withFixedExposedPort(2181, 2181);

```

### 转换docker-maven-plugin的容器配置

将<images>配置的内容转换为新的case-configuration.yml。

下面的样例为单服务配置，可以用`app-builtin-zookeeper.yml`模板：

```
<plugin>
    <groupId>io.fabric8</groupId>
    <artifactId>docker-maven-plugin</artifactId>
    <version>${docker-maven-plugin.version}</version>
    <configuration>
        <images>
            <image>
                <name>${image.name}</name>
                <run>
                    <ports>
                        <port>${dubbo.port}:${dubbo.port}</port>
                        <port>${zookeeper.port}:${zookeeper.port}</port>
                    </ports>
                    <wait>
                        <log>dubbo service started</log>
                    </wait>
                </run>
            </image>
        </images>
    </configuration>
</plugin>
```

下面的样例为provider + 外部zookeeper，可以用`app-external-zookeeper.yml`模板：

```
<configuration>
    <images>
        <image>
            <name>zookeeper:latest</name>
            <run>
                <ports>
                    <port>${zookeeper.port}:${zookeeper.port}</port>
                </ports>
                <wait>
                    <tcp>
                        <host>${dubbo-local-address}</host>
                        <ports>
                            <port>${zookeeper.port}</port>
                        </ports>
                    </tcp>
                </wait>
            </run>
        </image>
        <image>
            <name>${image.name}</name>
            <run>
                <ports>
                    <port>${dubbo.port}:${dubbo.port}</port>
                </ports>
                <wait>
                    <log>dubbo service started</log>
                </wait>
            </run>
        </image>
    </images>
</configuration>
```

可能使用了外部docker配置，需要同时将外部docker配置内容也转换掉。
比如下面的配置使用了`src/main/resources/docker/docker-compose.yml`：

```
<plugin>
    <groupId>io.fabric8</groupId>
    <artifactId>docker-maven-plugin</artifactId>
    <version>${docker-maven-plugin.version}</version>
    <configuration>
        <images>
            <image>
                <external>
                    <type>compose</type>
                    <basedir>src/main/resources/docker</basedir>
                    <composeFile>docker-compose.yml</composeFile>
                </external>
            </image>
            ...
        </images>
    </configuration>
</plugin>
```


### Maven配置优化

#### 统一命名的属性

为了满足测试多个Spring版本/Dubbo版本的需求，下面的属性要统一命名，以便测试时通过-D参数指定版本号：

`dubbo.version` : Dubbo版本号，所有dubbo的组件都使用这个属性

`spring.version` : Spring版本号，Spring应用配置此属性

`spring-boot.version` : SpringBoot版本号，SpringBoot应用配置此属性

`junit.version` : Junit 版本号，由于测试框架单独运行testcase，最好是统一的4.12

如果是Spring项目：

```xml
<properties>
    <spring.version>4.3.16.RELEASE</spring.version>
    <dubbo.version>2.7.7</dubbo.version>
    <junit.version>4.12</junit.version>
</properties>
```

如果是SpringBoot项目：

```xml
<properties>
    <spring-boot.version>1.5.13.RELEASE</spring-boot.version>
    <dubbo.version>2.7.7</dubbo.version>
    <junit.version>4.12</junit.version>
</properties>
```

#### 使用dependencyManagement管理依赖版本号

如果是Spring项目：

```xml
<dependencyManagement>
    <dependencies>
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-framework-bom</artifactId>
            <version>${spring.version}</version>
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
        <dependency>
            <groupId>org.apache.dubbo</groupId>
            <artifactId>dubbo-dependencies-zookeeper-curator5</artifactId>
            <version>${dubbo.version}</version>
            <type>pom</type>
        </dependency>
    </dependencies>
</dependencyManagement>
```

如果是SpringBoot项目：

```xml
<dependencyManagement>
    <dependencies>
        <dependency>
            <!-- Import dependency management from Spring Boot -->
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-dependencies</artifactId>
            <version>${spring-boot.version}</version>
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
        <dependency>
            <groupId>org.apache.dubbo</groupId>
            <artifactId>dubbo-dependencies-zookeeper-curator5</artifactId>
            <version>${dubbo.version}</version>
            <type>pom</type>
        </dependency>
    </dependencies>
</dependencyManagement>
```

注意： 将Spring/SpringBoot的bom放到dubbo-bom之前，优先使用Spring/SpringBoot的版本号，避免出现Spring组件版本号不一致的问题。

SpringBoot项目不要导入spring-framework-bom，避免因为传递的spring.version参数导致SpringBoot依赖的Spring版本发生改变。


#### 删除不必要版本号属性

使用dependencyManagement管理版本号之后，spring/spring-boot/dubbo的组件不需要配置版本号

如删除`spring-test`的版本号 `spring-test.version`，旧的配置：

```xml
<dependencies>
    <dependency>
        <groupId>org.apache.dubbo</groupId>
        <artifactId>dubbo</artifactId>
        <version>${dubbo.version}</version>
    </dependency>
    <dependency>
        <groupId>org.springframework</groupId>
        <artifactId>spring-test</artifactId>
        <version>${spring-test.version}</version>
        <scope>test</scope>
    </dependency>
</dependencies>
```

新的配置：

```xml
<dependencies>
    <dependency>
        <groupId>org.apache.dubbo</groupId>
        <artifactId>dubbo</artifactId>
    </dependency>
    <dependency>
        <groupId>org.springframework</groupId>
        <artifactId>spring-test</artifactId>
        <scope>test</scope>
    </dependency>
</dependencies>
```

#### zookeeper依赖

使用dubbo-dependencies-zookeeper导入zookeeper相关依赖，而不是分别依赖curator-framework和zookeeper。
旧的配置：

```xml
<dependencies>
    <dependency>	
        <groupId>org.apache.zookeeper</groupId>	
        <artifactId>zookeeper</artifactId>	
        <version>${zookeeper.version}</version>	
    </dependency>	
    <dependency>	
        <groupId>org.apache.curator</groupId>	
        <artifactId>curator-framework</artifactId>	
        <version>${curator.version}</version>	
    </dependency>
</dependencies>
```

新的配置：

```xml
<dependencies>
    <dependency>
        <groupId>org.apache.dubbo</groupId>
        <artifactId>dubbo-dependencies-zookeeper-curator5</artifactId>
        <type>pom</type>
    </dependency>
</dependencies>
```

#### 清理pom.xml

删除dubbo-integration-test profile及多余的properties

