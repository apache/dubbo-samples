package org.apache.dubbo.sample.tri;

import com.google.protobuf.ByteString;
import grpc.testing.EmptyOuterClass;
import grpc.testing.Messages.BoolValue;
import grpc.testing.Messages.Payload;
import grpc.testing.Messages.ResponseParameters;
import grpc.testing.Messages.SimpleRequest;
import grpc.testing.Messages.SimpleResponse;
import grpc.testing.Messages.StreamingInputCallRequest;
import grpc.testing.Messages.StreamingInputCallResponse;
import grpc.testing.Messages.StreamingOutputCallRequest;
import grpc.testing.Messages.StreamingOutputCallResponse;
import grpc.testing.TestServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.internal.testing.StreamRecorder;
import io.grpc.stub.ClientCallStreamObserver;
import io.grpc.stub.StreamObserver;
import org.junit.Assume;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

/**
 * See <a href="https://grpc.github.io/grpc/cpp/md_doc_http2-interop-test-descriptions.html">https://grpc.github.io/grpc/cpp/md_doc_http2-interop-test-descriptions.html</a>
 */
public class GrpcInterOPTest {

    private static TestServiceGrpc.TestServiceStub asyncStub;
    private static TestServiceGrpc.TestServiceBlockingStub blockingStub;
    private static final EmptyOuterClass.Empty EMPTY = EmptyOuterClass.Empty.getDefaultInstance();

    @BeforeClass
    public static void init() {
        ManagedChannel channel = ManagedChannelBuilder
                .forAddress(TriSampleConstants.HOST, TriSampleConstants.SERVER_PORT)
                .usePlaintext()
                .build();
        asyncStub = TestServiceGrpc.newStub(channel);
        blockingStub = TestServiceGrpc.newBlockingStub(channel);
    }

    @Test
    public void emptyUnary() {
        assertEquals(EmptyOuterClass.Empty.getDefaultInstance(), blockingStub.emptyCall(EMPTY));
    }

    public void cacheableUnary() {
        //TODO
    }

    @Test
    public void largeUnary() {
        assumeEnoughMemory();
        final SimpleRequest request = SimpleRequest.newBuilder()
                .setResponseSize(314159)
                .setPayload(Payload.newBuilder()
                        .setBody(ByteString.copyFrom(new byte[271828])))
                .build();
        final SimpleResponse goldenResponse = SimpleResponse.newBuilder()
                .setPayload(Payload.newBuilder()
                        .setBody(ByteString.copyFrom(new byte[314159])))
                .build();
        assertResponse(goldenResponse, blockingStub.unaryCall(request));
    }

    /**
     * Tests client per-message compression for unary calls. The Java API does not support inspecting
     * a message's compression level, so this is primarily intended to run against a gRPC C++ server.
     */
    @Test
    public void clientCompressedUnary() {
        assumeEnoughMemory();
        final SimpleRequest expectCompressedRequest =
                SimpleRequest.newBuilder()
                        .setExpectCompressed(BoolValue.newBuilder().setValue(true))
                        .setResponseSize(314159)
                        .setPayload(Payload.newBuilder().setBody(ByteString.copyFrom(new byte[271828])))
                        .build();
        final SimpleRequest expectUncompressedRequest =
                SimpleRequest.newBuilder()
                        .setExpectCompressed(BoolValue.newBuilder().setValue(false))
                        .setResponseSize(314159)
                        .setPayload(Payload.newBuilder().setBody(ByteString.copyFrom(new byte[271828])))
                        .build();
        final SimpleResponse goldenResponse =
                SimpleResponse.newBuilder()
                        .setPayload(Payload.newBuilder().setBody(ByteString.copyFrom(new byte[314159])))
                        .build();

        assertResponse(goldenResponse, blockingStub.withCompression("gzip").unaryCall(expectCompressedRequest));

        assertResponse(goldenResponse, blockingStub.unaryCall(expectUncompressedRequest));

    }

