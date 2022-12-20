package org.apache.dubbo.springboot.hibernate.samples;

import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author ouyangcan
 * @date 2022/12/18 23:56
 */
@Service
@EnableDubbo
@SpringBootApplication(exclude={DataSourceAutoConfiguration.class})
public class HibernateConsumerApplication {

    @DubboReference
    private HibernateService hibernateService;

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(HibernateConsumerApplication.class, args);
        HibernateConsumerApplication application = context.getBean(HibernateConsumerApplication.class);
        List<User> userList = application.findAll();
        System.out.println("result: " + userList);
    }

    public List<User> findAll() {
        return hibernateService.findAll();
    }
}




