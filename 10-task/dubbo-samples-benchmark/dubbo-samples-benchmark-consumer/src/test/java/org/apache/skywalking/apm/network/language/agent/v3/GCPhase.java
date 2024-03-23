// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: language-agent/JVMMetric.proto

package org.apache.skywalking.apm.network.language.agent.v3;

/**
 * Protobuf enum {@code skywalking.v3.GCPhase}
 */
public enum GCPhase
    implements com.google.protobuf.ProtocolMessageEnum {
  /**
   * <code>NEW = 0;</code>
   */
  NEW(0),
  /**
   * <code>OLD = 1;</code>
   */
  OLD(1),
  /**
   * <pre>
   * The type of GC doesn't have new and old phases, like Z Garbage Collector (ZGC)
   * </pre>
   *
   * <code>NORMAL = 2;</code>
   */
  NORMAL(2),
  UNRECOGNIZED(-1),
  ;

  /**
   * <code>NEW = 0;</code>
   */
  public static final int NEW_VALUE = 0;
  /**
   * <code>OLD = 1;</code>
   */
  public static final int OLD_VALUE = 1;
  /**
   * <pre>
   * The type of GC doesn't have new and old phases, like Z Garbage Collector (ZGC)
   * </pre>
   *
   * <code>NORMAL = 2;</code>
   */
  public static final int NORMAL_VALUE = 2;


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
  public static GCPhase valueOf(int value) {
    return forNumber(value);
  }

  /**
   * @param value The numeric wire value of the corresponding enum entry.
   * @return The enum associated with the given numeric wire value.
   */
  public static GCPhase forNumber(int value) {
    switch (value) {
      case 0: return NEW;
      case 1: return OLD;
      case 2: return NORMAL;
      default: return null;
    }
  }

  public static com.google.protobuf.Internal.EnumLiteMap<GCPhase>
      internalGetValueMap() {
    return internalValueMap;
  }
  private static final com.google.protobuf.Internal.EnumLiteMap<
      GCPhase> internalValueMap =
        new com.google.protobuf.Internal.EnumLiteMap<GCPhase>() {
          public GCPhase findValueByNumber(int number) {
            return GCPhase.forNumber(number);
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
    return JVMMetricOuterClass.getDescriptor().getEnumTypes().get(1);
  }

  private static final GCPhase[] VALUES = values();

  public static GCPhase valueOf(
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

  private GCPhase(int value) {
    this.value = value;
  }

  // @@protoc_insertion_point(enum_scope:skywalking.v3.GCPhase)
}