    @Test
    public void serverCompressedUnary() {
        assumeEnoughMemory();
        final SimpleRequest responseShouldBeCompressed =
                SimpleRequest.newBuilder()
                        .setResponseCompressed(BoolValue.newBuilder().setValue(true))
                        .setResponseSize(314159)
                        .setPayload(Payload.newBuilder().setBody(ByteString.copyFrom(new byte[271828])))
                        .build();
        final SimpleRequest responseShouldBeUncompressed =
                SimpleRequest.newBuilder()
                        .setResponseCompressed(BoolValue.newBuilder().setValue(false))
                        .setResponseSize(314159)
                        .setPayload(Payload.newBuilder().setBody(ByteString.copyFrom(new byte[271828])))
                        .build();
        final SimpleResponse goldenResponse =
                SimpleResponse.newBuilder()
                        .setPayload(Payload.newBuilder().setBody(ByteString.copyFrom(new byte[314159])))
                        .build();

        assertPayload(goldenResponse.getPayload(), blockingStub.unaryCall(responseShouldBeCompressed).getPayload());

        assertPayload(goldenResponse.getPayload(), blockingStub.unaryCall(responseShouldBeUncompressed).getPayload());
    }

    @Test
    public void clientStreaming() throws Exception {
        final List<StreamingInputCallRequest> requests = Arrays.asList(
                StreamingInputCallRequest.newBuilder()
                        .setPayload(Payload.newBuilder()
                                .setBody(ByteString.copyFrom(new byte[27182])))
                        .build(),
                StreamingInputCallRequest.newBuilder()
                        .setPayload(Payload.newBuilder()
                                .setBody(ByteString.copyFrom(new byte[8])))
                        .build(),
                StreamingInputCallRequest.newBuilder()
                        .setPayload(Payload.newBuilder()
                                .setBody(ByteString.copyFrom(new byte[1828])))
                        .build(),
                StreamingInputCallRequest.newBuilder()
                        .setPayload(Payload.newBuilder()
                                .setBody(ByteString.copyFrom(new byte[45904])))
                        .build());
        final StreamingInputCallResponse goldenResponse = StreamingInputCallResponse.newBuilder()
                .setAggregatedPayloadSize(74922)
                .build();

        StreamRecorder<StreamingInputCallResponse> responseObserver = StreamRecorder.create();
        StreamObserver<StreamingInputCallRequest> requestObserver =
                asyncStub.streamingInputCall(responseObserver);
        for (StreamingInputCallRequest request : requests) {
            requestObserver.onNext(request);
        }
        requestObserver.onCompleted();

        assertEquals(goldenResponse, responseObserver.firstValue().get());
        responseObserver.awaitCompletion();
        assert responseObserver.getValues().size() == 1;
        Throwable t = responseObserver.getError();
        if (t != null) {
            throw new AssertionError(t);
        }
    }

    /**
     * Tests client per-message compression for streaming calls. The Java API does not support
     * inspecting a message's compression level, so this is primarily intended to run against a gRPC
     * C++ server.
     */
    @Test
    public void clientCompressedStreaming() throws Exception {
        final StreamingInputCallRequest expectCompressedRequest =
                StreamingInputCallRequest.newBuilder()
                        .setExpectCompressed(BoolValue.newBuilder().setValue(true))
                        .setPayload(Payload.newBuilder().setBody(ByteString.copyFrom(new byte[27182])))
                        .build();
        final StreamingInputCallRequest expectUncompressedRequest =
                StreamingInputCallRequest.newBuilder()
                        .setExpectCompressed(BoolValue.newBuilder().setValue(false))
                        .setPayload(Payload.newBuilder().setBody(ByteString.copyFrom(new byte[45904])))
                        .build();
        final StreamingInputCallResponse goldenResponse =
                StreamingInputCallResponse.newBuilder().setAggregatedPayloadSize(73086).build();

        // Start a new stream
        StreamRecorder<StreamingInputCallResponse> responseObserver = StreamRecorder.create();
        ClientCallStreamObserver<StreamingInputCallRequest> clientCallStreamObserver =
                (ClientCallStreamObserver<StreamingInputCallRequest>) asyncStub.withCompression("gzip").streamingInputCall(responseObserver);
        clientCallStreamObserver.setMessageCompression(true);
        clientCallStreamObserver.onNext(expectCompressedRequest);
        clientCallStreamObserver.setMessageCompression(false);
        clientCallStreamObserver.onNext(expectUncompressedRequest);
        clientCallStreamObserver.onCompleted();
        responseObserver.awaitCompletion();
        assertSuccess(responseObserver);
        assertEquals(goldenResponse, responseObserver.firstValue().get());
    }


