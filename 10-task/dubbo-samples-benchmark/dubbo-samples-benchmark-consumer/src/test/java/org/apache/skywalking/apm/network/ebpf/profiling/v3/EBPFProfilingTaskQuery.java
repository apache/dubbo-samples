// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: ebpf/profiling/Profile.proto

package org.apache.skywalking.apm.network.ebpf.profiling.v3;

/**
 * Protobuf type {@code skywalking.v3.EBPFProfilingTaskQuery}
 */
public final class EBPFProfilingTaskQuery extends
    com.google.protobuf.GeneratedMessageV3 implements
    // @@protoc_insertion_point(message_implements:skywalking.v3.EBPFProfilingTaskQuery)
    EBPFProfilingTaskQueryOrBuilder {
private static final long serialVersionUID = 0L;
  // Use EBPFProfilingTaskQuery.newBuilder() to construct.
  private EBPFProfilingTaskQuery(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
    super(builder);
  }
  private EBPFProfilingTaskQuery() {
    roverInstanceId_ = "";
  }

  @Override
  @SuppressWarnings({"unused"})
  protected Object newInstance(
      UnusedPrivateParameter unused) {
    return new EBPFProfilingTaskQuery();
  }

  @Override
  public final com.google.protobuf.UnknownFieldSet
  getUnknownFields() {
    return this.unknownFields;
  }
  private EBPFProfilingTaskQuery(
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
          case 10: {
            String s = input.readStringRequireUtf8();

            roverInstanceId_ = s;
            break;
          }
          case 16: {

            latestUpdateTime_ = input.readInt64();
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
    return Profile.internal_static_skywalking_v3_EBPFProfilingTaskQuery_descriptor;
  }

  @Override
  protected FieldAccessorTable
      internalGetFieldAccessorTable() {
    return Profile.internal_static_skywalking_v3_EBPFProfilingTaskQuery_fieldAccessorTable
        .ensureFieldAccessorsInitialized(
            EBPFProfilingTaskQuery.class, Builder.class);
  }

  public static final int ROVERINSTANCEID_FIELD_NUMBER = 1;
  private volatile Object roverInstanceId_;
  /**
   * <pre>
   * rover instance id
   * </pre>
   *
   * <code>string roverInstanceId = 1;</code>
   * @return The roverInstanceId.
   */
  @Override
  public String getRoverInstanceId() {
    Object ref = roverInstanceId_;
    if (ref instanceof String) {
      return (String) ref;
    } else {
      com.google.protobuf.ByteString bs =
          (com.google.protobuf.ByteString) ref;
      String s = bs.toStringUtf8();
      roverInstanceId_ = s;
      return s;
    }
  }
  /**
   * <pre>
   * rover instance id
   * </pre>
   *
   * <code>string roverInstanceId = 1;</code>
   * @return The bytes for roverInstanceId.
   */
  @Override
  public com.google.protobuf.ByteString
      getRoverInstanceIdBytes() {
    Object ref = roverInstanceId_;
    if (ref instanceof String) {
      com.google.protobuf.ByteString b =
          com.google.protobuf.ByteString.copyFromUtf8(
              (String) ref);
      roverInstanceId_ = b;
      return b;
    } else {
      return (com.google.protobuf.ByteString) ref;
    }
  }

  public static final int LATESTUPDATETIME_FIELD_NUMBER = 2;
  private long latestUpdateTime_;
  /**
   * <pre>
   * latest task update time
   * </pre>
   *
   * <code>int64 latestUpdateTime = 2;</code>
   * @return The latestUpdateTime.
   */
  @Override
  public long getLatestUpdateTime() {
    return latestUpdateTime_;
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
    if (!com.google.protobuf.GeneratedMessageV3.isStringEmpty(roverInstanceId_)) {
      com.google.protobuf.GeneratedMessageV3.writeString(output, 1, roverInstanceId_);
    }
    if (latestUpdateTime_ != 0L) {
      output.writeInt64(2, latestUpdateTime_);
    }
    unknownFields.writeTo(output);
  }

  @Override
  public int getSerializedSize() {
    int size = memoizedSize;
    if (size != -1) return size;

    size = 0;
    if (!com.google.protobuf.GeneratedMessageV3.isStringEmpty(roverInstanceId_)) {
      size += com.google.protobuf.GeneratedMessageV3.computeStringSize(1, roverInstanceId_);
    }
    if (latestUpdateTime_ != 0L) {
      size += com.google.protobuf.CodedOutputStream
        .computeInt64Size(2, latestUpdateTime_);
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
    if (!(obj instanceof EBPFProfilingTaskQuery)) {
      return super.equals(obj);
    }
    EBPFProfilingTaskQuery other = (EBPFProfilingTaskQuery) obj;

    if (!getRoverInstanceId()
        .equals(other.getRoverInstanceId())) return false;
    if (getLatestUpdateTime()
        != other.getLatestUpdateTime()) return false;
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
    hash = (37 * hash) + ROVERINSTANCEID_FIELD_NUMBER;
    hash = (53 * hash) + getRoverInstanceId().hashCode();
    hash = (37 * hash) + LATESTUPDATETIME_FIELD_NUMBER;
    hash = (53 * hash) + com.google.protobuf.Internal.hashLong(
        getLatestUpdateTime());
    hash = (29 * hash) + unknownFields.hashCode();
    memoizedHashCode = hash;
    return hash;
  }

  public static EBPFProfilingTaskQuery parseFrom(
      java.nio.ByteBuffer data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static EBPFProfilingTaskQuery parseFrom(
      java.nio.ByteBuffer data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static EBPFProfilingTaskQuery parseFrom(
      com.google.protobuf.ByteString data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static EBPFProfilingTaskQuery parseFrom(
      com.google.protobuf.ByteString data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static EBPFProfilingTaskQuery parseFrom(byte[] data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static EBPFProfilingTaskQuery parseFrom(
      byte[] data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static EBPFProfilingTaskQuery parseFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static EBPFProfilingTaskQuery parseFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input, extensionRegistry);
  }
  public static EBPFProfilingTaskQuery parseDelimitedFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input);
  }
  public static EBPFProfilingTaskQuery parseDelimitedFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
  }
  public static EBPFProfilingTaskQuery parseFrom(
      com.google.protobuf.CodedInputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static EBPFProfilingTaskQuery parseFrom(
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
  public static Builder newBuilder(EBPFProfilingTaskQuery prototype) {
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
   * Protobuf type {@code skywalking.v3.EBPFProfilingTaskQuery}
   */
  public static final class Builder extends
      com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements
      // @@protoc_insertion_point(builder_implements:skywalking.v3.EBPFProfilingTaskQuery)
      EBPFProfilingTaskQueryOrBuilder {
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return Profile.internal_static_skywalking_v3_EBPFProfilingTaskQuery_descriptor;
    }

    @Override
    protected FieldAccessorTable
        internalGetFieldAccessorTable() {
      return Profile.internal_static_skywalking_v3_EBPFProfilingTaskQuery_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              EBPFProfilingTaskQuery.class, Builder.class);
    }

    // Construct using org.apache.skywalking.apm.network.ebpf.profiling.v3.EBPFProfilingTaskQuery.newBuilder()
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
      roverInstanceId_ = "";

      latestUpdateTime_ = 0L;

      return this;
    }

    @Override
    public com.google.protobuf.Descriptors.Descriptor
        getDescriptorForType() {
      return Profile.internal_static_skywalking_v3_EBPFProfilingTaskQuery_descriptor;
    }

    @Override
    public EBPFProfilingTaskQuery getDefaultInstanceForType() {
      return EBPFProfilingTaskQuery.getDefaultInstance();
    }

    @Override
    public EBPFProfilingTaskQuery build() {
      EBPFProfilingTaskQuery result = buildPartial();
      if (!result.isInitialized()) {
        throw newUninitializedMessageException(result);
      }
      return result;
    }

    @Override
    public EBPFProfilingTaskQuery buildPartial() {
      EBPFProfilingTaskQuery result = new EBPFProfilingTaskQuery(this);
      result.roverInstanceId_ = roverInstanceId_;
      result.latestUpdateTime_ = latestUpdateTime_;
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
      if (other instanceof EBPFProfilingTaskQuery) {
        return mergeFrom((EBPFProfilingTaskQuery)other);
      } else {
        super.mergeFrom(other);
        return this;
      }
    }

    public Builder mergeFrom(EBPFProfilingTaskQuery other) {
      if (other == EBPFProfilingTaskQuery.getDefaultInstance()) return this;
      if (!other.getRoverInstanceId().isEmpty()) {
        roverInstanceId_ = other.roverInstanceId_;
        onChanged();
      }
      if (other.getLatestUpdateTime() != 0L) {
        setLatestUpdateTime(other.getLatestUpdateTime());
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
      EBPFProfilingTaskQuery parsedMessage = null;
      try {
        parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
      } catch (com.google.protobuf.InvalidProtocolBufferException e) {
        parsedMessage = (EBPFProfilingTaskQuery) e.getUnfinishedMessage();
        throw e.unwrapIOException();
      } finally {
        if (parsedMessage != null) {
          mergeFrom(parsedMessage);
        }
      }
      return this;
    }

    private Object roverInstanceId_ = "";
    /**
     * <pre>
     * rover instance id
     * </pre>
     *
     * <code>string roverInstanceId = 1;</code>
     * @return The roverInstanceId.
     */
    public String getRoverInstanceId() {
      Object ref = roverInstanceId_;
      if (!(ref instanceof String)) {
        com.google.protobuf.ByteString bs =
            (com.google.protobuf.ByteString) ref;
        String s = bs.toStringUtf8();
        roverInstanceId_ = s;
        return s;
      } else {
        return (String) ref;
      }
    }
    /**
     * <pre>
     * rover instance id
     * </pre>
     *
     * <code>string roverInstanceId = 1;</code>
     * @return The bytes for roverInstanceId.
     */
    public com.google.protobuf.ByteString
        getRoverInstanceIdBytes() {
      Object ref = roverInstanceId_;
      if (ref instanceof String) {
        com.google.protobuf.ByteString b =
            com.google.protobuf.ByteString.copyFromUtf8(
                (String) ref);
        roverInstanceId_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }
    /**
     * <pre>
     * rover instance id
     * </pre>
     *
     * <code>string roverInstanceId = 1;</code>
     * @param value The roverInstanceId to set.
     * @return This builder for chaining.
     */
    public Builder setRoverInstanceId(
        String value) {
      if (value == null) {
    throw new NullPointerException();
  }

      roverInstanceId_ = value;
      onChanged();
      return this;
    }
    /**
     * <pre>
     * rover instance id
     * </pre>
     *
     * <code>string roverInstanceId = 1;</code>
     * @return This builder for chaining.
     */
    public Builder clearRoverInstanceId() {

      roverInstanceId_ = getDefaultInstance().getRoverInstanceId();
      onChanged();
      return this;
    }
    /**
     * <pre>
     * rover instance id
     * </pre>
     *
     * <code>string roverInstanceId = 1;</code>
     * @param value The bytes for roverInstanceId to set.
     * @return This builder for chaining.
     */
    public Builder setRoverInstanceIdBytes(
        com.google.protobuf.ByteString value) {
      if (value == null) {
    throw new NullPointerException();
  }
  checkByteStringIsUtf8(value);

      roverInstanceId_ = value;
      onChanged();
      return this;
    }

    private long latestUpdateTime_ ;
    /**
     * <pre>
     * latest task update time
     * </pre>
     *
     * <code>int64 latestUpdateTime = 2;</code>
     * @return The latestUpdateTime.
     */
    @Override
    public long getLatestUpdateTime() {
      return latestUpdateTime_;
    }
    /**
     * <pre>
     * latest task update time
     * </pre>
     *
     * <code>int64 latestUpdateTime = 2;</code>
     * @param value The latestUpdateTime to set.
     * @return This builder for chaining.
     */
    public Builder setLatestUpdateTime(long value) {

      latestUpdateTime_ = value;
      onChanged();
      return this;
    }
    /**
     * <pre>
     * latest task update time
     * </pre>
     *
     * <code>int64 latestUpdateTime = 2;</code>
     * @return This builder for chaining.
     */
    public Builder clearLatestUpdateTime() {

      latestUpdateTime_ = 0L;
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


    // @@protoc_insertion_point(builder_scope:skywalking.v3.EBPFProfilingTaskQuery)
  }

  // @@protoc_insertion_point(class_scope:skywalking.v3.EBPFProfilingTaskQuery)
  private static final EBPFProfilingTaskQuery DEFAULT_INSTANCE;
  static {
    DEFAULT_INSTANCE = new EBPFProfilingTaskQuery();
  }

  public static EBPFProfilingTaskQuery getDefaultInstance() {
    return DEFAULT_INSTANCE;
  }

  private static final com.google.protobuf.Parser<EBPFProfilingTaskQuery>
      PARSER = new com.google.protobuf.AbstractParser<EBPFProfilingTaskQuery>() {
    @Override
    public EBPFProfilingTaskQuery parsePartialFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return new EBPFProfilingTaskQuery(input, extensionRegistry);
    }
  };

  public static com.google.protobuf.Parser<EBPFProfilingTaskQuery> parser() {
    return PARSER;
  }

  @Override
  public com.google.protobuf.Parser<EBPFProfilingTaskQuery> getParserForType() {
    return PARSER;
  }

  @Override
  public EBPFProfilingTaskQuery getDefaultInstanceForType() {
    return DEFAULT_INSTANCE;
  }

}
