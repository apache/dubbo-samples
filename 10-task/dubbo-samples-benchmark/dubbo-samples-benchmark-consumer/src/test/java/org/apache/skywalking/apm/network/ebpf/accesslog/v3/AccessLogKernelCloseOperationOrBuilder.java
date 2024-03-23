// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: ebpf/accesslog.proto

package org.apache.skywalking.apm.network.ebpf.accesslog.v3;

public interface AccessLogKernelCloseOperationOrBuilder extends
    // @@protoc_insertion_point(interface_extends:skywalking.v3.AccessLogKernelCloseOperation)
    com.google.protobuf.MessageOrBuilder {

  /**
   * <pre>
   * Starting to close the connection timestamp
   * </pre>
   *
   * <code>.skywalking.v3.EBPFTimestamp startTime = 1;</code>
   * @return Whether the startTime field is set.
   */
  boolean hasStartTime();
  /**
   * <pre>
   * Starting to close the connection timestamp
   * </pre>
   *
   * <code>.skywalking.v3.EBPFTimestamp startTime = 1;</code>
   * @return The startTime.
   */
  EBPFTimestamp getStartTime();
  /**
   * <pre>
   * Starting to close the connection timestamp
   * </pre>
   *
   * <code>.skywalking.v3.EBPFTimestamp startTime = 1;</code>
   */
  EBPFTimestampOrBuilder getStartTimeOrBuilder();

  /**
   * <pre>
   * Finish close operation timestamp
   * </pre>
   *
   * <code>.skywalking.v3.EBPFTimestamp endTime = 2;</code>
   * @return Whether the endTime field is set.
   */
  boolean hasEndTime();
  /**
   * <pre>
   * Finish close operation timestamp
   * </pre>
   *
   * <code>.skywalking.v3.EBPFTimestamp endTime = 2;</code>
   * @return The endTime.
   */
  EBPFTimestamp getEndTime();
  /**
   * <pre>
   * Finish close operation timestamp
   * </pre>
   *
   * <code>.skywalking.v3.EBPFTimestamp endTime = 2;</code>
   */
  EBPFTimestampOrBuilder getEndTimeOrBuilder();

  /**
   * <pre>
   * Is the close operation success or not
   * </pre>
   *
   * <code>bool success = 3;</code>
   * @return The success.
   */
  boolean getSuccess();
}