    @Test
    public void serverStreaming() throws Exception {
        final StreamingOutputCallRequest request = StreamingOutputCallRequest.newBuilder()
                .addResponseParameters(ResponseParameters.newBuilder()
                        .setSize(31415))
                .addResponseParameters(ResponseParameters.newBuilder()
                        .setSize(9))
                .addResponseParameters(ResponseParameters.newBuilder()
                        .setSize(2653))
                .addResponseParameters(ResponseParameters.newBuilder()
                        .setSize(58979))
                .build();
        final List<StreamingOutputCallResponse> goldenResponses = Arrays.asList(
                StreamingOutputCallResponse.newBuilder()
                        .setPayload(Payload.newBuilder()
                                .setBody(ByteString.copyFrom(new byte[31415])))
                        .build(),
                StreamingOutputCallResponse.newBuilder()
                        .setPayload(Payload.newBuilder()
                                .setBody(ByteString.copyFrom(new byte[9])))
                        .build(),
                StreamingOutputCallResponse.newBuilder()
                        .setPayload(Payload.newBuilder()
                                .setBody(ByteString.copyFrom(new byte[2653])))
                        .build(),
                StreamingOutputCallResponse.newBuilder()
                        .setPayload(Payload.newBuilder()
                                .setBody(ByteString.copyFrom(new byte[58979])))
                        .build());

        StreamRecorder<StreamingOutputCallResponse> recorder = StreamRecorder.create();
        asyncStub.streamingOutputCall(request, recorder);
        recorder.awaitCompletion();
        assertSuccess(recorder);
        assertResponses(goldenResponses, recorder.getValues());
    }

    @Test
    public void serverCompressedStreaming() throws Exception {
        final StreamingOutputCallRequest request =
                StreamingOutputCallRequest.newBuilder()
                        .addResponseParameters(
                                ResponseParameters.newBuilder()
                                        .setCompressed(BoolValue.newBuilder().setValue(true))
                                        .setSize(31415))
                        .addResponseParameters(
                                ResponseParameters.newBuilder()
                                        .setCompressed(BoolValue.newBuilder().setValue(false))
                                        .setSize(92653))
                        .build();
        final List<StreamingOutputCallResponse> goldenResponses =
                Arrays.asList(
                        StreamingOutputCallResponse.newBuilder()
                                .setPayload(Payload.newBuilder().setBody(ByteString.copyFrom(new byte[31415])))
                                .build(),
                        StreamingOutputCallResponse.newBuilder()
                                .setPayload(Payload.newBuilder().setBody(ByteString.copyFrom(new byte[92653])))
                                .build());

        StreamRecorder<StreamingOutputCallResponse> recorder = StreamRecorder.create();
        asyncStub.streamingOutputCall(request, recorder);
        recorder.awaitCompletion();
        assertSuccess(recorder);
        assertResponses(goldenResponses, recorder.getValues());
    }

