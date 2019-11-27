package io.grpc.examples.routeguide;

import static io.grpc.examples.routeguide.RouteGuideGrpc.getServiceDescriptor;
import static io.grpc.stub.ServerCalls.asyncUnaryCall;
import static io.grpc.stub.ServerCalls.asyncServerStreamingCall;
import static io.grpc.stub.ServerCalls.asyncClientStreamingCall;
import static io.grpc.stub.ServerCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedStreamingCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall;

@javax.annotation.Generated(
value = "by DubboGrpc generator",
comments = "Source: route_guide.proto")
public final class DubboRouteGuideGrpc {
    private DubboRouteGuideGrpc() {}

    public static class DubboRouteGuideStub implements IRouteGuide {

        protected RouteGuideGrpc.RouteGuideBlockingStub blockingStub;
        protected RouteGuideGrpc.RouteGuideFutureStub futureStub;
        protected RouteGuideGrpc.RouteGuideStub stub;

        public DubboRouteGuideStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
           blockingStub = RouteGuideGrpc.newBlockingStub(channel).build(channel, callOptions);
           futureStub = RouteGuideGrpc.newFutureStub(channel).build(channel, callOptions);
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
        public io.grpc.examples.routeguide.Feature getFeature(io.grpc.examples.routeguide.Point request) {
            return blockingStub.getFeature(request);
        }

        public com.google.common.util.concurrent.ListenableFuture<io.grpc.examples.routeguide.Feature> getFeatureAsync(io.grpc.examples.routeguide.Point request) {
            return futureStub.getFeature(request);
        }

        public void getFeature(io.grpc.examples.routeguide.Point request, io.grpc.stub.StreamObserver<io.grpc.examples.routeguide.Feature> responseObserver){
            stub.getFeature(request, responseObserver);
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
        public java.util.Iterator<io.grpc.examples.routeguide.Feature> listFeatures(io.grpc.examples.routeguide.Rectangle request) {
            return blockingStub.listFeatures(request);
        }

        public void listFeatures(io.grpc.examples.routeguide.Rectangle request, io.grpc.stub.StreamObserver<io.grpc.examples.routeguide.Feature> responseObserver) {
            stub.listFeatures(request, responseObserver);
        }

        /**
         * <pre>
         *  A client-to-server streaming RPC.
         * 
         *  Accepts a stream of Points on a route being traversed, returning a
         *  RouteSummary when traversal is completed.
         * </pre>
         */
        public io.grpc.stub.StreamObserver<io.grpc.examples.routeguide.Point> recordRoute(io.grpc.stub.StreamObserver<io.grpc.examples.routeguide.RouteSummary> responseObserver) {
            return stub.recordRoute(responseObserver);
        }
        /**
         * <pre>
         *  A Bidirectional streaming RPC.
         * 
         *  Accepts a stream of RouteNotes sent while a route is being traversed,
         *  while receiving other RouteNotes (e.g. from other users).
         * </pre>
         */
        public io.grpc.stub.StreamObserver<io.grpc.examples.routeguide.RouteNote> routeChat(io.grpc.stub.StreamObserver<io.grpc.examples.routeguide.RouteNote> responseObserver) {
            return stub.routeChat(responseObserver);
        }
    }

    public static DubboRouteGuideStub getDubboStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new DubboRouteGuideStub(channel, callOptions);
    }

