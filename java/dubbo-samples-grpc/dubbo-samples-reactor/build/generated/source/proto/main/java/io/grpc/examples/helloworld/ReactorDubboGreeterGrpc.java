    package io.grpc.examples.helloworld;

import org.apache.dubbo.common.URL;
import org.apache.dubbo.config.ReferenceConfigBase;

import java.util.concurrent.TimeUnit;

import static org.apache.dubbo.common.constants.CommonConstants.DEFAULT_TIMEOUT;
import static org.apache.dubbo.common.constants.CommonConstants.TIMEOUT_KEY;

import static io.grpc.examples.helloworld.GreeterGrpc.getServiceDescriptor;
import static io.grpc.stub.ServerCalls.asyncUnaryCall;
import static io.grpc.stub.ServerCalls.asyncServerStreamingCall;
import static io.grpc.stub.ServerCalls.asyncClientStreamingCall;
import static io.grpc.stub.ServerCalls.asyncBidiStreamingCall;


@javax.annotation.Generated(
value = "by ReactorDubboGrpc generator",
comments = "Source: helloworld.proto")
public final class ReactorDubboGreeterGrpc {
private ReactorDubboGreeterGrpc() {}

public static ReactorDubboGreeterStub getDubboStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions, URL url, ReferenceConfigBase<?> referenceConfig) {
return new ReactorDubboGreeterStub(channel, callOptions, url, referenceConfig);
}

    /**
     * <pre>
     *  The greeting service definition.
     * </pre>
     */
public static final class ReactorDubboGreeterStub implements IReactorGreeter {

protected URL url;
protected ReferenceConfigBase<?> referenceConfig;

protected GreeterGrpc.GreeterStub stub;

public ReactorDubboGreeterStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions, URL url, ReferenceConfigBase<?> referenceConfig) {
this.url = url;
this.referenceConfig = referenceConfig;
stub = GreeterGrpc.newStub(channel).build(channel, callOptions);
}

        /**
         * <pre>
         *  Sends a greeting
         * </pre>
         */
    public reactor.core.publisher.Mono<io.grpc.examples.helloworld.HelloReply> sayHello(reactor.core.publisher.Mono<io.grpc.examples.helloworld.HelloRequest> reactorRequest) {
    GreeterGrpc.GreeterStub localStub = stub.withDeadlineAfter(url.getParameter(TIMEOUT_KEY, DEFAULT_TIMEOUT), TimeUnit.MILLISECONDS);
    return com.salesforce.reactorgrpc.stub.ClientCalls.oneToOne(reactorRequest, localStub::sayHello);
    }

        /**
         * <pre>
         *  Sends a greeting
         * </pre>
         */
    public reactor.core.publisher.Mono<io.grpc.examples.helloworld.HelloReply> sayHello(io.grpc.examples.helloworld.HelloRequest reactorRequest) {
    GreeterGrpc.GreeterStub localStub = stub.withDeadlineAfter(url.getParameter(TIMEOUT_KEY, DEFAULT_TIMEOUT), TimeUnit.MILLISECONDS);
    return com.salesforce.reactorgrpc.stub.ClientCalls.oneToOne(reactor.core.publisher.Mono.just(reactorRequest), localStub::sayHello);
    }

}

public interface IReactorGreeter {
        /**
         * <pre>
         *  Sends a greeting
         * </pre>
         */
    public reactor.core.publisher.Mono<io.grpc.examples.helloworld.HelloReply> sayHello(reactor.core.publisher.Mono<io.grpc.examples.helloworld.HelloRequest> reactorRequest);

        /**
         * <pre>
         *  Sends a greeting
         * </pre>
         */
    public reactor.core.publisher.Mono<io.grpc.examples.helloworld.HelloReply> sayHello(io.grpc.examples.helloworld.HelloRequest reactorRequest);

}

    /**
     * <pre>
     *  The greeting service definition.
     * </pre>
     */
public static abstract class GreeterImplBase implements IReactorGreeter, io.grpc.BindableService {

private IReactorGreeter proxiedImpl;

public final void setProxiedImpl(IReactorGreeter proxiedImpl) {
this.proxiedImpl = proxiedImpl;
}

        /**
         * <pre>
         *  Sends a greeting
         * </pre>
         */
    public final reactor.core.publisher.Mono<io.grpc.examples.helloworld.HelloReply> sayHello(io.grpc.examples.helloworld.HelloRequest reactorRequest) {
    throw new UnsupportedOperationException("No need to override this method, extend XxxImplBase and override all methods it allows.");
    }

        /**
         * <pre>
         *  Sends a greeting
         * </pre>
         */
    public reactor.core.publisher.Mono<io.grpc.examples.helloworld.HelloReply> sayHello(reactor.core.publisher.Mono<io.grpc.examples.helloworld.HelloRequest> request) {
    throw new io.grpc.StatusRuntimeException(io.grpc.Status.UNIMPLEMENTED);
    }

@java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
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

    private static final int METHODID_SAY_HELLO = 0;

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
private final IReactorGreeter serviceImpl;
private final int methodId;

MethodHandlers(IReactorGreeter serviceImpl, int methodId) {
this.serviceImpl = serviceImpl;
this.methodId = methodId;
}

@java.lang.Override
@java.lang.SuppressWarnings("unchecked")
public void invoke(Req request, io.grpc.stub.StreamObserver
<Resp> responseObserver) {
    switch (methodId) {
            case METHODID_SAY_HELLO:
            com.salesforce.reactorgrpc.stub.ServerCalls.oneToOne((io.grpc.examples.helloworld.HelloRequest) request,
            (io.grpc.stub.StreamObserver<io.grpc.examples.helloworld.HelloReply>) responseObserver,
            serviceImpl::sayHello);
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
