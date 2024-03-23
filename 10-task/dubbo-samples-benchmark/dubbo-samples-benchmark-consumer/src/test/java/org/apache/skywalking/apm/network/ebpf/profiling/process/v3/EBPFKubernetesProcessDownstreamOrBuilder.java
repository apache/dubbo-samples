// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: ebpf/profiling/Process.proto

package org.apache.skywalking.apm.network.ebpf.profiling.process.v3;

public interface EBPFKubernetesProcessDownstreamOrBuilder extends
    // @@protoc_insertion_point(interface_extends:skywalking.v3.EBPFKubernetesProcessDownstream)
    com.google.protobuf.MessageOrBuilder {

  /**
   * <code>int32 pid = 1;</code>
   * @return The pid.
   */
  int getPid();

  /**
   * <code>.skywalking.v3.EBPFProcessEntityMetadata entityMetadata = 2;</code>
   * @return Whether the entityMetadata field is set.
   */
  boolean hasEntityMetadata();
  /**
   * <code>.skywalking.v3.EBPFProcessEntityMetadata entityMetadata = 2;</code>
   * @return The entityMetadata.
   */
  EBPFProcessEntityMetadata getEntityMetadata();
  /**
   * <code>.skywalking.v3.EBPFProcessEntityMetadata entityMetadata = 2;</code>
   */
  EBPFProcessEntityMetadataOrBuilder getEntityMetadataOrBuilder();
}
