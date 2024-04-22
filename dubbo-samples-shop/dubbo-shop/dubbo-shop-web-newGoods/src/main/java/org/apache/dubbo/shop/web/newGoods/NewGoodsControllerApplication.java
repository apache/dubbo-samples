package org.apache.dubbo.shop.web.newGoods;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableDubbo
public class NewGoodsControllerApplication {
    public static void main(String[] args){
        SpringApplication.run(NewGoodsControllerApplication.class,args);
    }
}
