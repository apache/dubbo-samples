package org.apache.dubbo.sample.tri;

import io.grpc.Server;
import io.grpc.ServerBuilder;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class GrpcProvider {
    public static void main(String[] args) throws IOException, InterruptedException {
        final Server server = ServerBuilder.forPort(50051)
                .addService(new GrpcPbGreeterImpl(new PbGreeterImpl()))
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
