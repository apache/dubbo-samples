

## Dubbo Integration Test

### 测试框架

* 基于docker-compose 以容器方式运行

* dubbo-test-runner 模块
  
  构建`dubbo/sample-test` 镜像，在容器中启动Dubbo provider application 和 Dubbo testcase.

* dubbo-scenario-builder 模块

  构建测试场景，包含`docker-compose.yml`及`scenario.sh`脚本等。
  构建成功后，`scenario.sh`脚本可以单独运行。
  
### 编译测试镜像

```
cd dubbo-samples/test
./build-test-image.sh
```

如果构建镜像时apt更新数据很缓慢，可以使用Debian镜像服务器加快构建速度。
通过设置环境变量`DEBIAN_MIRROR=http_mirror_server`来指定Debian镜像地址,
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

通过`run-tests.sh`运行单个测试案例、测试案例列表或者全部测试案例。

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


#### 运行方式

* 运行单个测试案例

  `./run-tests.sh <project.basedir>`
  
* 调试单个测试案例

  ```
  DEBUG=service1,service2 ./run-tests.sh <project.basedir>
  ```

  可以通过设置环境变量`DEBUG=service1,service2`来指定哪些app/test服务开启远程调试，自动分配debug端口，
  具体端口可以查看生成的`docker-compose.yml`文件。
  
  注意：调试运行为`suspend=y`模式，Java应用被挂起，等待调试客户端连接才能继续执行Java代码。

  也可以查看日志文件`${scenario_home}/logs/{$service_name}.log`，如出现下面的信息可以知道已经打开调试端口：
  > Listening for transport dt_socket at address: 20660

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
   
   
#### 调试运行测试案例
    
  下面以`dubbo-samples-annotation`举例说明如何调试运行测试案例。
    
  先用普通方式执行一次测试案例，查看生成的`docker-compose.yml`，可知AnnotationProviderBootstrap的服务名称为`dubbo-samples-annotation`，
  test类的服务名为`dubbo-samples-annotation-test`。
  
  * 调试provider类：AnnotationProviderBootstrap
  
    执行启动命令，以suspend模式启动AnnotationProviderBootstrap：
    ```
    DEBUG=dubbo-sampes-annotation ./run-tests.sh ../dubbo-samples-annotation
    ```
    
    用命令查看日志：
     
    ```
    tail -f dubbo-sampes-annotation/target/logs/dubbo-sampes-annotation.log
    ```
    
    直到可以看到下面的日志信息：
  
    ```
    + java -agentlib:jdwp=transport=dt_socket,server=y,suspend=y,address=20660 -cp '/usr/local/dubbo/app/classes:/usr/local/dubbo/app/dependency/*' org.apache.dubbo.samples.annotation.AnnotationProviderBootstrap
    Listening for transport dt_socket at address: 20660
    ```
   
    要先在IDEA/eclipse上对要调试的代码加上断点，然后才能开始远程调试，这样可以调试应用启动过程。

    比如`AnnotationProviderBootstrap.main()` 第一行进行断点，然后创建远程调试，设置端口为20660。
    
    连接上后，开始执行`AnnotationProviderBootstrap`，然后在断点处暂停。
    
  * 调试test类：AnnotationServicesIT
    
    执行启动命令，以suspend模式启动test：
    ```
    DEBUG=dubbo-sampes-annotation-test ./run-tests.sh ../dubbo-samples-annotation
    ```
        
    用命令查看日志：
     
    ```
    tail -f dubbo-sampes-annotation/target/logs/dubbo-sampes-annotation-test.log
    ```
    
    直到可以看到下面的日志信息：
  
    ```
    + java -agentlib:jdwp=transport=dt_socket,server=y,suspend=y,address=20661 -Dzookeeper.address=dubbo-samples-annotation -Dzookeeper.port=2181 -Ddubbo.address=dubbo-samples-annotation -Ddubbo.port=20880 -jar dubbo-test-runner.jar /usr/local/dubbo/app/test-classes /usr/local/dubbo/app/classes /usr/local/dubbo/app/dependency /usr/local/dubbo//app/test-reports '**/*IT.class'
    Listening for transport dt_socket at address: 20661
    ```
    用上面的方法，先断点，然后再连接调试端口20661。
   
  * 同时调试provider和test类
  
    执行调试启动命令
    ```
    DEBUG=dubbo-sampes-annotation,dubbo-sampes-annotation-test ./run-tests.sh ../dubbo-samples-annotation
    ```
    
    如果同时调试多个suspend方式启动的app/test服务，需要按照依赖顺序连接调试端口，保证前置服务启动成功后，才能继续调试后一个服务。

**注意：**
  
  test容器启动后先等待dubbo服务端口，循环检查直到连接成功后才启动test类。如果test服务依赖的端口没有连接成功，则test类不会启动，也就不能进行远程调试。
  
  所以要保证AnnotationProviderBootstrap启动成功打开20880端口，test容器脚本检查[dubbo-samples-annotation:20880]端口连接成功，
  然后才会启动dubbo-test-runner执行testcase。
 
   
#### 测试步骤原理

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
  
    
### 定义测试用例

 测试用例配置文件为：`case-configuration.yml`，放在每个需要测试的工程basedir下。 
 
 
