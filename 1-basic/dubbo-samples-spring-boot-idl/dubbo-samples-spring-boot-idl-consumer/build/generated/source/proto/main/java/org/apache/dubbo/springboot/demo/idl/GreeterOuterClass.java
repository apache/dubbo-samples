// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: greeter.proto

package org.apache.dubbo.springboot.demo.idl;

public final class GreeterOuterClass {
  private GreeterOuterClass() {}
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistryLite registry) {
  }

  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistry registry) {
    registerAllExtensions(
        (com.google.protobuf.ExtensionRegistryLite) registry);
  }
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_org_apache_dubbo_springboot_demo_idl_GreeterRequest_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_org_apache_dubbo_springboot_demo_idl_GreeterRequest_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_org_apache_dubbo_springboot_demo_idl_GreeterReply_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_org_apache_dubbo_springboot_demo_idl_GreeterReply_fieldAccessorTable;

  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static  com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
    java.lang.String[] descriptorData = {
      "\n\rgreeter.proto\022$org.apache.dubbo.spring" +
      "boot.demo.idl\"\036\n\016GreeterRequest\022\014\n\004name\030" +
      "\001 \001(\t\"\037\n\014GreeterReply\022\017\n\007message\030\001 \001(\t2|" +
      "\n\007Greeter\022q\n\005greet\0224.org.apache.dubbo.sp" +
      "ringboot.demo.idl.GreeterRequest\0322.org.a" +
      "pache.dubbo.springboot.demo.idl.GreeterR" +
      "eplyB\002P\001b\006proto3"
    };
    descriptor = com.google.protobuf.Descriptors.FileDescriptor
      .internalBuildGeneratedFileFrom(descriptorData,
        new com.google.protobuf.Descriptors.FileDescriptor[] {
        });
    internal_static_org_apache_dubbo_springboot_demo_idl_GreeterRequest_descriptor =
      getDescriptor().getMessageTypes().get(0);
    internal_static_org_apache_dubbo_springboot_demo_idl_GreeterRequest_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_org_apache_dubbo_springboot_demo_idl_GreeterRequest_descriptor,
        new java.lang.String[] { "Name", });
    internal_static_org_apache_dubbo_springboot_demo_idl_GreeterReply_descriptor =
      getDescriptor().getMessageTypes().get(1);
    internal_static_org_apache_dubbo_springboot_demo_idl_GreeterReply_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_org_apache_dubbo_springboot_demo_idl_GreeterReply_descriptor,
        new java.lang.String[] { "Message", });
  }

  // @@protoc_insertion_point(outer_class_scope)
}
