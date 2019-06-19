# Dubbo Consul Example

This example shows how to use [consul](https://www.consul.io/) as Dubbo's registry center

## How To Run

#### Step 1. Start Consul as Registry Center

In this example, a docker compose file is provided to start the required consul agent easily. But at the same time, it requires you to prepare docker environment as a prerequisite. You can refer to [docker quick start](https://www.docker.com/get-started) to install.

```bash
docker run -p8500:8500 consul:latest
```

You may need also check further from [Consul Devops Handbook](https://imaginea.gitbooks.io/consul-devops-handbook/content/deploying_consul_in_docker_containers.html).

#### Step 2. Build Examples

Execute the following maven command under *dubbo-samples-consul* directory, or simply import the whole sample project into IDE.

```bash
mvn clean package
```

#### Step 3. Run Examples

0. Run *ConsulProvider* to start service provider
0. Run *ConsulConsumer* to start service consumer, you should see the following result in the console:
```bash
result: hello, consul
```
