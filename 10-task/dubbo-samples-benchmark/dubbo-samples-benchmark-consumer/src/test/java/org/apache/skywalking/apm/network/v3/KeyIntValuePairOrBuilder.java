// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: common/Common.proto

package org.apache.skywalking.apm.network.v3;

public interface KeyIntValuePairOrBuilder extends
    // @@protoc_insertion_point(interface_extends:skywalking.v3.KeyIntValuePair)
    com.google.protobuf.MessageOrBuilder {

  /**
   * <code>string key = 1;</code>
   * @return The key.
   */
  String getKey();
  /**
   * <code>string key = 1;</code>
   * @return The bytes for key.
   */
  com.google.protobuf.ByteString
      getKeyBytes();

  /**
   * <code>int64 value = 2;</code>
   * @return The value.
   */
  long getValue();
}