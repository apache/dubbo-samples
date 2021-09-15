package org.apache.dubbo.sample.tri.grpc;

import org.apache.dubbo.sample.tri.TriSampleConstants;
import org.apache.dubbo.sample.tri.service.impl.GrpcPbGreeterImpl;
import org.apache.dubbo.sample.tri.service.impl.PbGreeterImpl;

import io.grpc.ForwardingServerCall;
import io.grpc.Metadata;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.ServerCall;
import io.grpc.ServerCallHandler;
import io.grpc.ServerInterceptor;
import io.grpc.ServerInterceptors;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class GrpcProvider {
    public static void main(String[] args) throws IOException, InterruptedException {
        final Server server = ServerBuilder.forPort(TriSampleConstants.GRPC_SERVER_PORT)
                .addService(ServerInterceptors.intercept(new GrpcPbGreeterImpl(new PbGreeterImpl()), new ServerInterceptor() {
                    @Override
                    public <ReqT, RespT> ServerCall.Listener<ReqT> interceptCall(ServerCall<ReqT, RespT> serverCall,
                                                                                 Metadata metadata, ServerCallHandler<ReqT, RespT> serverCallHandler) {
                        return serverCallHandler.startCall(new ForwardingServerCall.SimpleForwardingServerCall(serverCall) {
                            @Override
                            public void sendHeaders(Metadata headers) {
                                final String key = "user-attachment";
                                final Metadata.Key<String> metaKey = Metadata.Key.of(key, Metadata.ASCII_STRING_MARSHALLER);
                                if (metadata.containsKey(metaKey)) {
                                    headers.put(metaKey, metadata.get(metaKey));
                                }
                                super.sendHeaders(headers);
                            }
                        }, metadata);
                    }

                    ;
                }))
                .build();
        server.start();
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                // Start graceful shutdown
                server.shutdown();
                try {
                    // Wait for RPCs to complete processing
                    if (!server.awaitTermination(30, TimeUnit.SECONDS)) {
                        // That was plenty of time. Let's cancel the remaining RPCs
                        server.shutdownNow();
                        // shutdownNow isn't instantaneous, so give a bit of time to clean resources up
                        // gracefully. Normally this will be well under a second.
                        server.awaitTermination(5, TimeUnit.SECONDS);
                    }
                } catch (InterruptedException ex) {
                    server.shutdownNow();
                }
            }
        });
        // This would normally be tied to the service's dependencies. For example, if HostnameGreeter
        // used a Channel to contact a required service, then when 'channel.getState() ==
        // TRANSIENT_FAILURE' we'd want to set NOT_SERVING. But HostnameGreeter has no dependencies, so
        // hard-coding SERVING is appropriate.
        server.awaitTermination();
    }
}
