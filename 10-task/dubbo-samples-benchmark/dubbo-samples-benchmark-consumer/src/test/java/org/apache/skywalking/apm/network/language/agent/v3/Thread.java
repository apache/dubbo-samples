// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: language-agent/JVMMetric.proto

package org.apache.skywalking.apm.network.language.agent.v3;

/**
 * <pre>
 * See: https://docs.oracle.com/javase/8/docs/api/java/lang/management/ThreadMXBean.html
 * </pre>
 *
 * Protobuf type {@code skywalking.v3.Thread}
 */
public final class Thread extends
    com.google.protobuf.GeneratedMessageV3 implements
    // @@protoc_insertion_point(message_implements:skywalking.v3.Thread)
    ThreadOrBuilder {
private static final long serialVersionUID = 0L;
  // Use Thread.newBuilder() to construct.
  private Thread(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
    super(builder);
  }
  private Thread() {
  }

  @Override
  @SuppressWarnings({"unused"})
  protected Object newInstance(
      UnusedPrivateParameter unused) {
    return new Thread();
  }

  @Override
  public final com.google.protobuf.UnknownFieldSet
  getUnknownFields() {
    return this.unknownFields;
  }
  private Thread(
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

            liveCount_ = input.readInt64();
            break;
          }
          case 16: {

            daemonCount_ = input.readInt64();
            break;
          }
          case 24: {

            peakCount_ = input.readInt64();
            break;
          }
          case 32: {

            runnableStateThreadCount_ = input.readInt64();
            break;
          }
          case 40: {

            blockedStateThreadCount_ = input.readInt64();
            break;
          }
          case 48: {

            waitingStateThreadCount_ = input.readInt64();
            break;
          }
          case 56: {

            timedWaitingStateThreadCount_ = input.readInt64();
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
    return JVMMetricOuterClass.internal_static_skywalking_v3_Thread_descriptor;
  }

  @Override
  protected FieldAccessorTable
      internalGetFieldAccessorTable() {
    return JVMMetricOuterClass.internal_static_skywalking_v3_Thread_fieldAccessorTable
        .ensureFieldAccessorsInitialized(
            Thread.class, Builder.class);
  }

  public static final int LIVECOUNT_FIELD_NUMBER = 1;
  private long liveCount_;
  /**
   * <code>int64 liveCount = 1;</code>
   * @return The liveCount.
   */
  @Override
  public long getLiveCount() {
    return liveCount_;
  }

  public static final int DAEMONCOUNT_FIELD_NUMBER = 2;
  private long daemonCount_;
  /**
   * <code>int64 daemonCount = 2;</code>
   * @return The daemonCount.
   */
  @Override
  public long getDaemonCount() {
    return daemonCount_;
  }

  public static final int PEAKCOUNT_FIELD_NUMBER = 3;
  private long peakCount_;
  /**
   * <code>int64 peakCount = 3;</code>
   * @return The peakCount.
   */
  @Override
  public long getPeakCount() {
    return peakCount_;
  }

  public static final int RUNNABLESTATETHREADCOUNT_FIELD_NUMBER = 4;
  private long runnableStateThreadCount_;
  /**
   * <code>int64 runnableStateThreadCount = 4;</code>
   * @return The runnableStateThreadCount.
   */
  @Override
  public long getRunnableStateThreadCount() {
    return runnableStateThreadCount_;
  }

  public static final int BLOCKEDSTATETHREADCOUNT_FIELD_NUMBER = 5;
  private long blockedStateThreadCount_;
  /**
   * <code>int64 blockedStateThreadCount = 5;</code>
   * @return The blockedStateThreadCount.
   */
  @Override
  public long getBlockedStateThreadCount() {
    return blockedStateThreadCount_;
  }

  public static final int WAITINGSTATETHREADCOUNT_FIELD_NUMBER = 6;
  private long waitingStateThreadCount_;
  /**
   * <code>int64 waitingStateThreadCount = 6;</code>
   * @return The waitingStateThreadCount.
   */
  @Override
  public long getWaitingStateThreadCount() {
    return waitingStateThreadCount_;
  }

  public static final int TIMEDWAITINGSTATETHREADCOUNT_FIELD_NUMBER = 7;
  private long timedWaitingStateThreadCount_;
  /**
   * <code>int64 timedWaitingStateThreadCount = 7;</code>
   * @return The timedWaitingStateThreadCount.
   */
  @Override
  public long getTimedWaitingStateThreadCount() {
    return timedWaitingStateThreadCount_;
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
    if (liveCount_ != 0L) {
      output.writeInt64(1, liveCount_);
    }
    if (daemonCount_ != 0L) {
      output.writeInt64(2, daemonCount_);
    }
    if (peakCount_ != 0L) {
      output.writeInt64(3, peakCount_);
    }
    if (runnableStateThreadCount_ != 0L) {
      output.writeInt64(4, runnableStateThreadCount_);
    }
    if (blockedStateThreadCount_ != 0L) {
      output.writeInt64(5, blockedStateThreadCount_);
    }
    if (waitingStateThreadCount_ != 0L) {
      output.writeInt64(6, waitingStateThreadCount_);
    }
    if (timedWaitingStateThreadCount_ != 0L) {
      output.writeInt64(7, timedWaitingStateThreadCount_);
    }
    unknownFields.writeTo(output);
  }

  @Override
  public int getSerializedSize() {
    int size = memoizedSize;
    if (size != -1) return size;

    size = 0;
    if (liveCount_ != 0L) {
      size += com.google.protobuf.CodedOutputStream
        .computeInt64Size(1, liveCount_);
    }
    if (daemonCount_ != 0L) {
      size += com.google.protobuf.CodedOutputStream
        .computeInt64Size(2, daemonCount_);
    }
    if (peakCount_ != 0L) {
      size += com.google.protobuf.CodedOutputStream
        .computeInt64Size(3, peakCount_);
    }
    if (runnableStateThreadCount_ != 0L) {
      size += com.google.protobuf.CodedOutputStream
        .computeInt64Size(4, runnableStateThreadCount_);
    }
    if (blockedStateThreadCount_ != 0L) {
      size += com.google.protobuf.CodedOutputStream
        .computeInt64Size(5, blockedStateThreadCount_);
    }
    if (waitingStateThreadCount_ != 0L) {
      size += com.google.protobuf.CodedOutputStream
        .computeInt64Size(6, waitingStateThreadCount_);
    }
    if (timedWaitingStateThreadCount_ != 0L) {
      size += com.google.protobuf.CodedOutputStream
        .computeInt64Size(7, timedWaitingStateThreadCount_);
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
    if (!(obj instanceof Thread)) {
      return super.equals(obj);
    }
    Thread other = (Thread) obj;

    if (getLiveCount()
        != other.getLiveCount()) return false;
    if (getDaemonCount()
        != other.getDaemonCount()) return false;
    if (getPeakCount()
        != other.getPeakCount()) return false;
    if (getRunnableStateThreadCount()
        != other.getRunnableStateThreadCount()) return false;
    if (getBlockedStateThreadCount()
        != other.getBlockedStateThreadCount()) return false;
    if (getWaitingStateThreadCount()
        != other.getWaitingStateThreadCount()) return false;
    if (getTimedWaitingStateThreadCount()
        != other.getTimedWaitingStateThreadCount()) return false;
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
    hash = (37 * hash) + LIVECOUNT_FIELD_NUMBER;
    hash = (53 * hash) + com.google.protobuf.Internal.hashLong(
        getLiveCount());
    hash = (37 * hash) + DAEMONCOUNT_FIELD_NUMBER;
    hash = (53 * hash) + com.google.protobuf.Internal.hashLong(
        getDaemonCount());
    hash = (37 * hash) + PEAKCOUNT_FIELD_NUMBER;
    hash = (53 * hash) + com.google.protobuf.Internal.hashLong(
        getPeakCount());
    hash = (37 * hash) + RUNNABLESTATETHREADCOUNT_FIELD_NUMBER;
    hash = (53 * hash) + com.google.protobuf.Internal.hashLong(
        getRunnableStateThreadCount());
    hash = (37 * hash) + BLOCKEDSTATETHREADCOUNT_FIELD_NUMBER;
    hash = (53 * hash) + com.google.protobuf.Internal.hashLong(
        getBlockedStateThreadCount());
    hash = (37 * hash) + WAITINGSTATETHREADCOUNT_FIELD_NUMBER;
    hash = (53 * hash) + com.google.protobuf.Internal.hashLong(
        getWaitingStateThreadCount());
    hash = (37 * hash) + TIMEDWAITINGSTATETHREADCOUNT_FIELD_NUMBER;
    hash = (53 * hash) + com.google.protobuf.Internal.hashLong(
        getTimedWaitingStateThreadCount());
    hash = (29 * hash) + unknownFields.hashCode();
    memoizedHashCode = hash;
    return hash;
  }

  public static Thread parseFrom(
      java.nio.ByteBuffer data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static Thread parseFrom(
      java.nio.ByteBuffer data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static Thread parseFrom(
      com.google.protobuf.ByteString data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static Thread parseFrom(
      com.google.protobuf.ByteString data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static Thread parseFrom(byte[] data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static Thread parseFrom(
      byte[] data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static Thread parseFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static Thread parseFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input, extensionRegistry);
  }
  public static Thread parseDelimitedFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input);
  }
  public static Thread parseDelimitedFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
  }
  public static Thread parseFrom(
      com.google.protobuf.CodedInputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static Thread parseFrom(
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
  public static Builder newBuilder(Thread prototype) {
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
   * <pre>
   * See: https://docs.oracle.com/javase/8/docs/api/java/lang/management/ThreadMXBean.html
   * </pre>
   *
   * Protobuf type {@code skywalking.v3.Thread}
   */
  public static final class Builder extends
      com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements
      // @@protoc_insertion_point(builder_implements:skywalking.v3.Thread)
      ThreadOrBuilder {
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return JVMMetricOuterClass.internal_static_skywalking_v3_Thread_descriptor;
    }

    @Override
    protected FieldAccessorTable
        internalGetFieldAccessorTable() {
      return JVMMetricOuterClass.internal_static_skywalking_v3_Thread_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              Thread.class, Builder.class);
    }

    // Construct using org.apache.skywalking.apm.network.language.agent.v3.Thread.newBuilder()
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
      liveCount_ = 0L;

      daemonCount_ = 0L;

      peakCount_ = 0L;

      runnableStateThreadCount_ = 0L;

      blockedStateThreadCount_ = 0L;

      waitingStateThreadCount_ = 0L;

      timedWaitingStateThreadCount_ = 0L;

      return this;
    }

    @Override
    public com.google.protobuf.Descriptors.Descriptor
        getDescriptorForType() {
      return JVMMetricOuterClass.internal_static_skywalking_v3_Thread_descriptor;
    }

    @Override
    public Thread getDefaultInstanceForType() {
      return Thread.getDefaultInstance();
    }

    @Override
    public Thread build() {
      Thread result = buildPartial();
      if (!result.isInitialized()) {
        throw newUninitializedMessageException(result);
      }
      return result;
    }

    @Override
    public Thread buildPartial() {
      Thread result = new Thread(this);
      result.liveCount_ = liveCount_;
      result.daemonCount_ = daemonCount_;
      result.peakCount_ = peakCount_;
      result.runnableStateThreadCount_ = runnableStateThreadCount_;
      result.blockedStateThreadCount_ = blockedStateThreadCount_;
      result.waitingStateThreadCount_ = waitingStateThreadCount_;
      result.timedWaitingStateThreadCount_ = timedWaitingStateThreadCount_;
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
      if (other instanceof Thread) {
        return mergeFrom((Thread)other);
      } else {
        super.mergeFrom(other);
        return this;
      }
    }

    public Builder mergeFrom(Thread other) {
      if (other == Thread.getDefaultInstance()) return this;
      if (other.getLiveCount() != 0L) {
        setLiveCount(other.getLiveCount());
      }
      if (other.getDaemonCount() != 0L) {
        setDaemonCount(other.getDaemonCount());
      }
      if (other.getPeakCount() != 0L) {
        setPeakCount(other.getPeakCount());
      }
      if (other.getRunnableStateThreadCount() != 0L) {
        setRunnableStateThreadCount(other.getRunnableStateThreadCount());
      }
      if (other.getBlockedStateThreadCount() != 0L) {
        setBlockedStateThreadCount(other.getBlockedStateThreadCount());
      }
      if (other.getWaitingStateThreadCount() != 0L) {
        setWaitingStateThreadCount(other.getWaitingStateThreadCount());
      }
      if (other.getTimedWaitingStateThreadCount() != 0L) {
        setTimedWaitingStateThreadCount(other.getTimedWaitingStateThreadCount());
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
      Thread parsedMessage = null;
      try {
        parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
      } catch (com.google.protobuf.InvalidProtocolBufferException e) {
        parsedMessage = (Thread) e.getUnfinishedMessage();
        throw e.unwrapIOException();
      } finally {
        if (parsedMessage != null) {
          mergeFrom(parsedMessage);
        }
      }
      return this;
    }

    private long liveCount_ ;
    /**
     * <code>int64 liveCount = 1;</code>
     * @return The liveCount.
     */
    @Override
    public long getLiveCount() {
      return liveCount_;
    }
    /**
     * <code>int64 liveCount = 1;</code>
     * @param value The liveCount to set.
     * @return This builder for chaining.
     */
    public Builder setLiveCount(long value) {

      liveCount_ = value;
      onChanged();
      return this;
    }
    /**
     * <code>int64 liveCount = 1;</code>
     * @return This builder for chaining.
     */
    public Builder clearLiveCount() {

      liveCount_ = 0L;
      onChanged();
      return this;
    }

    private long daemonCount_ ;
    /**
     * <code>int64 daemonCount = 2;</code>
     * @return The daemonCount.
     */
    @Override
    public long getDaemonCount() {
      return daemonCount_;
    }
    /**
     * <code>int64 daemonCount = 2;</code>
     * @param value The daemonCount to set.
     * @return This builder for chaining.
     */
    public Builder setDaemonCount(long value) {

      daemonCount_ = value;
      onChanged();
      return this;
    }
    /**
     * <code>int64 daemonCount = 2;</code>
     * @return This builder for chaining.
     */
    public Builder clearDaemonCount() {

      daemonCount_ = 0L;
      onChanged();
      return this;
    }

    private long peakCount_ ;
    /**
     * <code>int64 peakCount = 3;</code>
     * @return The peakCount.
     */
    @Override
    public long getPeakCount() {
      return peakCount_;
    }
    /**
     * <code>int64 peakCount = 3;</code>
     * @param value The peakCount to set.
     * @return This builder for chaining.
     */
    public Builder setPeakCount(long value) {

      peakCount_ = value;
      onChanged();
      return this;
    }
    /**
     * <code>int64 peakCount = 3;</code>
     * @return This builder for chaining.
     */
    public Builder clearPeakCount() {

      peakCount_ = 0L;
      onChanged();
      return this;
    }

    private long runnableStateThreadCount_ ;
    /**
     * <code>int64 runnableStateThreadCount = 4;</code>
     * @return The runnableStateThreadCount.
     */
    @Override
    public long getRunnableStateThreadCount() {
      return runnableStateThreadCount_;
    }
    /**
     * <code>int64 runnableStateThreadCount = 4;</code>
     * @param value The runnableStateThreadCount to set.
     * @return This builder for chaining.
     */
    public Builder setRunnableStateThreadCount(long value) {

      runnableStateThreadCount_ = value;
      onChanged();
      return this;
    }
    /**
     * <code>int64 runnableStateThreadCount = 4;</code>
     * @return This builder for chaining.
     */
    public Builder clearRunnableStateThreadCount() {

      runnableStateThreadCount_ = 0L;
      onChanged();
      return this;
    }

    private long blockedStateThreadCount_ ;
    /**
     * <code>int64 blockedStateThreadCount = 5;</code>
     * @return The blockedStateThreadCount.
     */
    @Override
    public long getBlockedStateThreadCount() {
      return blockedStateThreadCount_;
    }
    /**
     * <code>int64 blockedStateThreadCount = 5;</code>
     * @param value The blockedStateThreadCount to set.
     * @return This builder for chaining.
     */
    public Builder setBlockedStateThreadCount(long value) {

      blockedStateThreadCount_ = value;
      onChanged();
      return this;
    }
    /**
     * <code>int64 blockedStateThreadCount = 5;</code>
     * @return This builder for chaining.
     */
    public Builder clearBlockedStateThreadCount() {

      blockedStateThreadCount_ = 0L;
      onChanged();
      return this;
    }

    private long waitingStateThreadCount_ ;
    /**
     * <code>int64 waitingStateThreadCount = 6;</code>
     * @return The waitingStateThreadCount.
     */
    @Override
    public long getWaitingStateThreadCount() {
      return waitingStateThreadCount_;
    }
    /**
     * <code>int64 waitingStateThreadCount = 6;</code>
     * @param value The waitingStateThreadCount to set.
     * @return This builder for chaining.
     */
    public Builder setWaitingStateThreadCount(long value) {

      waitingStateThreadCount_ = value;
      onChanged();
      return this;
    }
    /**
     * <code>int64 waitingStateThreadCount = 6;</code>
     * @return This builder for chaining.
     */
    public Builder clearWaitingStateThreadCount() {

      waitingStateThreadCount_ = 0L;
      onChanged();
      return this;
    }

    private long timedWaitingStateThreadCount_ ;
    /**
     * <code>int64 timedWaitingStateThreadCount = 7;</code>
     * @return The timedWaitingStateThreadCount.
     */
    @Override
    public long getTimedWaitingStateThreadCount() {
      return timedWaitingStateThreadCount_;
    }
    /**
     * <code>int64 timedWaitingStateThreadCount = 7;</code>
     * @param value The timedWaitingStateThreadCount to set.
     * @return This builder for chaining.
     */
    public Builder setTimedWaitingStateThreadCount(long value) {

      timedWaitingStateThreadCount_ = value;
      onChanged();
      return this;
    }
    /**
     * <code>int64 timedWaitingStateThreadCount = 7;</code>
     * @return This builder for chaining.
     */
    public Builder clearTimedWaitingStateThreadCount() {

      timedWaitingStateThreadCount_ = 0L;
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


    // @@protoc_insertion_point(builder_scope:skywalking.v3.Thread)
  }

  // @@protoc_insertion_point(class_scope:skywalking.v3.Thread)
  private static final Thread DEFAULT_INSTANCE;
  static {
    DEFAULT_INSTANCE = new Thread();
  }

  public static Thread getDefaultInstance() {
    return DEFAULT_INSTANCE;
  }

  private static final com.google.protobuf.Parser<Thread>
      PARSER = new com.google.protobuf.AbstractParser<Thread>() {
    @Override
    public Thread parsePartialFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return new Thread(input, extensionRegistry);
    }
  };

  public static com.google.protobuf.Parser<Thread> parser() {
    return PARSER;
  }

  @Override
  public com.google.protobuf.Parser<Thread> getParserForType() {
    return PARSER;
  }

  @Override
  public Thread getDefaultInstanceForType() {
    return DEFAULT_INSTANCE;
  }

}

