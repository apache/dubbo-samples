// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: ebpf/accesslog.proto

package org.apache.skywalking.apm.network.ebpf.accesslog.v3;

public interface AccessLogKernelConnectOperationOrBuilder extends
    // @@protoc_insertion_point(interface_extends:skywalking.v3.AccessLogKernelConnectOperation)
    com.google.protobuf.MessageOrBuilder {

  /**
   * <pre>
   * Starting to connect with peer address timestamp
   * </pre>
   *
   * <code>.skywalking.v3.EBPFTimestamp startTime = 1;</code>
   * @return Whether the startTime field is set.
   */
  boolean hasStartTime();
  /**
   * <pre>
   * Starting to connect with peer address timestamp
   * </pre>
   *
   * <code>.skywalking.v3.EBPFTimestamp startTime = 1;</code>
   * @return The startTime.
   */
  EBPFTimestamp getStartTime();
  /**
   * <pre>
   * Starting to connect with peer address timestamp
   * </pre>
   *
   * <code>.skywalking.v3.EBPFTimestamp startTime = 1;</code>
   */
  EBPFTimestampOrBuilder getStartTimeOrBuilder();

  /**
   * <pre>
   * Finish connect operation timestamp
   * </pre>
   *
   * <code>.skywalking.v3.EBPFTimestamp endTime = 2;</code>
   * @return Whether the endTime field is set.
   */
  boolean hasEndTime();
  /**
   * <pre>
   * Finish connect operation timestamp
   * </pre>
   *
   * <code>.skywalking.v3.EBPFTimestamp endTime = 2;</code>
   * @return The endTime.
   */
  EBPFTimestamp getEndTime();
  /**
   * <pre>
   * Finish connect operation timestamp
   * </pre>
   *
   * <code>.skywalking.v3.EBPFTimestamp endTime = 2;</code>
   */
  EBPFTimestampOrBuilder getEndTimeOrBuilder();

  /**
   * <pre>
   * Is the connect operation success or not
   * </pre>
   *
   * <code>bool success = 3;</code>
   * @return The success.
   */
  boolean getSuccess();
}
