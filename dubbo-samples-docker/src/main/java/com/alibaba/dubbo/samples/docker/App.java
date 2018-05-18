package com.alibaba.dubbo.samples.docker;

import java.io.IOException;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
@ImportResource("classpath:dubbo-docker-provider.xml")
public class App {

	public static void main(String[] args) throws IOException {
		new SpringApplicationBuilder(App.class).web(false).run(args);
		System.in.read();
	}
}
