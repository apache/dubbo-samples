    package io.grpc.examples.routeguide;

import org.apache.dubbo.common.URL;
import org.apache.dubbo.config.ReferenceConfigBase;

import java.util.concurrent.TimeUnit;

import static org.apache.dubbo.common.constants.CommonConstants.DEFAULT_TIMEOUT;
import static org.apache.dubbo.common.constants.CommonConstants.TIMEOUT_KEY;

import static io.grpc.examples.routeguide.RouteGuideGrpc.getServiceDescriptor;
import static io.grpc.stub.ServerCalls.asyncUnaryCall;
import static io.grpc.stub.ServerCalls.asyncServerStreamingCall;
import static io.grpc.stub.ServerCalls.asyncClientStreamingCall;
import static io.grpc.stub.ServerCalls.asyncBidiStreamingCall;


@javax.annotation.Generated(
value = "by ReactorDubboGrpc generator",
comments = "Source: route_guide.proto")
public final class ReactorDubboRouteGuideGrpc {
private ReactorDubboRouteGuideGrpc() {}

public static ReactorDubboRouteGuideStub getDubboStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions, URL url, ReferenceConfigBase<?> referenceConfig) {
return new ReactorDubboRouteGuideStub(channel, callOptions, url, referenceConfig);
}

    /**
     * <pre>
     *  Interface exported by the server.
     * </pre>
     */
public static final class ReactorDubboRouteGuideStub implements IReactorRouteGuide {

protected URL url;
protected ReferenceConfigBase<?> referenceConfig;

protected RouteGuideGrpc.RouteGuideStub stub;

public ReactorDubboRouteGuideStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions, URL url, ReferenceConfigBase<?> referenceConfig) {
this.url = url;
this.referenceConfig = referenceConfig;
stub = RouteGuideGrpc.newStub(channel).build(channel, callOptions);
}

        /**
         * <pre>
         *  A simple RPC.
         * 
         *  Obtains the feature at a given position.
         * 
         *  A feature with an empty name is returned if there&#39;s no feature at the given
         *  position.
         * </pre>
         */
    public reactor.core.publisher.Mono<io.grpc.examples.routeguide.Feature> getFeature(reactor.core.publisher.Mono<io.grpc.examples.routeguide.Point> reactorRequest) {
    RouteGuideGrpc.RouteGuideStub localStub = stub.withDeadlineAfter(url.getParameter(TIMEOUT_KEY, DEFAULT_TIMEOUT), TimeUnit.MILLISECONDS);
    return com.salesforce.reactorgrpc.stub.ClientCalls.oneToOne(reactorRequest, localStub::getFeature);
    }

        /**
         * <pre>
         *  A server-to-client streaming RPC.
         * 
         *  Obtains the Features available within the given Rectangle.  Results are
         *  streamed rather than returned at once (e.g. in a response message with a
         *  repeated field), as the rectangle may cover a large area and contain a
         *  huge number of features.
         * </pre>
         */
    public reactor.core.publisher.Flux<io.grpc.examples.routeguide.Feature> listFeatures(reactor.core.publisher.Mono<io.grpc.examples.routeguide.Rectangle> reactorRequest) {
    RouteGuideGrpc.RouteGuideStub localStub = stub.withDeadlineAfter(url.getParameter(TIMEOUT_KEY, DEFAULT_TIMEOUT), TimeUnit.MILLISECONDS);
    return com.salesforce.reactorgrpc.stub.ClientCalls.oneToMany(reactorRequest, localStub::listFeatures);
    }

        /**
         * <pre>
         *  A client-to-server streaming RPC.
         * 
         *  Accepts a stream of Points on a route being traversed, returning a
         *  RouteSummary when traversal is completed.
         * </pre>
         */
    public reactor.core.publisher.Mono<io.grpc.examples.routeguide.RouteSummary> recordRoute(reactor.core.publisher.Flux<io.grpc.examples.routeguide.Point> reactorRequest) {
    RouteGuideGrpc.RouteGuideStub localStub = stub.withDeadlineAfter(url.getParameter(TIMEOUT_KEY, DEFAULT_TIMEOUT), TimeUnit.MILLISECONDS);
    return com.salesforce.reactorgrpc.stub.ClientCalls.manyToOne(reactorRequest, localStub::recordRoute);
    }

        /**
         * <pre>
         *  A Bidirectional streaming RPC.
         * 
         *  Accepts a stream of RouteNotes sent while a route is being traversed,
         *  while receiving other RouteNotes (e.g. from other users).
         * </pre>
         */
    public reactor.core.publisher.Flux<io.grpc.examples.routeguide.RouteNote> routeChat(reactor.core.publisher.Flux<io.grpc.examples.routeguide.RouteNote> reactorRequest) {
    RouteGuideGrpc.RouteGuideStub localStub = stub.withDeadlineAfter(url.getParameter(TIMEOUT_KEY, DEFAULT_TIMEOUT), TimeUnit.MILLISECONDS);
    return com.salesforce.reactorgrpc.stub.ClientCalls.manyToMany(reactorRequest, localStub::routeChat);
    }

        /**
         * <pre>
         *  A simple RPC.
         * 
         *  Obtains the feature at a given position.
         * 
         *  A feature with an empty name is returned if there&#39;s no feature at the given
         *  position.
         * </pre>
         */
    public reactor.core.publisher.Mono<io.grpc.examples.routeguide.Feature> getFeature(io.grpc.examples.routeguide.Point reactorRequest) {
    RouteGuideGrpc.RouteGuideStub localStub = stub.withDeadlineAfter(url.getParameter(TIMEOUT_KEY, DEFAULT_TIMEOUT), TimeUnit.MILLISECONDS);
    return com.salesforce.reactorgrpc.stub.ClientCalls.oneToOne(reactor.core.publisher.Mono.just(reactorRequest), localStub::getFeature);
    }

        /**
         * <pre>
         *  A server-to-client streaming RPC.
         * 
         *  Obtains the Features available within the given Rectangle.  Results are
         *  streamed rather than returned at once (e.g. in a response message with a
         *  repeated field), as the rectangle may cover a large area and contain a
         *  huge number of features.
         * </pre>
         */
    public reactor.core.publisher.Flux<io.grpc.examples.routeguide.Feature> listFeatures(io.grpc.examples.routeguide.Rectangle reactorRequest) {
    RouteGuideGrpc.RouteGuideStub localStub = stub.withDeadlineAfter(url.getParameter(TIMEOUT_KEY, DEFAULT_TIMEOUT), TimeUnit.MILLISECONDS);
    return com.salesforce.reactorgrpc.stub.ClientCalls.oneToMany(reactor.core.publisher.Mono.just(reactorRequest), localStub::listFeatures);
    }

}

