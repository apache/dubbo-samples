package org.apache.dubbo.sample.tri;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.Metadata;
import io.grpc.stub.MetadataUtils;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

public class GrpcClient {
    private static PbGreeterGrpc.PbGreeterStub stub;

    @BeforeClass
    public static void init() {
        final ManagedChannel channel = ManagedChannelBuilder.forAddress("127.0.0.1", 50051)
                .usePlaintext()
                .build();
        stub = PbGreeterGrpc.newStub(channel);
    }

    @Test
    public void clientSendLargeSizeHeader() throws InterruptedException {
        final Metadata.Key<String> key = Metadata.Key.of("large_size", Metadata.ASCII_STRING_MARSHALLER);
        StringBuilder sb = new StringBuilder("a");
        for (int j = 0; j < 15; j++) {
            sb.append(sb);
        }
        Metadata meta = new Metadata();
        meta.put(key, sb.toString());
        final PbGreeterGrpc.PbGreeterStub stub = MetadataUtils.attachHeaders(GrpcClient.stub, meta);
        stub.greet(GreeterRequest.newBuilder().setName("metadata").build(),
                new GrpcStreamObserverAdapter<>(new StdoutStreamObserver<>("meta")));
        TimeUnit.SECONDS.sleep(1);
    }
}

