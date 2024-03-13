package org.apache.dubbo.samples.tri.grpc.interop.client;

import org.apache.dubbo.common.stream.StreamObserver;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.samples.tri.grpc.Greeter;
import org.apache.dubbo.samples.tri.grpc.GreeterReply;
import org.apache.dubbo.samples.tri.grpc.GreeterRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Order(2)
@Component
public class TriOpTester implements ApplicationRunner {

    private static final Logger LOGGER = LoggerFactory.getLogger(TriOpTester.class);
    @DubboReference(interfaceClass = Greeter.class,url = "tri://localhost:8999", timeout = 15000)
    private Greeter greeter;
    @Override
    public void run(ApplicationArguments args) throws Exception {
        unary();
        biStream();
        serverStream();
        System.in.read();
    }
    public  void unary() {
        LOGGER.info("Start unary");
        final GreeterReply reply = greeter.greet(GreeterRequest.newBuilder()
                .setName("name")
                .build());
        LOGGER.info("Unary reply <-{}", reply);
    }

    private  void biStream() {
        StreamObserver<GreeterRequest> requestStreamObserver = greeter.biStream(new SampleStreamObserver());
        for (int i = 0; i < 10; i++) {
            GreeterRequest request = GreeterRequest.newBuilder().setName("name-" + i).build();
            requestStreamObserver.onNext(request);
        }
        requestStreamObserver.onCompleted();
    }

    private  void serverStream() {
        GreeterRequest request = GreeterRequest.newBuilder().setName("server stream request.").build();
        greeter.serverStream(request, new SampleStreamObserver());
    }

    private  class SampleStreamObserver implements StreamObserver<GreeterReply> {
        @Override
        public void onNext(GreeterReply data) {
            LOGGER.info("stream <- reply:{}", data);
        }

        @Override
        public void onError(Throwable throwable) {
            LOGGER.error("stream onError", throwable);
            throwable.printStackTrace();
        }
        @Override
        public void onCompleted() {
            LOGGER.info("stream completed");
        }
    }
}

