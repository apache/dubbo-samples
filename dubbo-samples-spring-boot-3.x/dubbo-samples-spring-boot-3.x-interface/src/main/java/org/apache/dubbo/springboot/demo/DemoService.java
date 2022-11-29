package org.apache.dubbo.springboot.demo;

import java.util.concurrent.CompletableFuture;

/**
 *
 * @author <a href="changningbo1995@gmail.com">James</a>
 * @since 2022/11/12
 */
public interface DemoService {

    String sayHello(String name);

    default CompletableFuture<String> sayHelloAsync(String name) {
        return CompletableFuture.completedFuture(sayHello(name));
    }
}
