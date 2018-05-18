有些部署场景需要动态指定服务注册的地址，如docker bridge网络模式下要指定注册宿主机ip以实现外网通信。dubbo提供了两对启动阶段的系统属性，用于设置对外通信的ip、port地址   
* DUBBO_IP_TO_REGISTRY --- 注册到注册中心的ip地址  
* DUBBO_PORT_TO_REGISTRY --- 注册到注册中心的port端口  
* DUBBO_IP_TO_BIND --- 监听ip地址  
* DUBBO_PORT_TO_BIND --- 监听port端口 

> 1. 以上四个配置项均为可选项，如不配置dubbo会自动获取ip与端口，请根据具体的部署场景灵活选择配置。 
> 2. dubbo支持多协议，**如果一个应用同时暴露多个不同协议服务，且需要为每个服务单独指定ip或port，请分别在以上属性前加协议前缀。** 如：  
> * HESSIAN_DUBBO_PORT_TO_BIND    hessian协议绑定的port
> * DUBBO_DUBBO_PORT_TO_BIND      dubbo协议绑定的port
> * HESSIAN_DUBBO_IP_TO_REGISTRY  hessian协议注册的ip
> * DUBBO_DUBBO_PORT_TO_BIND      dubbo协议注册的ip
> 3. `PORT_TO_REGISTRY`或`IP_TO_REGISTRY`不会用作默认`PORT_TO_BIND`或`IP_TO_BIND`，但是反过来是成立的
> * 如设置`PORT_TO_REGISTRY=20881` `IP_TO_REGISTRY=30.5.97.6`，则`PORT_TO_BIND` `IP_TO_BIND`不受影响
> * 如果设置`PORT_TO_BIND=20881` `IP_TO_BIND=30.5.97.6`，则默认`PORT_TO_REGISTRY=20881` `IP_TO_REGISTRY=30.5.97.6`
> 

[dubbo-docker-sample](https://github.com/dubbo/dubbo-docker-sample)工程本地运行流程： 
 
1. clone工程到本地 
```sh
git clone git@github.com:dubbo/dubbo-docker-sample.git
cd dubbo-docker-sample
```
2. 本地maven打包  
```sh
mvn clean install  
```
3. docker build构建镜像  
```sh
docker build --no-cache -t dubbo-docker-sample . 
```
Dockerfile
```sh
FROM openjdk:8-jdk-alpine
ADD target/dubbo-docker-sample-0.0.1-SNAPSHOT.jar app.jar
ENV JAVA_OPTS=""
ENTRYPOINT exec java $JAVA_OPTS -jar /app.jar
```
4. 从镜像创建容器并运行
```sh
# 由于我们使用zk注册中心，先启动zk容器
docker run --name zkserver --restart always -d zookeeper:3.4.9
```
```sh
docker run -e DUBBO_IP_TO_REGISTRY=30.5.97.6 -e DUBBO_PORT_TO_REGISTRY=20881 -p 30.5.97.6:20881:20880 --link zkserver:zkserver -it --rm dubbo-docker-sample
```
> 假设宿主机ip为30.5.97.6。    
> 通过环境变量 `DUBBO_IP_TO_REGISTRY=30.5.97.6` `DUBBO_PORT_TO_REGISTRY=20880` 设置provider注册到注册中心的ip、port      
> 通过`-p 30.5.97.6:20881:20880`做端口映射，其中20880是dubbo自动选择的监听port，由于没有设置监听ip，将监听0.0.0.0即所有ip地址  
> 启动后provider的注册地址为：30.5.97.6:20881，容器的监听地址为：0.0.0.0:20880  

5. 测试
从另外一个宿主机或容器执行
```sh
telnet 30.5.97.6 20881
ls
invoke com.alibaba.dubbo.test.docker.DemoService.hello("world")
```
