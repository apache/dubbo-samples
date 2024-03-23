// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: ebpf/accesslog.proto

package org.apache.skywalking.apm.network.ebpf.accesslog.v3;

public interface AccessLogKernelWriteL3MetricsOrBuilder extends
    // @@protoc_insertion_point(interface_extends:skywalking.v3.AccessLogKernelWriteL3Metrics)
    com.google.protobuf.MessageOrBuilder {

  /**
   * <pre>
   * total duration(nanosecond) of layer 3
   * </pre>
   *
   * <code>uint64 totalDuration = 1;</code>
   * @return The totalDuration.
   */
  long getTotalDuration();

  /**
   * <pre>
   * total local out use duration(nanoseconds) -&gt; kernel: ip_local_out
   * </pre>
   *
   * <code>uint64 totalLocalDuration = 2;</code>
   * @return The totalLocalDuration.
   */
  long getTotalLocalDuration();

  /**
   * <pre>
   * total output use duration(nanoseconds) -&gt; kernel: ip_finish_output2 - ip_output
   * </pre>
   *
   * <code>uint64 totalOutputDuration = 3;</code>
   * @return The totalOutputDuration.
   */
  long getTotalOutputDuration();

  /**
   * <pre>
   * total resolve remote MAC address(ARP Request) count and duration(nanosecond) -&gt; kernel: neigh_resolve_output
   * </pre>
   *
   * <code>uint64 totalResolveMACCount = 5;</code>
   * @return The totalResolveMACCount.
   */
  long getTotalResolveMACCount();

  /**
   * <code>uint64 totalResolveMACDuration = 6;</code>
   * @return The totalResolveMACDuration.
   */
  long getTotalResolveMACDuration();

  /**
   * <pre>
   * total netfiltering count and duration(nanosecond) -&gt; kernel: nf_hook
   * </pre>
   *
   * <code>uint64 totalNetFilterCount = 7;</code>
   * @return The totalNetFilterCount.
   */
  long getTotalNetFilterCount();

  /**
   * <code>uint64 totalNetFilterDuration = 8;</code>
   * @return The totalNetFilterDuration.
   */
  long getTotalNetFilterDuration();
}