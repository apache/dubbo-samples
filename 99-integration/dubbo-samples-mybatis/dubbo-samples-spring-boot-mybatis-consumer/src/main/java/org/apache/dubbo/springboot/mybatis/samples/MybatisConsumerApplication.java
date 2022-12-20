package org.apache.dubbo.springboot.mybatis.samples;

import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Service;

/**
 * @author ouyangcan
 * @date 2022/12/18 23:56
 */
@Service
@EnableDubbo
@SpringBootApplication(exclude={DataSourceAutoConfiguration.class})
public class MybatisConsumerApplication {

    @DubboReference(version = "1.0.0")
    private MybatisService mybatisService;

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(MybatisConsumerApplication.class, args);
        MybatisConsumerApplication application = context.getBean(MybatisConsumerApplication.class);
        User user = application.findByUserId(1L);
        System.out.println("result: " + user);
    }

    public User findByUserId(Long userId) {
        return mybatisService.findByUserId(userId);
    }
}




