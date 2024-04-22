package org.apache.dubbo.shop.service.newGoods;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableDubbo
@MapperScan(basePackages = "org.apache.dubbo.shop.mapper")
public class NewGoodsServiceImplApplication {
    public static void main(String[] args) {
        SpringApplication.run(NewGoodsServiceImplApplication.class,args);
    }
}
