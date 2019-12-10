package io.grpc.examples.helloworld;

import org.apache.dubbo.common.URL;
import org.apache.dubbo.config.ReferenceConfigBase;

import java.util.concurrent.TimeUnit;

import static io.grpc.examples.helloworld.GreeterGrpc.getServiceDescriptor;
import static io.grpc.stub.ServerCalls.asyncUnaryCall;
import static org.apache.dubbo.common.constants.CommonConstants.DEFAULT_TIMEOUT;
import static org.apache.dubbo.common.constants.CommonConstants.TIMEOUT_KEY;


@javax.annotation.Generated(
        value = "by RxDubboGrpc generator",
        comments = "Source: helloworld.proto")
public final class RxDubboGreeterGrpc {
    private static final int METHODID_SAY_HELLO = 0;

    private RxDubboGreeterGrpc() {
    }

    public static RxDubboGreeterStub getDubboStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions, URL url, ReferenceConfigBase<?> referenceConfig) {
        return new RxDubboGreeterStub(channel, callOptions, url, referenceConfig);
    }

    public interface IRxGreeter {
        /**
         * <pre>
         *  Sends a greeting
         * </pre>
         */
        public io.reactivex.Single<io.grpc.examples.helloworld.HelloReply> sayHello(io.reactivex.Single<io.grpc.examples.helloworld.HelloRequest> rxRequest);

        /**
         * <pre>
         *  Sends a greeting
         * </pre>
         */
        public io.reactivex.Single<io.grpc.examples.helloworld.HelloReply> sayHello(io.grpc.examples.helloworld.HelloRequest rxRequest);

    }

    /**
     * <pre>
     *  The greeting service definition.
     * </pre>
     */
    public static final class RxDubboGreeterStub implements IRxGreeter {

        protected URL url;
        protected ReferenceConfigBase<?> referenceConfig;

        protected GreeterGrpc.GreeterStub stub;

        public RxDubboGreeterStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions, URL url, ReferenceConfigBase<?> referenceConfig) {
            this.url = url;
            this.referenceConfig = referenceConfig;
            stub = GreeterGrpc.newStub(channel).build(channel, callOptions);
        }

        /**
         * <pre>
         *  Sends a greeting
         * </pre>
         */
        public io.reactivex.Single<io.grpc.examples.helloworld.HelloReply> sayHello(io.reactivex.Single<io.grpc.examples.helloworld.HelloRequest> rxRequest) {
            return com.salesforce.rxgrpc.stub.ClientCalls.oneToOne(rxRequest,
                    new com.salesforce.reactivegrpc.common.BiConsumer<io.grpc.examples.helloworld.HelloRequest, io.grpc.stub.StreamObserver<io.grpc.examples.helloworld.HelloReply>>() {
                        @java.lang.Override
                        public void accept(io.grpc.examples.helloworld.HelloRequest request, io.grpc.stub.StreamObserver<io.grpc.examples.helloworld.HelloReply> observer) {
                            stub.withDeadlineAfter(url.getParameter(TIMEOUT_KEY, DEFAULT_TIMEOUT), TimeUnit.MILLISECONDS).sayHello(request, observer);
                        }
                    });
        }

        /**
         * <pre>
         *  Sends a greeting
         * </pre>
         */
        public io.reactivex.Single<io.grpc.examples.helloworld.HelloReply> sayHello(io.grpc.examples.helloworld.HelloRequest rxRequest) {
            return com.salesforce.rxgrpc.stub.ClientCalls.oneToOne(io.reactivex.Single.just(rxRequest),
                    new com.salesforce.reactivegrpc.common.BiConsumer<io.grpc.examples.helloworld.HelloRequest, io.grpc.stub.StreamObserver<io.grpc.examples.helloworld.HelloReply>>() {
                        @java.lang.Override
                        public void accept(io.grpc.examples.helloworld.HelloRequest request, io.grpc.stub.StreamObserver<io.grpc.examples.helloworld.HelloReply> observer) {
                            stub.withDeadlineAfter(url.getParameter(TIMEOUT_KEY, DEFAULT_TIMEOUT), TimeUnit.MILLISECONDS).sayHello(request, observer);
                        }
                    });
        }

    }

    /**
     * <pre>
     *  The greeting service definition.
     * </pre>
     */
    public static abstract class GreeterImplBase implements IRxGreeter, io.grpc.BindableService {

        private IRxGreeter proxiedImpl;

        public final void setProxiedImpl(IRxGreeter proxiedImpl) {
            this.proxiedImpl = proxiedImpl;
        }

        /**
         * <pre>
         *  Sends a greeting
         * </pre>
         */
        public final io.reactivex.Single<io.grpc.examples.helloworld.HelloReply> sayHello(io.grpc.examples.helloworld.HelloRequest rxRequest) {
            throw new UnsupportedOperationException("No need to override this method, extend XxxImplBase and override all methods it allows.");
        }

        /**
         * <pre>
         *  Sends a greeting
         * </pre>
         */
        public io.reactivex.Single<io.grpc.examples.helloworld.HelloReply> sayHello(io.reactivex.Single<io.grpc.examples.helloworld.HelloRequest> request) {
            throw new io.grpc.StatusRuntimeException(io.grpc.Status.UNIMPLEMENTED);
        }

        @java.lang.Override
        public final io.grpc.ServerServiceDefinition bindService() {
            return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
                    .addMethod(
                            io.grpc.examples.helloworld.GreeterGrpc.getSayHelloMethod(),
                            asyncUnaryCall(
                                    new MethodHandlers<
                                            io.grpc.examples.helloworld.HelloRequest,
                                            io.grpc.examples.helloworld.HelloReply>(
                                            proxiedImpl, METHODID_SAY_HELLO)))
                    .build();
        }
    }

    private static final class MethodHandlers
            <Req, Resp> implements
            io.grpc.stub.ServerCalls.UnaryMethod
                    <Req, Resp>,
            io.grpc.stub.ServerCalls.ServerStreamingMethod
                    <Req, Resp>,
            io.grpc.stub.ServerCalls.ClientStreamingMethod
                    <Req, Resp>,
            io.grpc.stub.ServerCalls.BidiStreamingMethod
                    <Req, Resp> {
        private final IRxGreeter serviceImpl;
        private final int methodId;

        MethodHandlers(IRxGreeter serviceImpl, int methodId) {
            this.serviceImpl = serviceImpl;
            this.methodId = methodId;
        }

        @java.lang.Override
        @java.lang.SuppressWarnings("unchecked")
        public void invoke(Req request, io.grpc.stub.StreamObserver
                <Resp> responseObserver) {
            switch (methodId) {
                case METHODID_SAY_HELLO:
                    com.salesforce.rxgrpc.stub.ServerCalls.oneToOne((io.grpc.examples.helloworld.HelloRequest) request,
                            (io.grpc.stub.StreamObserver<io.grpc.examples.helloworld.HelloReply>) responseObserver,
                            new com.salesforce.reactivegrpc.common.Function
                                    <io.reactivex.Single
                                            <io.grpc.examples.helloworld.HelloRequest>, io.reactivex.Single<io.grpc.examples.helloworld.HelloReply>>() {
                                @java.lang.Override
                                public io.reactivex.Single<io.grpc.examples.helloworld.HelloReply> apply(io.reactivex.Single<io.grpc.examples.helloworld.HelloRequest> single) {
                                    return serviceImpl.sayHello(single);
                                }
                            });
                    break;
                default:
                    throw new java.lang.AssertionError();
            }
        }

        @java.lang.Override
        @java.lang.SuppressWarnings("unchecked")
        public io.grpc.stub.StreamObserver
                <Req> invoke(io.grpc.stub.StreamObserver
                                     <Resp> responseObserver) {
            switch (methodId) {
                default:
                    throw new java.lang.AssertionError();
            }
        }
    }

}
