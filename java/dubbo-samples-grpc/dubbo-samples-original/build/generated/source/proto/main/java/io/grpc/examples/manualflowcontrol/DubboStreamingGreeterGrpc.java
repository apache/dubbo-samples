package io.grpc.examples.manualflowcontrol;

import org.apache.dubbo.common.URL;
import org.apache.dubbo.config.ReferenceConfigBase;

import java.util.concurrent.TimeUnit;

import static org.apache.dubbo.common.constants.CommonConstants.DEFAULT_TIMEOUT;
import static org.apache.dubbo.common.constants.CommonConstants.TIMEOUT_KEY;

import static io.grpc.examples.manualflowcontrol.StreamingGreeterGrpc.getServiceDescriptor;
import static io.grpc.stub.ServerCalls.asyncUnaryCall;
import static io.grpc.stub.ServerCalls.asyncServerStreamingCall;
import static io.grpc.stub.ServerCalls.asyncClientStreamingCall;
import static io.grpc.stub.ServerCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedStreamingCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall;

@javax.annotation.Generated(
value = "by DubboGrpc generator",
comments = "Source: hello_streaming.proto")
public final class DubboStreamingGreeterGrpc {
    private DubboStreamingGreeterGrpc() {}

    public static class DubboStreamingGreeterStub implements IStreamingGreeter {

        protected URL url;
        protected ReferenceConfigBase<?> referenceConfig;

        protected StreamingGreeterGrpc.StreamingGreeterBlockingStub blockingStub;
        protected StreamingGreeterGrpc.StreamingGreeterFutureStub futureStub;
        protected StreamingGreeterGrpc.StreamingGreeterStub stub;

        public DubboStreamingGreeterStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions, URL url, ReferenceConfigBase<?> referenceConfig) {
            this.url = url;
            this.referenceConfig = referenceConfig;

            blockingStub = StreamingGreeterGrpc.newBlockingStub(channel).build(channel, callOptions);
            futureStub = StreamingGreeterGrpc.newFutureStub(channel).build(channel, callOptions);
            stub = StreamingGreeterGrpc.newStub(channel).build(channel, callOptions);
        }

        /**
         * <pre>
         *  Streams a many greetings
         * </pre>
         */
        public io.grpc.stub.StreamObserver<io.grpc.examples.manualflowcontrol.HelloRequest> sayHelloStreaming(io.grpc.stub.StreamObserver<io.grpc.examples.manualflowcontrol.HelloReply> responseObserver) {
            return stub
                .withDeadlineAfter(url.getParameter(TIMEOUT_KEY, DEFAULT_TIMEOUT), TimeUnit.MILLISECONDS)
                .sayHelloStreaming(responseObserver);
        }
    }

    public static DubboStreamingGreeterStub getDubboStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions, URL url, ReferenceConfigBase<?> referenceConfig) {
      return new DubboStreamingGreeterStub(channel, callOptions, url, referenceConfig);
    }

    public interface IStreamingGreeter {
        /**
         * <pre>
         *  Streams a many greetings
         * </pre>
         */
        public io.grpc.stub.StreamObserver<io.grpc.examples.manualflowcontrol.HelloRequest> sayHelloStreaming(io.grpc.stub.StreamObserver<io.grpc.examples.manualflowcontrol.HelloReply> responseObserver);

    }

    /**
     * <pre>
     *  The greeting service definition.
     * </pre>
     */
    public static abstract class StreamingGreeterImplBase implements io.grpc.BindableService, IStreamingGreeter {

        private IStreamingGreeter proxiedImpl;

        public final void setProxiedImpl(IStreamingGreeter proxiedImpl) {
            this.proxiedImpl = proxiedImpl;
        }

        public io.grpc.stub.StreamObserver<io.grpc.examples.manualflowcontrol.HelloRequest> sayHelloStreaming(
            io.grpc.stub.StreamObserver<io.grpc.examples.manualflowcontrol.HelloReply> responseObserver) {
            return asyncUnimplementedStreamingCall(io.grpc.examples.manualflowcontrol.StreamingGreeterGrpc.getSayHelloStreamingMethod(), responseObserver);
        }

        @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
            return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
                      .addMethod(
                        io.grpc.examples.manualflowcontrol.StreamingGreeterGrpc.getSayHelloStreamingMethod(),
                        asyncBidiStreamingCall(
                          new MethodHandlers<
                             io.grpc.examples.manualflowcontrol.HelloRequest,
                             io.grpc.examples.manualflowcontrol.HelloReply>(
                              proxiedImpl, METHODID_SAY_HELLO_STREAMING)))
                    .build();
        }
    }
    private static final int METHODID_SAY_HELLO_STREAMING = 0;

    private static final class MethodHandlers<Req, Resp> implements
            io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
            io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
            io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
            io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
        private final IStreamingGreeter serviceImpl;
        private final int methodId;

        MethodHandlers(IStreamingGreeter serviceImpl, int methodId) {
            this.serviceImpl = serviceImpl;
            this.methodId = methodId;
        }

        @java.lang.Override
        @java.lang.SuppressWarnings("unchecked")
        public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
            switch (methodId) {
                default:
                    throw new java.lang.AssertionError();
            }
        }

        @java.lang.Override
        @java.lang.SuppressWarnings("unchecked")
        public io.grpc.stub.StreamObserver<Req> invoke(io.grpc.stub.StreamObserver<Resp> responseObserver) {
            switch (methodId) {
                case METHODID_SAY_HELLO_STREAMING:
                    return (io.grpc.stub.StreamObserver<Req>) serviceImpl.sayHelloStreaming(
                        (io.grpc.stub.StreamObserver<io.grpc.examples.manualflowcontrol.HelloReply>) responseObserver);
                default:
                    throw new java.lang.AssertionError();
            }
        }
    }

}
