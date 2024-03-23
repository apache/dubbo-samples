// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: ebpf/profiling/Profile.proto

package org.apache.skywalking.apm.network.ebpf.profiling.v3;

public interface EBPFProfilingTaskMetadataOrBuilder extends
    // @@protoc_insertion_point(interface_extends:skywalking.v3.EBPFProfilingTaskMetadata)
    com.google.protobuf.MessageOrBuilder {

  /**
   * <pre>
   * profiling task id
   * </pre>
   *
   * <code>string taskId = 1;</code>
   * @return The taskId.
   */
  String getTaskId();
  /**
   * <pre>
   * profiling task id
   * </pre>
   *
   * <code>string taskId = 1;</code>
   * @return The bytes for taskId.
   */
  com.google.protobuf.ByteString
      getTaskIdBytes();

  /**
   * <pre>
   * profiling process id
   * </pre>
   *
   * <code>string processId = 2;</code>
   * @return The processId.
   */
  String getProcessId();
  /**
   * <pre>
   * profiling process id
   * </pre>
   *
   * <code>string processId = 2;</code>
   * @return The bytes for processId.
   */
  com.google.protobuf.ByteString
      getProcessIdBytes();

  /**
   * <pre>
   * the start time of this profiling process
   * </pre>
   *
   * <code>int64 profilingStartTime = 3;</code>
   * @return The profilingStartTime.
   */
  long getProfilingStartTime();

  /**
   * <pre>
   * report time
   * </pre>
   *
   * <code>int64 currentTime = 4;</code>
   * @return The currentTime.
   */
  long getCurrentTime();
}
