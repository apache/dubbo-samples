# Overview

Apache Dubbo has inbuilt tracing through [Micrometer Observations](https://micrometer.io/)
and [Micrometer Tracing](https://github.com/micrometer-metrics/tracing).

## 1. Adding Micrometer Observation To Your Project

In order to add Micrometer to the classpath and add metrics for Dubbo you need to add the `dubbo-metrics-api` dependency
as shown below:

```xml

<dependency>
    <groupId>org.apache.dubbo</groupId>
    <artifactId>dubbo-metrics-api</artifactId>
</dependency>
```

Thanks to the usage of [Micrometer Observations](https://micrometer.io/) Dubbo got instrumented once, but depending on
the setup will allow emission of metrics, tracer or other signals via custom `ObservationHandlers`. Please read
the [documentation under docs/observation](https://micrometer.io) for more information.

## 2. Adding Micrometer Tracing Bridge To Your Project

In order to start creating spans for Dubbo based projects a `bridge` between Micrometer Tracing and an actual Tracer is
required.

> NOTE: Tracer is a library that handles lifecycle of spans (e.g. it can create, start, stop, sample, report spans).

Micrometer Tracing supports [Brave](https://github.com/openzipkin/brave)
and [OpenTelemetry](https://github.com/open-telemetry/opentelemetry-java) as Tracers as shown below:

```xml

<!-- Brave Tracer -->
<dependency>
    <groupId>io.micrometer</groupId>
    <artifactId>micrometer-tracing-bridge-brave</artifactId>
</dependency>

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

OpenZipkin Zipkin with OpenTelemetry

```xml

<dependency>
    <groupId>io.opentelemetry</groupId>
    <artifactId>opentelemetry-exporter-zipkin</artifactId>
</dependency>
```

An OpenZipkin URL sender dependency to send out spans to Zipkin via a URLConnectionSender

```xml

<dependency>
    <groupId>io.zipkin.reporter2</groupId>
    <artifactId>zipkin-sender-urlconnection</artifactId>
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

        // ----- MICROMETER TRACING + BRAVE -----
        
        // [Brave component] Example of using a SpanHandler. SpanHandler is a component
        // that gets called when a span is finished. Here we have an example of setting it
        // up with sending spans
        // in a Zipkin format to the provided location via the UrlConnectionSender
        // (through the <io.zipkin.reporter2:zipkin-sender-urlconnection> dependency)
        // Another option could be to use a TestSpanHandler for testing purposes.
        SpanHandler spanHandler = new ZipkinSpanExporterBuilder().setEndpoint("http://localhost:9411/api/v2/spans").build();

        // [Brave component] CurrentTraceContext is a Brave component that allows you to
        // retrieve the current TraceContext.
        StrictCurrentTraceContext braveCurrentTraceContext=StrictCurrentTraceContext.create();

        // [Micrometer Tracing component] A Micrometer Tracing wrapper for Brave's
        // CurrentTraceContext
        CurrentTraceContext bridgeContext=new BraveCurrentTraceContext(this.braveCurrentTraceContext);

        // [Brave component] Tracing is the root component that allows to configure the
        // tracer, handlers, context propagation etc.
        Tracing tracing=Tracing.newBuilder().currentTraceContext(this.braveCurrentTraceContext).supportsJoin(false)
        .traceId128Bit(true)
        // For Baggage to work you need to provide a list of fields to propagate
        .propagationFactory(BaggagePropagation.newFactoryBuilder(B3Propagation.FACTORY)
        .add(BaggagePropagationConfig.SingleBaggageField
        .remote(BaggageField.create("from_span_in_scope 1")))
        .add(BaggagePropagationConfig.SingleBaggageField
        .remote(BaggageField.create("from_span_in_scope 2")))
        .add(BaggagePropagationConfig.SingleBaggageField.remote(BaggageField.create("from_span")))
        .build())
        .sampler(Sampler.ALWAYS_SAMPLE).addSpanHandler(this.spanHandler).build();


        // [Brave component] Tracer is a component that handles the life-cycle of a span
        brave.Tracer braveTracer=tracing.tracer();

        // [Micrometer Tracing component] A wrapper for Brave's Propagator
        Propagator propagator=new BravePropagator(tracing);

        // [Micrometer Tracing component] A Micrometer Tracing wrapper for Brave's Tracer
        Tracer tracer=new BraveTracer(braveTracer,bridgeContext,new BraveBaggageManager());

        // ----- MICROMETER CORE -----
        MeterRegistry meterRegistry=new SimpleMeterRegistry();

        // ----- MICROMETER OBSERVATION -----
        ObservationRegistry observationRegistry=ObservationRegistry.create();

        // Adding metrics handler
        observationRegistry.observationConfig().observationHandler(new TracingAwareMeterObservationHandler<>(new DefaultMeterObservationHandler(meterRegistry),tracer));

        // Adding tracing handlers
        observationRegistry.observationConfig()
        .observationHandler(new ObservationHandler.FirstMatchingCompositeObservationHandler(new PropagatingReceiverTracingObservationHandler<>(tracer,propagator),new PropagatingSenderTracingObservationHandler<>(tracer,propagator),new DefaultTracingObservationHandler(tracer)));


        // ----- DUBBO -----
        // reuse the applicationModel in your system
        ApplicationModel applicationModel=ApplicationModel.defaultModel();
        applicationModel.getBeanFactory().registerBean(observationRegistry);
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