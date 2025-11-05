# Using APISIX+Nacos to Proxy Dubbo Service

### Installation

You can choose any of the following ways to install APISIX:

- Docker
- Helm
- RPM
- DEB
- Source Code

This document uses Docker installation, which is relatively simple

To install APISIX using this method, you need to install [Docker](https://www.docker.com/) And

[docker-compose](https://docs.docker.com/compose/)

First, download [apisix-docker](https://github.com/apache/apisix-docker) Warehouse.

`git clone https://github.com/apache/apisix-docker.git`

`cd apisix-docker/example`

### Configuration

Next, enable the dubbo proxy plugin in the config.yaml file

`cd apisix-docker/example/apisix_conf/`

```yaml
# Add this in config.yaml
plugins:
  - ... # plugin you need
  - dubbo-proxy
```

Then note that due to the need to connect to the Nacos registry, it is necessary to modify the

`cd apisix-docker/example`

`vi docker-compose.yml`
Add the following content

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

Finally, use Docker Compose to enable APISIX:

`docker-compose -p docker-apisix up -d`

### Access

The code example can use the `dubbo-samples-gateway-apisix-dubbo` module under the `dubbo samples gateway` module
Create an Upstream pointing to Dubbo Provider using curl.

```yaml
curl http://127.0.0.1:9180/apisix/admin/upstreams/1  -H 'X-API-KEY: edd1c9f034335f136f87ad84b625c8f1' -X PUT -d '
{
    "service_name": "gateway-apisix-dubbo",
    "type": "roundrobin",
    "discovery_type": "nacos"
}'
```

Expose an HTTP route for ApisixService, the following configuration will forward requests starting with `/dubbo` to backend dubbo service `org.apache.dubbo.samples.api.ApisixService` and method `apisixDubbo`.

```yaml
curl [http://127.0.0.1:9180/apisix/admin/routes/1](http://127.0.0.1:9180/apisix/admin/routes/1)  -H 'X-API-KEY: edd1c9f034335f136f87ad84b625c8f1' -X PUT -d '
{
    "uris": [
        "/dubbo"
    ],
    "plugins": {
        "dubbo-proxy": {
            "service_name": "org.apache.dubbo.samples.gateway.apisix.dubboapi.ApisixService",
            "service_version": "0.0.0",
            "method": "apisixToDubbo"
        }
    },
    "upstream_id": 1
}'
```

Use the curl command to request Apache APISIX and view the returned results.

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

At this point, the most basic use of the Apisix proxy dubbo service sample has been completed