    public interface IRouteGuide {
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
        default public io.grpc.examples.routeguide.Feature getFeature(io.grpc.examples.routeguide.Point request) {
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
        default public com.google.common.util.concurrent.ListenableFuture<io.grpc.examples.routeguide.Feature> getFeatureAsync(io.grpc.examples.routeguide.Point request) {
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
        public void getFeature(io.grpc.examples.routeguide.Point request, io.grpc.stub.StreamObserver<io.grpc.examples.routeguide.Feature> responseObserver);

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
        default public java.util.Iterator<io.grpc.examples.routeguide.Feature> listFeatures(io.grpc.examples.routeguide.Rectangle request) {
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
        public void listFeatures(io.grpc.examples.routeguide.Rectangle request, io.grpc.stub.StreamObserver<io.grpc.examples.routeguide.Feature> responseObserver);

        /**
         * <pre>
         *  A client-to-server streaming RPC.
         * 
         *  Accepts a stream of Points on a route being traversed, returning a
         *  RouteSummary when traversal is completed.
         * </pre>
         */
        public io.grpc.stub.StreamObserver<io.grpc.examples.routeguide.Point> recordRoute(io.grpc.stub.StreamObserver<io.grpc.examples.routeguide.RouteSummary> responseObserver);

        /**
         * <pre>
         *  A Bidirectional streaming RPC.
         * 
         *  Accepts a stream of RouteNotes sent while a route is being traversed,
         *  while receiving other RouteNotes (e.g. from other users).
         * </pre>
         */
        public io.grpc.stub.StreamObserver<io.grpc.examples.routeguide.RouteNote> routeChat(io.grpc.stub.StreamObserver<io.grpc.examples.routeguide.RouteNote> responseObserver);

    }

    /**
     * <pre>
     *  Interface exported by the server.
     * </pre>
     */
    public static abstract class RouteGuideImplBase implements io.grpc.BindableService, IRouteGuide {

        private IRouteGuide proxiedImpl;

        public final void setProxiedImpl(IRouteGuide proxiedImpl) {
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
        @java.lang.Override
        public final io.grpc.examples.routeguide.Feature getFeature(io.grpc.examples.routeguide.Point request) {
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
        @java.lang.Override
        public final com.google.common.util.concurrent.ListenableFuture<io.grpc.examples.routeguide.Feature> getFeatureAsync(io.grpc.examples.routeguide.Point request) {
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
        @java.lang.Override
        public final java.util.Iterator<io.grpc.examples.routeguide.Feature> listFeatures(io.grpc.examples.routeguide.Rectangle request) {
           throw new UnsupportedOperationException("No need to override this method, extend XxxImplBase and override all methods it allows.");
        }

        public void getFeature(io.grpc.examples.routeguide.Point request,
            io.grpc.stub.StreamObserver<io.grpc.examples.routeguide.Feature> responseObserver) {
            asyncUnimplementedUnaryCall(io.grpc.examples.routeguide.RouteGuideGrpc.getGetFeatureMethod(), responseObserver);
        }
        public void listFeatures(io.grpc.examples.routeguide.Rectangle request,
            io.grpc.stub.StreamObserver<io.grpc.examples.routeguide.Feature> responseObserver) {
            asyncUnimplementedUnaryCall(io.grpc.examples.routeguide.RouteGuideGrpc.getListFeaturesMethod(), responseObserver);
        }
        public io.grpc.stub.StreamObserver<io.grpc.examples.routeguide.Point> recordRoute(
            io.grpc.stub.StreamObserver<io.grpc.examples.routeguide.RouteSummary> responseObserver) {
            return asyncUnimplementedStreamingCall(io.grpc.examples.routeguide.RouteGuideGrpc.getRecordRouteMethod(), responseObserver);
        }
        public io.grpc.stub.StreamObserver<io.grpc.examples.routeguide.RouteNote> routeChat(
            io.grpc.stub.StreamObserver<io.grpc.examples.routeguide.RouteNote> responseObserver) {
            return asyncUnimplementedStreamingCall(io.grpc.examples.routeguide.RouteGuideGrpc.getRouteChatMethod(), responseObserver);
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
                        asyncUnaryCall(
                          new MethodHandlers<
                             io.grpc.examples.routeguide.Rectangle,
                             io.grpc.examples.routeguide.Feature>(
                              proxiedImpl, METHODID_LIST_FEATURES)))
                      .addMethod(
                        io.grpc.examples.routeguide.RouteGuideGrpc.getRecordRouteMethod(),
                        asyncUnaryCall(
                          new MethodHandlers<
                             io.grpc.examples.routeguide.Point,
                             io.grpc.examples.routeguide.RouteSummary>(
                              proxiedImpl, METHODID_RECORD_ROUTE)))
                      .addMethod(
                        io.grpc.examples.routeguide.RouteGuideGrpc.getRouteChatMethod(),
                        asyncUnaryCall(
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

    private static final class MethodHandlers<Req, Resp> implements
            io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
            io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
            io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
            io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
        private final IRouteGuide serviceImpl;
        private final int methodId;

        MethodHandlers(IRouteGuide serviceImpl, int methodId) {
            this.serviceImpl = serviceImpl;
            this.methodId = methodId;
        }

        @java.lang.Override
        @java.lang.SuppressWarnings("unchecked")
        public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
            switch (methodId) {
                case METHODID_GET_FEATURE:
                    serviceImpl.getFeature((io.grpc.examples.routeguide.Point) request,
                        (io.grpc.stub.StreamObserver<io.grpc.examples.routeguide.Feature>) responseObserver);
                    break;
                case METHODID_LIST_FEATURES:
                    serviceImpl.listFeatures((io.grpc.examples.routeguide.Rectangle) request,
                        (io.grpc.stub.StreamObserver<io.grpc.examples.routeguide.Feature>) responseObserver);
                    break;
                default:
                    throw new java.lang.AssertionError();
            }
        }

        @java.lang.Override
        @java.lang.SuppressWarnings("unchecked")
        public io.grpc.stub.StreamObserver<Req> invoke(io.grpc.stub.StreamObserver<Resp> responseObserver) {
            switch (methodId) {
                case METHODID_RECORD_ROUTE:
                    return (io.grpc.stub.StreamObserver<Req>) serviceImpl.recordRoute(
                        (io.grpc.stub.StreamObserver<io.grpc.examples.routeguide.RouteSummary>) responseObserver);
                case METHODID_ROUTE_CHAT:
                    return (io.grpc.stub.StreamObserver<Req>) serviceImpl.routeChat(
                        (io.grpc.stub.StreamObserver<io.grpc.examples.routeguide.RouteNote>) responseObserver);
                default:
                    throw new java.lang.AssertionError();
            }
        }
    }

}
