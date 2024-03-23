// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: ebpf/accesslog.proto

package org.apache.skywalking.apm.network.ebpf.accesslog.v3;

/**
 * Protobuf type {@code skywalking.v3.ConnectionAddress}
 */
public final class ConnectionAddress extends
    com.google.protobuf.GeneratedMessageV3 implements
    // @@protoc_insertion_point(message_implements:skywalking.v3.ConnectionAddress)
    ConnectionAddressOrBuilder {
private static final long serialVersionUID = 0L;
  // Use ConnectionAddress.newBuilder() to construct.
  private ConnectionAddress(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
    super(builder);
  }
  private ConnectionAddress() {
  }

  @Override
  @SuppressWarnings({"unused"})
  protected Object newInstance(
      UnusedPrivateParameter unused) {
    return new ConnectionAddress();
  }

  @Override
  public final com.google.protobuf.UnknownFieldSet
  getUnknownFields() {
    return this.unknownFields;
  }
  private ConnectionAddress(
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
            KubernetesProcessAddress.Builder subBuilder = null;
            if (addressCase_ == 1) {
              subBuilder = ((KubernetesProcessAddress) address_).toBuilder();
            }
            address_ =
                input.readMessage(KubernetesProcessAddress.parser(), extensionRegistry);
            if (subBuilder != null) {
              subBuilder.mergeFrom((KubernetesProcessAddress) address_);
              address_ = subBuilder.buildPartial();
            }
            addressCase_ = 1;
            break;
          }
          case 18: {
            IPAddress.Builder subBuilder = null;
            if (addressCase_ == 2) {
              subBuilder = ((IPAddress) address_).toBuilder();
            }
            address_ =
                input.readMessage(IPAddress.parser(), extensionRegistry);
            if (subBuilder != null) {
              subBuilder.mergeFrom((IPAddress) address_);
              address_ = subBuilder.buildPartial();
            }
            addressCase_ = 2;
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
    return Accesslog.internal_static_skywalking_v3_ConnectionAddress_descriptor;
  }

  @Override
  protected FieldAccessorTable
      internalGetFieldAccessorTable() {
    return Accesslog.internal_static_skywalking_v3_ConnectionAddress_fieldAccessorTable
        .ensureFieldAccessorsInitialized(
            ConnectionAddress.class, Builder.class);
  }

  private int addressCase_ = 0;
  private Object address_;
  public enum AddressCase
      implements com.google.protobuf.Internal.EnumLite,
          InternalOneOfEnum {
    KUBERNETES(1),
    IP(2),
    ADDRESS_NOT_SET(0);
    private final int value;
    private AddressCase(int value) {
      this.value = value;
    }
    /**
     * @param value The number of the enum to look for.
     * @return The enum associated with the given number.
     * @deprecated Use {@link #forNumber(int)} instead.
     */
    @Deprecated
    public static AddressCase valueOf(int value) {
      return forNumber(value);
    }

    public static AddressCase forNumber(int value) {
      switch (value) {
        case 1: return KUBERNETES;
        case 2: return IP;
        case 0: return ADDRESS_NOT_SET;
        default: return null;
      }
    }
    public int getNumber() {
      return this.value;
    }
  };

  public AddressCase
  getAddressCase() {
    return AddressCase.forNumber(
        addressCase_);
  }

  public static final int KUBERNETES_FIELD_NUMBER = 1;
  /**
   * <pre>
   * if the address is monitored under the local machine, then return the kubernetes
   * </pre>
   *
   * <code>.skywalking.v3.KubernetesProcessAddress kubernetes = 1;</code>
   * @return Whether the kubernetes field is set.
   */
  @Override
  public boolean hasKubernetes() {
    return addressCase_ == 1;
  }
  /**
   * <pre>
   * if the address is monitored under the local machine, then return the kubernetes
   * </pre>
   *
   * <code>.skywalking.v3.KubernetesProcessAddress kubernetes = 1;</code>
   * @return The kubernetes.
   */
  @Override
  public KubernetesProcessAddress getKubernetes() {
    if (addressCase_ == 1) {
       return (KubernetesProcessAddress) address_;
    }
    return KubernetesProcessAddress.getDefaultInstance();
  }
  /**
   * <pre>
   * if the address is monitored under the local machine, then return the kubernetes
   * </pre>
   *
   * <code>.skywalking.v3.KubernetesProcessAddress kubernetes = 1;</code>
   */
  @Override
  public KubernetesProcessAddressOrBuilder getKubernetesOrBuilder() {
    if (addressCase_ == 1) {
       return (KubernetesProcessAddress) address_;
    }
    return KubernetesProcessAddress.getDefaultInstance();
  }

  public static final int IP_FIELD_NUMBER = 2;
  /**
   * <pre>
   * if the address cannot be aware, then return the ip address
   * </pre>
   *
   * <code>.skywalking.v3.IPAddress ip = 2;</code>
   * @return Whether the ip field is set.
   */
  @Override
  public boolean hasIp() {
    return addressCase_ == 2;
  }
  /**
   * <pre>
   * if the address cannot be aware, then return the ip address
   * </pre>
   *
   * <code>.skywalking.v3.IPAddress ip = 2;</code>
   * @return The ip.
   */
  @Override
  public IPAddress getIp() {
    if (addressCase_ == 2) {
       return (IPAddress) address_;
    }
    return IPAddress.getDefaultInstance();
  }
  /**
   * <pre>
   * if the address cannot be aware, then return the ip address
   * </pre>
   *
   * <code>.skywalking.v3.IPAddress ip = 2;</code>
   */
  @Override
  public IPAddressOrBuilder getIpOrBuilder() {
    if (addressCase_ == 2) {
       return (IPAddress) address_;
    }
    return IPAddress.getDefaultInstance();
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
    if (addressCase_ == 1) {
      output.writeMessage(1, (KubernetesProcessAddress) address_);
    }
    if (addressCase_ == 2) {
      output.writeMessage(2, (IPAddress) address_);
    }
    unknownFields.writeTo(output);
  }

  @Override
  public int getSerializedSize() {
    int size = memoizedSize;
    if (size != -1) return size;

    size = 0;
    if (addressCase_ == 1) {
      size += com.google.protobuf.CodedOutputStream
        .computeMessageSize(1, (KubernetesProcessAddress) address_);
    }
    if (addressCase_ == 2) {
      size += com.google.protobuf.CodedOutputStream
        .computeMessageSize(2, (IPAddress) address_);
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
    if (!(obj instanceof ConnectionAddress)) {
      return super.equals(obj);
    }
    ConnectionAddress other = (ConnectionAddress) obj;

    if (!getAddressCase().equals(other.getAddressCase())) return false;
    switch (addressCase_) {
      case 1:
        if (!getKubernetes()
            .equals(other.getKubernetes())) return false;
        break;
      case 2:
        if (!getIp()
            .equals(other.getIp())) return false;
        break;
      case 0:
      default:
    }
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
    switch (addressCase_) {
      case 1:
        hash = (37 * hash) + KUBERNETES_FIELD_NUMBER;
        hash = (53 * hash) + getKubernetes().hashCode();
        break;
      case 2:
        hash = (37 * hash) + IP_FIELD_NUMBER;
        hash = (53 * hash) + getIp().hashCode();
        break;
      case 0:
      default:
    }
    hash = (29 * hash) + unknownFields.hashCode();
    memoizedHashCode = hash;
    return hash;
  }

  public static ConnectionAddress parseFrom(
      java.nio.ByteBuffer data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static ConnectionAddress parseFrom(
      java.nio.ByteBuffer data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static ConnectionAddress parseFrom(
      com.google.protobuf.ByteString data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static ConnectionAddress parseFrom(
      com.google.protobuf.ByteString data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static ConnectionAddress parseFrom(byte[] data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static ConnectionAddress parseFrom(
      byte[] data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static ConnectionAddress parseFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static ConnectionAddress parseFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input, extensionRegistry);
  }
  public static ConnectionAddress parseDelimitedFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input);
  }
  public static ConnectionAddress parseDelimitedFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
  }
  public static ConnectionAddress parseFrom(
      com.google.protobuf.CodedInputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static ConnectionAddress parseFrom(
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
  public static Builder newBuilder(ConnectionAddress prototype) {
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
   * Protobuf type {@code skywalking.v3.ConnectionAddress}
   */
  public static final class Builder extends
      com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements
      // @@protoc_insertion_point(builder_implements:skywalking.v3.ConnectionAddress)
      ConnectionAddressOrBuilder {
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return Accesslog.internal_static_skywalking_v3_ConnectionAddress_descriptor;
    }

    @Override
    protected FieldAccessorTable
        internalGetFieldAccessorTable() {
      return Accesslog.internal_static_skywalking_v3_ConnectionAddress_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              ConnectionAddress.class, Builder.class);
    }

    // Construct using org.apache.skywalking.apm.network.ebpf.accesslog.v3.ConnectionAddress.newBuilder()
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
      addressCase_ = 0;
      address_ = null;
      return this;
    }

    @Override
    public com.google.protobuf.Descriptors.Descriptor
        getDescriptorForType() {
      return Accesslog.internal_static_skywalking_v3_ConnectionAddress_descriptor;
    }

    @Override
    public ConnectionAddress getDefaultInstanceForType() {
      return ConnectionAddress.getDefaultInstance();
    }

    @Override
    public ConnectionAddress build() {
      ConnectionAddress result = buildPartial();
      if (!result.isInitialized()) {
        throw newUninitializedMessageException(result);
      }
      return result;
    }

    @Override
    public ConnectionAddress buildPartial() {
      ConnectionAddress result = new ConnectionAddress(this);
      if (addressCase_ == 1) {
        if (kubernetesBuilder_ == null) {
          result.address_ = address_;
        } else {
          result.address_ = kubernetesBuilder_.build();
        }
      }
      if (addressCase_ == 2) {
        if (ipBuilder_ == null) {
          result.address_ = address_;
        } else {
          result.address_ = ipBuilder_.build();
        }
      }
      result.addressCase_ = addressCase_;
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
      if (other instanceof ConnectionAddress) {
        return mergeFrom((ConnectionAddress)other);
      } else {
        super.mergeFrom(other);
        return this;
      }
    }

    public Builder mergeFrom(ConnectionAddress other) {
      if (other == ConnectionAddress.getDefaultInstance()) return this;
      switch (other.getAddressCase()) {
        case KUBERNETES: {
          mergeKubernetes(other.getKubernetes());
          break;
        }
        case IP: {
          mergeIp(other.getIp());
          break;
        }
        case ADDRESS_NOT_SET: {
          break;
        }
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
      ConnectionAddress parsedMessage = null;
      try {
        parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
      } catch (com.google.protobuf.InvalidProtocolBufferException e) {
        parsedMessage = (ConnectionAddress) e.getUnfinishedMessage();
        throw e.unwrapIOException();
      } finally {
        if (parsedMessage != null) {
          mergeFrom(parsedMessage);
        }
      }
      return this;
    }
    private int addressCase_ = 0;
    private Object address_;
    public AddressCase
        getAddressCase() {
      return AddressCase.forNumber(
          addressCase_);
    }

    public Builder clearAddress() {
      addressCase_ = 0;
      address_ = null;
      onChanged();
      return this;
    }


    private com.google.protobuf.SingleFieldBuilderV3<
        KubernetesProcessAddress, KubernetesProcessAddress.Builder, KubernetesProcessAddressOrBuilder> kubernetesBuilder_;
    /**
     * <pre>
     * if the address is monitored under the local machine, then return the kubernetes
     * </pre>
     *
     * <code>.skywalking.v3.KubernetesProcessAddress kubernetes = 1;</code>
     * @return Whether the kubernetes field is set.
     */
    @Override
    public boolean hasKubernetes() {
      return addressCase_ == 1;
    }
    /**
     * <pre>
     * if the address is monitored under the local machine, then return the kubernetes
     * </pre>
     *
     * <code>.skywalking.v3.KubernetesProcessAddress kubernetes = 1;</code>
     * @return The kubernetes.
     */
    @Override
    public KubernetesProcessAddress getKubernetes() {
      if (kubernetesBuilder_ == null) {
        if (addressCase_ == 1) {
          return (KubernetesProcessAddress) address_;
        }
        return KubernetesProcessAddress.getDefaultInstance();
      } else {
        if (addressCase_ == 1) {
          return kubernetesBuilder_.getMessage();
        }
        return KubernetesProcessAddress.getDefaultInstance();
      }
    }
    /**
     * <pre>
     * if the address is monitored under the local machine, then return the kubernetes
     * </pre>
     *
     * <code>.skywalking.v3.KubernetesProcessAddress kubernetes = 1;</code>
     */
    public Builder setKubernetes(KubernetesProcessAddress value) {
      if (kubernetesBuilder_ == null) {
        if (value == null) {
          throw new NullPointerException();
        }
        address_ = value;
        onChanged();
      } else {
        kubernetesBuilder_.setMessage(value);
      }
      addressCase_ = 1;
      return this;
    }
    /**
     * <pre>
     * if the address is monitored under the local machine, then return the kubernetes
     * </pre>
     *
     * <code>.skywalking.v3.KubernetesProcessAddress kubernetes = 1;</code>
     */
    public Builder setKubernetes(
        KubernetesProcessAddress.Builder builderForValue) {
      if (kubernetesBuilder_ == null) {
        address_ = builderForValue.build();
        onChanged();
      } else {
        kubernetesBuilder_.setMessage(builderForValue.build());
      }
      addressCase_ = 1;
      return this;
    }
    /**
     * <pre>
     * if the address is monitored under the local machine, then return the kubernetes
     * </pre>
     *
     * <code>.skywalking.v3.KubernetesProcessAddress kubernetes = 1;</code>
     */
    public Builder mergeKubernetes(KubernetesProcessAddress value) {
      if (kubernetesBuilder_ == null) {
        if (addressCase_ == 1 &&
            address_ != KubernetesProcessAddress.getDefaultInstance()) {
          address_ = KubernetesProcessAddress.newBuilder((KubernetesProcessAddress) address_)
              .mergeFrom(value).buildPartial();
        } else {
          address_ = value;
        }
        onChanged();
      } else {
        if (addressCase_ == 1) {
          kubernetesBuilder_.mergeFrom(value);
        }
        kubernetesBuilder_.setMessage(value);
      }
      addressCase_ = 1;
      return this;
    }
    /**
     * <pre>
     * if the address is monitored under the local machine, then return the kubernetes
     * </pre>
     *
     * <code>.skywalking.v3.KubernetesProcessAddress kubernetes = 1;</code>
     */
    public Builder clearKubernetes() {
      if (kubernetesBuilder_ == null) {
        if (addressCase_ == 1) {
          addressCase_ = 0;
          address_ = null;
          onChanged();
        }
      } else {
        if (addressCase_ == 1) {
          addressCase_ = 0;
          address_ = null;
        }
        kubernetesBuilder_.clear();
      }
      return this;
    }
    /**
     * <pre>
     * if the address is monitored under the local machine, then return the kubernetes
     * </pre>
     *
     * <code>.skywalking.v3.KubernetesProcessAddress kubernetes = 1;</code>
     */
    public KubernetesProcessAddress.Builder getKubernetesBuilder() {
      return getKubernetesFieldBuilder().getBuilder();
    }
    /**
     * <pre>
     * if the address is monitored under the local machine, then return the kubernetes
     * </pre>
     *
     * <code>.skywalking.v3.KubernetesProcessAddress kubernetes = 1;</code>
     */
    @Override
    public KubernetesProcessAddressOrBuilder getKubernetesOrBuilder() {
      if ((addressCase_ == 1) && (kubernetesBuilder_ != null)) {
        return kubernetesBuilder_.getMessageOrBuilder();
      } else {
        if (addressCase_ == 1) {
          return (KubernetesProcessAddress) address_;
        }
        return KubernetesProcessAddress.getDefaultInstance();
      }
    }
    /**
     * <pre>
     * if the address is monitored under the local machine, then return the kubernetes
     * </pre>
     *
     * <code>.skywalking.v3.KubernetesProcessAddress kubernetes = 1;</code>
     */
    private com.google.protobuf.SingleFieldBuilderV3<
        KubernetesProcessAddress, KubernetesProcessAddress.Builder, KubernetesProcessAddressOrBuilder>
        getKubernetesFieldBuilder() {
      if (kubernetesBuilder_ == null) {
        if (!(addressCase_ == 1)) {
          address_ = KubernetesProcessAddress.getDefaultInstance();
        }
        kubernetesBuilder_ = new com.google.protobuf.SingleFieldBuilderV3<
            KubernetesProcessAddress, KubernetesProcessAddress.Builder, KubernetesProcessAddressOrBuilder>(
                (KubernetesProcessAddress) address_,
                getParentForChildren(),
                isClean());
        address_ = null;
      }
      addressCase_ = 1;
      onChanged();;
      return kubernetesBuilder_;
    }

    private com.google.protobuf.SingleFieldBuilderV3<
        IPAddress, IPAddress.Builder, IPAddressOrBuilder> ipBuilder_;
    /**
     * <pre>
     * if the address cannot be aware, then return the ip address
     * </pre>
     *
     * <code>.skywalking.v3.IPAddress ip = 2;</code>
     * @return Whether the ip field is set.
     */
    @Override
    public boolean hasIp() {
      return addressCase_ == 2;
    }
    /**
     * <pre>
     * if the address cannot be aware, then return the ip address
     * </pre>
     *
     * <code>.skywalking.v3.IPAddress ip = 2;</code>
     * @return The ip.
     */
    @Override
    public IPAddress getIp() {
      if (ipBuilder_ == null) {
        if (addressCase_ == 2) {
          return (IPAddress) address_;
        }
        return IPAddress.getDefaultInstance();
      } else {
        if (addressCase_ == 2) {
          return ipBuilder_.getMessage();
        }
        return IPAddress.getDefaultInstance();
      }
    }
    /**
     * <pre>
     * if the address cannot be aware, then return the ip address
     * </pre>
     *
     * <code>.skywalking.v3.IPAddress ip = 2;</code>
     */
    public Builder setIp(IPAddress value) {
      if (ipBuilder_ == null) {
        if (value == null) {
          throw new NullPointerException();
        }
        address_ = value;
        onChanged();
      } else {
        ipBuilder_.setMessage(value);
      }
      addressCase_ = 2;
      return this;
    }
    /**
     * <pre>
     * if the address cannot be aware, then return the ip address
     * </pre>
     *
     * <code>.skywalking.v3.IPAddress ip = 2;</code>
     */
    public Builder setIp(
        IPAddress.Builder builderForValue) {
      if (ipBuilder_ == null) {
        address_ = builderForValue.build();
        onChanged();
      } else {
        ipBuilder_.setMessage(builderForValue.build());
      }
      addressCase_ = 2;
      return this;
    }
    /**
     * <pre>
     * if the address cannot be aware, then return the ip address
     * </pre>
     *
     * <code>.skywalking.v3.IPAddress ip = 2;</code>
     */
    public Builder mergeIp(IPAddress value) {
      if (ipBuilder_ == null) {
        if (addressCase_ == 2 &&
            address_ != IPAddress.getDefaultInstance()) {
          address_ = IPAddress.newBuilder((IPAddress) address_)
              .mergeFrom(value).buildPartial();
        } else {
          address_ = value;
        }
        onChanged();
      } else {
        if (addressCase_ == 2) {
          ipBuilder_.mergeFrom(value);
        }
        ipBuilder_.setMessage(value);
      }
      addressCase_ = 2;
      return this;
    }
    /**
     * <pre>
     * if the address cannot be aware, then return the ip address
     * </pre>
     *
     * <code>.skywalking.v3.IPAddress ip = 2;</code>
     */
    public Builder clearIp() {
      if (ipBuilder_ == null) {
        if (addressCase_ == 2) {
          addressCase_ = 0;
          address_ = null;
          onChanged();
        }
      } else {
        if (addressCase_ == 2) {
          addressCase_ = 0;
          address_ = null;
        }
        ipBuilder_.clear();
      }
      return this;
    }
    /**
     * <pre>
     * if the address cannot be aware, then return the ip address
     * </pre>
     *
     * <code>.skywalking.v3.IPAddress ip = 2;</code>
     */
    public IPAddress.Builder getIpBuilder() {
      return getIpFieldBuilder().getBuilder();
    }
    /**
     * <pre>
     * if the address cannot be aware, then return the ip address
     * </pre>
     *
     * <code>.skywalking.v3.IPAddress ip = 2;</code>
     */
    @Override
    public IPAddressOrBuilder getIpOrBuilder() {
      if ((addressCase_ == 2) && (ipBuilder_ != null)) {
        return ipBuilder_.getMessageOrBuilder();
      } else {
        if (addressCase_ == 2) {
          return (IPAddress) address_;
        }
        return IPAddress.getDefaultInstance();
      }
    }
    /**
     * <pre>
     * if the address cannot be aware, then return the ip address
     * </pre>
     *
     * <code>.skywalking.v3.IPAddress ip = 2;</code>
     */
    private com.google.protobuf.SingleFieldBuilderV3<
        IPAddress, IPAddress.Builder, IPAddressOrBuilder>
        getIpFieldBuilder() {
      if (ipBuilder_ == null) {
        if (!(addressCase_ == 2)) {
          address_ = IPAddress.getDefaultInstance();
        }
        ipBuilder_ = new com.google.protobuf.SingleFieldBuilderV3<
            IPAddress, IPAddress.Builder, IPAddressOrBuilder>(
                (IPAddress) address_,
                getParentForChildren(),
                isClean());
        address_ = null;
      }
      addressCase_ = 2;
      onChanged();;
      return ipBuilder_;
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


    // @@protoc_insertion_point(builder_scope:skywalking.v3.ConnectionAddress)
  }

  // @@protoc_insertion_point(class_scope:skywalking.v3.ConnectionAddress)
  private static final ConnectionAddress DEFAULT_INSTANCE;
  static {
    DEFAULT_INSTANCE = new ConnectionAddress();
  }

  public static ConnectionAddress getDefaultInstance() {
    return DEFAULT_INSTANCE;
  }

  private static final com.google.protobuf.Parser<ConnectionAddress>
      PARSER = new com.google.protobuf.AbstractParser<ConnectionAddress>() {
    @Override
    public ConnectionAddress parsePartialFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return new ConnectionAddress(input, extensionRegistry);
    }
  };

  public static com.google.protobuf.Parser<ConnectionAddress> parser() {
    return PARSER;
  }

  @Override
  public com.google.protobuf.Parser<ConnectionAddress> getParserForType() {
    return PARSER;
  }

  @Override
  public ConnectionAddress getDefaultInstanceForType() {
    return DEFAULT_INSTANCE;
  }

}
