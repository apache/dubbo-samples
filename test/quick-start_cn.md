
## Dubbo Integration Test Quick Start

### 构建测试镜像

```
cd dubbo-samples/test
./build-test-image.sh
```

如果构建镜像时apt更新数据很缓慢，可以通过设置环境变量`DEBIAN_MIRROR=http_mirror_server`来指定Debian镜像地址加快构建速度。

比如下面脚本指定使用aliyun镜像服务器[http://mirrors.aliyun.com/ubuntu/](http://mirrors.aliyun.com/ubuntu/) :

```
cd dubbo-samples/test
DEBIAN_MIRROR=http://mirrors.aliyun.com ./build-test-image.sh
```

也可以执行封装好的脚本：

```
cd dubbo-samples/test
./build-test-image-use-aliyun-mirror.sh
```

### 运行测试案例

`run-tests.sh` 运行单个测试案例、测试案例列表或者全部测试案例。  

`kill-tests.sh` 强制停止所有测试用例，停止所有dubbo containers及全部run-tests.sh/scenario.sh进程。

注意：`Ctrl+C`中断执行`run-tests.sh`后，需要执行`kill-tests.sh`脚本。

#### 运行方式

* 运行单个测试案例

  ```
  ./run-tests.sh <project.basedir>
  ```
  
  比如运行`dubbo-samples-annotation`测试案例：
  
  ```
  ./run-tests.sh ../dubbo-samples-annotation
  ```
  
* 调试单个测试案例

  ```
  DEBUG=service1,service2 ./run-tests.sh <project.basedir>
  ```

  详细的调试方法，请参考"调试运行测试案例"小节。
  
* 运行指定的测试案例列表

  ```
  TEST_CASE_FILE=testcases1.txt ./run-tests.sh
  ```
 
* 运行全部测试案例
 
  ```
   BUILD=all  ./run-tests.sh
  ```
 
  run-tests.sh 运行全部测试案例的原理:

  (1) 编译整个dubbo-samples : `BUILD=all`   
  (2) 查找所有`case-configuration.yml`  
  (3) fork多进程按顺序运行测试案例


### 添加测试用例

 测试用例配置文件名为：`case-configuration.yml`，放在每个需要测试的工程basedir下。 
 
#### 简单的测试案例

测试工程满足以下条件，可以使用内置的模板简化配置：

* 单个Provider Application
* 使用嵌套的zookeeper或者外部的zookeeper
* 一个或者多个Test类，类名满足`**IT`，如`AnnotationServicesIT`
* 不依赖其它的服务

如使用嵌套的zookeeper的简单测试工程，`from`使用`app-builtin-zookeeper.yml`, 如`dubbo-samples-annotation`的配置：

```
from: app-builtin-zookeeper.yml

props:
  project_name: dubbo-samples-annotation
  main_class: org.apache.dubbo.samples.annotation.AnnotationProviderBootstrap
  zookeeper_port: 2181
  dubbo_port: 20880

```

`from: app-builtin-zookeeper.yml` : 指定使用`app-builtin-zookeeper.yml`模板，解析时自动将模板的内容合并到当前配置文件。

`props` : 配置文件内部的变量，自动替换模板中的变量占位符。如要使用全局变量请参考"全局变量"小节。

`project_name` : 工程名称变量，同时也是dubbo provider 服务名。

`main_class` ：provider application类名。

`zookeeper_port` : 工程代码及配置使用的zk端口。

`dubbo_port` : 工程代码及配置使用的dubbo provider服务端口。


如果使用外部的zookeeper，需要修改`from`使用`app-external-zookeeper.yml`，如`dubbo-samples-api`的配置：

```
from: app-external-zookeeper.yml

props:
  project_name: dubbo-samples-api
  main_class: org.apache.dubbo.samples.provider.Application
  dubbo_port: 20880
```

#### 多服务测试案例

以dubbo-samples-chain为例，说明一个比较复杂的多服务测试案例。
本案例一共有4个服务：

* 外部zookeeper
* dubbo-samples-chain-backend (provider)
* dubbo-samples-chain-middle (provider)
* dubbo-samples-chain-front (test)

测试配置文件位置放在父工程下面: `dubbo-samples-chain/case-configuration.yml`，
因为dubbo-samples-chain可以单独编译，作为一个整体来测试。

**(1) 添加第一个外部的zookeeper服务**

```
services:
  zookeeper:
    image: zookeeper:latest
```
容器的默认hostname会设置为serviceName，即`zookeeper`，其它容器可以直接使用hostname（即`zookeeper`）访问到这个容器。

如果没有要求特别的版本可以不写版本或者使用`latest`。

**(2) 添加第二个服务`dubbo-samples-chain-backend`**

```
services:
  ...

  dubbo-samples-chain-backend:
    type: app
    basedir: dubbo-samples-chain-backend
    mainClass: org.apache.dubbo.samples.chain.BackendProvider
    systemProps:
      - zookeeper.address=zookeeper
    waitPortsBeforeRun:
      - zookeeper:2181
    depends_on:
      - zookeeper

```
**因为是dubbo provider application，所以`type`设置为`app`。**

`basedir` 为子工程目录相对于`case-configuration.yml`所在目录的相对路径，本案例中就是下一级目录，
故设置为子工程目录名: `dubbo-samples-chain-backend`

`mainClass` 为启动的Java主类，设置为`org.apache.dubbo.samples.chain.BackendProvider`。

`systemProps` 为Java进程的系统属性，本案例需要使用zookeeper地址，故添加一个系统属性`zookeeper.address=zookeeper`，
端口是默认是2181，可以不配置。

`waitPortsBeforeRun` 设置启动Java类前等待的端口，脚本会自动检查配置的所有端口，全部通过后才启动Java类。
本工程中需要依赖zookeeper服务器，故设置等待端口`zookeeper:2181`。

`depends_on` 设置依赖的服务，可选配置，通过`waitPortsBeforeRun`已经解决了依赖的问题。


**(3) 添加第三个服务`dubbo-samples-chain-middle`**

这个子工程与第二服务类似，`basedir`和`mainClass`修改为本工程的对应值，不再重复说明。

```
services:
  ...

  dubbo-samples-chain-middle:
    type: app
    basedir: dubbo-samples-chain-middle
    mainClass: org.apache.dubbo.samples.chain.MiddleEndProvider
    systemProps:
      - zookeeper.address=zookeeper
    waitPortsBeforeRun:
      - zookeeper:2181
    depends_on:
      - zookeeper
```

**(4) 添加第四个服务`dubbo-samples-chain-front`**

```
services:
  ...

  dubbo-samples-chain-front:
    type: test
    basedir: dubbo-samples-chain-front
    tests:
      - "**/*IT.class"
    systemProps:
      - zookeeper.address=zookeeper
    waitPortsBeforeRun:
      - zookeeper:2181
      - dubbo-samples-chain-backend:20880
      - dubbo-samples-chain-middle:20881
    depends_on:
      - zookeeper
      - dubbo-samples-chain-backend
      - dubbo-samples-chain-middle
```

**本工程为测试工程，`type`设置为`test`。**

`basedir` 设置为工程目录名`dubbo-samples-chain-front`。

`tests` 为测试类的匹配规则（includes/excludes），这里test类命名约定为`*IT.java`，则匹配规则为："**/*IT.class"。

`systemProps` 系统属性添加zookeeper地址 `zookeeper.address=zookeeper`。

`waitPortsBeforeRun` 等待端口添加zookeeper及前两个服务：
 
```
    waitPortsBeforeRun:
      - zookeeper:2181
      - dubbo-samples-chain-backend:20880
      - dubbo-samples-chain-middle:20881
```

#### 全局变量

`case-configuration.yml`支持的全局变量如下，可以直接使用：

| 名称 | 描述 | 例子 |
| ---- | --- | --- |
| `_basedir` | `case-configuration.yml` 配置文件所在的目录 | 用于指定容器挂载的配置文件路径，如挂载mysql的初始化sql脚本目录：${_basedir}/src/main/resources/docker/sql:/docker-entrypoint-initdb.d |
| `_scenario_home`| scenario home，默认位于${_basedir}/target | |
| `_scenario_name`| scenario name，默认为${_basedir}的目录名（basename） | |

**注意**：volumes挂载的相对目录是scenario home(即${_basedir}/target)，不方便访问工程源码目录。
如果要挂载工程源码的目录，推荐使用${_basedir}/src/main/resources/xxx的形式，提高可读性.

#### 转换测试案例

请参考文档：[How to convert test cases](convert-case.md)

  
### 调试运行测试案例

  ```
  DEBUG=service1,service2 ./run-tests.sh <project.basedir>
  ```

  可以通过设置环境变量`DEBUG=service1,service2`来指定哪些app/test服务开启远程调试，自动分配debug端口，
  具体端口可以查看生成的`docker-compose.yml`文件。
  
  **注意：调试运行为`suspend=y`模式，Java应用被挂起，等待调试客户端连接才能继续执行Java代码。**
    
  下面以`dubbo-samples-annotation`举例说明如何调试运行测试案例。
    
  先用普通方式执行一次测试案例:
  
  ```
  ./run-tests.sh ../dubbo-samples-annotation
  ```
  
  查看生成的`dubbo-samples-annotation/target/docker-compose.yml`，可知AnnotationProviderBootstrap的服务名称为`dubbo-samples-annotation`，
  test类的服务名为`dubbo-samples-annotation-test`。
  
  * **调试provider类：AnnotationProviderBootstrap**
  
    执行启动命令，以suspend模式启动AnnotationProviderBootstrap：
    
    ```
    DEBUG=dubbo-samples-annotation ./run-tests.sh ../dubbo-samples-annotation
    ```
    
    直到可以看到下面的日志信息：
  
    ```
    + java -agentlib:jdwp=transport=dt_socket,server=y,suspend=y,address=20660 -cp '/usr/local/dubbo/app/classes:/usr/local/dubbo/app/dependency/*' org.apache.dubbo.samples.annotation.AnnotationProviderBootstrap
    Listening for transport dt_socket at address: 20660
    ```
   
    要先在IDEA/eclipse上对要调试的代码加上断点，然后才能开始远程调试，这样可以调试应用启动过程。

    比如`AnnotationProviderBootstrap.main()` 第一行进行断点，然后创建远程调试，设置端口为20660。
    
    连接上后，开始执行`AnnotationProviderBootstrap`，然后在断点处暂停。
    
  * **调试test类：AnnotationServicesIT**
    
    执行启动命令，以suspend模式启动test：
    
    ```
    DEBUG=dubbo-samples-annotation-test ./run-tests.sh ../dubbo-samples-annotation
    ```
    
    直到可以看到下面的日志信息：
  
    ```
    + java -agentlib:jdwp=transport=dt_socket,server=y,suspend=y,address=20660 -Dzookeeper.address=dubbo-samples-annotation -Dzookeeper.port=2181 -Ddubbo.address=dubbo-samples-annotation -Ddubbo.port=20880 -jar dubbo-test-runner.jar /usr/local/dubbo/app/test-classes /usr/local/dubbo/app/classes /usr/local/dubbo/app/dependency /usr/local/dubbo//app/test-reports '**/*IT.class'
    Listening for transport dt_socket at address: 20660
    ```
    用上面的方法，先断点，然后再连接调试端口20660。
   
  * **同时调试provider和test类**
  
    执行调试启动命令
    
    ```
    DEBUG=dubbo-samples-annotation,dubbo-samples-annotation-test ./run-tests.sh ../dubbo-samples-annotation
    ```
    
    同时调试多个suspend方式启动的app/test服务，需要按照依赖顺序连接调试端口，保证前置服务启动成功后，才能继续调试后一个服务。
    
    首先看到`AnnotationProviderBootstrap`的调试端口：
    
    ```
    + java -agentlib:jdwp=transport=dt_socket,server=y,suspend=y,address=20660 -cp '/usr/local/dubbo/app/classes:/usr/local/dubbo/app/dependency/*' org.apache.dubbo.samples.annotation.AnnotationProviderBootstrap
    Listening for transport dt_socket at address: 20660
    ```
    
    使用IDEA/eclipse 连接远程调试端口`localhost:20660`，连接上后测试继续运行，然后看到test类的调试端口：
    
    ```
    + java -Dzookeeper.address=dubbo-samples-annotation -Dzookeeper.port=2181 -Ddubbo.address=dubbo-samples-annotation -Ddubbo.port=20880 -agentlib:jdwp=transport=dt_socket,server=y,suspend=y,address=20661 -jar dubbo-test-runner.jar /usr/local/dubbo/app/test-classes /usr/local/dubbo/app/classes /usr/local/dubbo/app/dependency /usr/local/dubbo//app/test-reports '**/*IT.class'
    Listening for transport dt_socket at address: 20661
    ```
    
    再次连接调试端口`localhost:20661`，test类继续执行。
    

**注意：**
  
  test容器启动后先等待dubbo服务端口，循环检查直到连接成功后才启动test类。如果test服务依赖的端口没有连接成功，则test类不会启动，也就不能进行远程调试。
  
  所以要保证AnnotationProviderBootstrap启动成功打开20880端口，test容器脚本检查[dubbo-samples-annotation:20880]端口连接成功，
  然后才会启动dubbo-test-runner执行testcase。
 
 
### 测试框架原理

* 基于docker-compose 以容器方式运行

* dubbo-test-runner 模块
  
  构建`dubbo/sample-test` 镜像，在容器中启动Dubbo provider application 和 Dubbo testcase.

* dubbo-scenario-builder 模块

  构建测试场景，包含`docker-compose.yml`及`scenario.sh`脚本等。
  构建成功后，`scenario.sh`脚本可以单独运行。

#### 编译方式(BUILD)

默认值为`BUILD=case`，即每个case工程单独编译。

* `BUILD=case`  

  单独编译需要运行的测试工程。
  
  注意：有部分测试工程因为依赖问题不能独立编译，此时需要改为整体编译或者手工编译。

* `BUILD=all`  

  编译整个dubbo-samples
  
* `BUILD=n`  

  不自动编译测试工程，需要先手工编译成功后，再运行测试。
  
  Maven编译参数： mvn clean package dependency:copy-dependencies -DskipTests

   
#### scenario测试步骤

下面是脚本自动完成的步骤，只需要理解，不需要手工执行。    
对每个测试案例来说，都会按照下面的步骤进行处理。
  
* 编译测试工程
  
  如果按照case单独编译，则执行下面的编译命令：
  
  `mvn clean package dependency:copy-dependencies -DskipTests`
  
* 生成测试场景

  测试场景`scenario_home`的位置位于`${project.basedir}/target`
  
  `$scenario_home/scenario.sh`: 运行测试的脚本
  
  `$scenario_home/docker-compose.yml` : 生成的容器配置文件
  
  `$scenario_home/logs` : 测试相关日志
   
* 运行测试

  启动容器，等待并检查测试结果是否成功。
  
  `$scenario_home/scenario.sh`
