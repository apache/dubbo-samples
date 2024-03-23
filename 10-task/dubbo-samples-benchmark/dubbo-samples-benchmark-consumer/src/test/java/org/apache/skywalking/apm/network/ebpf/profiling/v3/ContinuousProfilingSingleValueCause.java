// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: ebpf/profiling/Continuous.proto

package org.apache.skywalking.apm.network.ebpf.profiling.v3;

/**
 * Protobuf type {@code skywalking.v3.ContinuousProfilingSingleValueCause}
 */
public final class ContinuousProfilingSingleValueCause extends
    com.google.protobuf.GeneratedMessageV3 implements
    // @@protoc_insertion_point(message_implements:skywalking.v3.ContinuousProfilingSingleValueCause)
    ContinuousProfilingSingleValueCauseOrBuilder {
private static final long serialVersionUID = 0L;
  // Use ContinuousProfilingSingleValueCause.newBuilder() to construct.
  private ContinuousProfilingSingleValueCause(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
    super(builder);
  }
  private ContinuousProfilingSingleValueCause() {
  }

  @Override
  @SuppressWarnings({"unused"})
  protected Object newInstance(
      UnusedPrivateParameter unused) {
    return new ContinuousProfilingSingleValueCause();
  }

  @Override
  public final com.google.protobuf.UnknownFieldSet
  getUnknownFields() {
    return this.unknownFields;
  }
  private ContinuousProfilingSingleValueCause(
      com.google.protobuf.CodedInputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    this();
    if (extensionRegistry == null) {
      throw new NullPointerException();
    }
    com.google.protobuf.UnknownFieldSet.Builder unknownFields =
        com.google.protobuf.UnknownFieldSet.newBuilder();
    try {
      boolean done = false;
      while (!done) {
        int tag = input.readTag();
        switch (tag) {
          case 0:
            done = true;
            break;
          case 9: {

            threshold_ = input.readDouble();
            break;
          }
          case 17: {

            current_ = input.readDouble();
            break;
          }
          default: {
            if (!parseUnknownField(
                input, unknownFields, extensionRegistry, tag)) {
              done = true;
            }
            break;
          }
        }
      }
    } catch (com.google.protobuf.InvalidProtocolBufferException e) {
      throw e.setUnfinishedMessage(this);
    } catch (java.io.IOException e) {
      throw new com.google.protobuf.InvalidProtocolBufferException(
          e).setUnfinishedMessage(this);
    } finally {
      this.unknownFields = unknownFields.build();
      makeExtensionsImmutable();
    }
  }
  public static final com.google.protobuf.Descriptors.Descriptor
      getDescriptor() {
    return Continuous.internal_static_skywalking_v3_ContinuousProfilingSingleValueCause_descriptor;
  }

  @Override
  protected FieldAccessorTable
      internalGetFieldAccessorTable() {
    return Continuous.internal_static_skywalking_v3_ContinuousProfilingSingleValueCause_fieldAccessorTable
        .ensureFieldAccessorsInitialized(
            ContinuousProfilingSingleValueCause.class, Builder.class);
  }

  public static final int THRESHOLD_FIELD_NUMBER = 1;
  private double threshold_;
  /**
   * <code>double threshold = 1;</code>
   * @return The threshold.
   */
  @Override
  public double getThreshold() {
    return threshold_;
  }

  public static final int CURRENT_FIELD_NUMBER = 2;
  private double current_;
  /**
   * <code>double current = 2;</code>
   * @return The current.
   */
  @Override
  public double getCurrent() {
    return current_;
  }

  private byte memoizedIsInitialized = -1;
  @Override
  public final boolean isInitialized() {
    byte isInitialized = memoizedIsInitialized;
    if (isInitialized == 1) return true;
    if (isInitialized == 0) return false;

    memoizedIsInitialized = 1;
    return true;
  }

  @Override
  public void writeTo(com.google.protobuf.CodedOutputStream output)
                      throws java.io.IOException {
    if (Double.doubleToRawLongBits(threshold_) != 0) {
      output.writeDouble(1, threshold_);
    }
    if (Double.doubleToRawLongBits(current_) != 0) {
      output.writeDouble(2, current_);
    }
    unknownFields.writeTo(output);
  }

  @Override
  public int getSerializedSize() {
    int size = memoizedSize;
    if (size != -1) return size;

    size = 0;
    if (Double.doubleToRawLongBits(threshold_) != 0) {
      size += com.google.protobuf.CodedOutputStream
        .computeDoubleSize(1, threshold_);
    }
    if (Double.doubleToRawLongBits(current_) != 0) {
      size += com.google.protobuf.CodedOutputStream
        .computeDoubleSize(2, current_);
    }
    size += unknownFields.getSerializedSize();
    memoizedSize = size;
    return size;
  }

  @Override
  public boolean equals(final Object obj) {
    if (obj == this) {
     return true;
    }
    if (!(obj instanceof ContinuousProfilingSingleValueCause)) {
      return super.equals(obj);
    }
    ContinuousProfilingSingleValueCause other = (ContinuousProfilingSingleValueCause) obj;

    if (Double.doubleToLongBits(getThreshold())
        != Double.doubleToLongBits(
            other.getThreshold())) return false;
    if (Double.doubleToLongBits(getCurrent())
        != Double.doubleToLongBits(
            other.getCurrent())) return false;
    if (!unknownFields.equals(other.unknownFields)) return false;
    return true;
  }

  @Override
  public int hashCode() {
    if (memoizedHashCode != 0) {
      return memoizedHashCode;
    }
    int hash = 41;
    hash = (19 * hash) + getDescriptor().hashCode();
    hash = (37 * hash) + THRESHOLD_FIELD_NUMBER;
    hash = (53 * hash) + com.google.protobuf.Internal.hashLong(
        Double.doubleToLongBits(getThreshold()));
    hash = (37 * hash) + CURRENT_FIELD_NUMBER;
    hash = (53 * hash) + com.google.protobuf.Internal.hashLong(
        Double.doubleToLongBits(getCurrent()));
    hash = (29 * hash) + unknownFields.hashCode();
    memoizedHashCode = hash;
    return hash;
  }

  public static ContinuousProfilingSingleValueCause parseFrom(
      java.nio.ByteBuffer data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static ContinuousProfilingSingleValueCause parseFrom(
      java.nio.ByteBuffer data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static ContinuousProfilingSingleValueCause parseFrom(
      com.google.protobuf.ByteString data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static ContinuousProfilingSingleValueCause parseFrom(
      com.google.protobuf.ByteString data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static ContinuousProfilingSingleValueCause parseFrom(byte[] data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static ContinuousProfilingSingleValueCause parseFrom(
      byte[] data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static ContinuousProfilingSingleValueCause parseFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static ContinuousProfilingSingleValueCause parseFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input, extensionRegistry);
  }
  public static ContinuousProfilingSingleValueCause parseDelimitedFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input);
  }
  public static ContinuousProfilingSingleValueCause parseDelimitedFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
  }
  public static ContinuousProfilingSingleValueCause parseFrom(
      com.google.protobuf.CodedInputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static ContinuousProfilingSingleValueCause parseFrom(
      com.google.protobuf.CodedInputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input, extensionRegistry);
  }

  @Override
  public Builder newBuilderForType() { return newBuilder(); }
  public static Builder newBuilder() {
    return DEFAULT_INSTANCE.toBuilder();
  }
  public static Builder newBuilder(ContinuousProfilingSingleValueCause prototype) {
    return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
  }
  @Override
  public Builder toBuilder() {
    return this == DEFAULT_INSTANCE
        ? new Builder() : new Builder().mergeFrom(this);
  }

  @Override
  protected Builder newBuilderForType(
      BuilderParent parent) {
    Builder builder = new Builder(parent);
    return builder;
  }
  /**
   * Protobuf type {@code skywalking.v3.ContinuousProfilingSingleValueCause}
   */
  public static final class Builder extends
      com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements
      // @@protoc_insertion_point(builder_implements:skywalking.v3.ContinuousProfilingSingleValueCause)
      ContinuousProfilingSingleValueCauseOrBuilder {
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return Continuous.internal_static_skywalking_v3_ContinuousProfilingSingleValueCause_descriptor;
    }

    @Override
    protected FieldAccessorTable
        internalGetFieldAccessorTable() {
      return Continuous.internal_static_skywalking_v3_ContinuousProfilingSingleValueCause_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              ContinuousProfilingSingleValueCause.class, Builder.class);
    }

    // Construct using org.apache.skywalking.apm.network.ebpf.profiling.v3.ContinuousProfilingSingleValueCause.newBuilder()
    private Builder() {
      maybeForceBuilderInitialization();
    }

    private Builder(
        BuilderParent parent) {
      super(parent);
      maybeForceBuilderInitialization();
    }
    private void maybeForceBuilderInitialization() {
      if (com.google.protobuf.GeneratedMessageV3
              .alwaysUseFieldBuilders) {
      }
    }
    @Override
    public Builder clear() {
      super.clear();
      threshold_ = 0D;

      current_ = 0D;

      return this;
    }

    @Override
    public com.google.protobuf.Descriptors.Descriptor
        getDescriptorForType() {
      return Continuous.internal_static_skywalking_v3_ContinuousProfilingSingleValueCause_descriptor;
    }

    @Override
    public ContinuousProfilingSingleValueCause getDefaultInstanceForType() {
      return ContinuousProfilingSingleValueCause.getDefaultInstance();
    }

    @Override
    public ContinuousProfilingSingleValueCause build() {
      ContinuousProfilingSingleValueCause result = buildPartial();
      if (!result.isInitialized()) {
        throw newUninitializedMessageException(result);
      }
      return result;
    }

    @Override
    public ContinuousProfilingSingleValueCause buildPartial() {
      ContinuousProfilingSingleValueCause result = new ContinuousProfilingSingleValueCause(this);
      result.threshold_ = threshold_;
      result.current_ = current_;
      onBuilt();
      return result;
    }

    @Override
    public Builder clone() {
      return super.clone();
    }
    @Override
    public Builder setField(
        com.google.protobuf.Descriptors.FieldDescriptor field,
        Object value) {
      return super.setField(field, value);
    }
    @Override
    public Builder clearField(
        com.google.protobuf.Descriptors.FieldDescriptor field) {
      return super.clearField(field);
    }
    @Override
    public Builder clearOneof(
        com.google.protobuf.Descriptors.OneofDescriptor oneof) {
      return super.clearOneof(oneof);
    }
    @Override
    public Builder setRepeatedField(
        com.google.protobuf.Descriptors.FieldDescriptor field,
        int index, Object value) {
      return super.setRepeatedField(field, index, value);
    }
    @Override
    public Builder addRepeatedField(
        com.google.protobuf.Descriptors.FieldDescriptor field,
        Object value) {
      return super.addRepeatedField(field, value);
    }
    @Override
    public Builder mergeFrom(com.google.protobuf.Message other) {
      if (other instanceof ContinuousProfilingSingleValueCause) {
        return mergeFrom((ContinuousProfilingSingleValueCause)other);
      } else {
        super.mergeFrom(other);
        return this;
      }
    }

    public Builder mergeFrom(ContinuousProfilingSingleValueCause other) {
      if (other == ContinuousProfilingSingleValueCause.getDefaultInstance()) return this;
      if (other.getThreshold() != 0D) {
        setThreshold(other.getThreshold());
      }
      if (other.getCurrent() != 0D) {
        setCurrent(other.getCurrent());
      }
      this.mergeUnknownFields(other.unknownFields);
      onChanged();
      return this;
    }

    @Override
    public final boolean isInitialized() {
      return true;
    }

    @Override
    public Builder mergeFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      ContinuousProfilingSingleValueCause parsedMessage = null;
      try {
        parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
      } catch (com.google.protobuf.InvalidProtocolBufferException e) {
        parsedMessage = (ContinuousProfilingSingleValueCause) e.getUnfinishedMessage();
        throw e.unwrapIOException();
      } finally {
        if (parsedMessage != null) {
          mergeFrom(parsedMessage);
        }
      }
      return this;
    }

    private double threshold_ ;
    /**
     * <code>double threshold = 1;</code>
     * @return The threshold.
     */
    @Override
    public double getThreshold() {
      return threshold_;
    }
    /**
     * <code>double threshold = 1;</code>
     * @param value The threshold to set.
     * @return This builder for chaining.
     */
    public Builder setThreshold(double value) {

      threshold_ = value;
      onChanged();
      return this;
    }
    /**
     * <code>double threshold = 1;</code>
     * @return This builder for chaining.
     */
    public Builder clearThreshold() {

      threshold_ = 0D;
      onChanged();
      return this;
    }

    private double current_ ;
    /**
     * <code>double current = 2;</code>
     * @return The current.
     */
    @Override
    public double getCurrent() {
      return current_;
    }
    /**
     * <code>double current = 2;</code>
     * @param value The current to set.
     * @return This builder for chaining.
     */
    public Builder setCurrent(double value) {

      current_ = value;
      onChanged();
      return this;
    }
    /**
     * <code>double current = 2;</code>
     * @return This builder for chaining.
     */
    public Builder clearCurrent() {

      current_ = 0D;
      onChanged();
      return this;
    }
    @Override
    public final Builder setUnknownFields(
        final com.google.protobuf.UnknownFieldSet unknownFields) {
      return super.setUnknownFields(unknownFields);
    }

    @Override
    public final Builder mergeUnknownFields(
        final com.google.protobuf.UnknownFieldSet unknownFields) {
      return super.mergeUnknownFields(unknownFields);
    }


    // @@protoc_insertion_point(builder_scope:skywalking.v3.ContinuousProfilingSingleValueCause)
  }

  // @@protoc_insertion_point(class_scope:skywalking.v3.ContinuousProfilingSingleValueCause)
  private static final ContinuousProfilingSingleValueCause DEFAULT_INSTANCE;
  static {
    DEFAULT_INSTANCE = new ContinuousProfilingSingleValueCause();
  }

  public static ContinuousProfilingSingleValueCause getDefaultInstance() {
    return DEFAULT_INSTANCE;
  }

  private static final com.google.protobuf.Parser<ContinuousProfilingSingleValueCause>
      PARSER = new com.google.protobuf.AbstractParser<ContinuousProfilingSingleValueCause>() {
    @Override
    public ContinuousProfilingSingleValueCause parsePartialFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return new ContinuousProfilingSingleValueCause(input, extensionRegistry);
    }
  };

  public static com.google.protobuf.Parser<ContinuousProfilingSingleValueCause> parser() {
    return PARSER;
  }

  @Override
  public com.google.protobuf.Parser<ContinuousProfilingSingleValueCause> getParserForType() {
    return PARSER;
  }

  @Override
  public ContinuousProfilingSingleValueCause getDefaultInstanceForType() {
    return DEFAULT_INSTANCE;
  }

}

