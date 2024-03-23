// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: ebpf/profiling/Process.proto

package org.apache.skywalking.apm.network.ebpf.profiling.process.v3;

public final class Process {
  private Process() {}
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistryLite registry) {
  }

  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistry registry) {
    registerAllExtensions(
        (com.google.protobuf.ExtensionRegistryLite) registry);
  }
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_skywalking_v3_EBPFProcessReportList_descriptor;
  static final
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_skywalking_v3_EBPFProcessReportList_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_skywalking_v3_EBPFProcessProperties_descriptor;
  static final
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_skywalking_v3_EBPFProcessProperties_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_skywalking_v3_EBPFHostProcessMetadata_descriptor;
  static final
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_skywalking_v3_EBPFHostProcessMetadata_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_skywalking_v3_EBPFProcessEntityMetadata_descriptor;
  static final
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_skywalking_v3_EBPFProcessEntityMetadata_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_skywalking_v3_EBPFKubernetesProcessMetadata_descriptor;
  static final
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_skywalking_v3_EBPFKubernetesProcessMetadata_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_skywalking_v3_EBPFReportProcessDownstream_descriptor;
  static final
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_skywalking_v3_EBPFReportProcessDownstream_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_skywalking_v3_EBPFProcessDownstream_descriptor;
  static final
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_skywalking_v3_EBPFProcessDownstream_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_skywalking_v3_EBPFHostProcessDownstream_descriptor;
  static final
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_skywalking_v3_EBPFHostProcessDownstream_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_skywalking_v3_EBPFKubernetesProcessDownstream_descriptor;
  static final
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_skywalking_v3_EBPFKubernetesProcessDownstream_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_skywalking_v3_EBPFProcessPingPkgList_descriptor;
  static final
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_skywalking_v3_EBPFProcessPingPkgList_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_skywalking_v3_EBPFProcessPingPkg_descriptor;
  static final
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_skywalking_v3_EBPFProcessPingPkg_fieldAccessorTable;

  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static  com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
    String[] descriptorData = {
      "\n\034ebpf/profiling/Process.proto\022\rskywalki" +
      "ng.v3\032\023common/Common.proto\032\024common/Comma" +
      "nd.proto\"e\n\025EBPFProcessReportList\0227\n\tpro" +
      "cesses\030\001 \003(\0132$.skywalking.v3.EBPFProcess" +
      "Properties\022\023\n\013ebpfAgentID\030\002 \001(\t\"\246\001\n\025EBPF" +
      "ProcessProperties\022=\n\013hostProcess\030\001 \001(\0132&" +
      ".skywalking.v3.EBPFHostProcessMetadataH\000" +
      "\022B\n\nk8sProcess\030\002 \001(\0132,.skywalking.v3.EBP" +
      "FKubernetesProcessMetadataH\000B\n\n\010metadata" +
      "\"\227\001\n\027EBPFHostProcessMetadata\0228\n\006entity\030\001" +
      " \001(\0132(.skywalking.v3.EBPFProcessEntityMe" +
      "tadata\022\013\n\003pid\030\002 \001(\005\0225\n\nproperties\030\003 \003(\0132" +
      "!.skywalking.v3.KeyStringValuePair\"z\n\031EB" +
      "PFProcessEntityMetadata\022\r\n\005layer\030\001 \001(\t\022\023" +
      "\n\013serviceName\030\002 \001(\t\022\024\n\014instanceName\030\003 \001(" +
      "\t\022\023\n\013processName\030\004 \001(\t\022\016\n\006labels\030\005 \003(\t\"\235" +
      "\001\n\035EBPFKubernetesProcessMetadata\0228\n\006enti" +
      "ty\030\001 \001(\0132(.skywalking.v3.EBPFProcessEnti" +
      "tyMetadata\022\013\n\003pid\030\002 \001(\005\0225\n\nproperties\030\003 " +
      "\003(\0132!.skywalking.v3.KeyStringValuePair\"V" +
      "\n\033EBPFReportProcessDownstream\0227\n\tprocess" +
      "es\030\001 \003(\0132$.skywalking.v3.EBPFProcessDown" +
      "stream\"\274\001\n\025EBPFProcessDownstream\022\021\n\tproc" +
      "essId\030\001 \001(\t\022?\n\013hostProcess\030\002 \001(\0132(.skywa" +
      "lking.v3.EBPFHostProcessDownstreamH\000\022D\n\n" +
      "k8sProcess\030\003 \001(\0132..skywalking.v3.EBPFKub" +
      "ernetesProcessDownstreamH\000B\t\n\007process\"j\n" +
      "\031EBPFHostProcessDownstream\022\013\n\003pid\030\001 \001(\005\022" +
      "@\n\016entityMetadata\030\002 \001(\0132(.skywalking.v3." +
      "EBPFProcessEntityMetadata\"p\n\037EBPFKuberne" +
      "tesProcessDownstream\022\013\n\003pid\030\001 \001(\005\022@\n\016ent" +
      "ityMetadata\030\002 \001(\0132(.skywalking.v3.EBPFPr" +
      "ocessEntityMetadata\"c\n\026EBPFProcessPingPk" +
      "gList\0224\n\tprocesses\030\001 \003(\0132!.skywalking.v3" +
      ".EBPFProcessPingPkg\022\023\n\013ebpfAgentID\030\002 \001(\t" +
      "\"\215\001\n\022EBPFProcessPingPkg\022@\n\016entityMetadat" +
      "a\030\001 \001(\0132(.skywalking.v3.EBPFProcessEntit" +
      "yMetadata\0225\n\nproperties\030\002 \003(\0132!.skywalki" +
      "ng.v3.KeyStringValuePair2\312\001\n\022EBPFProcess" +
      "Service\022e\n\017reportProcesses\022$.skywalking." +
      "v3.EBPFProcessReportList\032*.skywalking.v3" +
      ".EBPFReportProcessDownstream\"\000\022M\n\tkeepAl" +
      "ive\022%.skywalking.v3.EBPFProcessPingPkgLi" +
      "st\032\027.skywalking.v3.Commands\"\000B\203\001\n;org.ap" +
      "ache.skywalking.apm.network.ebpf.profili" +
      "ng.process.v3P\001ZBskywalking.apache.org/r" +
      "epo/goapi/collect/ebpf/profiling/process" +
      "/v3b\006proto3"
    };
    descriptor = com.google.protobuf.Descriptors.FileDescriptor
      .internalBuildGeneratedFileFrom(descriptorData,
        new com.google.protobuf.Descriptors.FileDescriptor[] {
          org.apache.skywalking.apm.network.common.v3.Common.getDescriptor(),
          org.apache.skywalking.apm.network.common.v3.CommandOuterClass.getDescriptor(),
        });
    internal_static_skywalking_v3_EBPFProcessReportList_descriptor =
      getDescriptor().getMessageTypes().get(0);
    internal_static_skywalking_v3_EBPFProcessReportList_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_skywalking_v3_EBPFProcessReportList_descriptor,
        new String[] { "Processes", "EbpfAgentID", });
    internal_static_skywalking_v3_EBPFProcessProperties_descriptor =
      getDescriptor().getMessageTypes().get(1);
    internal_static_skywalking_v3_EBPFProcessProperties_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_skywalking_v3_EBPFProcessProperties_descriptor,
        new String[] { "HostProcess", "K8SProcess", "Metadata", });
    internal_static_skywalking_v3_EBPFHostProcessMetadata_descriptor =
      getDescriptor().getMessageTypes().get(2);
    internal_static_skywalking_v3_EBPFHostProcessMetadata_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_skywalking_v3_EBPFHostProcessMetadata_descriptor,
        new String[] { "Entity", "Pid", "Properties", });
    internal_static_skywalking_v3_EBPFProcessEntityMetadata_descriptor =
      getDescriptor().getMessageTypes().get(3);
    internal_static_skywalking_v3_EBPFProcessEntityMetadata_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_skywalking_v3_EBPFProcessEntityMetadata_descriptor,
        new String[] { "Layer", "ServiceName", "InstanceName", "ProcessName", "Labels", });
    internal_static_skywalking_v3_EBPFKubernetesProcessMetadata_descriptor =
      getDescriptor().getMessageTypes().get(4);
    internal_static_skywalking_v3_EBPFKubernetesProcessMetadata_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_skywalking_v3_EBPFKubernetesProcessMetadata_descriptor,
        new String[] { "Entity", "Pid", "Properties", });
    internal_static_skywalking_v3_EBPFReportProcessDownstream_descriptor =
      getDescriptor().getMessageTypes().get(5);
    internal_static_skywalking_v3_EBPFReportProcessDownstream_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_skywalking_v3_EBPFReportProcessDownstream_descriptor,
        new String[] { "Processes", });
    internal_static_skywalking_v3_EBPFProcessDownstream_descriptor =
      getDescriptor().getMessageTypes().get(6);
    internal_static_skywalking_v3_EBPFProcessDownstream_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_skywalking_v3_EBPFProcessDownstream_descriptor,
        new String[] { "ProcessId", "HostProcess", "K8SProcess", "Process", });
    internal_static_skywalking_v3_EBPFHostProcessDownstream_descriptor =
      getDescriptor().getMessageTypes().get(7);
    internal_static_skywalking_v3_EBPFHostProcessDownstream_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_skywalking_v3_EBPFHostProcessDownstream_descriptor,
        new String[] { "Pid", "EntityMetadata", });
    internal_static_skywalking_v3_EBPFKubernetesProcessDownstream_descriptor =
      getDescriptor().getMessageTypes().get(8);
    internal_static_skywalking_v3_EBPFKubernetesProcessDownstream_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_skywalking_v3_EBPFKubernetesProcessDownstream_descriptor,
        new String[] { "Pid", "EntityMetadata", });
    internal_static_skywalking_v3_EBPFProcessPingPkgList_descriptor =
      getDescriptor().getMessageTypes().get(9);
    internal_static_skywalking_v3_EBPFProcessPingPkgList_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_skywalking_v3_EBPFProcessPingPkgList_descriptor,
        new String[] { "Processes", "EbpfAgentID", });
    internal_static_skywalking_v3_EBPFProcessPingPkg_descriptor =
      getDescriptor().getMessageTypes().get(10);
    internal_static_skywalking_v3_EBPFProcessPingPkg_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_skywalking_v3_EBPFProcessPingPkg_descriptor,
        new String[] { "EntityMetadata", "Properties", });
    org.apache.skywalking.apm.network.common.v3.Common.getDescriptor();
    org.apache.skywalking.apm.network.common.v3.CommandOuterClass.getDescriptor();
  }

  // @@protoc_insertion_point(outer_class_scope)
}
