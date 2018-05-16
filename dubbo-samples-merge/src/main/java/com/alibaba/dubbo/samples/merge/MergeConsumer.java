package com.alibaba.dubbo.samples.merge;

import java.util.List;

import com.alibaba.dubbo.samples.merge.api.MergeService;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * MergeConsumer
 */
public class MergeConsumer {

    public static void main(String[] args) throws Exception {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(new String[]{"META-INF/spring/merge-consumer.xml"});
        context.start();
        MergeService mergeService = (MergeService) context.getBean("mergeService");
        for (int i = 0; i < Integer.MAX_VALUE; i++) {
            try {
                List<String> result = mergeService.mergeResult();
                System.out.println("(" + i + ") " + result);
                Thread.sleep(1000);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
