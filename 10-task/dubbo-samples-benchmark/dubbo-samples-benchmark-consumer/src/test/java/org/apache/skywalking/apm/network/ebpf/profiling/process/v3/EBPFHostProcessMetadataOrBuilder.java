// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: ebpf/profiling/Process.proto

package org.apache.skywalking.apm.network.ebpf.profiling.process.v3;

public interface EBPFHostProcessMetadataOrBuilder extends
    // @@protoc_insertion_point(interface_extends:skywalking.v3.EBPFHostProcessMetadata)
    com.google.protobuf.MessageOrBuilder {

  /**
   * <pre>
   * [required] Entity metadata
   * Must ensure that entity information is unique at the time of reporting
   * </pre>
   *
   * <code>.skywalking.v3.EBPFProcessEntityMetadata entity = 1;</code>
   * @return Whether the entity field is set.
   */
  boolean hasEntity();
  /**
   * <pre>
   * [required] Entity metadata
   * Must ensure that entity information is unique at the time of reporting
   * </pre>
   *
   * <code>.skywalking.v3.EBPFProcessEntityMetadata entity = 1;</code>
   * @return The entity.
   */
  EBPFProcessEntityMetadata getEntity();
  /**
   * <pre>
   * [required] Entity metadata
   * Must ensure that entity information is unique at the time of reporting
   * </pre>
   *
   * <code>.skywalking.v3.EBPFProcessEntityMetadata entity = 1;</code>
   */
  EBPFProcessEntityMetadataOrBuilder getEntityOrBuilder();

  /**
   * <pre>
   * [required] The Process id of the host
   * </pre>
   *
   * <code>int32 pid = 2;</code>
   * @return The pid.
   */
  int getPid();

  /**
   * <pre>
   * [optional] properties of the process
   * </pre>
   *
   * <code>repeated .skywalking.v3.KeyStringValuePair properties = 3;</code>
   */
  java.util.List<org.apache.skywalking.apm.network.common.v3.KeyStringValuePair>
      getPropertiesList();
  /**
   * <pre>
   * [optional] properties of the process
   * </pre>
   *
   * <code>repeated .skywalking.v3.KeyStringValuePair properties = 3;</code>
   */
  org.apache.skywalking.apm.network.common.v3.KeyStringValuePair getProperties(int index);
  /**
   * <pre>
   * [optional] properties of the process
   * </pre>
   *
   * <code>repeated .skywalking.v3.KeyStringValuePair properties = 3;</code>
   */
  int getPropertiesCount();
  /**
   * <pre>
   * [optional] properties of the process
   * </pre>
   *
   * <code>repeated .skywalking.v3.KeyStringValuePair properties = 3;</code>
   */
  java.util.List<? extends org.apache.skywalking.apm.network.common.v3.KeyStringValuePairOrBuilder>
      getPropertiesOrBuilderList();
  /**
   * <pre>
   * [optional] properties of the process
   * </pre>
   *
   * <code>repeated .skywalking.v3.KeyStringValuePair properties = 3;</code>
   */
  org.apache.skywalking.apm.network.common.v3.KeyStringValuePairOrBuilder getPropertiesOrBuilder(
      int index);
}