public interface IReactorRouteGuide {
        /**
         * <pre>
         *  A simple RPC.
         * 
         *  Obtains the feature at a given position.
         * 
         *  A feature with an empty name is returned if there&#39;s no feature at the given
         *  position.
         * </pre>
         */
    public reactor.core.publisher.Mono<io.grpc.examples.routeguide.Feature> getFeature(reactor.core.publisher.Mono<io.grpc.examples.routeguide.Point> reactorRequest);

        /**
         * <pre>
         *  A server-to-client streaming RPC.
         * 
         *  Obtains the Features available within the given Rectangle.  Results are
         *  streamed rather than returned at once (e.g. in a response message with a
         *  repeated field), as the rectangle may cover a large area and contain a
         *  huge number of features.
         * </pre>
         */
    public reactor.core.publisher.Flux<io.grpc.examples.routeguide.Feature> listFeatures(reactor.core.publisher.Mono<io.grpc.examples.routeguide.Rectangle> reactorRequest);

        /**
         * <pre>
         *  A client-to-server streaming RPC.
         * 
         *  Accepts a stream of Points on a route being traversed, returning a
         *  RouteSummary when traversal is completed.
         * </pre>
         */
    public reactor.core.publisher.Mono<io.grpc.examples.routeguide.RouteSummary> recordRoute(reactor.core.publisher.Flux<io.grpc.examples.routeguide.Point> reactorRequest);

