

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

### 运行测试案例

#### 编译方式(BUILD)

* `BUILD=all`  
  编译整个dubbo-samples
  
* `BUILD=case`  
  编译需要运行的测试工程（注意有部分测试工程因为依赖问题不能独立编译）

* `BUILD=n`  
  不自动编译测试工程，需要先手工编译成功后，再运行测试。
  
  Maven编译参数： mvn clean package dependency:copy-dependencies -DskipTests

#### 测试步骤

* 编译测试工程

  `mvn clean package dependency:copy-dependencies -DskipTests`
  
* 生成测试场景

  测试场景`scenario_home`的位置位于`${project.basedir}/target`
  
  `$scenario_home/scenario.sh`: 运行测试的脚本
  
  `$scenario_home/docker-compose.yml` : 生成的容器配置文件
  
  `$scenario_home/logs` : 测试相关日志
   
* 运行测试

  `$scenario_home/scenario.sh`

#### 运行方式

* 运行全部测试案例

  (1) 编译整个dubbo-samples : `BUILD=all`   
  (2) 查找所有`case-configuration.yml`  
  (3) fork多进程按顺序运行测试  
 
  ```
   ./run-tests.sh
  ```
   等同于
  ```
   BUILD=all  ./run-tests.sh
  ```
  
* 运行单个测试案例

  `BUILD=case  ./run-tests.sh <project.basedir>`
  
* 调试单个测试案例

  ```
  BUILD=case DEBUG=1 ./run-tests.sh <project.basedir>
  ```

  默认`suspend=y`，可以用`DEBUG_SUSPEND=n`修改为不等待连接调试端口:
  
  ```
  BUILD=case DEBUG=1 DEBUG_SUSPEND=n ./run-tests.sh <project.basedir>
  ```

* 运行指定的测试案例列表

  ```
  BUILD=case TEST_CASE_FILE=testcases1.txt ./run-tests.sh
  ```
    
### 定义测试用例

 测试用例配置文件为：`case-configuration.yml`，放在每个需要测试的工程basedir下。 
 
 
