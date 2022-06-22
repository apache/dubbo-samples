package org.apache.dubbo.samples.action;

import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.samples.Greeter;
import org.apache.dubbo.samples.GreeterReply;
import org.apache.dubbo.samples.GreeterRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("annotatedConsumer")
public class GreetingServiceConsumer {

    private static final Logger LOGGER = LoggerFactory.getLogger(GreetingServiceConsumer.class);

    @DubboReference(version = "1.0.0", providedBy = "dubbo-samples-mesh-provider", url = "tri://dubbo-samples-mesh-provider:50052")
    private Greeter greeter;

    public void doSayHello(String name) {
        final GreeterReply reply = greeter.greet(GreeterRequest.newBuilder()
                .setName(name)
                .build());
        LOGGER.info("consumer Unary reply <-{}", reply);
    }

}
