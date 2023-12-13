package org.apache.dubbo.sample.tri;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.44.1)",
    comments = "Source: greeter.proto")
@io.grpc.stub.annotations.GrpcGenerated
public final class GreeterGrpc {

  private GreeterGrpc() {}

  public static final String SERVICE_NAME = "org.apache.dubbo.sample.tri.Greeter";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<org.apache.dubbo.sample.tri.GreeterRequest,
      org.apache.dubbo.sample.tri.GreeterReply> getGreetMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "greet",
      requestType = org.apache.dubbo.sample.tri.GreeterRequest.class,
      responseType = org.apache.dubbo.sample.tri.GreeterReply.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<org.apache.dubbo.sample.tri.GreeterRequest,
      org.apache.dubbo.sample.tri.GreeterReply> getGreetMethod() {
    io.grpc.MethodDescriptor<org.apache.dubbo.sample.tri.GreeterRequest, org.apache.dubbo.sample.tri.GreeterReply> getGreetMethod;
    if ((getGreetMethod = GreeterGrpc.getGreetMethod) == null) {
      synchronized (GreeterGrpc.class) {
        if ((getGreetMethod = GreeterGrpc.getGreetMethod) == null) {
          GreeterGrpc.getGreetMethod = getGreetMethod =
              io.grpc.MethodDescriptor.<org.apache.dubbo.sample.tri.GreeterRequest, org.apache.dubbo.sample.tri.GreeterReply>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "greet"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  org.apache.dubbo.sample.tri.GreeterRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  org.apache.dubbo.sample.tri.GreeterReply.getDefaultInstance()))
              .setSchemaDescriptor(new GreeterMethodDescriptorSupplier("greet"))
              .build();
        }
      }
    }
    return getGreetMethod;
  }

  private static volatile io.grpc.MethodDescriptor<org.apache.dubbo.sample.tri.GreeterRequest,
      org.apache.dubbo.sample.tri.GreeterReply> getGreetClientStreamMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "greetClientStream",
      requestType = org.apache.dubbo.sample.tri.GreeterRequest.class,
      responseType = org.apache.dubbo.sample.tri.GreeterReply.class,
      methodType = io.grpc.MethodDescriptor.MethodType.CLIENT_STREAMING)
  public static io.grpc.MethodDescriptor<org.apache.dubbo.sample.tri.GreeterRequest,
      org.apache.dubbo.sample.tri.GreeterReply> getGreetClientStreamMethod() {
    io.grpc.MethodDescriptor<org.apache.dubbo.sample.tri.GreeterRequest, org.apache.dubbo.sample.tri.GreeterReply> getGreetClientStreamMethod;
    if ((getGreetClientStreamMethod = GreeterGrpc.getGreetClientStreamMethod) == null) {
      synchronized (GreeterGrpc.class) {
        if ((getGreetClientStreamMethod = GreeterGrpc.getGreetClientStreamMethod) == null) {
          GreeterGrpc.getGreetClientStreamMethod = getGreetClientStreamMethod =
              io.grpc.MethodDescriptor.<org.apache.dubbo.sample.tri.GreeterRequest, org.apache.dubbo.sample.tri.GreeterReply>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.CLIENT_STREAMING)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "greetClientStream"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  org.apache.dubbo.sample.tri.GreeterRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  org.apache.dubbo.sample.tri.GreeterReply.getDefaultInstance()))
              .setSchemaDescriptor(new GreeterMethodDescriptorSupplier("greetClientStream"))
              .build();
        }
      }
    }
    return getGreetClientStreamMethod;
  }

  private static volatile io.grpc.MethodDescriptor<org.apache.dubbo.sample.tri.GreeterRequest,
      org.apache.dubbo.sample.tri.GreeterReply> getGreetServerStreamMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "greetServerStream",
      requestType = org.apache.dubbo.sample.tri.GreeterRequest.class,
      responseType = org.apache.dubbo.sample.tri.GreeterReply.class,
      methodType = io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING)
  public static io.grpc.MethodDescriptor<org.apache.dubbo.sample.tri.GreeterRequest,
      org.apache.dubbo.sample.tri.GreeterReply> getGreetServerStreamMethod() {
    io.grpc.MethodDescriptor<org.apache.dubbo.sample.tri.GreeterRequest, org.apache.dubbo.sample.tri.GreeterReply> getGreetServerStreamMethod;
    if ((getGreetServerStreamMethod = GreeterGrpc.getGreetServerStreamMethod) == null) {
      synchronized (GreeterGrpc.class) {
        if ((getGreetServerStreamMethod = GreeterGrpc.getGreetServerStreamMethod) == null) {
          GreeterGrpc.getGreetServerStreamMethod = getGreetServerStreamMethod =
              io.grpc.MethodDescriptor.<org.apache.dubbo.sample.tri.GreeterRequest, org.apache.dubbo.sample.tri.GreeterReply>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "greetServerStream"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  org.apache.dubbo.sample.tri.GreeterRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  org.apache.dubbo.sample.tri.GreeterReply.getDefaultInstance()))
              .setSchemaDescriptor(new GreeterMethodDescriptorSupplier("greetServerStream"))
              .build();
        }
      }
    }
    return getGreetServerStreamMethod;
  }

  private static volatile io.grpc.MethodDescriptor<org.apache.dubbo.sample.tri.GreeterRequest,
      org.apache.dubbo.sample.tri.GreeterReply> getGreetStreamMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "greetStream",
      requestType = org.apache.dubbo.sample.tri.GreeterRequest.class,
      responseType = org.apache.dubbo.sample.tri.GreeterReply.class,
      methodType = io.grpc.MethodDescriptor.MethodType.BIDI_STREAMING)
  public static io.grpc.MethodDescriptor<org.apache.dubbo.sample.tri.GreeterRequest,
      org.apache.dubbo.sample.tri.GreeterReply> getGreetStreamMethod() {
    io.grpc.MethodDescriptor<org.apache.dubbo.sample.tri.GreeterRequest, org.apache.dubbo.sample.tri.GreeterReply> getGreetStreamMethod;
    if ((getGreetStreamMethod = GreeterGrpc.getGreetStreamMethod) == null) {
      synchronized (GreeterGrpc.class) {
        if ((getGreetStreamMethod = GreeterGrpc.getGreetStreamMethod) == null) {
          GreeterGrpc.getGreetStreamMethod = getGreetStreamMethod =
              io.grpc.MethodDescriptor.<org.apache.dubbo.sample.tri.GreeterRequest, org.apache.dubbo.sample.tri.GreeterReply>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.BIDI_STREAMING)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "greetStream"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  org.apache.dubbo.sample.tri.GreeterRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  org.apache.dubbo.sample.tri.GreeterReply.getDefaultInstance()))
              .setSchemaDescriptor(new GreeterMethodDescriptorSupplier("greetStream"))
              .build();
        }
      }
    }
    return getGreetStreamMethod;
  }

  private static volatile io.grpc.MethodDescriptor<org.apache.dubbo.sample.tri.GreeterRequest,
      org.apache.dubbo.sample.tri.GreeterReply> getUpperCaseGreetMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "UpperCaseGreet",
      requestType = org.apache.dubbo.sample.tri.GreeterRequest.class,
      responseType = org.apache.dubbo.sample.tri.GreeterReply.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<org.apache.dubbo.sample.tri.GreeterRequest,
      org.apache.dubbo.sample.tri.GreeterReply> getUpperCaseGreetMethod() {
    io.grpc.MethodDescriptor<org.apache.dubbo.sample.tri.GreeterRequest, org.apache.dubbo.sample.tri.GreeterReply> getUpperCaseGreetMethod;
    if ((getUpperCaseGreetMethod = GreeterGrpc.getUpperCaseGreetMethod) == null) {
      synchronized (GreeterGrpc.class) {
        if ((getUpperCaseGreetMethod = GreeterGrpc.getUpperCaseGreetMethod) == null) {
          GreeterGrpc.getUpperCaseGreetMethod = getUpperCaseGreetMethod =
              io.grpc.MethodDescriptor.<org.apache.dubbo.sample.tri.GreeterRequest, org.apache.dubbo.sample.tri.GreeterReply>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "UpperCaseGreet"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  org.apache.dubbo.sample.tri.GreeterRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  org.apache.dubbo.sample.tri.GreeterReply.getDefaultInstance()))
              .setSchemaDescriptor(new GreeterMethodDescriptorSupplier("UpperCaseGreet"))
              .build();
        }
      }
    }
    return getUpperCaseGreetMethod;
  }

  private static volatile io.grpc.MethodDescriptor<org.apache.dubbo.sample.tri.GreeterRequest,
      org.apache.dubbo.sample.tri.GreeterReply> getGreetWithAttachmentMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "greetWithAttachment",
      requestType = org.apache.dubbo.sample.tri.GreeterRequest.class,
      responseType = org.apache.dubbo.sample.tri.GreeterReply.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<org.apache.dubbo.sample.tri.GreeterRequest,
      org.apache.dubbo.sample.tri.GreeterReply> getGreetWithAttachmentMethod() {
    io.grpc.MethodDescriptor<org.apache.dubbo.sample.tri.GreeterRequest, org.apache.dubbo.sample.tri.GreeterReply> getGreetWithAttachmentMethod;
    if ((getGreetWithAttachmentMethod = GreeterGrpc.getGreetWithAttachmentMethod) == null) {
      synchronized (GreeterGrpc.class) {
        if ((getGreetWithAttachmentMethod = GreeterGrpc.getGreetWithAttachmentMethod) == null) {
          GreeterGrpc.getGreetWithAttachmentMethod = getGreetWithAttachmentMethod =
              io.grpc.MethodDescriptor.<org.apache.dubbo.sample.tri.GreeterRequest, org.apache.dubbo.sample.tri.GreeterReply>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "greetWithAttachment"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  org.apache.dubbo.sample.tri.GreeterRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  org.apache.dubbo.sample.tri.GreeterReply.getDefaultInstance()))
              .setSchemaDescriptor(new GreeterMethodDescriptorSupplier("greetWithAttachment"))
              .build();
        }
      }
    }
    return getGreetWithAttachmentMethod;
  }

  private static volatile io.grpc.MethodDescriptor<org.apache.dubbo.sample.tri.GreeterRequest,
      org.apache.dubbo.sample.tri.GreeterReply> getGreetReturnBigAttachmentMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "greetReturnBigAttachment",
      requestType = org.apache.dubbo.sample.tri.GreeterRequest.class,
      responseType = org.apache.dubbo.sample.tri.GreeterReply.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<org.apache.dubbo.sample.tri.GreeterRequest,
      org.apache.dubbo.sample.tri.GreeterReply> getGreetReturnBigAttachmentMethod() {
    io.grpc.MethodDescriptor<org.apache.dubbo.sample.tri.GreeterRequest, org.apache.dubbo.sample.tri.GreeterReply> getGreetReturnBigAttachmentMethod;
    if ((getGreetReturnBigAttachmentMethod = GreeterGrpc.getGreetReturnBigAttachmentMethod) == null) {
      synchronized (GreeterGrpc.class) {
        if ((getGreetReturnBigAttachmentMethod = GreeterGrpc.getGreetReturnBigAttachmentMethod) == null) {
          GreeterGrpc.getGreetReturnBigAttachmentMethod = getGreetReturnBigAttachmentMethod =
              io.grpc.MethodDescriptor.<org.apache.dubbo.sample.tri.GreeterRequest, org.apache.dubbo.sample.tri.GreeterReply>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "greetReturnBigAttachment"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  org.apache.dubbo.sample.tri.GreeterRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  org.apache.dubbo.sample.tri.GreeterReply.getDefaultInstance()))
              .setSchemaDescriptor(new GreeterMethodDescriptorSupplier("greetReturnBigAttachment"))
              .build();
        }
      }
    }
    return getGreetReturnBigAttachmentMethod;
  }

  private static volatile io.grpc.MethodDescriptor<org.apache.dubbo.sample.tri.GreeterRequest,
      org.apache.dubbo.sample.tri.GreeterReply> getGreetExceptionMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "greetException",
      requestType = org.apache.dubbo.sample.tri.GreeterRequest.class,
      responseType = org.apache.dubbo.sample.tri.GreeterReply.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<org.apache.dubbo.sample.tri.GreeterRequest,
      org.apache.dubbo.sample.tri.GreeterReply> getGreetExceptionMethod() {
    io.grpc.MethodDescriptor<org.apache.dubbo.sample.tri.GreeterRequest, org.apache.dubbo.sample.tri.GreeterReply> getGreetExceptionMethod;
    if ((getGreetExceptionMethod = GreeterGrpc.getGreetExceptionMethod) == null) {
      synchronized (GreeterGrpc.class) {
        if ((getGreetExceptionMethod = GreeterGrpc.getGreetExceptionMethod) == null) {
          GreeterGrpc.getGreetExceptionMethod = getGreetExceptionMethod =
              io.grpc.MethodDescriptor.<org.apache.dubbo.sample.tri.GreeterRequest, org.apache.dubbo.sample.tri.GreeterReply>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "greetException"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  org.apache.dubbo.sample.tri.GreeterRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  org.apache.dubbo.sample.tri.GreeterReply.getDefaultInstance()))
              .setSchemaDescriptor(new GreeterMethodDescriptorSupplier("greetException"))
              .build();
        }
      }
    }
    return getGreetExceptionMethod;
  }

  private static volatile io.grpc.MethodDescriptor<org.apache.dubbo.sample.tri.GreeterRequest,
      org.apache.dubbo.sample.tri.GreeterReply> getCancelBiStreamMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "cancelBiStream",
      requestType = org.apache.dubbo.sample.tri.GreeterRequest.class,
      responseType = org.apache.dubbo.sample.tri.GreeterReply.class,
      methodType = io.grpc.MethodDescriptor.MethodType.BIDI_STREAMING)
  public static io.grpc.MethodDescriptor<org.apache.dubbo.sample.tri.GreeterRequest,
      org.apache.dubbo.sample.tri.GreeterReply> getCancelBiStreamMethod() {
    io.grpc.MethodDescriptor<org.apache.dubbo.sample.tri.GreeterRequest, org.apache.dubbo.sample.tri.GreeterReply> getCancelBiStreamMethod;
    if ((getCancelBiStreamMethod = GreeterGrpc.getCancelBiStreamMethod) == null) {
      synchronized (GreeterGrpc.class) {
        if ((getCancelBiStreamMethod = GreeterGrpc.getCancelBiStreamMethod) == null) {
          GreeterGrpc.getCancelBiStreamMethod = getCancelBiStreamMethod =
              io.grpc.MethodDescriptor.<org.apache.dubbo.sample.tri.GreeterRequest, org.apache.dubbo.sample.tri.GreeterReply>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.BIDI_STREAMING)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "cancelBiStream"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  org.apache.dubbo.sample.tri.GreeterRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  org.apache.dubbo.sample.tri.GreeterReply.getDefaultInstance()))
              .setSchemaDescriptor(new GreeterMethodDescriptorSupplier("cancelBiStream"))
              .build();
        }
      }
    }
    return getCancelBiStreamMethod;
  }

  private static volatile io.grpc.MethodDescriptor<org.apache.dubbo.sample.tri.GreeterRequest,
      org.apache.dubbo.sample.tri.GreeterReply> getCancelBiStream2Method;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "cancelBiStream2",
      requestType = org.apache.dubbo.sample.tri.GreeterRequest.class,
      responseType = org.apache.dubbo.sample.tri.GreeterReply.class,
      methodType = io.grpc.MethodDescriptor.MethodType.BIDI_STREAMING)
  public static io.grpc.MethodDescriptor<org.apache.dubbo.sample.tri.GreeterRequest,
      org.apache.dubbo.sample.tri.GreeterReply> getCancelBiStream2Method() {
    io.grpc.MethodDescriptor<org.apache.dubbo.sample.tri.GreeterRequest, org.apache.dubbo.sample.tri.GreeterReply> getCancelBiStream2Method;
    if ((getCancelBiStream2Method = GreeterGrpc.getCancelBiStream2Method) == null) {
      synchronized (GreeterGrpc.class) {
        if ((getCancelBiStream2Method = GreeterGrpc.getCancelBiStream2Method) == null) {
          GreeterGrpc.getCancelBiStream2Method = getCancelBiStream2Method =
              io.grpc.MethodDescriptor.<org.apache.dubbo.sample.tri.GreeterRequest, org.apache.dubbo.sample.tri.GreeterReply>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.BIDI_STREAMING)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "cancelBiStream2"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  org.apache.dubbo.sample.tri.GreeterRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  org.apache.dubbo.sample.tri.GreeterReply.getDefaultInstance()))
              .setSchemaDescriptor(new GreeterMethodDescriptorSupplier("cancelBiStream2"))
              .build();
        }
      }
    }
    return getCancelBiStream2Method;
  }

  private static volatile io.grpc.MethodDescriptor<org.apache.dubbo.sample.tri.GreeterRequest,
      org.apache.dubbo.sample.tri.GreeterReply> getCompressorBiStreamMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "compressorBiStream",
      requestType = org.apache.dubbo.sample.tri.GreeterRequest.class,
      responseType = org.apache.dubbo.sample.tri.GreeterReply.class,
      methodType = io.grpc.MethodDescriptor.MethodType.BIDI_STREAMING)
  public static io.grpc.MethodDescriptor<org.apache.dubbo.sample.tri.GreeterRequest,
      org.apache.dubbo.sample.tri.GreeterReply> getCompressorBiStreamMethod() {
    io.grpc.MethodDescriptor<org.apache.dubbo.sample.tri.GreeterRequest, org.apache.dubbo.sample.tri.GreeterReply> getCompressorBiStreamMethod;
    if ((getCompressorBiStreamMethod = GreeterGrpc.getCompressorBiStreamMethod) == null) {
      synchronized (GreeterGrpc.class) {
        if ((getCompressorBiStreamMethod = GreeterGrpc.getCompressorBiStreamMethod) == null) {
          GreeterGrpc.getCompressorBiStreamMethod = getCompressorBiStreamMethod =
              io.grpc.MethodDescriptor.<org.apache.dubbo.sample.tri.GreeterRequest, org.apache.dubbo.sample.tri.GreeterReply>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.BIDI_STREAMING)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "compressorBiStream"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  org.apache.dubbo.sample.tri.GreeterRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  org.apache.dubbo.sample.tri.GreeterReply.getDefaultInstance()))
              .setSchemaDescriptor(new GreeterMethodDescriptorSupplier("compressorBiStream"))
              .build();
        }
      }
    }
    return getCompressorBiStreamMethod;
  }

  private static volatile io.grpc.MethodDescriptor<org.apache.dubbo.sample.tri.GreeterRequest,
      org.apache.dubbo.sample.tri.GreeterReply> getClientCompressorBiStreamMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "clientCompressorBiStream",
      requestType = org.apache.dubbo.sample.tri.GreeterRequest.class,
      responseType = org.apache.dubbo.sample.tri.GreeterReply.class,
      methodType = io.grpc.MethodDescriptor.MethodType.BIDI_STREAMING)
  public static io.grpc.MethodDescriptor<org.apache.dubbo.sample.tri.GreeterRequest,
      org.apache.dubbo.sample.tri.GreeterReply> getClientCompressorBiStreamMethod() {
    io.grpc.MethodDescriptor<org.apache.dubbo.sample.tri.GreeterRequest, org.apache.dubbo.sample.tri.GreeterReply> getClientCompressorBiStreamMethod;
    if ((getClientCompressorBiStreamMethod = GreeterGrpc.getClientCompressorBiStreamMethod) == null) {
      synchronized (GreeterGrpc.class) {
        if ((getClientCompressorBiStreamMethod = GreeterGrpc.getClientCompressorBiStreamMethod) == null) {
          GreeterGrpc.getClientCompressorBiStreamMethod = getClientCompressorBiStreamMethod =
              io.grpc.MethodDescriptor.<org.apache.dubbo.sample.tri.GreeterRequest, org.apache.dubbo.sample.tri.GreeterReply>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.BIDI_STREAMING)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "clientCompressorBiStream"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  org.apache.dubbo.sample.tri.GreeterRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  org.apache.dubbo.sample.tri.GreeterReply.getDefaultInstance()))
              .setSchemaDescriptor(new GreeterMethodDescriptorSupplier("clientCompressorBiStream"))
              .build();
        }
      }
    }
    return getClientCompressorBiStreamMethod;
  }

  private static volatile io.grpc.MethodDescriptor<org.apache.dubbo.sample.tri.GreeterRequest,
      org.apache.dubbo.sample.tri.GreeterReply> getServerCompressorBiStreamMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "serverCompressorBiStream",
      requestType = org.apache.dubbo.sample.tri.GreeterRequest.class,
      responseType = org.apache.dubbo.sample.tri.GreeterReply.class,
      methodType = io.grpc.MethodDescriptor.MethodType.BIDI_STREAMING)
  public static io.grpc.MethodDescriptor<org.apache.dubbo.sample.tri.GreeterRequest,
      org.apache.dubbo.sample.tri.GreeterReply> getServerCompressorBiStreamMethod() {
    io.grpc.MethodDescriptor<org.apache.dubbo.sample.tri.GreeterRequest, org.apache.dubbo.sample.tri.GreeterReply> getServerCompressorBiStreamMethod;
    if ((getServerCompressorBiStreamMethod = GreeterGrpc.getServerCompressorBiStreamMethod) == null) {
      synchronized (GreeterGrpc.class) {
        if ((getServerCompressorBiStreamMethod = GreeterGrpc.getServerCompressorBiStreamMethod) == null) {
          GreeterGrpc.getServerCompressorBiStreamMethod = getServerCompressorBiStreamMethod =
              io.grpc.MethodDescriptor.<org.apache.dubbo.sample.tri.GreeterRequest, org.apache.dubbo.sample.tri.GreeterReply>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.BIDI_STREAMING)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "serverCompressorBiStream"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  org.apache.dubbo.sample.tri.GreeterRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  org.apache.dubbo.sample.tri.GreeterReply.getDefaultInstance()))
              .setSchemaDescriptor(new GreeterMethodDescriptorSupplier("serverCompressorBiStream"))
              .build();
        }
      }
    }
    return getServerCompressorBiStreamMethod;
  }

  private static volatile io.grpc.MethodDescriptor<org.apache.dubbo.sample.tri.GreeterRequest,
      org.apache.dubbo.sample.tri.GreeterReply> getCancelServerStreamMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "cancelServerStream",
      requestType = org.apache.dubbo.sample.tri.GreeterRequest.class,
      responseType = org.apache.dubbo.sample.tri.GreeterReply.class,
      methodType = io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING)
  public static io.grpc.MethodDescriptor<org.apache.dubbo.sample.tri.GreeterRequest,
      org.apache.dubbo.sample.tri.GreeterReply> getCancelServerStreamMethod() {
    io.grpc.MethodDescriptor<org.apache.dubbo.sample.tri.GreeterRequest, org.apache.dubbo.sample.tri.GreeterReply> getCancelServerStreamMethod;
    if ((getCancelServerStreamMethod = GreeterGrpc.getCancelServerStreamMethod) == null) {
      synchronized (GreeterGrpc.class) {
        if ((getCancelServerStreamMethod = GreeterGrpc.getCancelServerStreamMethod) == null) {
          GreeterGrpc.getCancelServerStreamMethod = getCancelServerStreamMethod =
              io.grpc.MethodDescriptor.<org.apache.dubbo.sample.tri.GreeterRequest, org.apache.dubbo.sample.tri.GreeterReply>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "cancelServerStream"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  org.apache.dubbo.sample.tri.GreeterRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  org.apache.dubbo.sample.tri.GreeterReply.getDefaultInstance()))
              .setSchemaDescriptor(new GreeterMethodDescriptorSupplier("cancelServerStream"))
              .build();
        }
      }
    }
    return getCancelServerStreamMethod;
  }

  private static volatile io.grpc.MethodDescriptor<org.apache.dubbo.sample.tri.GreeterRequest,
      org.apache.dubbo.sample.tri.GreeterReply> getQueryCancelResultMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "queryCancelResult",
      requestType = org.apache.dubbo.sample.tri.GreeterRequest.class,
      responseType = org.apache.dubbo.sample.tri.GreeterReply.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<org.apache.dubbo.sample.tri.GreeterRequest,
      org.apache.dubbo.sample.tri.GreeterReply> getQueryCancelResultMethod() {
    io.grpc.MethodDescriptor<org.apache.dubbo.sample.tri.GreeterRequest, org.apache.dubbo.sample.tri.GreeterReply> getQueryCancelResultMethod;
    if ((getQueryCancelResultMethod = GreeterGrpc.getQueryCancelResultMethod) == null) {
      synchronized (GreeterGrpc.class) {
        if ((getQueryCancelResultMethod = GreeterGrpc.getQueryCancelResultMethod) == null) {
          GreeterGrpc.getQueryCancelResultMethod = getQueryCancelResultMethod =
              io.grpc.MethodDescriptor.<org.apache.dubbo.sample.tri.GreeterRequest, org.apache.dubbo.sample.tri.GreeterReply>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "queryCancelResult"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  org.apache.dubbo.sample.tri.GreeterRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  org.apache.dubbo.sample.tri.GreeterReply.getDefaultInstance()))
              .setSchemaDescriptor(new GreeterMethodDescriptorSupplier("queryCancelResult"))
              .build();
        }
      }
    }
    return getQueryCancelResultMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static GreeterStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<GreeterStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<GreeterStub>() {
        @java.lang.Override
        public GreeterStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new GreeterStub(channel, callOptions);
        }
      };
    return GreeterStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static GreeterBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<GreeterBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<GreeterBlockingStub>() {
        @java.lang.Override
        public GreeterBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new GreeterBlockingStub(channel, callOptions);
        }
      };
    return GreeterBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static GreeterFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<GreeterFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<GreeterFutureStub>() {
        @java.lang.Override
        public GreeterFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new GreeterFutureStub(channel, callOptions);
        }
      };
    return GreeterFutureStub.newStub(factory, channel);
  }

  /**
   */
  public static abstract class GreeterImplBase implements io.grpc.BindableService {

    /**
     * <pre>
     * unary
     * </pre>
     */
    public void greet(org.apache.dubbo.sample.tri.GreeterRequest request,
        io.grpc.stub.StreamObserver<org.apache.dubbo.sample.tri.GreeterReply> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGreetMethod(), responseObserver);
    }

    /**
     * <pre>
     * clientStream
     * </pre>
     */
    public io.grpc.stub.StreamObserver<org.apache.dubbo.sample.tri.GreeterRequest> greetClientStream(
        io.grpc.stub.StreamObserver<org.apache.dubbo.sample.tri.GreeterReply> responseObserver) {
      return io.grpc.stub.ServerCalls.asyncUnimplementedStreamingCall(getGreetClientStreamMethod(), responseObserver);
    }

    /**
     * <pre>
     * serverStream
     * </pre>
     */
    public void greetServerStream(org.apache.dubbo.sample.tri.GreeterRequest request,
        io.grpc.stub.StreamObserver<org.apache.dubbo.sample.tri.GreeterReply> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGreetServerStreamMethod(), responseObserver);
    }

    /**
     * <pre>
     * bi streaming
     * </pre>
     */
    public io.grpc.stub.StreamObserver<org.apache.dubbo.sample.tri.GreeterRequest> greetStream(
        io.grpc.stub.StreamObserver<org.apache.dubbo.sample.tri.GreeterReply> responseObserver) {
      return io.grpc.stub.ServerCalls.asyncUnimplementedStreamingCall(getGreetStreamMethod(), responseObserver);
    }

    /**
     * <pre>
     *  upper case method
     * </pre>
     */
    public void upperCaseGreet(org.apache.dubbo.sample.tri.GreeterRequest request,
        io.grpc.stub.StreamObserver<org.apache.dubbo.sample.tri.GreeterReply> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getUpperCaseGreetMethod(), responseObserver);
    }

    /**
     */
    public void greetWithAttachment(org.apache.dubbo.sample.tri.GreeterRequest request,
        io.grpc.stub.StreamObserver<org.apache.dubbo.sample.tri.GreeterReply> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGreetWithAttachmentMethod(), responseObserver);
    }

    /**
     */
    public void greetReturnBigAttachment(org.apache.dubbo.sample.tri.GreeterRequest request,
        io.grpc.stub.StreamObserver<org.apache.dubbo.sample.tri.GreeterReply> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGreetReturnBigAttachmentMethod(), responseObserver);
    }

    /**
     */
    public void greetException(org.apache.dubbo.sample.tri.GreeterRequest request,
        io.grpc.stub.StreamObserver<org.apache.dubbo.sample.tri.GreeterReply> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGreetExceptionMethod(), responseObserver);
    }

    /**
     */
    public io.grpc.stub.StreamObserver<org.apache.dubbo.sample.tri.GreeterRequest> cancelBiStream(
        io.grpc.stub.StreamObserver<org.apache.dubbo.sample.tri.GreeterReply> responseObserver) {
      return io.grpc.stub.ServerCalls.asyncUnimplementedStreamingCall(getCancelBiStreamMethod(), responseObserver);
    }

    /**
     */
    public io.grpc.stub.StreamObserver<org.apache.dubbo.sample.tri.GreeterRequest> cancelBiStream2(
        io.grpc.stub.StreamObserver<org.apache.dubbo.sample.tri.GreeterReply> responseObserver) {
      return io.grpc.stub.ServerCalls.asyncUnimplementedStreamingCall(getCancelBiStream2Method(), responseObserver);
    }

    /**
     */
    public io.grpc.stub.StreamObserver<org.apache.dubbo.sample.tri.GreeterRequest> compressorBiStream(
        io.grpc.stub.StreamObserver<org.apache.dubbo.sample.tri.GreeterReply> responseObserver) {
      return io.grpc.stub.ServerCalls.asyncUnimplementedStreamingCall(getCompressorBiStreamMethod(), responseObserver);
    }

    /**
     */
    public io.grpc.stub.StreamObserver<org.apache.dubbo.sample.tri.GreeterRequest> clientCompressorBiStream(
        io.grpc.stub.StreamObserver<org.apache.dubbo.sample.tri.GreeterReply> responseObserver) {
      return io.grpc.stub.ServerCalls.asyncUnimplementedStreamingCall(getClientCompressorBiStreamMethod(), responseObserver);
    }

    /**
     */
    public io.grpc.stub.StreamObserver<org.apache.dubbo.sample.tri.GreeterRequest> serverCompressorBiStream(
        io.grpc.stub.StreamObserver<org.apache.dubbo.sample.tri.GreeterReply> responseObserver) {
      return io.grpc.stub.ServerCalls.asyncUnimplementedStreamingCall(getServerCompressorBiStreamMethod(), responseObserver);
    }

    /**
     */
    public void cancelServerStream(org.apache.dubbo.sample.tri.GreeterRequest request,
        io.grpc.stub.StreamObserver<org.apache.dubbo.sample.tri.GreeterReply> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getCancelServerStreamMethod(), responseObserver);
    }

    /**
     */
    public void queryCancelResult(org.apache.dubbo.sample.tri.GreeterRequest request,
        io.grpc.stub.StreamObserver<org.apache.dubbo.sample.tri.GreeterReply> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getQueryCancelResultMethod(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getGreetMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                org.apache.dubbo.sample.tri.GreeterRequest,
                org.apache.dubbo.sample.tri.GreeterReply>(
                  this, METHODID_GREET)))
          .addMethod(
            getGreetClientStreamMethod(),
            io.grpc.stub.ServerCalls.asyncClientStreamingCall(
              new MethodHandlers<
                org.apache.dubbo.sample.tri.GreeterRequest,
                org.apache.dubbo.sample.tri.GreeterReply>(
                  this, METHODID_GREET_CLIENT_STREAM)))
          .addMethod(
            getGreetServerStreamMethod(),
            io.grpc.stub.ServerCalls.asyncServerStreamingCall(
              new MethodHandlers<
                org.apache.dubbo.sample.tri.GreeterRequest,
                org.apache.dubbo.sample.tri.GreeterReply>(
                  this, METHODID_GREET_SERVER_STREAM)))
          .addMethod(
            getGreetStreamMethod(),
            io.grpc.stub.ServerCalls.asyncBidiStreamingCall(
              new MethodHandlers<
                org.apache.dubbo.sample.tri.GreeterRequest,
                org.apache.dubbo.sample.tri.GreeterReply>(
                  this, METHODID_GREET_STREAM)))
          .addMethod(
            getUpperCaseGreetMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                org.apache.dubbo.sample.tri.GreeterRequest,
                org.apache.dubbo.sample.tri.GreeterReply>(
                  this, METHODID_UPPER_CASE_GREET)))
          .addMethod(
            getGreetWithAttachmentMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                org.apache.dubbo.sample.tri.GreeterRequest,
                org.apache.dubbo.sample.tri.GreeterReply>(
                  this, METHODID_GREET_WITH_ATTACHMENT)))
          .addMethod(
            getGreetReturnBigAttachmentMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                org.apache.dubbo.sample.tri.GreeterRequest,
                org.apache.dubbo.sample.tri.GreeterReply>(
                  this, METHODID_GREET_RETURN_BIG_ATTACHMENT)))
          .addMethod(
            getGreetExceptionMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                org.apache.dubbo.sample.tri.GreeterRequest,
                org.apache.dubbo.sample.tri.GreeterReply>(
                  this, METHODID_GREET_EXCEPTION)))
          .addMethod(
            getCancelBiStreamMethod(),
            io.grpc.stub.ServerCalls.asyncBidiStreamingCall(
              new MethodHandlers<
                org.apache.dubbo.sample.tri.GreeterRequest,
                org.apache.dubbo.sample.tri.GreeterReply>(
                  this, METHODID_CANCEL_BI_STREAM)))
          .addMethod(
            getCancelBiStream2Method(),
            io.grpc.stub.ServerCalls.asyncBidiStreamingCall(
              new MethodHandlers<
                org.apache.dubbo.sample.tri.GreeterRequest,
                org.apache.dubbo.sample.tri.GreeterReply>(
                  this, METHODID_CANCEL_BI_STREAM2)))
          .addMethod(
            getCompressorBiStreamMethod(),
            io.grpc.stub.ServerCalls.asyncBidiStreamingCall(
              new MethodHandlers<
                org.apache.dubbo.sample.tri.GreeterRequest,
                org.apache.dubbo.sample.tri.GreeterReply>(
                  this, METHODID_COMPRESSOR_BI_STREAM)))
          .addMethod(
            getClientCompressorBiStreamMethod(),
            io.grpc.stub.ServerCalls.asyncBidiStreamingCall(
              new MethodHandlers<
                org.apache.dubbo.sample.tri.GreeterRequest,
                org.apache.dubbo.sample.tri.GreeterReply>(
                  this, METHODID_CLIENT_COMPRESSOR_BI_STREAM)))
          .addMethod(
            getServerCompressorBiStreamMethod(),
            io.grpc.stub.ServerCalls.asyncBidiStreamingCall(
              new MethodHandlers<
                org.apache.dubbo.sample.tri.GreeterRequest,
                org.apache.dubbo.sample.tri.GreeterReply>(
                  this, METHODID_SERVER_COMPRESSOR_BI_STREAM)))
          .addMethod(
            getCancelServerStreamMethod(),
            io.grpc.stub.ServerCalls.asyncServerStreamingCall(
              new MethodHandlers<
                org.apache.dubbo.sample.tri.GreeterRequest,
                org.apache.dubbo.sample.tri.GreeterReply>(
                  this, METHODID_CANCEL_SERVER_STREAM)))
          .addMethod(
            getQueryCancelResultMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                org.apache.dubbo.sample.tri.GreeterRequest,
                org.apache.dubbo.sample.tri.GreeterReply>(
                  this, METHODID_QUERY_CANCEL_RESULT)))
          .build();
    }
  }

  /**
   */
  public static final class GreeterStub extends io.grpc.stub.AbstractAsyncStub<GreeterStub> {
    private GreeterStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected GreeterStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new GreeterStub(channel, callOptions);
    }

    /**
     * <pre>
     * unary
     * </pre>
     */
    public void greet(org.apache.dubbo.sample.tri.GreeterRequest request,
        io.grpc.stub.StreamObserver<org.apache.dubbo.sample.tri.GreeterReply> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGreetMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * clientStream
     * </pre>
     */
    public io.grpc.stub.StreamObserver<org.apache.dubbo.sample.tri.GreeterRequest> greetClientStream(
        io.grpc.stub.StreamObserver<org.apache.dubbo.sample.tri.GreeterReply> responseObserver) {
      return io.grpc.stub.ClientCalls.asyncClientStreamingCall(
          getChannel().newCall(getGreetClientStreamMethod(), getCallOptions()), responseObserver);
    }

    /**
     * <pre>
     * serverStream
     * </pre>
     */
    public void greetServerStream(org.apache.dubbo.sample.tri.GreeterRequest request,
        io.grpc.stub.StreamObserver<org.apache.dubbo.sample.tri.GreeterReply> responseObserver) {
      io.grpc.stub.ClientCalls.asyncServerStreamingCall(
          getChannel().newCall(getGreetServerStreamMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * bi streaming
     * </pre>
     */
    public io.grpc.stub.StreamObserver<org.apache.dubbo.sample.tri.GreeterRequest> greetStream(
        io.grpc.stub.StreamObserver<org.apache.dubbo.sample.tri.GreeterReply> responseObserver) {
      return io.grpc.stub.ClientCalls.asyncBidiStreamingCall(
          getChannel().newCall(getGreetStreamMethod(), getCallOptions()), responseObserver);
    }

    /**
     * <pre>
     *  upper case method
     * </pre>
     */
    public void upperCaseGreet(org.apache.dubbo.sample.tri.GreeterRequest request,
        io.grpc.stub.StreamObserver<org.apache.dubbo.sample.tri.GreeterReply> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getUpperCaseGreetMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void greetWithAttachment(org.apache.dubbo.sample.tri.GreeterRequest request,
        io.grpc.stub.StreamObserver<org.apache.dubbo.sample.tri.GreeterReply> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGreetWithAttachmentMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void greetReturnBigAttachment(org.apache.dubbo.sample.tri.GreeterRequest request,
        io.grpc.stub.StreamObserver<org.apache.dubbo.sample.tri.GreeterReply> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGreetReturnBigAttachmentMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void greetException(org.apache.dubbo.sample.tri.GreeterRequest request,
        io.grpc.stub.StreamObserver<org.apache.dubbo.sample.tri.GreeterReply> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGreetExceptionMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public io.grpc.stub.StreamObserver<org.apache.dubbo.sample.tri.GreeterRequest> cancelBiStream(
        io.grpc.stub.StreamObserver<org.apache.dubbo.sample.tri.GreeterReply> responseObserver) {
      return io.grpc.stub.ClientCalls.asyncBidiStreamingCall(
          getChannel().newCall(getCancelBiStreamMethod(), getCallOptions()), responseObserver);
    }

    /**
     */
    public io.grpc.stub.StreamObserver<org.apache.dubbo.sample.tri.GreeterRequest> cancelBiStream2(
        io.grpc.stub.StreamObserver<org.apache.dubbo.sample.tri.GreeterReply> responseObserver) {
      return io.grpc.stub.ClientCalls.asyncBidiStreamingCall(
          getChannel().newCall(getCancelBiStream2Method(), getCallOptions()), responseObserver);
    }

    /**
     */
    public io.grpc.stub.StreamObserver<org.apache.dubbo.sample.tri.GreeterRequest> compressorBiStream(
        io.grpc.stub.StreamObserver<org.apache.dubbo.sample.tri.GreeterReply> responseObserver) {
      return io.grpc.stub.ClientCalls.asyncBidiStreamingCall(
          getChannel().newCall(getCompressorBiStreamMethod(), getCallOptions()), responseObserver);
    }

    /**
     */
    public io.grpc.stub.StreamObserver<org.apache.dubbo.sample.tri.GreeterRequest> clientCompressorBiStream(
        io.grpc.stub.StreamObserver<org.apache.dubbo.sample.tri.GreeterReply> responseObserver) {
      return io.grpc.stub.ClientCalls.asyncBidiStreamingCall(
          getChannel().newCall(getClientCompressorBiStreamMethod(), getCallOptions()), responseObserver);
    }

    /**
     */
    public io.grpc.stub.StreamObserver<org.apache.dubbo.sample.tri.GreeterRequest> serverCompressorBiStream(
        io.grpc.stub.StreamObserver<org.apache.dubbo.sample.tri.GreeterReply> responseObserver) {
      return io.grpc.stub.ClientCalls.asyncBidiStreamingCall(
          getChannel().newCall(getServerCompressorBiStreamMethod(), getCallOptions()), responseObserver);
    }

    /**
     */
    public void cancelServerStream(org.apache.dubbo.sample.tri.GreeterRequest request,
        io.grpc.stub.StreamObserver<org.apache.dubbo.sample.tri.GreeterReply> responseObserver) {
      io.grpc.stub.ClientCalls.asyncServerStreamingCall(
          getChannel().newCall(getCancelServerStreamMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void queryCancelResult(org.apache.dubbo.sample.tri.GreeterRequest request,
        io.grpc.stub.StreamObserver<org.apache.dubbo.sample.tri.GreeterReply> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getQueryCancelResultMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   */
  public static final class GreeterBlockingStub extends io.grpc.stub.AbstractBlockingStub<GreeterBlockingStub> {
    private GreeterBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected GreeterBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new GreeterBlockingStub(channel, callOptions);
    }

    /**
     * <pre>
     * unary
     * </pre>
     */
    public org.apache.dubbo.sample.tri.GreeterReply greet(org.apache.dubbo.sample.tri.GreeterRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGreetMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * serverStream
     * </pre>
     */
    public java.util.Iterator<org.apache.dubbo.sample.tri.GreeterReply> greetServerStream(
        org.apache.dubbo.sample.tri.GreeterRequest request) {
      return io.grpc.stub.ClientCalls.blockingServerStreamingCall(
          getChannel(), getGreetServerStreamMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     *  upper case method
     * </pre>
     */
    public org.apache.dubbo.sample.tri.GreeterReply upperCaseGreet(org.apache.dubbo.sample.tri.GreeterRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getUpperCaseGreetMethod(), getCallOptions(), request);
    }

    /**
     */
    public org.apache.dubbo.sample.tri.GreeterReply greetWithAttachment(org.apache.dubbo.sample.tri.GreeterRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGreetWithAttachmentMethod(), getCallOptions(), request);
    }

    /**
     */
    public org.apache.dubbo.sample.tri.GreeterReply greetReturnBigAttachment(org.apache.dubbo.sample.tri.GreeterRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGreetReturnBigAttachmentMethod(), getCallOptions(), request);
    }

    /**
     */
    public org.apache.dubbo.sample.tri.GreeterReply greetException(org.apache.dubbo.sample.tri.GreeterRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGreetExceptionMethod(), getCallOptions(), request);
    }

    /**
     */
    public java.util.Iterator<org.apache.dubbo.sample.tri.GreeterReply> cancelServerStream(
        org.apache.dubbo.sample.tri.GreeterRequest request) {
      return io.grpc.stub.ClientCalls.blockingServerStreamingCall(
          getChannel(), getCancelServerStreamMethod(), getCallOptions(), request);
    }

    /**
     */
    public org.apache.dubbo.sample.tri.GreeterReply queryCancelResult(org.apache.dubbo.sample.tri.GreeterRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getQueryCancelResultMethod(), getCallOptions(), request);
    }
  }

  /**
   */
  public static final class GreeterFutureStub extends io.grpc.stub.AbstractFutureStub<GreeterFutureStub> {
    private GreeterFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected GreeterFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new GreeterFutureStub(channel, callOptions);
    }

    /**
     * <pre>
     * unary
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<org.apache.dubbo.sample.tri.GreeterReply> greet(
        org.apache.dubbo.sample.tri.GreeterRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGreetMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     *  upper case method
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<org.apache.dubbo.sample.tri.GreeterReply> upperCaseGreet(
        org.apache.dubbo.sample.tri.GreeterRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getUpperCaseGreetMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<org.apache.dubbo.sample.tri.GreeterReply> greetWithAttachment(
        org.apache.dubbo.sample.tri.GreeterRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGreetWithAttachmentMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<org.apache.dubbo.sample.tri.GreeterReply> greetReturnBigAttachment(
        org.apache.dubbo.sample.tri.GreeterRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGreetReturnBigAttachmentMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<org.apache.dubbo.sample.tri.GreeterReply> greetException(
        org.apache.dubbo.sample.tri.GreeterRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGreetExceptionMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<org.apache.dubbo.sample.tri.GreeterReply> queryCancelResult(
        org.apache.dubbo.sample.tri.GreeterRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getQueryCancelResultMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_GREET = 0;
  private static final int METHODID_GREET_SERVER_STREAM = 1;
  private static final int METHODID_UPPER_CASE_GREET = 2;
  private static final int METHODID_GREET_WITH_ATTACHMENT = 3;
  private static final int METHODID_GREET_RETURN_BIG_ATTACHMENT = 4;
  private static final int METHODID_GREET_EXCEPTION = 5;
  private static final int METHODID_CANCEL_SERVER_STREAM = 6;
  private static final int METHODID_QUERY_CANCEL_RESULT = 7;
  private static final int METHODID_GREET_CLIENT_STREAM = 8;
  private static final int METHODID_GREET_STREAM = 9;
  private static final int METHODID_CANCEL_BI_STREAM = 10;
  private static final int METHODID_CANCEL_BI_STREAM2 = 11;
  private static final int METHODID_COMPRESSOR_BI_STREAM = 12;
  private static final int METHODID_CLIENT_COMPRESSOR_BI_STREAM = 13;
  private static final int METHODID_SERVER_COMPRESSOR_BI_STREAM = 14;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final GreeterImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(GreeterImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_GREET:
          serviceImpl.greet((org.apache.dubbo.sample.tri.GreeterRequest) request,
              (io.grpc.stub.StreamObserver<org.apache.dubbo.sample.tri.GreeterReply>) responseObserver);
          break;
        case METHODID_GREET_SERVER_STREAM:
          serviceImpl.greetServerStream((org.apache.dubbo.sample.tri.GreeterRequest) request,
              (io.grpc.stub.StreamObserver<org.apache.dubbo.sample.tri.GreeterReply>) responseObserver);
          break;
        case METHODID_UPPER_CASE_GREET:
          serviceImpl.upperCaseGreet((org.apache.dubbo.sample.tri.GreeterRequest) request,
              (io.grpc.stub.StreamObserver<org.apache.dubbo.sample.tri.GreeterReply>) responseObserver);
          break;
        case METHODID_GREET_WITH_ATTACHMENT:
          serviceImpl.greetWithAttachment((org.apache.dubbo.sample.tri.GreeterRequest) request,
              (io.grpc.stub.StreamObserver<org.apache.dubbo.sample.tri.GreeterReply>) responseObserver);
          break;
        case METHODID_GREET_RETURN_BIG_ATTACHMENT:
          serviceImpl.greetReturnBigAttachment((org.apache.dubbo.sample.tri.GreeterRequest) request,
              (io.grpc.stub.StreamObserver<org.apache.dubbo.sample.tri.GreeterReply>) responseObserver);
          break;
        case METHODID_GREET_EXCEPTION:
          serviceImpl.greetException((org.apache.dubbo.sample.tri.GreeterRequest) request,
              (io.grpc.stub.StreamObserver<org.apache.dubbo.sample.tri.GreeterReply>) responseObserver);
          break;
        case METHODID_CANCEL_SERVER_STREAM:
          serviceImpl.cancelServerStream((org.apache.dubbo.sample.tri.GreeterRequest) request,
              (io.grpc.stub.StreamObserver<org.apache.dubbo.sample.tri.GreeterReply>) responseObserver);
          break;
        case METHODID_QUERY_CANCEL_RESULT:
          serviceImpl.queryCancelResult((org.apache.dubbo.sample.tri.GreeterRequest) request,
              (io.grpc.stub.StreamObserver<org.apache.dubbo.sample.tri.GreeterReply>) responseObserver);
          break;
        default:
          throw new AssertionError();
      }
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public io.grpc.stub.StreamObserver<Req> invoke(
        io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_GREET_CLIENT_STREAM:
          return (io.grpc.stub.StreamObserver<Req>) serviceImpl.greetClientStream(
              (io.grpc.stub.StreamObserver<org.apache.dubbo.sample.tri.GreeterReply>) responseObserver);
        case METHODID_GREET_STREAM:
          return (io.grpc.stub.StreamObserver<Req>) serviceImpl.greetStream(
              (io.grpc.stub.StreamObserver<org.apache.dubbo.sample.tri.GreeterReply>) responseObserver);
        case METHODID_CANCEL_BI_STREAM:
          return (io.grpc.stub.StreamObserver<Req>) serviceImpl.cancelBiStream(
              (io.grpc.stub.StreamObserver<org.apache.dubbo.sample.tri.GreeterReply>) responseObserver);
        case METHODID_CANCEL_BI_STREAM2:
          return (io.grpc.stub.StreamObserver<Req>) serviceImpl.cancelBiStream2(
              (io.grpc.stub.StreamObserver<org.apache.dubbo.sample.tri.GreeterReply>) responseObserver);
        case METHODID_COMPRESSOR_BI_STREAM:
          return (io.grpc.stub.StreamObserver<Req>) serviceImpl.compressorBiStream(
              (io.grpc.stub.StreamObserver<org.apache.dubbo.sample.tri.GreeterReply>) responseObserver);
        case METHODID_CLIENT_COMPRESSOR_BI_STREAM:
          return (io.grpc.stub.StreamObserver<Req>) serviceImpl.clientCompressorBiStream(
              (io.grpc.stub.StreamObserver<org.apache.dubbo.sample.tri.GreeterReply>) responseObserver);
        case METHODID_SERVER_COMPRESSOR_BI_STREAM:
          return (io.grpc.stub.StreamObserver<Req>) serviceImpl.serverCompressorBiStream(
              (io.grpc.stub.StreamObserver<org.apache.dubbo.sample.tri.GreeterReply>) responseObserver);
        default:
          throw new AssertionError();
      }
    }
  }

  private static abstract class GreeterBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    GreeterBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return org.apache.dubbo.sample.tri.GreeterOuterClass.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("Greeter");
    }
  }

  private static final class GreeterFileDescriptorSupplier
      extends GreeterBaseDescriptorSupplier {
    GreeterFileDescriptorSupplier() {}
  }

  private static final class GreeterMethodDescriptorSupplier
      extends GreeterBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    GreeterMethodDescriptorSupplier(String methodName) {
      this.methodName = methodName;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.MethodDescriptor getMethodDescriptor() {
      return getServiceDescriptor().findMethodByName(methodName);
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (GreeterGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new GreeterFileDescriptorSupplier())
              .addMethod(getGreetMethod())
              .addMethod(getGreetClientStreamMethod())
              .addMethod(getGreetServerStreamMethod())
              .addMethod(getGreetStreamMethod())
              .addMethod(getUpperCaseGreetMethod())
              .addMethod(getGreetWithAttachmentMethod())
              .addMethod(getGreetReturnBigAttachmentMethod())
              .addMethod(getGreetExceptionMethod())
              .addMethod(getCancelBiStreamMethod())
              .addMethod(getCancelBiStream2Method())
              .addMethod(getCompressorBiStreamMethod())
              .addMethod(getClientCompressorBiStreamMethod())
              .addMethod(getServerCompressorBiStreamMethod())
              .addMethod(getCancelServerStreamMethod())
              .addMethod(getQueryCancelResultMethod())
              .build();
        }
      }
    }
    return result;
  }
}
