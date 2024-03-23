// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: profile/Profile.proto

package org.apache.skywalking.apm.network.language.profile.v3;

/**
 * <pre>
 * profile task finished report
 * </pre>
 *
 * Protobuf type {@code skywalking.v3.ProfileTaskFinishReport}
 */
public final class ProfileTaskFinishReport extends
    com.google.protobuf.GeneratedMessageV3 implements
    // @@protoc_insertion_point(message_implements:skywalking.v3.ProfileTaskFinishReport)
    ProfileTaskFinishReportOrBuilder {
private static final long serialVersionUID = 0L;
  // Use ProfileTaskFinishReport.newBuilder() to construct.
  private ProfileTaskFinishReport(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
    super(builder);
  }
  private ProfileTaskFinishReport() {
    service_ = "";
    serviceInstance_ = "";
    taskId_ = "";
  }

  @Override
  @SuppressWarnings({"unused"})
  protected Object newInstance(
      UnusedPrivateParameter unused) {
    return new ProfileTaskFinishReport();
  }

  @Override
  public final com.google.protobuf.UnknownFieldSet
  getUnknownFields() {
    return this.unknownFields;
  }
  private ProfileTaskFinishReport(
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

            service_ = s;
            break;
          }
          case 18: {
            String s = input.readStringRequireUtf8();

            serviceInstance_ = s;
            break;
          }
          case 26: {
            String s = input.readStringRequireUtf8();

            taskId_ = s;
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
    return Profile.internal_static_skywalking_v3_ProfileTaskFinishReport_descriptor;
  }

  @Override
  protected FieldAccessorTable
      internalGetFieldAccessorTable() {
    return Profile.internal_static_skywalking_v3_ProfileTaskFinishReport_fieldAccessorTable
        .ensureFieldAccessorsInitialized(
            ProfileTaskFinishReport.class, Builder.class);
  }

  public static final int SERVICE_FIELD_NUMBER = 1;
  private volatile Object service_;
  /**
   * <pre>
   * current sniffer information
   * </pre>
   *
   * <code>string service = 1;</code>
   * @return The service.
   */
  @Override
  public String getService() {
    Object ref = service_;
    if (ref instanceof String) {
      return (String) ref;
    } else {
      com.google.protobuf.ByteString bs =
          (com.google.protobuf.ByteString) ref;
      String s = bs.toStringUtf8();
      service_ = s;
      return s;
    }
  }
  /**
   * <pre>
   * current sniffer information
   * </pre>
   *
   * <code>string service = 1;</code>
   * @return The bytes for service.
   */
  @Override
  public com.google.protobuf.ByteString
      getServiceBytes() {
    Object ref = service_;
    if (ref instanceof String) {
      com.google.protobuf.ByteString b =
          com.google.protobuf.ByteString.copyFromUtf8(
              (String) ref);
      service_ = b;
      return b;
    } else {
      return (com.google.protobuf.ByteString) ref;
    }
  }

  public static final int SERVICEINSTANCE_FIELD_NUMBER = 2;
  private volatile Object serviceInstance_;
  /**
   * <code>string serviceInstance = 2;</code>
   * @return The serviceInstance.
   */
  @Override
  public String getServiceInstance() {
    Object ref = serviceInstance_;
    if (ref instanceof String) {
      return (String) ref;
    } else {
      com.google.protobuf.ByteString bs =
          (com.google.protobuf.ByteString) ref;
      String s = bs.toStringUtf8();
      serviceInstance_ = s;
      return s;
    }
  }
  /**
   * <code>string serviceInstance = 2;</code>
   * @return The bytes for serviceInstance.
   */
  @Override
  public com.google.protobuf.ByteString
      getServiceInstanceBytes() {
    Object ref = serviceInstance_;
    if (ref instanceof String) {
      com.google.protobuf.ByteString b =
          com.google.protobuf.ByteString.copyFromUtf8(
              (String) ref);
      serviceInstance_ = b;
      return b;
    } else {
      return (com.google.protobuf.ByteString) ref;
    }
  }

  public static final int TASKID_FIELD_NUMBER = 3;
  private volatile Object taskId_;
  /**
   * <pre>
   * profile task
   * </pre>
   *
   * <code>string taskId = 3;</code>
   * @return The taskId.
   */
  @Override
  public String getTaskId() {
    Object ref = taskId_;
    if (ref instanceof String) {
      return (String) ref;
    } else {
      com.google.protobuf.ByteString bs =
          (com.google.protobuf.ByteString) ref;
      String s = bs.toStringUtf8();
      taskId_ = s;
      return s;
    }
  }
  /**
   * <pre>
   * profile task
   * </pre>
   *
   * <code>string taskId = 3;</code>
   * @return The bytes for taskId.
   */
  @Override
  public com.google.protobuf.ByteString
      getTaskIdBytes() {
    Object ref = taskId_;
    if (ref instanceof String) {
      com.google.protobuf.ByteString b =
          com.google.protobuf.ByteString.copyFromUtf8(
              (String) ref);
      taskId_ = b;
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
    if (!com.google.protobuf.GeneratedMessageV3.isStringEmpty(service_)) {
      com.google.protobuf.GeneratedMessageV3.writeString(output, 1, service_);
    }
    if (!com.google.protobuf.GeneratedMessageV3.isStringEmpty(serviceInstance_)) {
      com.google.protobuf.GeneratedMessageV3.writeString(output, 2, serviceInstance_);
    }
    if (!com.google.protobuf.GeneratedMessageV3.isStringEmpty(taskId_)) {
      com.google.protobuf.GeneratedMessageV3.writeString(output, 3, taskId_);
    }
    unknownFields.writeTo(output);
  }

  @Override
  public int getSerializedSize() {
    int size = memoizedSize;
    if (size != -1) return size;

    size = 0;
    if (!com.google.protobuf.GeneratedMessageV3.isStringEmpty(service_)) {
      size += com.google.protobuf.GeneratedMessageV3.computeStringSize(1, service_);
    }
    if (!com.google.protobuf.GeneratedMessageV3.isStringEmpty(serviceInstance_)) {
      size += com.google.protobuf.GeneratedMessageV3.computeStringSize(2, serviceInstance_);
    }
    if (!com.google.protobuf.GeneratedMessageV3.isStringEmpty(taskId_)) {
      size += com.google.protobuf.GeneratedMessageV3.computeStringSize(3, taskId_);
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
    if (!(obj instanceof ProfileTaskFinishReport)) {
      return super.equals(obj);
    }
    ProfileTaskFinishReport other = (ProfileTaskFinishReport) obj;

    if (!getService()
        .equals(other.getService())) return false;
    if (!getServiceInstance()
        .equals(other.getServiceInstance())) return false;
    if (!getTaskId()
        .equals(other.getTaskId())) return false;
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
    hash = (37 * hash) + SERVICE_FIELD_NUMBER;
    hash = (53 * hash) + getService().hashCode();
    hash = (37 * hash) + SERVICEINSTANCE_FIELD_NUMBER;
    hash = (53 * hash) + getServiceInstance().hashCode();
    hash = (37 * hash) + TASKID_FIELD_NUMBER;
    hash = (53 * hash) + getTaskId().hashCode();
    hash = (29 * hash) + unknownFields.hashCode();
    memoizedHashCode = hash;
    return hash;
  }

  public static ProfileTaskFinishReport parseFrom(
      java.nio.ByteBuffer data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static ProfileTaskFinishReport parseFrom(
      java.nio.ByteBuffer data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static ProfileTaskFinishReport parseFrom(
      com.google.protobuf.ByteString data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static ProfileTaskFinishReport parseFrom(
      com.google.protobuf.ByteString data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static ProfileTaskFinishReport parseFrom(byte[] data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static ProfileTaskFinishReport parseFrom(
      byte[] data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static ProfileTaskFinishReport parseFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static ProfileTaskFinishReport parseFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input, extensionRegistry);
  }
  public static ProfileTaskFinishReport parseDelimitedFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input);
  }
  public static ProfileTaskFinishReport parseDelimitedFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
  }
  public static ProfileTaskFinishReport parseFrom(
      com.google.protobuf.CodedInputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static ProfileTaskFinishReport parseFrom(
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
  public static Builder newBuilder(ProfileTaskFinishReport prototype) {
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
   * profile task finished report
   * </pre>
   *
   * Protobuf type {@code skywalking.v3.ProfileTaskFinishReport}
   */
  public static final class Builder extends
      com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements
      // @@protoc_insertion_point(builder_implements:skywalking.v3.ProfileTaskFinishReport)
      ProfileTaskFinishReportOrBuilder {
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return Profile.internal_static_skywalking_v3_ProfileTaskFinishReport_descriptor;
    }

    @Override
    protected FieldAccessorTable
        internalGetFieldAccessorTable() {
      return Profile.internal_static_skywalking_v3_ProfileTaskFinishReport_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              ProfileTaskFinishReport.class, Builder.class);
    }

    // Construct using org.apache.skywalking.apm.network.language.profile.v3.ProfileTaskFinishReport.newBuilder()
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
      service_ = "";

      serviceInstance_ = "";

      taskId_ = "";

      return this;
    }

    @Override
    public com.google.protobuf.Descriptors.Descriptor
        getDescriptorForType() {
      return Profile.internal_static_skywalking_v3_ProfileTaskFinishReport_descriptor;
    }

    @Override
    public ProfileTaskFinishReport getDefaultInstanceForType() {
      return ProfileTaskFinishReport.getDefaultInstance();
    }

    @Override
    public ProfileTaskFinishReport build() {
      ProfileTaskFinishReport result = buildPartial();
      if (!result.isInitialized()) {
        throw newUninitializedMessageException(result);
      }
      return result;
    }

    @Override
    public ProfileTaskFinishReport buildPartial() {
      ProfileTaskFinishReport result = new ProfileTaskFinishReport(this);
      result.service_ = service_;
      result.serviceInstance_ = serviceInstance_;
      result.taskId_ = taskId_;
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
      if (other instanceof ProfileTaskFinishReport) {
        return mergeFrom((ProfileTaskFinishReport)other);
      } else {
        super.mergeFrom(other);
        return this;
      }
    }

    public Builder mergeFrom(ProfileTaskFinishReport other) {
      if (other == ProfileTaskFinishReport.getDefaultInstance()) return this;
      if (!other.getService().isEmpty()) {
        service_ = other.service_;
        onChanged();
      }
      if (!other.getServiceInstance().isEmpty()) {
        serviceInstance_ = other.serviceInstance_;
        onChanged();
      }
      if (!other.getTaskId().isEmpty()) {
        taskId_ = other.taskId_;
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
      ProfileTaskFinishReport parsedMessage = null;
      try {
        parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
      } catch (com.google.protobuf.InvalidProtocolBufferException e) {
        parsedMessage = (ProfileTaskFinishReport) e.getUnfinishedMessage();
        throw e.unwrapIOException();
      } finally {
        if (parsedMessage != null) {
          mergeFrom(parsedMessage);
        }
      }
      return this;
    }

    private Object service_ = "";
    /**
     * <pre>
     * current sniffer information
     * </pre>
     *
     * <code>string service = 1;</code>
     * @return The service.
     */
    public String getService() {
      Object ref = service_;
      if (!(ref instanceof String)) {
        com.google.protobuf.ByteString bs =
            (com.google.protobuf.ByteString) ref;
        String s = bs.toStringUtf8();
        service_ = s;
        return s;
      } else {
        return (String) ref;
      }
    }
    /**
     * <pre>
     * current sniffer information
     * </pre>
     *
     * <code>string service = 1;</code>
     * @return The bytes for service.
     */
    public com.google.protobuf.ByteString
        getServiceBytes() {
      Object ref = service_;
      if (ref instanceof String) {
        com.google.protobuf.ByteString b =
            com.google.protobuf.ByteString.copyFromUtf8(
                (String) ref);
        service_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }
    /**
     * <pre>
     * current sniffer information
     * </pre>
     *
     * <code>string service = 1;</code>
     * @param value The service to set.
     * @return This builder for chaining.
     */
    public Builder setService(
        String value) {
      if (value == null) {
    throw new NullPointerException();
  }

      service_ = value;
      onChanged();
      return this;
    }
    /**
     * <pre>
     * current sniffer information
     * </pre>
     *
     * <code>string service = 1;</code>
     * @return This builder for chaining.
     */
    public Builder clearService() {

      service_ = getDefaultInstance().getService();
      onChanged();
      return this;
    }
    /**
     * <pre>
     * current sniffer information
     * </pre>
     *
     * <code>string service = 1;</code>
     * @param value The bytes for service to set.
     * @return This builder for chaining.
     */
    public Builder setServiceBytes(
        com.google.protobuf.ByteString value) {
      if (value == null) {
    throw new NullPointerException();
  }
  checkByteStringIsUtf8(value);

      service_ = value;
      onChanged();
      return this;
    }

    private Object serviceInstance_ = "";
    /**
     * <code>string serviceInstance = 2;</code>
     * @return The serviceInstance.
     */
    public String getServiceInstance() {
      Object ref = serviceInstance_;
      if (!(ref instanceof String)) {
        com.google.protobuf.ByteString bs =
            (com.google.protobuf.ByteString) ref;
        String s = bs.toStringUtf8();
        serviceInstance_ = s;
        return s;
      } else {
        return (String) ref;
      }
    }
    /**
     * <code>string serviceInstance = 2;</code>
     * @return The bytes for serviceInstance.
     */
    public com.google.protobuf.ByteString
        getServiceInstanceBytes() {
      Object ref = serviceInstance_;
      if (ref instanceof String) {
        com.google.protobuf.ByteString b =
            com.google.protobuf.ByteString.copyFromUtf8(
                (String) ref);
        serviceInstance_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }
    /**
     * <code>string serviceInstance = 2;</code>
     * @param value The serviceInstance to set.
     * @return This builder for chaining.
     */
    public Builder setServiceInstance(
        String value) {
      if (value == null) {
    throw new NullPointerException();
  }

      serviceInstance_ = value;
      onChanged();
      return this;
    }
    /**
     * <code>string serviceInstance = 2;</code>
     * @return This builder for chaining.
     */
    public Builder clearServiceInstance() {

      serviceInstance_ = getDefaultInstance().getServiceInstance();
      onChanged();
      return this;
    }
    /**
     * <code>string serviceInstance = 2;</code>
     * @param value The bytes for serviceInstance to set.
     * @return This builder for chaining.
     */
    public Builder setServiceInstanceBytes(
        com.google.protobuf.ByteString value) {
      if (value == null) {
    throw new NullPointerException();
  }
  checkByteStringIsUtf8(value);

      serviceInstance_ = value;
      onChanged();
      return this;
    }

    private Object taskId_ = "";
    /**
     * <pre>
     * profile task
     * </pre>
     *
     * <code>string taskId = 3;</code>
     * @return The taskId.
     */
    public String getTaskId() {
      Object ref = taskId_;
      if (!(ref instanceof String)) {
        com.google.protobuf.ByteString bs =
            (com.google.protobuf.ByteString) ref;
        String s = bs.toStringUtf8();
        taskId_ = s;
        return s;
      } else {
        return (String) ref;
      }
    }
    /**
     * <pre>
     * profile task
     * </pre>
     *
     * <code>string taskId = 3;</code>
     * @return The bytes for taskId.
     */
    public com.google.protobuf.ByteString
        getTaskIdBytes() {
      Object ref = taskId_;
      if (ref instanceof String) {
        com.google.protobuf.ByteString b =
            com.google.protobuf.ByteString.copyFromUtf8(
                (String) ref);
        taskId_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }
    /**
     * <pre>
     * profile task
     * </pre>
     *
     * <code>string taskId = 3;</code>
     * @param value The taskId to set.
     * @return This builder for chaining.
     */
    public Builder setTaskId(
        String value) {
      if (value == null) {
    throw new NullPointerException();
  }

      taskId_ = value;
      onChanged();
      return this;
    }
    /**
     * <pre>
     * profile task
     * </pre>
     *
     * <code>string taskId = 3;</code>
     * @return This builder for chaining.
     */
    public Builder clearTaskId() {

      taskId_ = getDefaultInstance().getTaskId();
      onChanged();
      return this;
    }
    /**
     * <pre>
     * profile task
     * </pre>
     *
     * <code>string taskId = 3;</code>
     * @param value The bytes for taskId to set.
     * @return This builder for chaining.
     */
    public Builder setTaskIdBytes(
        com.google.protobuf.ByteString value) {
      if (value == null) {
    throw new NullPointerException();
  }
  checkByteStringIsUtf8(value);

      taskId_ = value;
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


    // @@protoc_insertion_point(builder_scope:skywalking.v3.ProfileTaskFinishReport)
  }

  // @@protoc_insertion_point(class_scope:skywalking.v3.ProfileTaskFinishReport)
  private static final ProfileTaskFinishReport DEFAULT_INSTANCE;
  static {
    DEFAULT_INSTANCE = new ProfileTaskFinishReport();
  }

  public static ProfileTaskFinishReport getDefaultInstance() {
    return DEFAULT_INSTANCE;
  }

  private static final com.google.protobuf.Parser<ProfileTaskFinishReport>
      PARSER = new com.google.protobuf.AbstractParser<ProfileTaskFinishReport>() {
    @Override
    public ProfileTaskFinishReport parsePartialFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return new ProfileTaskFinishReport(input, extensionRegistry);
    }
  };

  public static com.google.protobuf.Parser<ProfileTaskFinishReport> parser() {
    return PARSER;
  }

  @Override
  public com.google.protobuf.Parser<ProfileTaskFinishReport> getParserForType() {
    return PARSER;
  }

  @Override
  public ProfileTaskFinishReport getDefaultInstanceForType() {
    return DEFAULT_INSTANCE;
  }

}

