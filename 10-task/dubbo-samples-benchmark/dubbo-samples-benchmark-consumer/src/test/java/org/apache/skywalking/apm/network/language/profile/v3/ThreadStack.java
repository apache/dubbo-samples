// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: profile/Profile.proto

package org.apache.skywalking.apm.network.language.profile.v3;

/**
 * Protobuf type {@code skywalking.v3.ThreadStack}
 */
public final class ThreadStack extends
    com.google.protobuf.GeneratedMessageV3 implements
    // @@protoc_insertion_point(message_implements:skywalking.v3.ThreadStack)
    ThreadStackOrBuilder {
private static final long serialVersionUID = 0L;
  // Use ThreadStack.newBuilder() to construct.
  private ThreadStack(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
    super(builder);
  }
  private ThreadStack() {
    codeSignatures_ = com.google.protobuf.LazyStringArrayList.EMPTY;
  }

  @Override
  @SuppressWarnings({"unused"})
  protected Object newInstance(
      UnusedPrivateParameter unused) {
    return new ThreadStack();
  }

  @Override
  public final com.google.protobuf.UnknownFieldSet
  getUnknownFields() {
    return this.unknownFields;
  }
  private ThreadStack(
      com.google.protobuf.CodedInputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    this();
    if (extensionRegistry == null) {
      throw new NullPointerException();
    }
    int mutable_bitField0_ = 0;
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
            if (!((mutable_bitField0_ & 0x00000001) != 0)) {
              codeSignatures_ = new com.google.protobuf.LazyStringArrayList();
              mutable_bitField0_ |= 0x00000001;
            }
            codeSignatures_.add(s);
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
      if (((mutable_bitField0_ & 0x00000001) != 0)) {
        codeSignatures_ = codeSignatures_.getUnmodifiableView();
      }
      this.unknownFields = unknownFields.build();
      makeExtensionsImmutable();
    }
  }
  public static final com.google.protobuf.Descriptors.Descriptor
      getDescriptor() {
    return Profile.internal_static_skywalking_v3_ThreadStack_descriptor;
  }

  @Override
  protected FieldAccessorTable
      internalGetFieldAccessorTable() {
    return Profile.internal_static_skywalking_v3_ThreadStack_fieldAccessorTable
        .ensureFieldAccessorsInitialized(
            ThreadStack.class, Builder.class);
  }

  public static final int CODESIGNATURES_FIELD_NUMBER = 1;
  private com.google.protobuf.LazyStringList codeSignatures_;
  /**
   * <pre>
   * stack code signature list
   * </pre>
   *
   * <code>repeated string codeSignatures = 1;</code>
   * @return A list containing the codeSignatures.
   */
  public com.google.protobuf.ProtocolStringList
      getCodeSignaturesList() {
    return codeSignatures_;
  }
  /**
   * <pre>
   * stack code signature list
   * </pre>
   *
   * <code>repeated string codeSignatures = 1;</code>
   * @return The count of codeSignatures.
   */
  public int getCodeSignaturesCount() {
    return codeSignatures_.size();
  }
  /**
   * <pre>
   * stack code signature list
   * </pre>
   *
   * <code>repeated string codeSignatures = 1;</code>
   * @param index The index of the element to return.
   * @return The codeSignatures at the given index.
   */
  public String getCodeSignatures(int index) {
    return codeSignatures_.get(index);
  }
  /**
   * <pre>
   * stack code signature list
   * </pre>
   *
   * <code>repeated string codeSignatures = 1;</code>
   * @param index The index of the value to return.
   * @return The bytes of the codeSignatures at the given index.
   */
  public com.google.protobuf.ByteString
      getCodeSignaturesBytes(int index) {
    return codeSignatures_.getByteString(index);
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
    for (int i = 0; i < codeSignatures_.size(); i++) {
      com.google.protobuf.GeneratedMessageV3.writeString(output, 1, codeSignatures_.getRaw(i));
    }
    unknownFields.writeTo(output);
  }

  @Override
  public int getSerializedSize() {
    int size = memoizedSize;
    if (size != -1) return size;

    size = 0;
    {
      int dataSize = 0;
      for (int i = 0; i < codeSignatures_.size(); i++) {
        dataSize += computeStringSizeNoTag(codeSignatures_.getRaw(i));
      }
      size += dataSize;
      size += 1 * getCodeSignaturesList().size();
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
    if (!(obj instanceof ThreadStack)) {
      return super.equals(obj);
    }
    ThreadStack other = (ThreadStack) obj;

    if (!getCodeSignaturesList()
        .equals(other.getCodeSignaturesList())) return false;
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
    if (getCodeSignaturesCount() > 0) {
      hash = (37 * hash) + CODESIGNATURES_FIELD_NUMBER;
      hash = (53 * hash) + getCodeSignaturesList().hashCode();
    }
    hash = (29 * hash) + unknownFields.hashCode();
    memoizedHashCode = hash;
    return hash;
  }

  public static ThreadStack parseFrom(
      java.nio.ByteBuffer data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static ThreadStack parseFrom(
      java.nio.ByteBuffer data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static ThreadStack parseFrom(
      com.google.protobuf.ByteString data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static ThreadStack parseFrom(
      com.google.protobuf.ByteString data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static ThreadStack parseFrom(byte[] data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static ThreadStack parseFrom(
      byte[] data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static ThreadStack parseFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static ThreadStack parseFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input, extensionRegistry);
  }
  public static ThreadStack parseDelimitedFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input);
  }
  public static ThreadStack parseDelimitedFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
  }
  public static ThreadStack parseFrom(
      com.google.protobuf.CodedInputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static ThreadStack parseFrom(
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
  public static Builder newBuilder(ThreadStack prototype) {
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
   * Protobuf type {@code skywalking.v3.ThreadStack}
   */
  public static final class Builder extends
      com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements
      // @@protoc_insertion_point(builder_implements:skywalking.v3.ThreadStack)
      ThreadStackOrBuilder {
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return Profile.internal_static_skywalking_v3_ThreadStack_descriptor;
    }

    @Override
    protected FieldAccessorTable
        internalGetFieldAccessorTable() {
      return Profile.internal_static_skywalking_v3_ThreadStack_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              ThreadStack.class, Builder.class);
    }

    // Construct using org.apache.skywalking.apm.network.language.profile.v3.ThreadStack.newBuilder()
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
      codeSignatures_ = com.google.protobuf.LazyStringArrayList.EMPTY;
      bitField0_ = (bitField0_ & ~0x00000001);
      return this;
    }

    @Override
    public com.google.protobuf.Descriptors.Descriptor
        getDescriptorForType() {
      return Profile.internal_static_skywalking_v3_ThreadStack_descriptor;
    }

    @Override
    public ThreadStack getDefaultInstanceForType() {
      return ThreadStack.getDefaultInstance();
    }

    @Override
    public ThreadStack build() {
      ThreadStack result = buildPartial();
      if (!result.isInitialized()) {
        throw newUninitializedMessageException(result);
      }
      return result;
    }

    @Override
    public ThreadStack buildPartial() {
      ThreadStack result = new ThreadStack(this);
      int from_bitField0_ = bitField0_;
      if (((bitField0_ & 0x00000001) != 0)) {
        codeSignatures_ = codeSignatures_.getUnmodifiableView();
        bitField0_ = (bitField0_ & ~0x00000001);
      }
      result.codeSignatures_ = codeSignatures_;
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
      if (other instanceof ThreadStack) {
        return mergeFrom((ThreadStack)other);
      } else {
        super.mergeFrom(other);
        return this;
      }
    }

    public Builder mergeFrom(ThreadStack other) {
      if (other == ThreadStack.getDefaultInstance()) return this;
      if (!other.codeSignatures_.isEmpty()) {
        if (codeSignatures_.isEmpty()) {
          codeSignatures_ = other.codeSignatures_;
          bitField0_ = (bitField0_ & ~0x00000001);
        } else {
          ensureCodeSignaturesIsMutable();
          codeSignatures_.addAll(other.codeSignatures_);
        }
        onChanged();
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
      ThreadStack parsedMessage = null;
      try {
        parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
      } catch (com.google.protobuf.InvalidProtocolBufferException e) {
        parsedMessage = (ThreadStack) e.getUnfinishedMessage();
        throw e.unwrapIOException();
      } finally {
        if (parsedMessage != null) {
          mergeFrom(parsedMessage);
        }
      }
      return this;
    }
    private int bitField0_;

    private com.google.protobuf.LazyStringList codeSignatures_ = com.google.protobuf.LazyStringArrayList.EMPTY;
    private void ensureCodeSignaturesIsMutable() {
      if (!((bitField0_ & 0x00000001) != 0)) {
        codeSignatures_ = new com.google.protobuf.LazyStringArrayList(codeSignatures_);
        bitField0_ |= 0x00000001;
       }
    }
    /**
     * <pre>
     * stack code signature list
     * </pre>
     *
     * <code>repeated string codeSignatures = 1;</code>
     * @return A list containing the codeSignatures.
     */
    public com.google.protobuf.ProtocolStringList
        getCodeSignaturesList() {
      return codeSignatures_.getUnmodifiableView();
    }
    /**
     * <pre>
     * stack code signature list
     * </pre>
     *
     * <code>repeated string codeSignatures = 1;</code>
     * @return The count of codeSignatures.
     */
    public int getCodeSignaturesCount() {
      return codeSignatures_.size();
    }
    /**
     * <pre>
     * stack code signature list
     * </pre>
     *
     * <code>repeated string codeSignatures = 1;</code>
     * @param index The index of the element to return.
     * @return The codeSignatures at the given index.
     */
    public String getCodeSignatures(int index) {
      return codeSignatures_.get(index);
    }
    /**
     * <pre>
     * stack code signature list
     * </pre>
     *
     * <code>repeated string codeSignatures = 1;</code>
     * @param index The index of the value to return.
     * @return The bytes of the codeSignatures at the given index.
     */
    public com.google.protobuf.ByteString
        getCodeSignaturesBytes(int index) {
      return codeSignatures_.getByteString(index);
    }
    /**
     * <pre>
     * stack code signature list
     * </pre>
     *
     * <code>repeated string codeSignatures = 1;</code>
     * @param index The index to set the value at.
     * @param value The codeSignatures to set.
     * @return This builder for chaining.
     */
    public Builder setCodeSignatures(
        int index, String value) {
      if (value == null) {
    throw new NullPointerException();
  }
  ensureCodeSignaturesIsMutable();
      codeSignatures_.set(index, value);
      onChanged();
      return this;
    }
    /**
     * <pre>
     * stack code signature list
     * </pre>
     *
     * <code>repeated string codeSignatures = 1;</code>
     * @param value The codeSignatures to add.
     * @return This builder for chaining.
     */
    public Builder addCodeSignatures(
        String value) {
      if (value == null) {
    throw new NullPointerException();
  }
  ensureCodeSignaturesIsMutable();
      codeSignatures_.add(value);
      onChanged();
      return this;
    }
    /**
     * <pre>
     * stack code signature list
     * </pre>
     *
     * <code>repeated string codeSignatures = 1;</code>
     * @param values The codeSignatures to add.
     * @return This builder for chaining.
     */
    public Builder addAllCodeSignatures(
        Iterable<String> values) {
      ensureCodeSignaturesIsMutable();
      com.google.protobuf.AbstractMessageLite.Builder.addAll(
          values, codeSignatures_);
      onChanged();
      return this;
    }
    /**
     * <pre>
     * stack code signature list
     * </pre>
     *
     * <code>repeated string codeSignatures = 1;</code>
     * @return This builder for chaining.
     */
    public Builder clearCodeSignatures() {
      codeSignatures_ = com.google.protobuf.LazyStringArrayList.EMPTY;
      bitField0_ = (bitField0_ & ~0x00000001);
      onChanged();
      return this;
    }
    /**
     * <pre>
     * stack code signature list
     * </pre>
     *
     * <code>repeated string codeSignatures = 1;</code>
     * @param value The bytes of the codeSignatures to add.
     * @return This builder for chaining.
     */
    public Builder addCodeSignaturesBytes(
        com.google.protobuf.ByteString value) {
      if (value == null) {
    throw new NullPointerException();
  }
  checkByteStringIsUtf8(value);
      ensureCodeSignaturesIsMutable();
      codeSignatures_.add(value);
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


    // @@protoc_insertion_point(builder_scope:skywalking.v3.ThreadStack)
  }

  // @@protoc_insertion_point(class_scope:skywalking.v3.ThreadStack)
  private static final ThreadStack DEFAULT_INSTANCE;
  static {
    DEFAULT_INSTANCE = new ThreadStack();
  }

  public static ThreadStack getDefaultInstance() {
    return DEFAULT_INSTANCE;
  }

  private static final com.google.protobuf.Parser<ThreadStack>
      PARSER = new com.google.protobuf.AbstractParser<ThreadStack>() {
    @Override
    public ThreadStack parsePartialFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return new ThreadStack(input, extensionRegistry);
    }
  };

  public static com.google.protobuf.Parser<ThreadStack> parser() {
    return PARSER;
  }

  @Override
  public com.google.protobuf.Parser<ThreadStack> getParserForType() {
    return PARSER;
  }

  @Override
  public ThreadStack getDefaultInstanceForType() {
    return DEFAULT_INSTANCE;
  }

}

