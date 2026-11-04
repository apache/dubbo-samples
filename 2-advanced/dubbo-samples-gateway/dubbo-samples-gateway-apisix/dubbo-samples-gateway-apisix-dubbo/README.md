# Proxy a Dubbo Service Through Apache APISIX

This sample exposes the Dubbo service implemented in this directory through an HTTP route in Apache APISIX. APISIX discovers the provider through Nacos and uses the [`dubbo-proxy` plugin](https://apisix.apache.org/docs/apisix/plugins/dubbo-proxy/) to convert the HTTP request to the Dubbo protocol.

This example covers HTTP-to-Dubbo protocol conversion. It does not proxy a Dubbo 3 Triple service. For Triple, see the [APISIX Triple sample](../dubbo-samples-gateway-apisix-triple/README.md) and the [Dubbo gateway guide for Triple](https://dubbo.apache.org/en/overview/mannual/java-sdk/tasks/gateway/triple/).

## Prerequisites

- Docker and Docker Compose
- JDK 17
- Maven
- [`yq`](https://github.com/mikefarah/yq) for reading the APISIX Admin API key from the local configuration

The commands below use:

- Apache APISIX 3.17.0
- Dubbo 3.3.1 and Spring Boot 3.2.3 from this sample
- Nacos 2.1.1

The published `nacos/nacos-server:v2.1.1` image targets `linux/amd64`. On an arm64 host, Docker must use amd64 emulation, so Nacos readiness can take longer.

The `dubbo-proxy` plugin uses Hessian2 serialization and requires both the request and response types of the Dubbo method to be `Map<String, Object>`. The `ApisixService` interface in this sample satisfies that contract.

## 1. Start Nacos and APISIX

Run the commands in this README from the root of the `dubbo-samples` repository and keep the same shell open. Save both repository paths, clone the APISIX Docker repository as a sibling, and open its example directory:

```bash
dubbo_samples_dir="$(pwd)"
apisix_docker_dir="$(dirname "$dubbo_samples_dir")/apisix-docker"

git clone https://github.com/apache/apisix-docker.git "$apisix_docker_dir"
cd "$apisix_docker_dir/example"
```

Add `dubbo-proxy` to the `plugins` list in `apisix_conf/config.yaml`. If a `plugins` list already exists, keep its existing entries and append `dubbo-proxy`:

```yaml
plugins:
  - dubbo-proxy
```

Configure Nacos service discovery in the same file:

```yaml
discovery:
  nacos:
    host:
      - http://nacos:nacos@nacos:8848
```

Add the following service to `docker-compose.yml` under `services`. Attach it to the existing `apisix` network:

```yaml
  nacos:
    image: nacos/nacos-server:v2.1.1
    environment:
      MODE: standalone
      PREFER_HOST_MODE: hostname
    ports:
      - "8848:8848"
      - "9848:9848"
    networks:
      - apisix
```

Start etcd, Nacos, and APISIX. The project name is specified so that the Docker network name used in the next section is deterministic:

```bash
APISIX_IMAGE_TAG=3.17.0-debian \
  docker compose -p docker-apisix up -d etcd nacos apisix
```

Read the Admin API key from the configuration, then wait until APISIX and Nacos are ready before continuing:

```bash
admin_key=$(yq '.deployment.admin.admin_key[0].key' apisix_conf/config.yaml | sed 's/"//g')

curl --fail http://127.0.0.1:9180/apisix/admin/plugins/list \
  -H "X-API-KEY: $admin_key"
curl --fail http://127.0.0.1:8848/nacos/v1/console/health/readiness
```

## 2. Build and start the Dubbo provider

Return to the `dubbo-samples` repository and build the provider with JDK 17:

```bash
cd "$dubbo_samples_dir"

mvn \
  -f 2-advanced/dubbo-samples-gateway/dubbo-samples-gateway-apisix/dubbo-samples-gateway-apisix-dubbo/pom.xml \
  -pl dubbo-samples-gateway-apisix-dubbo-provider \
  -am \
  -DskipTests \
  clean package
```

Set the absolute path of the provider JAR, and then start it on the same Docker network as Nacos and APISIX:

```bash
provider_jar="$(pwd)/2-advanced/dubbo-samples-gateway/dubbo-samples-gateway-apisix/dubbo-samples-gateway-apisix-dubbo/dubbo-samples-gateway-apisix-dubbo-provider/target/dubbo-samples-gateway-apisix-dubbo-provider-1.0-SNAPSHOT.jar"

docker run -d \
  --name dubbo-apisix-provider \
  --network docker-apisix_apisix \
  --mount "type=bind,source=${provider_jar},target=/app/provider.jar,readonly" \
  eclipse-temurin:17-jre \
  java -jar /app/provider.jar --nacos.address=nacos
```

Check the provider logs and confirm that `ApisixService` was exported and registered with Nacos:

```bash
docker logs dubbo-apisix-provider
```

The provider application name is `gateway-apisix-dubbo`, which is the Nacos service name used by the APISIX Upstream below.

## 3. Create the APISIX Upstream and Route

Return to the `apisix-docker/example` directory. The commands below use the `admin_key` variable read from `apisix_conf/config.yaml` in step 1, so no Admin API credential is copied into this README.

```bash
cd "$apisix_docker_dir/example"
```

Create an Upstream that discovers the Dubbo provider from Nacos:

```bash
curl --fail-with-body http://127.0.0.1:9180/apisix/admin/upstreams/1 \
  -H "X-API-KEY: $admin_key" \
  -H "Content-Type: application/json" \
  -X PUT \
  -d '
{
  "service_name": "gateway-apisix-dubbo",
  "type": "roundrobin",
  "discovery_type": "nacos"
}'
```

Create the HTTP route:

```bash
curl --fail-with-body http://127.0.0.1:9180/apisix/admin/routes/1 \
  -H "X-API-KEY: $admin_key" \
  -H "Content-Type: application/json" \
  -X PUT \
  -d '
{
  "uri": "/dubbo",
  "plugins": {
    "dubbo-proxy": {
      "service_name": "org.apache.dubbo.samples.gateway.apisix.dubbo.api.ApisixService",
      "service_version": "0.0.0",
      "method": "apisixToDubbo"
    }
  },
  "upstream_id": "1"
}'
```

The route fields match this sample:

- `service_name` is the fully qualified `ApisixService` interface name.
- `method` is the `apisixToDubbo` method implemented by `ApisixServiceImpl`.
- `service_version` is `0.0.0` because the sample exports the service without an explicit Dubbo version, which uses Dubbo's default service version.

## 4. Verify the HTTP-to-Dubbo request

Send a JSON request to the APISIX route:

```bash
curl -i --fail-with-body http://127.0.0.1:9080/dubbo \
  -H "Content-Type: application/json" \
  -X POST \
  -d '{"name":"hello"}'
```

The provider receives the HTTP request data in a `Map<String, Object>` and returns a map that APISIX converts to the HTTP response.

The response should include status `200`, the `author` response header set by `ApisixServiceImpl`, and the response body `dubbo success`:

```text
HTTP/1.1 200 OK
author: yang siming

dubbo success
```

## Cleanup

```bash
docker rm -f dubbo-apisix-provider
docker compose -p docker-apisix down
```
