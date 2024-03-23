// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: ebpf/profiling/Process.proto

package org.apache.skywalking.apm.network.ebpf.profiling.process.v3;

public interface EBPFProcessDownstreamOrBuilder extends
    // @@protoc_insertion_point(interface_extends:skywalking.v3.EBPFProcessDownstream)
    com.google.protobuf.MessageOrBuilder {

  /**
   * <pre>
   * Generated process id
   * </pre>
   *
   * <code>string processId = 1;</code>
   * @return The processId.
   */
  String getProcessId();
  /**
   * <pre>
   * Generated process id
   * </pre>
   *
   * <code>string processId = 1;</code>
   * @return The bytes for processId.
   */
  com.google.protobuf.ByteString
      getProcessIdBytes();

  /**
   * <code>.skywalking.v3.EBPFHostProcessDownstream hostProcess = 2;</code>
   * @return Whether the hostProcess field is set.
   */
  boolean hasHostProcess();
  /**
   * <code>.skywalking.v3.EBPFHostProcessDownstream hostProcess = 2;</code>
   * @return The hostProcess.
   */
  EBPFHostProcessDownstream getHostProcess();
  /**
   * <code>.skywalking.v3.EBPFHostProcessDownstream hostProcess = 2;</code>
   */
  EBPFHostProcessDownstreamOrBuilder getHostProcessOrBuilder();

  /**
   * <code>.skywalking.v3.EBPFKubernetesProcessDownstream k8sProcess = 3;</code>
   * @return Whether the k8sProcess field is set.
   */
  boolean hasK8SProcess();
  /**
   * <code>.skywalking.v3.EBPFKubernetesProcessDownstream k8sProcess = 3;</code>
   * @return The k8sProcess.
   */
  EBPFKubernetesProcessDownstream getK8SProcess();
  /**
   * <code>.skywalking.v3.EBPFKubernetesProcessDownstream k8sProcess = 3;</code>
   */
  EBPFKubernetesProcessDownstreamOrBuilder getK8SProcessOrBuilder();

  public EBPFProcessDownstream.ProcessCase getProcessCase();
}
