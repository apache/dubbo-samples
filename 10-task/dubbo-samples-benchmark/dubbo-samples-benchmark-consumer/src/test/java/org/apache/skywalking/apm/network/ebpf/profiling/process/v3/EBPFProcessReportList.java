// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: ebpf/profiling/Process.proto

package org.apache.skywalking.apm.network.ebpf.profiling.process.v3;

/**
 * Protobuf type {@code skywalking.v3.EBPFProcessReportList}
 */
public final class EBPFProcessReportList extends
    com.google.protobuf.GeneratedMessageV3 implements
    // @@protoc_insertion_point(message_implements:skywalking.v3.EBPFProcessReportList)
    EBPFProcessReportListOrBuilder {
private static final long serialVersionUID = 0L;
  // Use EBPFProcessReportList.newBuilder() to construct.
  private EBPFProcessReportList(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
    super(builder);
  }
  private EBPFProcessReportList() {
    processes_ = java.util.Collections.emptyList();
    ebpfAgentID_ = "";
  }

  @Override
  @SuppressWarnings({"unused"})
  protected Object newInstance(
      UnusedPrivateParameter unused) {
    return new EBPFProcessReportList();
  }

  @Override
  public final com.google.protobuf.UnknownFieldSet
  getUnknownFields() {
    return this.unknownFields;
  }
  private EBPFProcessReportList(
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
            if (!((mutable_bitField0_ & 0x00000001) != 0)) {
              processes_ = new java.util.ArrayList<EBPFProcessProperties>();
              mutable_bitField0_ |= 0x00000001;
            }
            processes_.add(
                input.readMessage(EBPFProcessProperties.parser(), extensionRegistry));
            break;
          }
          case 18: {
            String s = input.readStringRequireUtf8();

            ebpfAgentID_ = s;
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
        processes_ = java.util.Collections.unmodifiableList(processes_);
      }
      this.unknownFields = unknownFields.build();
      makeExtensionsImmutable();
    }
  }
  public static final com.google.protobuf.Descriptors.Descriptor
      getDescriptor() {
    return Process.internal_static_skywalking_v3_EBPFProcessReportList_descriptor;
  }

  @Override
  protected FieldAccessorTable
      internalGetFieldAccessorTable() {
    return Process.internal_static_skywalking_v3_EBPFProcessReportList_fieldAccessorTable
        .ensureFieldAccessorsInitialized(
            EBPFProcessReportList.class, Builder.class);
  }

  public static final int PROCESSES_FIELD_NUMBER = 1;
  private java.util.List<EBPFProcessProperties> processes_;
  /**
   * <code>repeated .skywalking.v3.EBPFProcessProperties processes = 1;</code>
   */
  @Override
  public java.util.List<EBPFProcessProperties> getProcessesList() {
    return processes_;
  }
  /**
   * <code>repeated .skywalking.v3.EBPFProcessProperties processes = 1;</code>
   */
  @Override
  public java.util.List<? extends EBPFProcessPropertiesOrBuilder>
      getProcessesOrBuilderList() {
    return processes_;
  }
  /**
   * <code>repeated .skywalking.v3.EBPFProcessProperties processes = 1;</code>
   */
  @Override
  public int getProcessesCount() {
    return processes_.size();
  }
  /**
   * <code>repeated .skywalking.v3.EBPFProcessProperties processes = 1;</code>
   */
  @Override
  public EBPFProcessProperties getProcesses(int index) {
    return processes_.get(index);
  }
  /**
   * <code>repeated .skywalking.v3.EBPFProcessProperties processes = 1;</code>
   */
  @Override
  public EBPFProcessPropertiesOrBuilder getProcessesOrBuilder(
      int index) {
    return processes_.get(index);
  }

  public static final int EBPFAGENTID_FIELD_NUMBER = 2;
  private volatile Object ebpfAgentID_;
  /**
   * <pre>
   * An ID generated by eBPF agent, should be unique globally.
   * </pre>
   *
   * <code>string ebpfAgentID = 2;</code>
   * @return The ebpfAgentID.
   */
  @Override
  public String getEbpfAgentID() {
    Object ref = ebpfAgentID_;
    if (ref instanceof String) {
      return (String) ref;
    } else {
      com.google.protobuf.ByteString bs =
          (com.google.protobuf.ByteString) ref;
      String s = bs.toStringUtf8();
      ebpfAgentID_ = s;
      return s;
    }
  }
  /**
   * <pre>
   * An ID generated by eBPF agent, should be unique globally.
   * </pre>
   *
   * <code>string ebpfAgentID = 2;</code>
   * @return The bytes for ebpfAgentID.
   */
  @Override
  public com.google.protobuf.ByteString
      getEbpfAgentIDBytes() {
    Object ref = ebpfAgentID_;
    if (ref instanceof String) {
      com.google.protobuf.ByteString b =
          com.google.protobuf.ByteString.copyFromUtf8(
              (String) ref);
      ebpfAgentID_ = b;
      return b;
    } else {
      return (com.google.protobuf.ByteString) ref;
    }
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
    for (int i = 0; i < processes_.size(); i++) {
      output.writeMessage(1, processes_.get(i));
    }
    if (!com.google.protobuf.GeneratedMessageV3.isStringEmpty(ebpfAgentID_)) {
      com.google.protobuf.GeneratedMessageV3.writeString(output, 2, ebpfAgentID_);
    }
    unknownFields.writeTo(output);
  }

  @Override
  public int getSerializedSize() {
    int size = memoizedSize;
    if (size != -1) return size;

    size = 0;
    for (int i = 0; i < processes_.size(); i++) {
      size += com.google.protobuf.CodedOutputStream
        .computeMessageSize(1, processes_.get(i));
    }
    if (!com.google.protobuf.GeneratedMessageV3.isStringEmpty(ebpfAgentID_)) {
      size += com.google.protobuf.GeneratedMessageV3.computeStringSize(2, ebpfAgentID_);
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
    if (!(obj instanceof EBPFProcessReportList)) {
      return super.equals(obj);
    }
    EBPFProcessReportList other = (EBPFProcessReportList) obj;

    if (!getProcessesList()
        .equals(other.getProcessesList())) return false;
    if (!getEbpfAgentID()
        .equals(other.getEbpfAgentID())) return false;
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
    if (getProcessesCount() > 0) {
      hash = (37 * hash) + PROCESSES_FIELD_NUMBER;
      hash = (53 * hash) + getProcessesList().hashCode();
    }
    hash = (37 * hash) + EBPFAGENTID_FIELD_NUMBER;
    hash = (53 * hash) + getEbpfAgentID().hashCode();
    hash = (29 * hash) + unknownFields.hashCode();
    memoizedHashCode = hash;
    return hash;
  }

  public static EBPFProcessReportList parseFrom(
      java.nio.ByteBuffer data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static EBPFProcessReportList parseFrom(
      java.nio.ByteBuffer data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static EBPFProcessReportList parseFrom(
      com.google.protobuf.ByteString data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static EBPFProcessReportList parseFrom(
      com.google.protobuf.ByteString data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static EBPFProcessReportList parseFrom(byte[] data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static EBPFProcessReportList parseFrom(
      byte[] data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static EBPFProcessReportList parseFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static EBPFProcessReportList parseFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input, extensionRegistry);
  }
  public static EBPFProcessReportList parseDelimitedFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input);
  }
  public static EBPFProcessReportList parseDelimitedFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
  }
  public static EBPFProcessReportList parseFrom(
      com.google.protobuf.CodedInputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static EBPFProcessReportList parseFrom(
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
  public static Builder newBuilder(EBPFProcessReportList prototype) {
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
   * Protobuf type {@code skywalking.v3.EBPFProcessReportList}
   */
  public static final class Builder extends
      com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements
      // @@protoc_insertion_point(builder_implements:skywalking.v3.EBPFProcessReportList)
      EBPFProcessReportListOrBuilder {
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return Process.internal_static_skywalking_v3_EBPFProcessReportList_descriptor;
    }

    @Override
    protected FieldAccessorTable
        internalGetFieldAccessorTable() {
      return Process.internal_static_skywalking_v3_EBPFProcessReportList_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              EBPFProcessReportList.class, Builder.class);
    }

    // Construct using org.apache.skywalking.apm.network.ebpf.profiling.process.v3.EBPFProcessReportList.newBuilder()
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
        getProcessesFieldBuilder();
      }
    }
    @Override
    public Builder clear() {
      super.clear();
      if (processesBuilder_ == null) {
        processes_ = java.util.Collections.emptyList();
        bitField0_ = (bitField0_ & ~0x00000001);
      } else {
        processesBuilder_.clear();
      }
      ebpfAgentID_ = "";

      return this;
    }

    @Override
    public com.google.protobuf.Descriptors.Descriptor
        getDescriptorForType() {
      return Process.internal_static_skywalking_v3_EBPFProcessReportList_descriptor;
    }

    @Override
    public EBPFProcessReportList getDefaultInstanceForType() {
      return EBPFProcessReportList.getDefaultInstance();
    }

    @Override
    public EBPFProcessReportList build() {
      EBPFProcessReportList result = buildPartial();
      if (!result.isInitialized()) {
        throw newUninitializedMessageException(result);
      }
      return result;
    }

    @Override
    public EBPFProcessReportList buildPartial() {
      EBPFProcessReportList result = new EBPFProcessReportList(this);
      int from_bitField0_ = bitField0_;
      if (processesBuilder_ == null) {
        if (((bitField0_ & 0x00000001) != 0)) {
          processes_ = java.util.Collections.unmodifiableList(processes_);
          bitField0_ = (bitField0_ & ~0x00000001);
        }
        result.processes_ = processes_;
      } else {
        result.processes_ = processesBuilder_.build();
      }
      result.ebpfAgentID_ = ebpfAgentID_;
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
      if (other instanceof EBPFProcessReportList) {
        return mergeFrom((EBPFProcessReportList)other);
      } else {
        super.mergeFrom(other);
        return this;
      }
    }

    public Builder mergeFrom(EBPFProcessReportList other) {
      if (other == EBPFProcessReportList.getDefaultInstance()) return this;
      if (processesBuilder_ == null) {
        if (!other.processes_.isEmpty()) {
          if (processes_.isEmpty()) {
            processes_ = other.processes_;
            bitField0_ = (bitField0_ & ~0x00000001);
          } else {
            ensureProcessesIsMutable();
            processes_.addAll(other.processes_);
          }
          onChanged();
        }
      } else {
        if (!other.processes_.isEmpty()) {
          if (processesBuilder_.isEmpty()) {
            processesBuilder_.dispose();
            processesBuilder_ = null;
            processes_ = other.processes_;
            bitField0_ = (bitField0_ & ~0x00000001);
            processesBuilder_ =
              com.google.protobuf.GeneratedMessageV3.alwaysUseFieldBuilders ?
                 getProcessesFieldBuilder() : null;
          } else {
            processesBuilder_.addAllMessages(other.processes_);
          }
        }
      }
      if (!other.getEbpfAgentID().isEmpty()) {
        ebpfAgentID_ = other.ebpfAgentID_;
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
      EBPFProcessReportList parsedMessage = null;
      try {
        parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
      } catch (com.google.protobuf.InvalidProtocolBufferException e) {
        parsedMessage = (EBPFProcessReportList) e.getUnfinishedMessage();
        throw e.unwrapIOException();
      } finally {
        if (parsedMessage != null) {
          mergeFrom(parsedMessage);
        }
      }
      return this;
    }
    private int bitField0_;

    private java.util.List<EBPFProcessProperties> processes_ =
      java.util.Collections.emptyList();
    private void ensureProcessesIsMutable() {
      if (!((bitField0_ & 0x00000001) != 0)) {
        processes_ = new java.util.ArrayList<EBPFProcessProperties>(processes_);
        bitField0_ |= 0x00000001;
       }
    }

    private com.google.protobuf.RepeatedFieldBuilderV3<
        EBPFProcessProperties, EBPFProcessProperties.Builder, EBPFProcessPropertiesOrBuilder> processesBuilder_;

    /**
     * <code>repeated .skywalking.v3.EBPFProcessProperties processes = 1;</code>
     */
    public java.util.List<EBPFProcessProperties> getProcessesList() {
      if (processesBuilder_ == null) {
        return java.util.Collections.unmodifiableList(processes_);
      } else {
        return processesBuilder_.getMessageList();
      }
    }
    /**
     * <code>repeated .skywalking.v3.EBPFProcessProperties processes = 1;</code>
     */
    public int getProcessesCount() {
      if (processesBuilder_ == null) {
        return processes_.size();
      } else {
        return processesBuilder_.getCount();
      }
    }
    /**
     * <code>repeated .skywalking.v3.EBPFProcessProperties processes = 1;</code>
     */
    public EBPFProcessProperties getProcesses(int index) {
      if (processesBuilder_ == null) {
        return processes_.get(index);
      } else {
        return processesBuilder_.getMessage(index);
      }
    }
    /**
     * <code>repeated .skywalking.v3.EBPFProcessProperties processes = 1;</code>
     */
    public Builder setProcesses(
        int index, EBPFProcessProperties value) {
      if (processesBuilder_ == null) {
        if (value == null) {
          throw new NullPointerException();
        }
        ensureProcessesIsMutable();
        processes_.set(index, value);
        onChanged();
      } else {
        processesBuilder_.setMessage(index, value);
      }
      return this;
    }
    /**
     * <code>repeated .skywalking.v3.EBPFProcessProperties processes = 1;</code>
     */
    public Builder setProcesses(
        int index, EBPFProcessProperties.Builder builderForValue) {
      if (processesBuilder_ == null) {
        ensureProcessesIsMutable();
        processes_.set(index, builderForValue.build());
        onChanged();
      } else {
        processesBuilder_.setMessage(index, builderForValue.build());
      }
      return this;
    }
    /**
     * <code>repeated .skywalking.v3.EBPFProcessProperties processes = 1;</code>
     */
    public Builder addProcesses(EBPFProcessProperties value) {
      if (processesBuilder_ == null) {
        if (value == null) {
          throw new NullPointerException();
        }
        ensureProcessesIsMutable();
        processes_.add(value);
        onChanged();
      } else {
        processesBuilder_.addMessage(value);
      }
      return this;
    }
    /**
     * <code>repeated .skywalking.v3.EBPFProcessProperties processes = 1;</code>
     */
    public Builder addProcesses(
        int index, EBPFProcessProperties value) {
      if (processesBuilder_ == null) {
        if (value == null) {
          throw new NullPointerException();
        }
        ensureProcessesIsMutable();
        processes_.add(index, value);
        onChanged();
      } else {
        processesBuilder_.addMessage(index, value);
      }
      return this;
    }
    /**
     * <code>repeated .skywalking.v3.EBPFProcessProperties processes = 1;</code>
     */
    public Builder addProcesses(
        EBPFProcessProperties.Builder builderForValue) {
      if (processesBuilder_ == null) {
        ensureProcessesIsMutable();
        processes_.add(builderForValue.build());
        onChanged();
      } else {
        processesBuilder_.addMessage(builderForValue.build());
      }
      return this;
    }
    /**
     * <code>repeated .skywalking.v3.EBPFProcessProperties processes = 1;</code>
     */
    public Builder addProcesses(
        int index, EBPFProcessProperties.Builder builderForValue) {
      if (processesBuilder_ == null) {
        ensureProcessesIsMutable();
        processes_.add(index, builderForValue.build());
        onChanged();
      } else {
        processesBuilder_.addMessage(index, builderForValue.build());
      }
      return this;
    }
    /**
     * <code>repeated .skywalking.v3.EBPFProcessProperties processes = 1;</code>
     */
    public Builder addAllProcesses(
        Iterable<? extends EBPFProcessProperties> values) {
      if (processesBuilder_ == null) {
        ensureProcessesIsMutable();
        com.google.protobuf.AbstractMessageLite.Builder.addAll(
            values, processes_);
        onChanged();
      } else {
        processesBuilder_.addAllMessages(values);
      }
      return this;
    }
    /**
     * <code>repeated .skywalking.v3.EBPFProcessProperties processes = 1;</code>
     */
    public Builder clearProcesses() {
      if (processesBuilder_ == null) {
        processes_ = java.util.Collections.emptyList();
        bitField0_ = (bitField0_ & ~0x00000001);
        onChanged();
      } else {
        processesBuilder_.clear();
      }
      return this;
    }
    /**
     * <code>repeated .skywalking.v3.EBPFProcessProperties processes = 1;</code>
     */
    public Builder removeProcesses(int index) {
      if (processesBuilder_ == null) {
        ensureProcessesIsMutable();
        processes_.remove(index);
        onChanged();
      } else {
        processesBuilder_.remove(index);
      }
      return this;
    }
    /**
     * <code>repeated .skywalking.v3.EBPFProcessProperties processes = 1;</code>
     */
    public EBPFProcessProperties.Builder getProcessesBuilder(
        int index) {
      return getProcessesFieldBuilder().getBuilder(index);
    }
    /**
     * <code>repeated .skywalking.v3.EBPFProcessProperties processes = 1;</code>
     */
    public EBPFProcessPropertiesOrBuilder getProcessesOrBuilder(
        int index) {
      if (processesBuilder_ == null) {
        return processes_.get(index);  } else {
        return processesBuilder_.getMessageOrBuilder(index);
      }
    }
    /**
     * <code>repeated .skywalking.v3.EBPFProcessProperties processes = 1;</code>
     */
    public java.util.List<? extends EBPFProcessPropertiesOrBuilder>
         getProcessesOrBuilderList() {
      if (processesBuilder_ != null) {
        return processesBuilder_.getMessageOrBuilderList();
      } else {
        return java.util.Collections.unmodifiableList(processes_);
      }
    }
    /**
     * <code>repeated .skywalking.v3.EBPFProcessProperties processes = 1;</code>
     */
    public EBPFProcessProperties.Builder addProcessesBuilder() {
      return getProcessesFieldBuilder().addBuilder(
          EBPFProcessProperties.getDefaultInstance());
    }
    /**
     * <code>repeated .skywalking.v3.EBPFProcessProperties processes = 1;</code>
     */
    public EBPFProcessProperties.Builder addProcessesBuilder(
        int index) {
      return getProcessesFieldBuilder().addBuilder(
          index, EBPFProcessProperties.getDefaultInstance());
    }
    /**
     * <code>repeated .skywalking.v3.EBPFProcessProperties processes = 1;</code>
     */
    public java.util.List<EBPFProcessProperties.Builder>
         getProcessesBuilderList() {
      return getProcessesFieldBuilder().getBuilderList();
    }
    private com.google.protobuf.RepeatedFieldBuilderV3<
        EBPFProcessProperties, EBPFProcessProperties.Builder, EBPFProcessPropertiesOrBuilder>
        getProcessesFieldBuilder() {
      if (processesBuilder_ == null) {
        processesBuilder_ = new com.google.protobuf.RepeatedFieldBuilderV3<
            EBPFProcessProperties, EBPFProcessProperties.Builder, EBPFProcessPropertiesOrBuilder>(
                processes_,
                ((bitField0_ & 0x00000001) != 0),
                getParentForChildren(),
                isClean());
        processes_ = null;
      }
      return processesBuilder_;
    }

    private Object ebpfAgentID_ = "";
    /**
     * <pre>
     * An ID generated by eBPF agent, should be unique globally.
     * </pre>
     *
     * <code>string ebpfAgentID = 2;</code>
     * @return The ebpfAgentID.
     */
    public String getEbpfAgentID() {
      Object ref = ebpfAgentID_;
      if (!(ref instanceof String)) {
        com.google.protobuf.ByteString bs =
            (com.google.protobuf.ByteString) ref;
        String s = bs.toStringUtf8();
        ebpfAgentID_ = s;
        return s;
      } else {
        return (String) ref;
      }
    }
    /**
     * <pre>
     * An ID generated by eBPF agent, should be unique globally.
     * </pre>
     *
     * <code>string ebpfAgentID = 2;</code>
     * @return The bytes for ebpfAgentID.
     */
    public com.google.protobuf.ByteString
        getEbpfAgentIDBytes() {
      Object ref = ebpfAgentID_;
      if (ref instanceof String) {
        com.google.protobuf.ByteString b =
            com.google.protobuf.ByteString.copyFromUtf8(
                (String) ref);
        ebpfAgentID_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }
    /**
     * <pre>
     * An ID generated by eBPF agent, should be unique globally.
     * </pre>
     *
     * <code>string ebpfAgentID = 2;</code>
     * @param value The ebpfAgentID to set.
     * @return This builder for chaining.
     */
    public Builder setEbpfAgentID(
        String value) {
      if (value == null) {
    throw new NullPointerException();
  }

      ebpfAgentID_ = value;
      onChanged();
      return this;
    }
    /**
     * <pre>
     * An ID generated by eBPF agent, should be unique globally.
     * </pre>
     *
     * <code>string ebpfAgentID = 2;</code>
     * @return This builder for chaining.
     */
    public Builder clearEbpfAgentID() {

      ebpfAgentID_ = getDefaultInstance().getEbpfAgentID();
      onChanged();
      return this;
    }
    /**
     * <pre>
     * An ID generated by eBPF agent, should be unique globally.
     * </pre>
     *
     * <code>string ebpfAgentID = 2;</code>
     * @param value The bytes for ebpfAgentID to set.
     * @return This builder for chaining.
     */
    public Builder setEbpfAgentIDBytes(
        com.google.protobuf.ByteString value) {
      if (value == null) {
    throw new NullPointerException();
  }
  checkByteStringIsUtf8(value);

      ebpfAgentID_ = value;
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


    // @@protoc_insertion_point(builder_scope:skywalking.v3.EBPFProcessReportList)
  }

  // @@protoc_insertion_point(class_scope:skywalking.v3.EBPFProcessReportList)
  private static final EBPFProcessReportList DEFAULT_INSTANCE;
  static {
    DEFAULT_INSTANCE = new EBPFProcessReportList();
  }

  public static EBPFProcessReportList getDefaultInstance() {
    return DEFAULT_INSTANCE;
  }

  private static final com.google.protobuf.Parser<EBPFProcessReportList>
      PARSER = new com.google.protobuf.AbstractParser<EBPFProcessReportList>() {
    @Override
    public EBPFProcessReportList parsePartialFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return new EBPFProcessReportList(input, extensionRegistry);
    }
  };

  public static com.google.protobuf.Parser<EBPFProcessReportList> parser() {
    return PARSER;
  }

  @Override
  public com.google.protobuf.Parser<EBPFProcessReportList> getParserForType() {
    return PARSER;
  }

  @Override
  public EBPFProcessReportList getDefaultInstanceForType() {
    return DEFAULT_INSTANCE;
  }

}

