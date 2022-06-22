# Dubbo mesh using Istio

可以按照下文步骤，将 Demo 部署到本地集群，也可在 [KataCoda 在线快速体验]()。

## 1 总体目标

* 部署 Dubbo 应用到 Kubernetes + Istio
* 基于 Kubernetes 内置 Service 实现服务发现,Pilot 监听api-server，通过XDS下发给Envoy实例
* 将 Dubbo 应用对接到 Kubernetes 生命周期
* 利用 istio 的envoy代理实现服务调用

## 2 基本流程

1. 创建一个 Dubbo
   应用( [dubbo-samples-kubernetes](https://github.com/apache/dubbo-samples/tree/master/dubbo-samples-kubernetes) )
2. 构建容器镜像并推送到镜像仓库（ [dubboteam 示例例镜像](https://hub.docker.com/u/dubboteam) ）
3. 分别部署 Dubbo Provider 与 Dubbo Consumer 到 Kubernetes
4. 验证服务发现与调用正常

## 3 详细步骤

### 3.1 环境要求

请确保本地安装如下环境，以提供容器运行时、Kubernetes集群及访问工具

* [Docker](https://www.docker.com/get-started/)
* [Minikube](https://minikube.sigs.k8s.io/docs/start/)
* [Kubectl](https://kubernetes.io/docs/tasks/tools/)
* [Kubens(optional)](https://github.com/ahmetb/kubectx)
* [Istio](https://istio.io/latest/zh/)

通过以下命令启动本地 Kubernetes 集群

```shell
minikube start
```

通过 kubectl 检查集群正常运行，且 kubectl 绑定到默认本地集群

```shell
kubectl cluster-info
```

### 3.2 前置条件

由于示例 Dubbo 项目均部署在 Pod 中且与 API-SERVER 有交互，因此有相应的权限要求，我们这里创建独立 ServiceAccount 并绑定必须的 Roles，后面所有的 Dubbo Kubernetes
资源都将使用这里新建的 ServiceAccount。

通过以下命令我们创建了独立的 Namespace `dubbo-demo` 与 ServiceAccount `dubbo-sa`。

```shell
# 初始化命名空间和账号
kubectl apply -f https://raw.githubusercontent.com/apache/dubbo-samples/master/dubbo-samples-kubernetes/dubbo-samples-apiserver-provider/src/main/resources/k8s/ServiceAccount.yml

# 切换命名空间
kubens dubbo-demo

# dubbo-demo 开启自动注入
kubectl label namespace dubbo-demo istio-injection=enabled

# 注意项目路径一定是英文，否则protobuf编译失败
```

### 3.3 项目与镜像打包（可跳过）

示例项目及相关镜像均已就绪，以下仅为指引说明，可直接跳过此步骤直接查看 3.4 小节。  
https://github.com/apache/dubbo-samples/tree/master/dubbo-samples-kubernetes/

注意，由于 kubernetes 为独立扩展项目，开启 Kubernetes 支持前请添加如下依赖到 pom.xml

```xml

<dependency>
    <groupId>org.apache.dubbo.extensions</groupId>
    <artifactId>dubbo-registry-kubernetes</artifactId>
    <version>1.0.2-SNAPSHOT</version>
</dependency>
```

设置 Dubbo 项目使用 Kubernetes 作为注册中心，这里通过 DEFAULT_MASTER_HOST指定使用默认 API-SERVER 集群地址 kubernetes.default.srv，同时还指定了
namespace、trustCerts 两个参数

```properties
dubbo.application.name=dubbo-samples-apiserver-provider
dubbo.application.metadataServicePort=20885
dubbo.registry.address=kubernetes://DEFAULT_MASTER_HOST?registry-type=service&duplicate=false&namespace=dubbo-demo&trustCerts=true
dubbo.protocol.name=tri
dubbo.protocol.port=50052
dubbo.application.qosEnable=true
dubbo.application.qosAcceptForeignIp=true

```

如果要在本地打包镜像，可通过提供的Dockerfile打包镜像（也可以直接使用示例提供好的镜像包）

```shell
# 打包镜像
docker build -f ./Dockerfile -t dubbo-samples-apiserver-provider .

docker build -f ./Dockerfile -t dubbo-samples-apiserver-consumer .

# 重命名镜像
docker tag dubbo-samples-apiserver-provider:latest 15841721425/dubbo-samples-apiserver-provider

docker tag dubbo-samples-apiserver-consumer:latest 15841721425/dubbo-samples-apiserver-consumer

# 推到镜像仓库
docker push 15841721425/dubbo-samples-apiserver-provider

docker push 15841721425/dubbo-samples-apiserver-consumer
```

### 3.4 部署到 Kubernetes

#### 3.4.1 部署 Provider

```shell
# 部署 Service
kubectl apply -f https://raw.githubusercontent.com/apache/dubbo-samples/master/dubbo-samples-kubernetes/dubbo-samples-apiserver-provider/src/main/resources/k8s/Service.yml

# 部署 Deployment
kubectl apply -f https://raw.githubusercontent.com/apache/dubbo-samples/master/dubbo-samples-kubernetes/dubbo-samples-apiserver-provider/src/main/resources/k8s/Deployment.yml
```

以上命令创建了一个名为 `dubbo-samples-apiserver-provider` 的 Service，注意这里的 service name 与项目中的 dubbo 应用名是一样的。

接着 Deployment 部署了一个 2 副本的 pod 实例，至此 Provider 启动完成。  
可以通过如下命令检查启动日志。

```shell
# 查看 pod 列表
kubectl get pods -l app=dubbo-samples-apiserver-provider

# 查看 pod 部署日志
kubectl logs your-pod-id
```

#### 3.4.2 部署 Consumer

```shell
# 部署 Service
kubectl apply -f https://raw.githubusercontent.com/apache/dubbo-samples/master/dubbo-samples-kubernetes/dubbo-samples-apiserver-consumer/src/main/resources/k8s/Service.yml

# 部署 Deployment
kubectl apply -f https://raw.githubusercontent.com/apache/dubbo-samples/master/dubbo-samples-kubernetes/dubbo-samples-apiserver-consumer/src/main/resources/k8s/Deployment.yml
```

部署 consumer 与 provider 是一样的，这里也保持了 K8S Service 与 Dubbo consumer 名字一致： dubbo-samples-apiserver-consumer。

检查启动日志，consumer 完成对 provider 服务的消费。

```shell
# 查看 pod 列表
kubectl get pods -l app=dubbo-samples-apiserver-consumer

# 查看 pod 部署日志
kubectl logs your-pod-id

# 查看 pod isitio-proxy 日志
kubectl logs your-pod-id -c istio-proxy
```

可以看到 consumer pod 日志输出如下:

```bash
[22/04/22 01:10:24:024UTC]main INFO deploy.DefaultApplicationDeployer:[DUBBO]Dubbo Application[1.1](dubbo-samples-apiserver-consumer)is ready.,dubbo version:3.0.7,current host:172.17.0.6
        result:hello,Kubernetes Api Server
```

consumer istio-proxy 日志输出如下:

```shell
[2022-06-22T08:25:02.647Z] "POST /org.apache.dubbo.samples.Greeter/greet HTTP/2" 200 
- via_upstream - "-" 28 34 25 3 "-" "-" "11830c24-e1d0-997f-9f40-0237f272cd37" "dubbo-samples-apiserver-provider:50052" "172.17.0.9:50052" 
outbound|50052||dubbo-samples-apiserver-provider.dubbo-demo.svc.cluster.local 172.17.0.5:34826 172.17.0.9:50052 172.17.0.5:34824 - default
```

可以看到 provider pod 日志输出如下:

```shell
[22/06/22 08:22:36:036 UTC] Dubbo-protocol-50052-thread-8  INFO impl.GreeterImpl: 
Server test dubbo tri k8s received greet request name: "Kubernetes Api Server"
```

provider istio-proxy 日志输出如下:

```shell
[2022-06-22T08:23:28.440Z] "POST /org.apache.dubbo.samples.Greeter/greet HTTP/2" 200 
- via_upstream - "-" 28 34 15 2 "-" "-" "4ed30165-83d5-9c58-8458-9d558ba3bc5a" "dubbo-samples-apiserver-provider:50052" "172.17.0.9:50052" 
inbound|50052|| 127.0.0.6:40761 172.17.0.9:50052 172.17.0.5:33626 outbound_.50052_._.dubbo-samples-apiserver-provider.dubbo-demo.svc.cluster.local default
```

### 3.5 检查 Consumer 正常消费服务

TBD
> * 改造 consumer 支持 spring-web
> * Consumer service 暴露对外地址与端口
> * 访问 http 地址验证行为

## 4 最佳实践

TBD

* rediness probe
* liveness probe

## 5 CI/CD

* 接入 Skalfold

## 6 附录 k8s manifests

ServiceAccount.yml

```yaml
apiVersion: v1
kind: Namespace
metadata:
  name: dubbo-demo
---
apiVersion: rbac.authorization.k8s.io/v1
kind: Role
metadata:
  namespace: dubbo-demo
  name: dubbo-role
rules:
  - apiGroups: [ "" ]
    resources: [ "pods" ]
    verbs: [ "get", "watch", "list", "update", "patch" ]
  - apiGroups: [ "", "service.dubbo.apache.org" ]
    resources: [ "services", "endpoints", "virtualservices", "destinationrules" ]
    verbs: [ "get", "watch", "list" ]
---
apiVersion: v1
kind: ServiceAccount
metadata:
  name: dubbo-sa
  namespace: dubbo-demo
---
apiVersion: rbac.authorization.k8s.io/v1
kind: RoleBinding
metadata:
  name: dubbo-sa-bind
  namespace: dubbo-demo
roleRef:
  apiGroup: rbac.authorization.k8s.io
  kind: Role
  name: dubbo-role
subjects:
  - kind: ServiceAccount
    name: dubbo-sa
```

Service.yml

```yaml
apiVersion: v1
kind: Service
metadata:
   name: dubbo-samples-apiserver-provider
   namespace: dubbo-demo
spec:
   clusterIP: None
   selector:
      app: dubbo-samples-apiserver-provider
   ports:
      - name: tri
        protocol: TCP
        port: 50052
        targetPort: 50052
      - name: dubbo
        protocol: TCP
        port: 20880
        targetPort: 20880
      - name: qos
        protocol: TCP
        port: 22222
        targetPort: 22222
      - name: test
        protocol: TCP
        port: 31000
        targetPort: 31000
```

Deployment.yml

```yaml
apiVersion: apps/v1
kind: Deployment
metadata:
   name: dubbo-samples-apiserver-provider
   namespace: dubbo-demo
spec:
   replicas: 2
   selector:
      matchLabels:
         app: dubbo-samples-apiserver-provider
   template:
      metadata:
         labels:
            app: dubbo-samples-apiserver-provider
      spec:
         serviceAccountName: dubbo-sa
         containers:
            - name: server
              image: 15841721425/dubbo-samples-apiserver-provider
              imagePullPolicy: IfNotPresent
              ports:
                 - containerPort: 50052
                   protocol: TCP
                 - containerPort: 22222
                   protocol: TCP
                 - containerPort: 31000
                   protocol: TCP
              livenessProbe:
                 httpGet:
                    path: /live
                    port: 22222
                 initialDelaySeconds: 5
                 periodSeconds: 5
              readinessProbe:
                 httpGet:
                    path: /ready
                    port: 22222
                 initialDelaySeconds: 5
                 periodSeconds: 5
              startupProbe:
                 httpGet:
                    path: /startup
                    port: 22222
                 failureThreshold: 30
                 periodSeconds: 10
```

Service.yml

```yaml
apiVersion: v1
kind: Service
metadata:
   name: dubbo-samples-apiserver-consumer
   namespace: dubbo-demo
spec:
   clusterIP: None
   selector:
      app: dubbo-samples-apiserver-consumer
   ports:
      - name: dubbo
        protocol: TCP
        port: 20880
        targetPort: 20880
      - name: qos
        protocol: TCP
        port: 22222
        targetPort: 22222
      - name: test
        protocol: TCP
        port: 31000
        targetPort: 31000
```

Deployment.yml

```yaml
apiVersion: apps/v1
kind: Deployment
metadata:
   name: dubbo-samples-apiserver-consumer
   namespace: dubbo-demo
spec:
   replicas: 1
   selector:
      matchLabels:
         app: dubbo-samples-apiserver-consumer
   template:
      metadata:
         labels:
            app: dubbo-samples-apiserver-consumer
      spec:
         serviceAccountName: dubbo-sa
         containers:
            - name: server
              image: 15841721425/dubbo-samples-apiserver-consumer
              imagePullPolicy: IfNotPresent
              ports:
                 - containerPort: 20880
                   protocol: TCP
                 - containerPort: 22222
                   protocol: TCP
                 - containerPort: 31000
                   protocol: TCP
              livenessProbe:
                 httpGet:
                    path: /live
                    port: 22222
                 initialDelaySeconds: 5
                 periodSeconds: 5
              readinessProbe:
                 httpGet:
                    path: /ready
                    port: 22222
                 initialDelaySeconds: 5
                 periodSeconds: 5
              startupProbe:
                 httpGet:
                    path: /startup
                    port: 22222
                 failureThreshold: 30
                 periodSeconds: 10
```
