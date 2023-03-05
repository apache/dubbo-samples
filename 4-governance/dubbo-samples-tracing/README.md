# Overview

Apache Dubbo has inbuilt tracing through [Micrometer Observations](https://micrometer.io/)
and [Micrometer Tracing](https://github.com/micrometer-metrics/tracing).

## Quick Start

### Install & Start Zipkin

Follow [Zipkin's quick start](https://zipkin.io/pages/quickstart.html) to install zipkin.

Here we use docker to quickly start a zipkin server.

```bash
docker run -d -p 9411:9411 --name zipkin openzipkin/zipkin
```

Then you can verify zipkin server works by access [http://localhost:9411](http://localhost:9411)

![zipkin_home](static/zipkin_home.png)

### Start Provider

Start `org.apache.dubbo.springboot.demo.provider.ProviderApplication` directly from IDE.

### Start Consumer

Start `org.apache.dubbo.springboot.demo.consumer.ConsumerApplication` directly from IDE.

### Check Result

Open [http://localhost:9411/zipkin/](http://localhost:9411/zipkin/) in browser.

![zipkin.png](static/zipkin.png)

## How To Use Dubbo Tracing In Your Project

### 1. Adding Micrometer Observation To Your Project

In order to add Micrometer to the classpath and add metrics for Dubbo you need to add the `dubbo-metrics-default`
dependency as shown below:

```xml

<dependency>
    <groupId>org.apache.dubbo</groupId>
    <artifactId>dubbo-metrics-default</artifactId>
</dependency>
```

Thanks to the usage of [Micrometer Observations](https://micrometer.io/) Dubbo got instrumented once, but depending on
the setup will allow emission of metrics, tracer or other signals via custom `ObservationHandlers`. Please read
the [documentation under docs/observation](https://micrometer.io) for more information.

### 2. Adding Micrometer Tracing Bridge To Your Project

In order to start creating spans for Dubbo based projects a `bridge` between Micrometer Tracing and an actual Tracer is
required.

> NOTE: Tracer is a library that handles lifecycle of spans (e.g. it can create, start, stop, sample, report spans).

Micrometer Tracing supports [OpenTelemetry](https://github.com/open-telemetry/opentelemetry-java)
and [Brave](https://github.com/openzipkin/brave) as Tracers as shown below:

```xml

<!-- OpenTelemetry Tracer -->
<dependency>
    <groupId>io.micrometer</groupId>
    <artifactId>micrometer-tracing-bridge-otel</artifactId>
</dependency>
```

## 3. Adding Micrometer Tracing Exporter To Your Project

After having added the Tracer, an exporter (also known as a reporter) is required. It's a component that will export the
finished span and send it to a reporting system. Micrometer Tracer natively supports Tanzu Observability by Wavefront
and Zipkin as shown below:

OpenZipkin Zipkin with OpenTelemetry

```xml

<dependency>
    <groupId>io.opentelemetry</groupId>
    <artifactId>opentelemetry-exporter-zipkin</artifactId>
</dependency>
```

You can read more about tracing setup [this documentation, under docs/tracing](https://micrometer.io/).

## 4. Setting Up The Observation Registry

To use Micrometer Observation an `ObservationRegistry` needs setting up. In essence `ObservationRegistry` requires
passing of `ObservationHandler`s that will react to lifecycle events of observations such as start, stop etc. There are
3 main types of handlers

* `MeterObservationHandler` - metrics related handlers (coming from `micrometer-core`)
* `TracingObservationHandler` - tracing related handlers (coming from `micrometer-tracing`)
* `ObservationHandler` - any other handler

`ObservationRegistry` will iterate over all handlers and will pick all of the matching ones. It's good practice to put
all `MeterObservationHandler`s into one `FirstMatchingCompositeObservationHandler` and all `TracingObservationHandler`s
into another composite so that only one gets executed respectively. Example of such setup can be found below (an up to
date copy is maintained [here under docs/tracing](https://micrometer.io)).

```java

@Configuration
public class ObservationConfiguration {

    @Bean
    ApplicationModal applicationModal() {
        // ----- MICROMETER TRACING + OpenTelemetry -----
        SpanHandler spanHandler = new ZipkinSpanExporterBuilder().setEndpoint("http://localhost:9411/api/v2/spans").build();

        // [Micrometer Tracing component] A Micrometer Tracing wrapper for Otel's OtelCurrentTraceContext
        OtelCurrentTraceContext otelCurrentTraceContext = new OtelCurrentTraceContext();

        String applicationName = "dubbo.application.name";
        SdkTracerProvider sdkTracerProvider = SdkTracerProvider.builder().setSampler(alwaysOn())
                .addSpanProcessor(BatchSpanProcessor.builder(spanExporter()).build())
                .setResource(Resource.create(Attributes.of(ResourceAttributes.SERVICE_NAME, applicationName)))
                .build();

        OpenTelemetrySdk openTelemetrySdk = OpenTelemetrySdk.builder().setTracerProvider(sdkTracerProvider)
                .setPropagators(contextPropagators()).build();

        // [OpenTelemetry component] otelTracer is the root component that allows to configure 
        // the tracer, handlers, context propagation etc.
        io.opentelemetry.api.trace.Tracer otelTracer = openTelemetrySdk.getTracerProvider().get("io.micrometer.micrometer-tracing");

        Slf4JEventListener slf4JEventListener = new slf4JEventListener();
        Slf4JBaggageEventListener slf4JBaggageEventListener = new Slf4JBaggageEventListener(Collections.emptyList());
        OtelTracer tracer = new OtelTracer(otelTracer, otelCurrentTraceContext, event -> {
            slf4JEventListener.onEvent(event);
            slf4JBaggageEventListener.onEvent(event);
        }, new OtelBaggageManager(otelCurrentTraceContext, Collections.emptyList(), Collections.emptyList()));

        // [Micrometer Tracing component] A wrapper for Otel's Propagator
        Propagator propagator = new OtelPropagator(ContextPropagators.create(B3Propagator.injectingSingleHeader()), otelTracer);

        // ----- MICROMETER CORE -----
        MeterRegistry meterRegistry = new SimpleMeterRegistry();

        // ----- MICROMETER OBSERVATION -----
        ObservationRegistry observationRegistry = ObservationRegistry.create();

        // Adding metrics handler
        observationRegistry.observationConfig().observationHandler(
                new TracingAwareMeterObservationHandler<>(new DefaultMeterObservationHandler(meterRegistry), tracer));

        // Adding tracing handlers
        observationRegistry.observationConfig()
                .observationHandler(new ObservationHandler.FirstMatchingCompositeObservationHandler(
                        new PropagatingReceiverTracingObservationHandler<>(tracer, propagator),
                        new PropagatingSenderTracingObservationHandler<>(tracer, propagator),
                        new DefaultTracingObservationHandler(tracer)));

        // ----- DUBBO -----
        // reuse the applicationModel in your system
        ApplicationModel applicationModel = ApplicationModel.defaultModel();
        applicationModel.getBeanFactory().registerBean(observationRegistry);
    }
}

```

By using the new consumer and provider Dubbo filters that use Micrometer Observation, after setting up the registry,
metrics and traces would be created and spans would be sent to Zipkin upon their closing.

> IMPORTANT! Staring from Spring Boot 3 the whole Micrometer Tracer, Micrometer Core and Micrometer Observation setup
> happens out of the box. You don't need to manually set it up.

## 5. Customizing Observation Filters

To customize the tags present in metrics (low cardinality tags) and in spans (low and high cardinality tags) you should
create your own versions of `DubboServerObservationConvention` (server side) and `DubboClientObservationConvention` (
client side) and register them in the `ApplicationModel`'s `BeanFactory`. To reuse the existing ones
check `DefaultDubboServerObservationConvention` (server side) and `DefaultDubboClientObservationConvention` (client
side).

## 6. Sample Setup

Since Micrometer Observation is a new feature in Micrometer 1.10, Spring Boot 2 doesn't have it configured out of the
box (SB2 uses Micrometer 1.9). In
this [this demo sample](https://github.com/apache/dubbo/tree/3.2/dubbo-demo/dubbo-demo-spring-boot) you can see how
Micrometer Observation is manually set up together with OpenTelemetry Bridge.

## Extension

### Other Micrometer Tracing Bridge

```xml
<!-- Brave Tracer -->
<dependency>
    <groupId>io.micrometer</groupId>
    <artifactId>micrometer-tracing-bridge-brave</artifactId>
</dependency>
```

### Other Micrometer Tracing Exporter

Tanzu Observability by Wavefront

```xml

<dependency>
    <groupId>io.micrometer</groupId>
    <artifactId>micrometer-tracing-reporter-wavefront</artifactId>
</dependency>
```

OpenZipkin Zipkin with Brave

```xml

<dependency>
    <groupId>io.zipkin.reporter2</groupId>
    <artifactId>zipkin-reporter-brave</artifactId>
</dependency>
```

An OpenZipkin URL sender dependency to send out spans to Zipkin via a URLConnectionSender

```xml

<dependency>
    <groupId>io.zipkin.reporter2</groupId>
    <artifactId>zipkin-sender-urlconnection</artifactId>
</dependency>
```