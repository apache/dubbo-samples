// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: ebpf/accesslog.proto

package org.apache.skywalking.apm.network.ebpf.accesslog.v3;

/**
 * Protobuf type {@code skywalking.v3.AccessLogKernelReadL3Metrics}
 */
public final class AccessLogKernelReadL3Metrics extends
    com.google.protobuf.GeneratedMessageV3 implements
    // @@protoc_insertion_point(message_implements:skywalking.v3.AccessLogKernelReadL3Metrics)
    AccessLogKernelReadL3MetricsOrBuilder {
private static final long serialVersionUID = 0L;
  // Use AccessLogKernelReadL3Metrics.newBuilder() to construct.
  private AccessLogKernelReadL3Metrics(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
    super(builder);
  }
  private AccessLogKernelReadL3Metrics() {
  }

  @Override
  @SuppressWarnings({"unused"})
  protected Object newInstance(
      UnusedPrivateParameter unused) {
    return new AccessLogKernelReadL3Metrics();
  }

  @Override
  public final com.google.protobuf.UnknownFieldSet
  getUnknownFields() {
    return this.unknownFields;
  }
  private AccessLogKernelReadL3Metrics(
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
          case 8: {

            totalDuration_ = input.readUInt64();
            break;
          }
          case 16: {

            totalRecvDuration_ = input.readUInt64();
            break;
          }
          case 24: {

            totalLocalDuration_ = input.readUInt64();
            break;
          }
          case 32: {

            totalNetFilterCount_ = input.readUInt64();
            break;
          }
          case 40: {

            totalNetFilterDuration_ = input.readUInt64();
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
    return Accesslog.internal_static_skywalking_v3_AccessLogKernelReadL3Metrics_descriptor;
  }

  @Override
  protected FieldAccessorTable
      internalGetFieldAccessorTable() {
    return Accesslog.internal_static_skywalking_v3_AccessLogKernelReadL3Metrics_fieldAccessorTable
        .ensureFieldAccessorsInitialized(
            AccessLogKernelReadL3Metrics.class, Builder.class);
  }

  public static final int TOTALDURATION_FIELD_NUMBER = 1;
  private long totalDuration_;
  /**
   * <code>uint64 totalDuration = 1;</code>
   * @return The totalDuration.
   */
  @Override
  public long getTotalDuration() {
    return totalDuration_;
  }

  public static final int TOTALRECVDURATION_FIELD_NUMBER = 2;
  private long totalRecvDuration_;
  /**
   * <pre>
   * total local receive use duration(nanoseconds) -&gt; kernel: ip_rcv_finish - ip_rcv
   * </pre>
   *
   * <code>uint64 totalRecvDuration = 2;</code>
   * @return The totalRecvDuration.
   */
  @Override
  public long getTotalRecvDuration() {
    return totalRecvDuration_;
  }

  public static final int TOTALLOCALDURATION_FIELD_NUMBER = 3;
  private long totalLocalDuration_;
  /**
   * <pre>
   * total local use duration(nanoseconds) -&gt; kernel: ip_local_deliver_finish - ip_local_deliver
   * </pre>
   *
   * <code>uint64 totalLocalDuration = 3;</code>
   * @return The totalLocalDuration.
   */
  @Override
  public long getTotalLocalDuration() {
    return totalLocalDuration_;
  }

  public static final int TOTALNETFILTERCOUNT_FIELD_NUMBER = 4;
  private long totalNetFilterCount_;
  /**
   * <pre>
   * total netfiltering count and duration(nanosecond) -&gt; kernel: nf_hook
   * </pre>
   *
   * <code>uint64 totalNetFilterCount = 4;</code>
   * @return The totalNetFilterCount.
   */
  @Override
  public long getTotalNetFilterCount() {
    return totalNetFilterCount_;
  }

  public static final int TOTALNETFILTERDURATION_FIELD_NUMBER = 5;
  private long totalNetFilterDuration_;
  /**
   * <code>uint64 totalNetFilterDuration = 5;</code>
   * @return The totalNetFilterDuration.
   */
  @Override
  public long getTotalNetFilterDuration() {
    return totalNetFilterDuration_;
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
    if (totalDuration_ != 0L) {
      output.writeUInt64(1, totalDuration_);
    }
    if (totalRecvDuration_ != 0L) {
      output.writeUInt64(2, totalRecvDuration_);
    }
    if (totalLocalDuration_ != 0L) {
      output.writeUInt64(3, totalLocalDuration_);
    }
    if (totalNetFilterCount_ != 0L) {
      output.writeUInt64(4, totalNetFilterCount_);
    }
    if (totalNetFilterDuration_ != 0L) {
      output.writeUInt64(5, totalNetFilterDuration_);
    }
    unknownFields.writeTo(output);
  }

  @Override
  public int getSerializedSize() {
    int size = memoizedSize;
    if (size != -1) return size;

    size = 0;
    if (totalDuration_ != 0L) {
      size += com.google.protobuf.CodedOutputStream
        .computeUInt64Size(1, totalDuration_);
    }
    if (totalRecvDuration_ != 0L) {
      size += com.google.protobuf.CodedOutputStream
        .computeUInt64Size(2, totalRecvDuration_);
    }
    if (totalLocalDuration_ != 0L) {
      size += com.google.protobuf.CodedOutputStream
        .computeUInt64Size(3, totalLocalDuration_);
    }
    if (totalNetFilterCount_ != 0L) {
      size += com.google.protobuf.CodedOutputStream
        .computeUInt64Size(4, totalNetFilterCount_);
    }
    if (totalNetFilterDuration_ != 0L) {
      size += com.google.protobuf.CodedOutputStream
        .computeUInt64Size(5, totalNetFilterDuration_);
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
    if (!(obj instanceof AccessLogKernelReadL3Metrics)) {
      return super.equals(obj);
    }
    AccessLogKernelReadL3Metrics other = (AccessLogKernelReadL3Metrics) obj;

    if (getTotalDuration()
        != other.getTotalDuration()) return false;
    if (getTotalRecvDuration()
        != other.getTotalRecvDuration()) return false;
    if (getTotalLocalDuration()
        != other.getTotalLocalDuration()) return false;
    if (getTotalNetFilterCount()
        != other.getTotalNetFilterCount()) return false;
    if (getTotalNetFilterDuration()
        != other.getTotalNetFilterDuration()) return false;
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
    hash = (37 * hash) + TOTALDURATION_FIELD_NUMBER;
    hash = (53 * hash) + com.google.protobuf.Internal.hashLong(
        getTotalDuration());
    hash = (37 * hash) + TOTALRECVDURATION_FIELD_NUMBER;
    hash = (53 * hash) + com.google.protobuf.Internal.hashLong(
        getTotalRecvDuration());
    hash = (37 * hash) + TOTALLOCALDURATION_FIELD_NUMBER;
    hash = (53 * hash) + com.google.protobuf.Internal.hashLong(
        getTotalLocalDuration());
    hash = (37 * hash) + TOTALNETFILTERCOUNT_FIELD_NUMBER;
    hash = (53 * hash) + com.google.protobuf.Internal.hashLong(
        getTotalNetFilterCount());
    hash = (37 * hash) + TOTALNETFILTERDURATION_FIELD_NUMBER;
    hash = (53 * hash) + com.google.protobuf.Internal.hashLong(
        getTotalNetFilterDuration());
    hash = (29 * hash) + unknownFields.hashCode();
    memoizedHashCode = hash;
    return hash;
  }

  public static AccessLogKernelReadL3Metrics parseFrom(
      java.nio.ByteBuffer data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static AccessLogKernelReadL3Metrics parseFrom(
      java.nio.ByteBuffer data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static AccessLogKernelReadL3Metrics parseFrom(
      com.google.protobuf.ByteString data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static AccessLogKernelReadL3Metrics parseFrom(
      com.google.protobuf.ByteString data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static AccessLogKernelReadL3Metrics parseFrom(byte[] data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static AccessLogKernelReadL3Metrics parseFrom(
      byte[] data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static AccessLogKernelReadL3Metrics parseFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static AccessLogKernelReadL3Metrics parseFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input, extensionRegistry);
  }
  public static AccessLogKernelReadL3Metrics parseDelimitedFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input);
  }
  public static AccessLogKernelReadL3Metrics parseDelimitedFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
  }
  public static AccessLogKernelReadL3Metrics parseFrom(
      com.google.protobuf.CodedInputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static AccessLogKernelReadL3Metrics parseFrom(
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
  public static Builder newBuilder(AccessLogKernelReadL3Metrics prototype) {
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
   * Protobuf type {@code skywalking.v3.AccessLogKernelReadL3Metrics}
   */
  public static final class Builder extends
      com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements
      // @@protoc_insertion_point(builder_implements:skywalking.v3.AccessLogKernelReadL3Metrics)
      AccessLogKernelReadL3MetricsOrBuilder {
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return Accesslog.internal_static_skywalking_v3_AccessLogKernelReadL3Metrics_descriptor;
    }

    @Override
    protected FieldAccessorTable
        internalGetFieldAccessorTable() {
      return Accesslog.internal_static_skywalking_v3_AccessLogKernelReadL3Metrics_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              AccessLogKernelReadL3Metrics.class, Builder.class);
    }

    // Construct using org.apache.skywalking.apm.network.ebpf.accesslog.v3.AccessLogKernelReadL3Metrics.newBuilder()
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
      totalDuration_ = 0L;

      totalRecvDuration_ = 0L;

      totalLocalDuration_ = 0L;

      totalNetFilterCount_ = 0L;

      totalNetFilterDuration_ = 0L;

      return this;
    }

    @Override
    public com.google.protobuf.Descriptors.Descriptor
        getDescriptorForType() {
      return Accesslog.internal_static_skywalking_v3_AccessLogKernelReadL3Metrics_descriptor;
    }

    @Override
    public AccessLogKernelReadL3Metrics getDefaultInstanceForType() {
      return AccessLogKernelReadL3Metrics.getDefaultInstance();
    }

    @Override
    public AccessLogKernelReadL3Metrics build() {
      AccessLogKernelReadL3Metrics result = buildPartial();
      if (!result.isInitialized()) {
        throw newUninitializedMessageException(result);
      }
      return result;
    }

    @Override
    public AccessLogKernelReadL3Metrics buildPartial() {
      AccessLogKernelReadL3Metrics result = new AccessLogKernelReadL3Metrics(this);
      result.totalDuration_ = totalDuration_;
      result.totalRecvDuration_ = totalRecvDuration_;
      result.totalLocalDuration_ = totalLocalDuration_;
      result.totalNetFilterCount_ = totalNetFilterCount_;
      result.totalNetFilterDuration_ = totalNetFilterDuration_;
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
      if (other instanceof AccessLogKernelReadL3Metrics) {
        return mergeFrom((AccessLogKernelReadL3Metrics)other);
      } else {
        super.mergeFrom(other);
        return this;
      }
    }

    public Builder mergeFrom(AccessLogKernelReadL3Metrics other) {
      if (other == AccessLogKernelReadL3Metrics.getDefaultInstance()) return this;
      if (other.getTotalDuration() != 0L) {
        setTotalDuration(other.getTotalDuration());
      }
      if (other.getTotalRecvDuration() != 0L) {
        setTotalRecvDuration(other.getTotalRecvDuration());
      }
      if (other.getTotalLocalDuration() != 0L) {
        setTotalLocalDuration(other.getTotalLocalDuration());
      }
      if (other.getTotalNetFilterCount() != 0L) {
        setTotalNetFilterCount(other.getTotalNetFilterCount());
      }
      if (other.getTotalNetFilterDuration() != 0L) {
        setTotalNetFilterDuration(other.getTotalNetFilterDuration());
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
      AccessLogKernelReadL3Metrics parsedMessage = null;
      try {
        parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
      } catch (com.google.protobuf.InvalidProtocolBufferException e) {
        parsedMessage = (AccessLogKernelReadL3Metrics) e.getUnfinishedMessage();
        throw e.unwrapIOException();
      } finally {
        if (parsedMessage != null) {
          mergeFrom(parsedMessage);
        }
      }
      return this;
    }

    private long totalDuration_ ;
    /**
     * <code>uint64 totalDuration = 1;</code>
     * @return The totalDuration.
     */
    @Override
    public long getTotalDuration() {
      return totalDuration_;
    }
    /**
     * <code>uint64 totalDuration = 1;</code>
     * @param value The totalDuration to set.
     * @return This builder for chaining.
     */
    public Builder setTotalDuration(long value) {

      totalDuration_ = value;
      onChanged();
      return this;
    }
    /**
     * <code>uint64 totalDuration = 1;</code>
     * @return This builder for chaining.
     */
    public Builder clearTotalDuration() {

      totalDuration_ = 0L;
      onChanged();
      return this;
    }

    private long totalRecvDuration_ ;
    /**
     * <pre>
     * total local receive use duration(nanoseconds) -&gt; kernel: ip_rcv_finish - ip_rcv
     * </pre>
     *
     * <code>uint64 totalRecvDuration = 2;</code>
     * @return The totalRecvDuration.
     */
    @Override
    public long getTotalRecvDuration() {
      return totalRecvDuration_;
    }
    /**
     * <pre>
     * total local receive use duration(nanoseconds) -&gt; kernel: ip_rcv_finish - ip_rcv
     * </pre>
     *
     * <code>uint64 totalRecvDuration = 2;</code>
     * @param value The totalRecvDuration to set.
     * @return This builder for chaining.
     */
    public Builder setTotalRecvDuration(long value) {

      totalRecvDuration_ = value;
      onChanged();
      return this;
    }
    /**
     * <pre>
     * total local receive use duration(nanoseconds) -&gt; kernel: ip_rcv_finish - ip_rcv
     * </pre>
     *
     * <code>uint64 totalRecvDuration = 2;</code>
     * @return This builder for chaining.
     */
    public Builder clearTotalRecvDuration() {

      totalRecvDuration_ = 0L;
      onChanged();
      return this;
    }

    private long totalLocalDuration_ ;
    /**
     * <pre>
     * total local use duration(nanoseconds) -&gt; kernel: ip_local_deliver_finish - ip_local_deliver
     * </pre>
     *
     * <code>uint64 totalLocalDuration = 3;</code>
     * @return The totalLocalDuration.
     */
    @Override
    public long getTotalLocalDuration() {
      return totalLocalDuration_;
    }
    /**
     * <pre>
     * total local use duration(nanoseconds) -&gt; kernel: ip_local_deliver_finish - ip_local_deliver
     * </pre>
     *
     * <code>uint64 totalLocalDuration = 3;</code>
     * @param value The totalLocalDuration to set.
     * @return This builder for chaining.
     */
    public Builder setTotalLocalDuration(long value) {

      totalLocalDuration_ = value;
      onChanged();
      return this;
    }
    /**
     * <pre>
     * total local use duration(nanoseconds) -&gt; kernel: ip_local_deliver_finish - ip_local_deliver
     * </pre>
     *
     * <code>uint64 totalLocalDuration = 3;</code>
     * @return This builder for chaining.
     */
    public Builder clearTotalLocalDuration() {

      totalLocalDuration_ = 0L;
      onChanged();
      return this;
    }

    private long totalNetFilterCount_ ;
    /**
     * <pre>
     * total netfiltering count and duration(nanosecond) -&gt; kernel: nf_hook
     * </pre>
     *
     * <code>uint64 totalNetFilterCount = 4;</code>
     * @return The totalNetFilterCount.
     */
    @Override
    public long getTotalNetFilterCount() {
      return totalNetFilterCount_;
    }
    /**
     * <pre>
     * total netfiltering count and duration(nanosecond) -&gt; kernel: nf_hook
     * </pre>
     *
     * <code>uint64 totalNetFilterCount = 4;</code>
     * @param value The totalNetFilterCount to set.
     * @return This builder for chaining.
     */
    public Builder setTotalNetFilterCount(long value) {

      totalNetFilterCount_ = value;
      onChanged();
      return this;
    }
    /**
     * <pre>
     * total netfiltering count and duration(nanosecond) -&gt; kernel: nf_hook
     * </pre>
     *
     * <code>uint64 totalNetFilterCount = 4;</code>
     * @return This builder for chaining.
     */
    public Builder clearTotalNetFilterCount() {

      totalNetFilterCount_ = 0L;
      onChanged();
      return this;
    }

    private long totalNetFilterDuration_ ;
    /**
     * <code>uint64 totalNetFilterDuration = 5;</code>
     * @return The totalNetFilterDuration.
     */
    @Override
    public long getTotalNetFilterDuration() {
      return totalNetFilterDuration_;
    }
    /**
     * <code>uint64 totalNetFilterDuration = 5;</code>
     * @param value The totalNetFilterDuration to set.
     * @return This builder for chaining.
     */
    public Builder setTotalNetFilterDuration(long value) {

      totalNetFilterDuration_ = value;
      onChanged();
      return this;
    }
    /**
     * <code>uint64 totalNetFilterDuration = 5;</code>
     * @return This builder for chaining.
     */
    public Builder clearTotalNetFilterDuration() {

      totalNetFilterDuration_ = 0L;
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


    // @@protoc_insertion_point(builder_scope:skywalking.v3.AccessLogKernelReadL3Metrics)
  }

  // @@protoc_insertion_point(class_scope:skywalking.v3.AccessLogKernelReadL3Metrics)
  private static final AccessLogKernelReadL3Metrics DEFAULT_INSTANCE;
  static {
    DEFAULT_INSTANCE = new AccessLogKernelReadL3Metrics();
  }

  public static AccessLogKernelReadL3Metrics getDefaultInstance() {
    return DEFAULT_INSTANCE;
  }

  private static final com.google.protobuf.Parser<AccessLogKernelReadL3Metrics>
      PARSER = new com.google.protobuf.AbstractParser<AccessLogKernelReadL3Metrics>() {
    @Override
    public AccessLogKernelReadL3Metrics parsePartialFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return new AccessLogKernelReadL3Metrics(input, extensionRegistry);
    }
  };

  public static com.google.protobuf.Parser<AccessLogKernelReadL3Metrics> parser() {
    return PARSER;
  }

  @Override
  public com.google.protobuf.Parser<AccessLogKernelReadL3Metrics> getParserForType() {
    return PARSER;
  }

  @Override
  public AccessLogKernelReadL3Metrics getDefaultInstanceForType() {
    return DEFAULT_INSTANCE;
  }

}
