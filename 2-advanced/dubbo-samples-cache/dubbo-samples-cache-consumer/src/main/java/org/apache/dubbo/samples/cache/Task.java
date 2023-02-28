package org.apache.dubbo.samples.cache;

import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.samples.cache.api.CacheService;
import org.springframework.boot.CommandLineRunner;

public class Task implements CommandLineRunner {
    @DubboReference(cache = "true")
    CacheService cacheService;


    @Override
    public void run(String... args) {
        new Thread(() -> {
            String fix = null;
            cacheService.findCache("0");
            for (int i = 0; i < 5; i++) {
                String result = cacheService.findCache("0");
                if (fix == null || fix.equals(result)) {
                    System.out.println("OK: " + result);
                } else {
                    System.err.println("ERROR: " + result);
                }
                fix = result;
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }

            // default cache.size is 1000 for LRU, should have cache expired if invoke more than 1001 times
            for (int n = 0; n < 1001; n++) {
                String pre = null;
                cacheService.findCache(String.valueOf(n));
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
        });
    }
}

