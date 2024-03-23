// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: ebpf/accesslog.proto

package org.apache.skywalking.apm.network.ebpf.accesslog.v3;

/**
 * Protobuf enum {@code skywalking.v3.AccessLogConnectionTLSMode}
 */
public enum AccessLogConnectionTLSMode
    implements com.google.protobuf.ProtocolMessageEnum {
  /**
   * <code>Plain = 0;</code>
   */
  Plain(0),
  /**
   * <code>TLS = 1;</code>
   */
  TLS(1),
  UNRECOGNIZED(-1),
  ;

  /**
   * <code>Plain = 0;</code>
   */
  public static final int Plain_VALUE = 0;
  /**
   * <code>TLS = 1;</code>
   */
  public static final int TLS_VALUE = 1;


  public final int getNumber() {
    if (this == UNRECOGNIZED) {
      throw new IllegalArgumentException(
          "Can't get the number of an unknown enum value.");
    }
    return value;
  }

  /**
   * @param value The numeric wire value of the corresponding enum entry.
   * @return The enum associated with the given numeric wire value.
   * @deprecated Use {@link #forNumber(int)} instead.
   */
  @Deprecated
  public static AccessLogConnectionTLSMode valueOf(int value) {
    return forNumber(value);
  }

  /**
   * @param value The numeric wire value of the corresponding enum entry.
   * @return The enum associated with the given numeric wire value.
   */
  public static AccessLogConnectionTLSMode forNumber(int value) {
    switch (value) {
      case 0: return Plain;
      case 1: return TLS;
      default: return null;
    }
  }

  public static com.google.protobuf.Internal.EnumLiteMap<AccessLogConnectionTLSMode>
      internalGetValueMap() {
    return internalValueMap;
  }
  private static final com.google.protobuf.Internal.EnumLiteMap<
      AccessLogConnectionTLSMode> internalValueMap =
        new com.google.protobuf.Internal.EnumLiteMap<AccessLogConnectionTLSMode>() {
          public AccessLogConnectionTLSMode findValueByNumber(int number) {
            return AccessLogConnectionTLSMode.forNumber(number);
          }
        };

  public final com.google.protobuf.Descriptors.EnumValueDescriptor
      getValueDescriptor() {
    if (this == UNRECOGNIZED) {
      throw new IllegalStateException(
          "Can't get the descriptor of an unrecognized enum value.");
    }
    return getDescriptor().getValues().get(ordinal());
  }
  public final com.google.protobuf.Descriptors.EnumDescriptor
      getDescriptorForType() {
    return getDescriptor();
  }
  public static final com.google.protobuf.Descriptors.EnumDescriptor
      getDescriptor() {
    return Accesslog.getDescriptor().getEnumTypes().get(0);
  }

  private static final AccessLogConnectionTLSMode[] VALUES = values();

  public static AccessLogConnectionTLSMode valueOf(
      com.google.protobuf.Descriptors.EnumValueDescriptor desc) {
    if (desc.getType() != getDescriptor()) {
      throw new IllegalArgumentException(
        "EnumValueDescriptor is not for this type.");
    }
    if (desc.getIndex() == -1) {
      return UNRECOGNIZED;
    }
    return VALUES[desc.getIndex()];
  }

  private final int value;

  private AccessLogConnectionTLSMode(int value) {
    this.value = value;
  }

  // @@protoc_insertion_point(enum_scope:skywalking.v3.AccessLogConnectionTLSMode)
}

