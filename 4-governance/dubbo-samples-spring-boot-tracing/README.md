## 说明

目前 Dubbo 内置了 [Micrometer](https://micrometer.io/)（Micrometer 为最流行的可观察性系统在检测客户端上提供了一个统一的门面，相当于日志领域的SLF4J，SpringBoot3
内置的可观测门面组件）。

## 案例模块说明

- dubbo-samples-spring-boot-tracing-zipkin：在SpringBoot项目中如何使用Dubbo-tracing相关的starter，将trace信息上报到zipkin
- dubbo-samples-spring-boot-tracing-otel-otlp：在SpringBoot项目中如何使用Dubbo-tracing相关的starter，以 Opentelemetry 作为 Tracer，将 Trace 信息上报到 Otlp（Opentelemetry 自定义的协议，[详细文档](https://opentelemetry.io/docs/specs/otel/protocol/)）

## Tracing相关概念

- Span：基本工作单元。例如，发送 RPC 是一个新的 span，发送对 RPC 的响应也是如此。Span还有其他数据，例如description、带时间戳的事件、键值注释（标签）、导致它们的跨度的
  ID 和进程 ID（通常是 IP 地址）。跨度可以启动和停止，并且它们会跟踪它们的时间信息。创建跨度后，您必须在将来的某个时间点停止它。

- Trace：一组形成树状结构的跨度。例如，如果您运行分布式大数据存储，则可能会通过请求形成跟踪PUT。

- Annotation/Event : 用于及时记录一个事件的存在。

- Tracing context：为了使分布式跟踪工作，跟踪上下文（跟踪标识符、跨度标识符等）必须通过进程（例如通过线程）和网络传播。

- Log correlation：部分跟踪上下文（例如跟踪标识符、跨度标识符）可以填充到给定应用程序的日志中。然后可以将所有日志收集到一个存储中，并通过跟踪
  ID 对它们进行分组。这样就可以从所有按时间顺序排列的服务中获取单个业务操作（跟踪）的所有日志。

- Latency analysis tools：一种收集导出跨度并可视化整个跟踪的工具。允许轻松进行延迟分析。

- Tracer: 处理span生命周期的库（Dubbo 目前支持 Opentelemetry 和 Brave）。它可以通过 Exporter 创建、启动、停止和报告 Spans
  到外部系统（如 Zipkin、Jagger 等）。

- Exporter: 将产生的 Trace 信息通过 http 等接口上报到外部系统，比如上报到 Zipkin。

## SpringBoot Starters

对于 SpringBoot 用户，Dubbo 提供了 Tracing 相关的 starters，自动装配 Micrometer 相关的配置代码，且用户可自由选择 Tracer
和Exporter。

### Opentelemetry 作为 Tracer，将 Trace 信息 export 到 Zipkin

```yaml
  <dependency>
      <groupId>org.apache.dubbo</groupId>
      <artifactId>dubbo-spring-boot-tracing-otel-zipkin-starter</artifactId>
      <version>${version}</version>
  </dependency>
```

### Brave 作为 Tracer，将 Trace 信息 export 到 Zipkin

```yaml
  <dependency>
      <groupId>org.apache.dubbo</groupId>
      <artifactId>dubbo-spring-boot-tracing-brave-zipkin-starter</artifactId>
      <version>${version}</version>
  </dependency>
```

### Opentelemetry 作为 Tracer，将 Trace 信息 export 到 Otlp Collector

```yaml
 <dependency>
      <groupId>org.apache.dubbo</groupId>
      <artifactId>dubbo-spring-boot-tracing-otel-otlp-starter</artifactId>
      <version>${version}</version>
  </dependency>
```

### 自由组装 Tracer 和 Exporter

如果用户基于 Micrometer 有自定义的需求，想将 Trace 信息上报至其他外部系统观测，可参照如下自由组装 Tracer 和 Exporter：

```yaml
  <!-- 自动装配 -->
  <dependency>
      <groupId>org.apache.dubbo</groupId>
      <artifactId>dubbo-spring-boot-observability-starter</artifactId>
      <version>${version}</version>
  </dependency>
  <!-- otel作为tracer -->
  <dependency>
      <groupId>io.micrometer</groupId>
      <artifactId>micrometer-tracing-bridge-otel</artifactId>
      <version>${version}</version>
  </dependency>
  <!-- export到zipkin -->
  <dependency>
      <groupId>io.opentelemetry</groupId>
      <artifactId>opentelemetry-exporter-zipkin</artifactId>
      <version>${version}</version>
  </dependency>
```

```yaml
  <!-- 自动装配 -->
  <dependency>
      <groupId>org.apache.dubbo</groupId>
      <artifactId>dubbo-spring-boot-observability-starter</artifactId>
      <version>${version}</version>
  </dependency>
  <!-- brave作为tracer  -->
  <dependency>
      <groupId>io.micrometer</groupId>
      <artifactId>micrometer-tracing-bridge-brave</artifactId>
      <version>${version}</version>
  </dependency>
  <!-- export到zipkin -->
  <dependency>
      <groupId>io.zipkin.reporter2</groupId>
      <artifactId>zipkin-reporter-brave</artifactId>
      <version>${version}</version>
  </dependency>
```

后续还会补齐更多的 starters，如 Jagger、SkyWalking等。