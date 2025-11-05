# Dubbo using Kubernetes as registry

可以按照下文步骤，将 Demo 部署到本地集群。

## 1 总体目标

* 部署 Dubbo 应用到 Kubernetes
* 基于 Kubernetes 内置 Service 实现服务发现
* 将 Dubbo 应用对接到 Kubernetes 生命周期

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
```

### 3.3 部署到 Kubernetes

#### 3.3.1 部署 Provider

```shell
# 部署 Service
kubectl apply -f https://raw.githubusercontent.com/apache/dubbo-samples/master/dubbo-samples-kubernetes/dubbo-samples-apiserver-provider/src/main/resources/k8s/Service.yml

# 部署 Deployment
kubectl apply -f https://raw.githubusercontent.com/apache/dubbo-samples/master/dubbo-samples-kubernetes/dubbo-samples-apiserver-provider/src/main/resources/k8s/Deployment.yml
```

以上命令创建了一个名为 `dubbo-samples-apiserver-provider` 的 Service，注意这里的 service name 与项目中的 dubbo 应用名是一样的。

接着 Deployment 部署了一个 3 副本的 pod 实例，至此 Provider 启动完成。  
可以通过如下命令检查启动日志。

```shell
# 查看 pod 列表
kubectl get pods -l app=dubbo-samples-apiserver-provider

# 查看 pod 部署日志
kubectl logs your-pod-id
```

#### 3.3.2 部署 Consumer

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
```

可以看到日志输出如下:

```java
[22/04/22 01:10:24:024UTC]main INFO deploy.DefaultApplicationDeployer:[DUBBO]Dubbo Application[1.1](dubbo-samples-apiserver-consumer)is ready.,dubbo version:3.0.7,current host:172.17.0.6
        result:hello,Kubernetes Api Server
```

### 3.4 修改项目并打包（可跳过）

示例项目及相关镜像均已就绪，此小节仅面向需要修改示例并查看部署效果的用户。
https://github.com/apache/dubbo-samples/tree/master/dubbo-samples-kubernetes/

设置 Dubbo 项目使用 Kubernetes 作为注册中心，这里通过 DEFAULT_MASTER_HOST指定使用默认 API-SERVER 集群地址 kubernetes.default.svc，同时还指定了
namespace、trustCerts 两个参数

```properties
dubbo.application.name=dubbo-samples-apiserver-provider
dubbo.application.metadataServicePort=20885
dubbo.registry.address=kubernetes://DEFAULT_MASTER_HOST:443?registry-type=service&duplicate=false&namespace=dubbo-demo&trustCerts=true
dubbo.protocol.name=dubbo
dubbo.protocol.port=20880
dubbo.application.qosEnable=true
dubbo.application.qosAcceptForeignIp=true
dubbo.provider.token=true
```

如果要在本地打包镜像，可通过 jib-maven-plugin 插件打包镜像

```shell
# 打包并推送镜像
mvn compile jib:build
```

> Jib 插件会自动打包并发布镜像。注意，本地开发需将 jib 插件配置中的 docker registry 组织 dubboteam 改为自己有权限的组织（包括其他 kubernetes manifests 中的 dubboteam 也要修改，以确保 kubernetes 部署的是自己定制后的镜像），如遇到 jib 插件认证问题，请参考[相应链接](https://github.com/GoogleContainerTools/jib/blob/master/docs/faq.md#what-should-i-do-when-the-registry-responds-with-unauthorized)配置 docker registry 认证信息。
> 可以通过直接在命令行指定 `mvn compile jib:build -Djib.to.auth.username=x -Djib.to.auth.password=x -Djib.from.auth.username=x -Djib.from.auth.username=x`，或者使用 docker-credential-helper.

## 4 最佳实践

TBD

* rediness probe
* liveness probe
* ci/cd 接入 Skalfold

## 5 附录 k8s manifests

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
  - apiGroups: [""]
    resources: ["pods"]
    verbs: ["get", "watch", "list", "update", "patch"]
  - apiGroups: ["", "service.dubbo.apache.org"]
    resources: ["services", "endpoints", "virtualservices", "destinationrules"]
    verbs: ["get", "watch", "list"]
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
    - protocol: TCP
      port: 20880
      targetPort: 20880
```

Deployment.yml

```yaml
apiVersion: apps/v1
kind: Deployment
metadata:
  name: dubbo-samples-apiserver-provider
  namespace: dubbo-demo
spec:
  replicas: 3
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
          image: apache/dubbo-demo:dubbo-samples-apiserver-provider_0.0.1
          ports:
            - containerPort: 20880
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
    - protocol: TCP
      port: 20880
      targetPort: 20880
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
          image: apache/dubbo-demo:dubbo-samples-apiserver-consumer_0.0.1
          ports:
            - containerPort: 20880
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
