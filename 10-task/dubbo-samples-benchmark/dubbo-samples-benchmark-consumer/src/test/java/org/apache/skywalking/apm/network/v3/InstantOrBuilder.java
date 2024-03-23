// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: common/Common.proto

package org.apache.skywalking.apm.network.v3;

public interface InstantOrBuilder extends
    // @@protoc_insertion_point(interface_extends:skywalking.v3.Instant)
    com.google.protobuf.MessageOrBuilder {

  /**
   * <pre>
   * The number of seconds from the epoch of 1970-01-01T00:00:00Z.
   * </pre>
   *
   * <code>int64 seconds = 1;</code>
   * @return The seconds.
   */
  long getSeconds();

  /**
   * <pre>
   * The number of nanoseconds, later along the time-line, from the seconds field.
   * This is always positive, and never exceeds 999,999,999.
   * </pre>
   *
   * <code>int32 nanos = 2;</code>
   * @return The nanos.
   */
  int getNanos();
}