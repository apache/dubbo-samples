// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: event/Event.proto

package org.apache.skywalking.apm.network.common.v3;

public interface SourceOrBuilder extends
    // @@protoc_insertion_point(interface_extends:skywalking.v3.Source)
    com.google.protobuf.MessageOrBuilder {

  /**
   * <code>string service = 1;</code>
   * @return The service.
   */
  String getService();
  /**
   * <code>string service = 1;</code>
   * @return The bytes for service.
   */
  com.google.protobuf.ByteString
      getServiceBytes();

  /**
   * <code>string serviceInstance = 2;</code>
   * @return The serviceInstance.
   */
  String getServiceInstance();
  /**
   * <code>string serviceInstance = 2;</code>
   * @return The bytes for serviceInstance.
   */
  com.google.protobuf.ByteString
      getServiceInstanceBytes();

  /**
   * <code>string endpoint = 3;</code>
   * @return The endpoint.
   */
  String getEndpoint();
  /**
   * <code>string endpoint = 3;</code>
   * @return The bytes for endpoint.
   */
  com.google.protobuf.ByteString
      getEndpointBytes();
}