        /**
         * <pre>
         *  A Bidirectional streaming RPC.
         * 
         *  Accepts a stream of RouteNotes sent while a route is being traversed,
         *  while receiving other RouteNotes (e.g. from other users).
         * </pre>
         */
    public reactor.core.publisher.Flux<io.grpc.examples.routeguide.RouteNote> routeChat(reactor.core.publisher.Flux<io.grpc.examples.routeguide.RouteNote> reactorRequest);

        /**
         * <pre>
         *  A simple RPC.
         * 
         *  Obtains the feature at a given position.
         * 
         *  A feature with an empty name is returned if there&#39;s no feature at the given
         *  position.
         * </pre>
         */
    public reactor.core.publisher.Mono<io.grpc.examples.routeguide.Feature> getFeature(io.grpc.examples.routeguide.Point reactorRequest);

        /**
         * <pre>
         *  A server-to-client streaming RPC.
         * 
         *  Obtains the Features available within the given Rectangle.  Results are
         *  streamed rather than returned at once (e.g. in a response message with a
         *  repeated field), as the rectangle may cover a large area and contain a
         *  huge number of features.
         * </pre>
         */
    public reactor.core.publisher.Flux<io.grpc.examples.routeguide.Feature> listFeatures(io.grpc.examples.routeguide.Rectangle reactorRequest);

}

    /**
     * <pre>
     *  Interface exported by the server.
     * </pre>
     */
public static abstract class RouteGuideImplBase implements IReactorRouteGuide, io.grpc.BindableService {

private IReactorRouteGuide proxiedImpl;

public final void setProxiedImpl(IReactorRouteGuide proxiedImpl) {
this.proxiedImpl = proxiedImpl;
}

        /**
         * <pre>
         *  A simple RPC.
         * 
         *  Obtains the feature at a given position.
         * 
         *  A feature with an empty name is returned if there&#39;s no feature at the given
         *  position.
         * </pre>
         */
    public final reactor.core.publisher.Mono<io.grpc.examples.routeguide.Feature> getFeature(io.grpc.examples.routeguide.Point reactorRequest) {
    throw new UnsupportedOperationException("No need to override this method, extend XxxImplBase and override all methods it allows.");
    }

        /**
         * <pre>
         *  A server-to-client streaming RPC.
         * 
         *  Obtains the Features available within the given Rectangle.  Results are
         *  streamed rather than returned at once (e.g. in a response message with a
         *  repeated field), as the rectangle may cover a large area and contain a
         *  huge number of features.
         * </pre>
         */
    public final reactor.core.publisher.Flux<io.grpc.examples.routeguide.Feature> listFeatures(io.grpc.examples.routeguide.Rectangle reactorRequest) {
    throw new UnsupportedOperationException("No need to override this method, extend XxxImplBase and override all methods it allows.");
    }

        /**
         * <pre>
         *  A simple RPC.
         * 
         *  Obtains the feature at a given position.
         * 
         *  A feature with an empty name is returned if there&#39;s no feature at the given
         *  position.
         * </pre>
         */
    public reactor.core.publisher.Mono<io.grpc.examples.routeguide.Feature> getFeature(reactor.core.publisher.Mono<io.grpc.examples.routeguide.Point> request) {
    throw new io.grpc.StatusRuntimeException(io.grpc.Status.UNIMPLEMENTED);
    }

        /**
         * <pre>
         *  A server-to-client streaming RPC.
         * 
         *  Obtains the Features available within the given Rectangle.  Results are
         *  streamed rather than returned at once (e.g. in a response message with a
         *  repeated field), as the rectangle may cover a large area and contain a
         *  huge number of features.
         * </pre>
         */
    public reactor.core.publisher.Flux<io.grpc.examples.routeguide.Feature> listFeatures(reactor.core.publisher.Mono<io.grpc.examples.routeguide.Rectangle> request) {
    throw new io.grpc.StatusRuntimeException(io.grpc.Status.UNIMPLEMENTED);
    }

        /**
         * <pre>
         *  A client-to-server streaming RPC.
         * 
         *  Accepts a stream of Points on a route being traversed, returning a
         *  RouteSummary when traversal is completed.
         * </pre>
         */
    public reactor.core.publisher.Mono<io.grpc.examples.routeguide.RouteSummary> recordRoute(reactor.core.publisher.Flux<io.grpc.examples.routeguide.Point> request) {
    throw new io.grpc.StatusRuntimeException(io.grpc.Status.UNIMPLEMENTED);
    }