    @Test
    public void pingPong() throws Exception {
        final List<StreamingOutputCallRequest> requests = Arrays.asList(
                StreamingOutputCallRequest.newBuilder()
                        .addResponseParameters(ResponseParameters.newBuilder()
                                .setSize(31415))
                        .setPayload(Payload.newBuilder()
                                .setBody(ByteString.copyFrom(new byte[27182])))
                        .build(),
                StreamingOutputCallRequest.newBuilder()
                        .addResponseParameters(ResponseParameters.newBuilder()
                                .setSize(9))
                        .setPayload(Payload.newBuilder()
                                .setBody(ByteString.copyFrom(new byte[8])))
                        .build(),
                StreamingOutputCallRequest.newBuilder()
                        .addResponseParameters(ResponseParameters.newBuilder()
                                .setSize(2653))
                        .setPayload(Payload.newBuilder()
                                .setBody(ByteString.copyFrom(new byte[1828])))
                        .build(),
                StreamingOutputCallRequest.newBuilder()
                        .addResponseParameters(ResponseParameters.newBuilder()
                                .setSize(58979))
                        .setPayload(Payload.newBuilder()
                                .setBody(ByteString.copyFrom(new byte[45904])))
                        .build());
        final List<StreamingOutputCallResponse> goldenResponses = Arrays.asList(
                StreamingOutputCallResponse.newBuilder()
                        .setPayload(Payload.newBuilder()
                                .setBody(ByteString.copyFrom(new byte[31415])))
                        .build(),
                StreamingOutputCallResponse.newBuilder()
                        .setPayload(Payload.newBuilder()
                                .setBody(ByteString.copyFrom(new byte[9])))
                        .build(),
                StreamingOutputCallResponse.newBuilder()
                        .setPayload(Payload.newBuilder()
                                .setBody(ByteString.copyFrom(new byte[2653])))
                        .build(),
                StreamingOutputCallResponse.newBuilder()
                        .setPayload(Payload.newBuilder()
                                .setBody(ByteString.copyFrom(new byte[58979])))
                        .build());

        final ArrayBlockingQueue<Object> queue = new ArrayBlockingQueue<>(5);
        StreamObserver<StreamingOutputCallRequest> requestObserver
                = asyncStub.fullDuplexCall(new StreamObserver<StreamingOutputCallResponse>() {
            @Override
            public void onNext(StreamingOutputCallResponse response) {
                queue.add(response);
            }

            @Override
            public void onError(Throwable t) {
                queue.add(t);
            }

            @Override
            public void onCompleted() {
                queue.add("Completed");
            }
        });
        for (int i = 0; i < requests.size(); i++) {
            assertNull(queue.peek());
            requestObserver.onNext(requests.get(i));
            Object actualResponse = queue.poll(operationTimeoutMillis(), TimeUnit.MILLISECONDS);
            assertNotNull("Timed out waiting for response", actualResponse);
            if (actualResponse instanceof Throwable) {
                throw new AssertionError(actualResponse);
            }
            assertResponse(goldenResponses.get(i), (StreamingOutputCallResponse) actualResponse);
        }
        requestObserver.onCompleted();
        assertEquals("Completed", queue.poll(operationTimeoutMillis(), TimeUnit.MILLISECONDS));
    }

    @Test
    public void emptyStream() throws Exception {
        StreamRecorder<StreamingOutputCallResponse> responseObserver = StreamRecorder.create();
        StreamObserver<StreamingOutputCallRequest> requestObserver
                = asyncStub.fullDuplexCall(responseObserver);
        requestObserver.onCompleted();
        responseObserver.awaitCompletion(operationTimeoutMillis(), TimeUnit.MILLISECONDS);
        assertSuccess(responseObserver);
        assertTrue("Expected an empty stream", responseObserver.getValues().isEmpty());
    }

    protected static void assumeEnoughMemory() {
        Runtime r = Runtime.getRuntime();
        long usedMem = r.totalMemory() - r.freeMemory();
        long actuallyFreeMemory = r.maxMemory() - usedMem;
        Assume.assumeTrue(
                actuallyFreeMemory + " is not sufficient to run this test",
                actuallyFreeMemory >= 64 * 1024 * 1024);
    }

    private void assertPayload(Payload expected, Payload actual) {
        // Compare non deprecated fields in Payload, to make this test forward compatible.
        if (expected == null || actual == null) {
            assertEquals(expected, actual);
        } else {
            assertEquals(expected.getBody(), actual.getBody());
        }
    }

    protected int operationTimeoutMillis() {
        return 5000;
    }

    private void assertResponse(
            StreamingOutputCallResponse expected, StreamingOutputCallResponse actual) {
        if (expected == null || actual == null) {
            assertEquals(expected, actual);
        } else {
            assertPayload(expected.getPayload(), actual.getPayload());
        }
    }


    protected static void assertSuccess(StreamRecorder<?> recorder) {
        if (recorder.getError() != null) {
            throw new AssertionError(recorder.getError());
        }
    }

    // Helper methods for responses containing Payload since proto equals does not ignore deprecated
    // fields (PayloadType).
    private void assertResponses(
            Collection<StreamingOutputCallResponse> expected,
            Collection<StreamingOutputCallResponse> actual) {
        assertSame(expected.size(), actual.size());
        Iterator<StreamingOutputCallResponse> expectedIter = expected.iterator();
        Iterator<StreamingOutputCallResponse> actualIter = actual.iterator();

        while (expectedIter.hasNext()) {
            assertResponse(expectedIter.next(), actualIter.next());
        }
    }

    private void assertResponse(SimpleResponse expected, SimpleResponse actual) {
        assertPayload(expected.getPayload(), actual.getPayload());
        assertEquals(expected.getUsername(), actual.getUsername());
        assertEquals(expected.getOauthScope(), actual.getOauthScope());
    }
}
