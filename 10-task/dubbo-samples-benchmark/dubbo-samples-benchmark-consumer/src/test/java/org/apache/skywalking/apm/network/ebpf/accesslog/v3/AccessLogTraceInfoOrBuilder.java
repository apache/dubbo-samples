// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: ebpf/accesslog.proto

package org.apache.skywalking.apm.network.ebpf.accesslog.v3;

public interface AccessLogTraceInfoOrBuilder extends
    // @@protoc_insertion_point(interface_extends:skywalking.v3.AccessLogTraceInfo)
    com.google.protobuf.MessageOrBuilder {

  /**
   * <code>.skywalking.v3.AccessLogTraceInfoProvider provider = 1;</code>
   * @return The enum numeric value on the wire for provider.
   */
  int getProviderValue();
  /**
   * <code>.skywalking.v3.AccessLogTraceInfoProvider provider = 1;</code>
   * @return The provider.
   */
  AccessLogTraceInfoProvider getProvider();

  /**
   * <pre>
   * [Optional] A string id represents the whole trace.
   * </pre>
   *
   * <code>string traceId = 2;</code>
   * @return The traceId.
   */
  String getTraceId();
  /**
   * <pre>
   * [Optional] A string id represents the whole trace.
   * </pre>
   *
   * <code>string traceId = 2;</code>
   * @return The bytes for traceId.
   */
  com.google.protobuf.ByteString
      getTraceIdBytes();

  /**
   * <pre>
   * A unique id represents this segment. Other segments could use this id to reference as a child segment.
   * [Optional] when this span reference
   * </pre>
   *
   * <code>string traceSegmentId = 3;</code>
   * @return The traceSegmentId.
   */
  String getTraceSegmentId();
  /**
   * <pre>
   * A unique id represents this segment. Other segments could use this id to reference as a child segment.
   * [Optional] when this span reference
   * </pre>
   *
   * <code>string traceSegmentId = 3;</code>
   * @return The bytes for traceSegmentId.
   */
  com.google.protobuf.ByteString
      getTraceSegmentIdBytes();

  /**
   * <pre>
   * If type == SkyWalking
   * The number id of the span. Should be unique in the whole segment.
   * Starting at 0
   * If type == Zipkin
   * The type of span ID is string.
   * </pre>
   *
   * <code>string spanId = 4;</code>
   * @return The spanId.
   */
  String getSpanId();
  /**
   * <pre>
   * If type == SkyWalking
   * The number id of the span. Should be unique in the whole segment.
   * Starting at 0
   * If type == Zipkin
   * The type of span ID is string.
   * </pre>
   *
   * <code>string spanId = 4;</code>
   * @return The bytes for spanId.
   */
  com.google.protobuf.ByteString
      getSpanIdBytes();
}