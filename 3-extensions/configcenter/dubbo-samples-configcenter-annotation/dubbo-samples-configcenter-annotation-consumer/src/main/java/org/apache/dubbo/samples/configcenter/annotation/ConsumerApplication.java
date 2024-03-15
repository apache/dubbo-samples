package org.apache.dubbo.samples.configcenter.annotation;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.apache.dubbo.samples.configcenter.annotation.action.AnnotationAction;
import org.springframework.beans.BeansException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

@SpringBootApplication
@EnableDubbo
public class ConsumerApplication implements ApplicationContextAware {

    private static ApplicationContext context;

    public static void main(String[] args) {
        SpringApplication.run(ConsumerApplication.class, args);
        AnnotationAction consumer = (AnnotationAction)context.getBean("annotationAction");
        System.out.println(consumer.doSayHello("zookeeper"));

        System.exit(0);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        context = applicationContext;
    }

}
