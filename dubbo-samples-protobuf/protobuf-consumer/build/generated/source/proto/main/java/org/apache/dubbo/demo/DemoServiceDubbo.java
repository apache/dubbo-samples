package org.apache.dubbo.demo;

import java.util.concurrent.atomic.AtomicBoolean;
/**
 * <pre>
 * The demo service definition.
 * </pre>
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.19.0-SNAPSHOT)",
    comments = "Source: DemoService.proto")
public final class DemoServiceDubbo {

  private static final AtomicBoolean registered = new AtomicBoolean();

  static {
      if (registered.compareAndSet(false, true)) {
         org.apache.dubbo.common.serialize.protobuf.support.ProtobufUtils.marshaller(
              org.apache.dubbo.demo.HelloRequest.getDefaultInstance());
         org.apache.dubbo.common.serialize.protobuf.support.ProtobufUtils.marshaller(
              org.apache.dubbo.demo.HelloReply.getDefaultInstance());
      }
  }

  private DemoServiceDubbo() {}

  public static final String SERVICE_NAME = "demoservice.DemoService";

  /**
   * Code generated for Dubbo
   */
  public interface IDemoService {

  org.apache.dubbo.demo.HelloReply sayHello(org.apache.dubbo.demo.HelloRequest request);

  java.util.concurrent.CompletableFuture<org.apache.dubbo.demo.HelloReply> sayHelloAsync(
  org.apache.dubbo.demo.HelloRequest request);

}

}
