package org.apache.dubbo;

import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.rpc.RpcContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

@Component
public class HiConsumer {
    private static final Logger logger = LoggerFactory.getLogger(HiConsumer.class);

    @DubboReference(async = true, check = false)
    private HiService hiService;


    public void asyncCallMethod1() {

        CompletableFuture<String> future = hiService.sayHelloAsync("Alice");

        future.whenComplete((result, error) -> {
            if (error != null) {
                logger.debug("failed: {}", error.getMessage());
            } else {
                logger.info("result: {}", result);
            }
        });

        future.thenApply(result -> "[PROCESSED] " + result.toUpperCase())
                .thenAccept(processed -> logger.info("result: {}", processed));

        try {
            String result = future.get(3000, TimeUnit.MILLISECONDS);
            logger.info("sync wait result: {}", result);
        } catch (Exception e) {
            logger.debug(e.getMessage());
        }
    }


    public void asyncCallMethod2() {

        String syncResult = hiService.sayHello("Bob");
        logger.info("sync return value (usually null, meaningless): {}", syncResult);

        CompletableFuture<Object> future = RpcContext.getServiceContext().getCompletableFuture();

        if (future != null) {
            future.whenComplete((result, error) -> {
                if (error != null) {
                    logger.error(error.getMessage());
                } else {
                    logger.info("async callback result: {}", result);
                }
            });

            future.thenApply(result -> "[PROCESSED] " + ((String) result).toUpperCase())
                    .thenAccept(processed -> logger.info("result: {}", processed));

            try {
                Object result = future.get(3000, TimeUnit.MILLISECONDS);
                logger.info("sync wait result: {}", result);
            } catch (Exception e) {
                logger.debug(e.getMessage());
            }
        }
    }


    public void compareDemo() {
        asyncCallMethod1();
        asyncCallMethod2();
    }

}
