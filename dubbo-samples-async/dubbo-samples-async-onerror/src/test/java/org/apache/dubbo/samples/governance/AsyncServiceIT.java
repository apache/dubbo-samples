package org.apache.dubbo.samples.governance;

import org.apache.dubbo.rpc.RpcContext;
import org.apache.dubbo.samples.governance.api.AsyncService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/spring/async-consumer.xml"})
public class AsyncServiceIT {

    @Autowired
    private AsyncService asyncService;

    @Test(expected = org.apache.dubbo.remoting.TimeoutException.class)
    public void testSayHelloTimeout() throws Throwable {
        try {
            asyncService.sayHelloTimeout("timeout world");
            CompletableFuture<String> helloFuture = RpcContext.getContext().getCompletableFuture();
            String result = helloFuture.get();
            System.out.println("result: "+result);
        } catch (Exception e) {
            e.printStackTrace();
            throw e.getCause();
        }
    }
}
