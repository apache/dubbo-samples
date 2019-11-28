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


@javax.annotation.Generated(
value = "by ReactorDubboGrpc generator",
comments = "Source: hello_streaming.proto")
public final class ReactorDubboStreamingGreeterGrpc {
private ReactorDubboStreamingGreeterGrpc() {}

public static ReactorDubboStreamingGreeterStub getDubboStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions, URL url, ReferenceConfigBase<?> referenceConfig) {
return new ReactorDubboStreamingGreeterStub(channel, callOptions, url, referenceConfig);
}

    /**
     * <pre>
     *  The greeting service definition.
     * </pre>
     */
public static final class ReactorDubboStreamingGreeterStub implements IReactorStreamingGreeter {

protected URL url;
protected ReferenceConfigBase<?> referenceConfig;

protected StreamingGreeterGrpc.StreamingGreeterStub stub;

public ReactorDubboStreamingGreeterStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions, URL url, ReferenceConfigBase<?> referenceConfig) {
this.url = url;
this.referenceConfig = referenceConfig;
stub = StreamingGreeterGrpc.newStub(channel).build(channel, callOptions);
}

        /**
         * <pre>
         *  Streams a many greetings
         * </pre>
         */
    public reactor.core.publisher.Flux<io.grpc.examples.manualflowcontrol.HelloReply> sayHelloStreaming(reactor.core.publisher.Flux<io.grpc.examples.manualflowcontrol.HelloRequest> reactorRequest) {
    StreamingGreeterGrpc.StreamingGreeterStub localStub = stub.withDeadlineAfter(url.getParameter(TIMEOUT_KEY, DEFAULT_TIMEOUT), TimeUnit.MILLISECONDS);
    return com.salesforce.reactorgrpc.stub.ClientCalls.manyToMany(reactorRequest, localStub::sayHelloStreaming);
    }

}

public interface IReactorStreamingGreeter {
        /**
         * <pre>
         *  Streams a many greetings
         * </pre>
         */
    public reactor.core.publisher.Flux<io.grpc.examples.manualflowcontrol.HelloReply> sayHelloStreaming(reactor.core.publisher.Flux<io.grpc.examples.manualflowcontrol.HelloRequest> reactorRequest);

}

    /**
     * <pre>
     *  The greeting service definition.
     * </pre>
     */
public static abstract class StreamingGreeterImplBase implements IReactorStreamingGreeter, io.grpc.BindableService {

private IReactorStreamingGreeter proxiedImpl;

public final void setProxiedImpl(IReactorStreamingGreeter proxiedImpl) {
this.proxiedImpl = proxiedImpl;
}

        /**
         * <pre>
         *  Streams a many greetings
         * </pre>
         */
    public reactor.core.publisher.Flux<io.grpc.examples.manualflowcontrol.HelloReply> sayHelloStreaming(reactor.core.publisher.Flux<io.grpc.examples.manualflowcontrol.HelloRequest> request) {
    throw new io.grpc.StatusRuntimeException(io.grpc.Status.UNIMPLEMENTED);
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
private final IReactorStreamingGreeter serviceImpl;
private final int methodId;

MethodHandlers(IReactorStreamingGreeter serviceImpl, int methodId) {
this.serviceImpl = serviceImpl;
this.methodId = methodId;
}

@java.lang.Override
@java.lang.SuppressWarnings("unchecked")
public void invoke(Req request, io.grpc.stub.StreamObserver
<Resp> responseObserver) {
    switch (methodId) {
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
                    case METHODID_SAY_HELLO_STREAMING:
                    return (io.grpc.stub.StreamObserver
                <Req>) com.salesforce.reactorgrpc.stub.ServerCalls.manyToMany(
                    (io.grpc.stub.StreamObserver<io.grpc.examples.manualflowcontrol.HelloReply>) responseObserver,
                    serviceImpl::sayHelloStreaming);
            default:
            throw new java.lang.AssertionError();
            }
            }
            }

            }
