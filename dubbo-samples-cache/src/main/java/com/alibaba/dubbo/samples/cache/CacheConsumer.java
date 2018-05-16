package com.alibaba.dubbo.samples.cache;

import com.alibaba.dubbo.samples.cache.api.CacheService;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * CacheConsumer
 */
public class CacheConsumer {

    public static void main(String[] args) throws Exception {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(new String[]{"META-INF/spring/cache-consumer.xml"});
        context.start();

        CacheService cacheService = (CacheService) context.getBean("cacheService");

        // verify cache, same result is returned for different invocations (in fact, the return value increases
        // on every invocation on the server side)
        String fix = null;
        for (int i = 0; i < 5; i++) {
            String result = cacheService.findCache("0");
            if (fix == null || fix.equals(result)) {
                System.out.println("OK: " + result);
            } else {
                System.err.println("ERROR: " + result);
            }
            fix = result;
            Thread.sleep(500);
        }

        // default cache.size is 1000 for LRU, should have cache expired if invoke more than 1001 times
        for (int n = 0; n < 1001; n++) {
            String pre = null;
            for (int i = 0; i < 10; i++) {
                String result = cacheService.findCache(String.valueOf(n));
                if (pre != null && !pre.equals(result)) {
                    System.err.println("ERROR: " + result);
                }
                pre = result;
            }
        }

        // verify if the first cache item is expired in LRU cache
        String result = cacheService.findCache("0");
        if (fix != null && !fix.equals(result)) {
            System.out.println("OK: " + result);
        } else {
            System.err.println("ERROR: " + result);
        }
    }

}
