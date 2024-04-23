# 使用APISIX+Nacos代理Dubbo服务

### 安装

你可以选择以下任意一种方式安装 APISIX：

- Docker
- Helm
- RPM
- DEB
- Source Code

本文档使用Docker安装，较为简单

使用此方法安装 APISIX，你需要安装 [Docker](https://www.docker.com/) 和 [Docker Compose](https://docs.docker.com/compose/)。

首先下载 [apisix-docker](https://github.com/apache/apisix-docker) 仓库。

`git clone https://github.com/apache/apisix-docker.gitcd apisix-docker/example`

### 配置

接着在 config.yaml 文件中进行 dubbo-proxy 插件启用

```yaml
# Add this in config.yaml
plugins:
  - ... # plugin you need
  - dubbo-proxy
```

然后注意由于要接入到nacos注册中心，因此需要修改`docker-compose.yaml`

添加如下内容

```yaml
  nacos:
    image: nacos/nacos-server:v2.1.1
    container_name: nacos-standalone
    environment:
    - PREFER_HOST_MODE=hostname
    - MODE=standalone
    ports:
    - "8848:8848"
    - "9848:9848"
    networks:
      apisix:
```

最后使用 `docker-compose` 启用 APISIX：`docker-compose -p docker-apisix up -d`

### 接入

代码示例可以使用`dubbo-samples-gateway`模块下的`dubbo-samples-gateway-apisix-dubbo`

使用curl创建指向 Dubbo Provider 的 Upstream。

```yaml
curl [http://127.0.0.1:9180/apisix/admin/upstreams/1](http://127.0.0.1:9180/apisix/admin/upstreams/1)  -H 'X-API-KEY: edd1c9f034335f136f87ad84b625c8f1' -X PUT -d '
{
    "nodes": {
        "10.21.32.168:20880": 1
    },
    "type": "roundrobin",
    "discovery_type": "nacos"
}'
```

为 ApisixService暴露一个 HTTP 路由。

```yaml
curl [http://127.0.0.1:9180/apisix/admin/routes/1](http://127.0.0.1:9180/apisix/admin/routes/1)  -H 'X-API-KEY: edd1c9f034335f136f87ad84b625c8f1' -X PUT -d '
{
    "uris": [
        "/dubbo"
    ],
    "plugins": {
        "dubbo-proxy": {
            "service_name": "org.apache.dubbo.samples.api.ApisixService",
            "service_version": "0.0.0",
            "method": "apisixToDubbo"
        }
    },
    "upstream_id": 1
}'
```

使用 curl 命令请求 Apache APISIX，并查看返回结果。

```bash
curl http://127.0.0.1:9080/dubbo -H "Host: example.org"  -X POST --data '{"name": "hello"}'
```

```bash
dubbo success

Key = accept-language, Value = zh-CN,zh;q=0.8
Key = host, Value = 10.21.32.168:9080
Key = connection, Value = keep-alive
Key = accept-encoding, Value = gzip, deflate
Key = accept, Value = text/html,application/json,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8
Key = user-agent, Value = Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/53
```

至此一个最基本的使用apisix代理dubbo服务sample就完成了