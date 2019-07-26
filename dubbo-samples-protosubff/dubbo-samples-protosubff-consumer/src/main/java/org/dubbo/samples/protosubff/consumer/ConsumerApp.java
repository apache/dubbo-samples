package org.dubbo.samples.protosubff.consumer;

import org.dubbo.samples.protosubff.patch.ProtosubffPatch;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ConsumerApp {
	
	public static void main(String[] args) {
		// 使用 protosubff时候 会出现心跳 问题 这是个小的解决方案
		ProtosubffPatch.patch();
		SpringApplication.run(ConsumerApp.class, args);
	}
}
