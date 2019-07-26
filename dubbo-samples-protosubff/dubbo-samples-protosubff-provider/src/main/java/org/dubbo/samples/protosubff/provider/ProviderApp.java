package org.dubbo.samples.protosubff.provider;

import org.dubbo.samples.protosubff.patch.ProtosubffPatch;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;

@EnableAutoConfiguration
public class ProviderApp {
	
	public static void main(String[] args) {
		// 使用 protosubff时候 会出现心跳 问题 这是个小的解决方案
		ProtosubffPatch.patch();
		new SpringApplicationBuilder(ProviderApp.class).web(WebApplicationType.NONE).run(args);
	}
}