        /**
         * <pre>
         *  A Bidirectional streaming RPC.
         * 
         *  Accepts a stream of RouteNotes sent while a route is being traversed,
         *  while receiving other RouteNotes (e.g. from other users).
         * </pre>
         */
    public reactor.core.publisher.Flux<io.grpc.examples.routeguide.RouteNote> routeChat(reactor.core.publisher.Flux<io.grpc.examples.routeguide.RouteNote> request) {
    throw new io.grpc.StatusRuntimeException(io.grpc.Status.UNIMPLEMENTED);
    }

@java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
    .addMethod(
    io.grpc.examples.routeguide.RouteGuideGrpc.getGetFeatureMethod(),
    asyncUnaryCall(
    new MethodHandlers<
    io.grpc.examples.routeguide.Point,
    io.grpc.examples.routeguide.Feature>(
    proxiedImpl, METHODID_GET_FEATURE)))
    .addMethod(
    io.grpc.examples.routeguide.RouteGuideGrpc.getListFeaturesMethod(),
    asyncServerStreamingCall(
    new MethodHandlers<
    io.grpc.examples.routeguide.Rectangle,
    io.grpc.examples.routeguide.Feature>(
    proxiedImpl, METHODID_LIST_FEATURES)))
    .addMethod(
    io.grpc.examples.routeguide.RouteGuideGrpc.getRecordRouteMethod(),
    asyncClientStreamingCall(
    new MethodHandlers<
    io.grpc.examples.routeguide.Point,
    io.grpc.examples.routeguide.RouteSummary>(
    proxiedImpl, METHODID_RECORD_ROUTE)))
    .addMethod(
    io.grpc.examples.routeguide.RouteGuideGrpc.getRouteChatMethod(),
    asyncBidiStreamingCall(
    new MethodHandlers<
    io.grpc.examples.routeguide.RouteNote,
    io.grpc.examples.routeguide.RouteNote>(
    proxiedImpl, METHODID_ROUTE_CHAT)))
.build();
}
}

    private static final int METHODID_GET_FEATURE = 0;
    private static final int METHODID_LIST_FEATURES = 1;
    private static final int METHODID_RECORD_ROUTE = 2;
    private static final int METHODID_ROUTE_CHAT = 3;

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
private final IReactorRouteGuide serviceImpl;
private final int methodId;

MethodHandlers(IReactorRouteGuide serviceImpl, int methodId) {
this.serviceImpl = serviceImpl;
this.methodId = methodId;
}

@java.lang.Override
@java.lang.SuppressWarnings("unchecked")
public void invoke(Req request, io.grpc.stub.StreamObserver
<Resp> responseObserver) {
    switch (methodId) {
            case METHODID_GET_FEATURE:
            com.salesforce.reactorgrpc.stub.ServerCalls.oneToOne((io.grpc.examples.routeguide.Point) request,
            (io.grpc.stub.StreamObserver<io.grpc.examples.routeguide.Feature>) responseObserver,
            serviceImpl::getFeature);
            break;
            case METHODID_LIST_FEATURES:
            com.salesforce.reactorgrpc.stub.ServerCalls.oneToMany((io.grpc.examples.routeguide.Rectangle) request,
            (io.grpc.stub.StreamObserver<io.grpc.examples.routeguide.Feature>) responseObserver,
            serviceImpl::listFeatures);
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
                    case METHODID_RECORD_ROUTE:
                    return (io.grpc.stub.StreamObserver
                <Req>) com.salesforce.reactorgrpc.stub.ServerCalls.manyToOne(
                    (io.grpc.stub.StreamObserver<io.grpc.examples.routeguide.RouteSummary>) responseObserver,
                    serviceImpl::recordRoute);
                    case METHODID_ROUTE_CHAT:
                    return (io.grpc.stub.StreamObserver
                <Req>) com.salesforce.reactorgrpc.stub.ServerCalls.manyToMany(
                    (io.grpc.stub.StreamObserver<io.grpc.examples.routeguide.RouteNote>) responseObserver,
                    serviceImpl::routeChat);
            default:
            throw new java.lang.AssertionError();
            }
            }
            }

            }
